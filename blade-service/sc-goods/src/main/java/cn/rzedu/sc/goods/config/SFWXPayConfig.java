package cn.rzedu.sc.goods.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class SFWXPayConfig implements WXPayConfig {

    /** 微信分配的公众号ID */
    private static String APP_ID;

    /** 微信支付分配的商户号 */
    private static String MCH_ID;

    /** 商户平台设置的密钥key */
    private static String KEY;

    /** 接收微信支付结果通知的回调地址 */
    private static String NOTIFY_URL;

    @Value("${wechat.config.appId}")
    public void setAPP_ID(String appId) {
        SFWXPayConfig.APP_ID = appId;
    }

    @Value("${wechat.config.pay.mchId}")
    public void setMCH_ID(String mchId) {
        SFWXPayConfig.MCH_ID = mchId;
    }

    @Value("${wechat.config.pay.key}")
    public void setKEY(String key) {
        SFWXPayConfig.KEY = key;
    }

    @Value("${wechat.config.pay.notifyUrl}")
    public void setNOTIFY_URL(String notifyUrl) {
        SFWXPayConfig.NOTIFY_URL = notifyUrl;
    }

    public String getNotifyUrl() {
        return NOTIFY_URL;
    }

    private byte[] certData;

    public SFWXPayConfig() throws Exception {
	       /* String certPath = "";
	        File file = new File(certPath);
	        InputStream certStream = new FileInputStream(file);
	        this.certData = new byte[(int) file.length()];
	        certStream.read(this.certData);
	        certStream.close();*/
    }


    @Override
    public String getAppID() {
        return APP_ID;
    }

    @Override
    public String getMchID() {
        return MCH_ID;
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
