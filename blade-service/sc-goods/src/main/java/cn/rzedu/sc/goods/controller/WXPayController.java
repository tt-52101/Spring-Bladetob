package cn.rzedu.sc.goods.controller;

import cn.rzedu.sc.goods.config.SFWXPayConfig;
import cn.rzedu.sc.goods.service.IOrderService;
import cn.rzedu.sc.goods.utils.OrderCodeUtil;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springblade.core.tool.api.R;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付 控制器
 *
 * @author Blade
 * @since 2020-10-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wxpay")
@Api(value = "微信支付", tags = "微信支付")
public class WXPayController {

    private final static Logger logger = LoggerFactory.getLogger(WXPayController.class);

    private IOrderService orderService;

    /**
     * 返回成功xml
     */
    private static String resSuccessXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code>" +
            "<return_msg><![CDATA[OK]]></return_msg></xml>";

    /**
     * 返回失败xml
     */
    private static String resFailXml = "<xml><return_code><![CDATA[FAIL]]></return_code>" +
            "<return_msg><![CDATA[报文为空]]></return_msg></xml>";


    /**
     * 统一下单
     * @param openId
     * @param orderCode
     * @param name
     * @param price
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/unifiedOrder")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "统一下单", notes = "统一下单")
    public Map<String, String> WXPayUnifiedOrder(
            @ApiParam(value = "用户openId") @RequestParam("openId") String openId,
            @ApiParam(value = "订单号") @RequestParam("orderCode")  String orderCode,
            @ApiParam(value = "商品名，格式：商家名称-销售商品类目") @RequestParam("name")  String name,
            @ApiParam(value = "订单总金额，单位为分") @RequestParam("price")  Integer price,
            HttpServletRequest request
    ) throws Exception {
        SFWXPayConfig config = new SFWXPayConfig();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        //商品描述，格式：商家名称-销售商品类目
        data.put("body", name);
        //标价金额
        data.put("total_fee", price.toString());
        //商户订单号
        data.put("out_trade_no", orderCode);
        //终端IP，用户的客户端IP
        data.put("spbill_create_ip", request.getRemoteAddr());
        //通知地址，异步接收微信支付结果通知的回调地址，通知url不能携带参数，调用支付成功后的接口，用于修改订单状态
        data.put("notify_url", config.getNotifyUrl());
        //交易类型
        data.put("trade_type", "JSAPI");
        //微信用户在商户对应appid下的唯一标识，trade_type=JSAPI时必传
        data.put("openid", openId);
        try {
            //统一下单接口
            Map<String, String> resp = wxpay.unifiedOrder(data);
            System.out.println(resp);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * H5调用，生成签名
     * @param prepayId
     * @return
     * @throws Exception
     */
    @PostMapping("/js/generateSign")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "H5调用，生成签名", notes = "生成签名")
    public Map<String, String> generateSign(
            @ApiParam(value = "预支付会话标识，由统一下单接口返回") @RequestParam("prepayId")  String prepayId
    ) throws Exception {
        //参与签名的参数为:appId、timeStamp、nonceStr、package、signType
        SFWXPayConfig config = new SFWXPayConfig();
        Map<String, String> data = new HashMap<String, String>(6);
        data.put("appId", config.getAppID());
        data.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000));
        data.put("nonceStr", WXPayUtil.generateNonceStr());
        data.put("package", "prepay_id=" + prepayId);
        data.put("signType", "MD5");
        data.put("paySign", WXPayUtil.generateSignature(data, config.getKey()));
        return data;
    }


    /**
     * 微信支付回调接口
     *
     * 特别提醒：
     * 1、商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。
     * 2、当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
     * 在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
     * @return
     */
    @PostMapping("/pay/notify")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "微信支付回调接口", notes = "通过【统一下单API】中提交的参数notify_url设置，如果链接无法访问，商户将无法接收到微信通知")
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        String resXml = "";
        InputStream inStream;
        try {
            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }

            // 获取微信调用我们notify_url的返回信息
            String result = new String(outSteam.toByteArray(), "utf-8");
            System.out.println("wxnotify:微信支付----result----=" + result);

            // 关闭流
            outSteam.close();
            inStream.close();

            // xml转换为map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(result);
            boolean isSuccess = false;
            if (WXPayConstants.SUCCESS.equalsIgnoreCase(resultMap.get("result_code"))) {
                //return_code和result_code都为SUCCESS是表示支付成功
                System.out.println("wxnotify:微信支付----返回成功");

                //签名验证
                SFWXPayConfig config = new SFWXPayConfig();
                if (WXPayUtil.isSignatureValid(resultMap, config.getKey())) {
                    System.out.println("wxnotify:微信支付----验证签名成功");
                    resXml = resSuccessXml;
                    isSuccess = true;
                } else {
                    System.out.println("wxnotify:微信支付----判断签名错误");
                }

            } else {
                System.out.println("wxnotify:支付失败,错误信息：" + resultMap.get("return_msg"));
                resXml = resFailXml;
            }

            //验证成功回调方法，处理业务 - 修改订单状态
            if (isSuccess) {
                //订单号
                String orderCode = resultMap.get("out_trade_no");
                Integer total_fee = Integer.valueOf(resultMap.get("total_fee"));
                System.out.println("wxnotify:微信支付回调：修改的订单===>" + orderCode);
                R updateResult = orderService.payNotify(orderCode, total_fee);
                System.out.println("订单修改结果==>" + updateResult);
            }

        } catch (Exception e) {
            logger.info("wxnotify:支付回调发布异常：", e);
        } finally {
//            try {
//                // 处理业务完毕
//                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
//                out.write(resXml.getBytes());
//                out.flush();
//                out.close();
//            } catch (IOException e) {
//               logger.info("wxnotify:支付回调发布异常:out：", e);
//            }
        }
        return resXml;
    }

    /**
     * 订单查询
     *
     * @return
     * @throws Exception
     */
    @PostMapping("trackOrder")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "订单查询", notes = "订单查询")
    public Map<String, String> trackOrder(@RequestParam("orderCode") String orderCode)
            throws Exception {
        SFWXPayConfig config = new SFWXPayConfig();
        WXPay wxpay = new WXPay(config);
        Map<String, String> data = new HashMap<String, String>();
        data.put("out_trade_no", orderCode);
        try {
            Map<String, String> resp = wxpay.orderQuery(data);
            System.out.println(resp);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}


