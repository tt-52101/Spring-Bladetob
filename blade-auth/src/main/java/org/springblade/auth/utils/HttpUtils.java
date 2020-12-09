package org.springblade.auth.utils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import net.sf.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    /**
     * 请求超时时间
     */
    private static final int TIME_OUT = 120000;

    /**
     * Https请求
     */
    private static final String HTTPS = "https";

    /**
     * Content-Type
     */
    private static final String CONTENT_TYPE = "Content-Type";

    /**
     * 表单提交方式Content-Type
     */
    private static final String FORM_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";

    /**
     * JSON提交方式Content-Type
     */
    private static final String JSON_TYPE = "application/json;charset=UTF-8";

    /**
     * 发送Get请求
     *
     * @param url 请求URL
     * @return HTTP响应对象
     * @throws IOException 程序异常时抛出，由调用者处理
     */
    public static Connection.Response get(String url) throws IOException {
        return get(url, null);
    }

    /**
     * 发送Get请求
     *
     * @param url 请求URL
     * @param headers 请求头参数
     * @return HTTP响应对象
     * @throws IOException 程序异常时抛出，由调用者处理
     */
    public static Connection.Response get(String url, Map<String, String> headers) throws IOException {
        if (null == url || url.isEmpty()) {
            throw new RuntimeException("The request URL is blank.");
        }

        // 如果是Https请求
        if (url.startsWith(HTTPS)) {
            getTrust();
        }
        Connection connection = Jsoup.connect(url);
        connection.method(Connection.Method.GET);
        connection.timeout(TIME_OUT);
        connection.ignoreHttpErrors(true);
        connection.ignoreContentType(true);
        connection.maxBodySize(0);

        if (null != headers) {
            connection.headers(headers);
        }

        Connection.Response response = connection.execute();
        return response;
    }

    /**
     * 发送JSON格式参数POST请求
     *
     * @param url 请求路径
     * @param params JSON格式请求参数
     * @return HTTP响应对象
     * @throws IOException 程序异常时抛出，由调用者处理
     */
    public static Connection.Response post(String url, String params) throws IOException {
        return doPostRequest(url, null, params);
    }

    /**
     * 发送JSON格式参数POST请求
     *
     * @param url 请求路径
     * @param headers 请求头中设置的参数
     * @param params JSON格式请求参数
     * @return HTTP响应对象
     * @throws IOException 程序异常时抛出，由调用者处理
     */
    public static Connection.Response post(String url, Map<String, String> headers, String params) throws IOException {
        return doPostRequest(url, headers, params);
    }

    /**
     * 字符串参数post请求
     *
     * @param url 请求URL地址
     * @param paramMap 请求字符串参数集合
     * @return HTTP响应对象
     * @throws IOException 程序异常时抛出，由调用者处理
     */
    public static Connection.Response post(String url, Map<String, String> paramMap) throws IOException {
        return doPostRequest(url, null, paramMap, null);
    }

    /**
     * 带请求头的普通表单提交方式post请求
     *
     * @param headers 请求头参数
     * @param url 请求URL地址
     * @param paramMap 请求字符串参数集合
     * @return HTTP响应对象
     * @throws IOException 程序异常时抛出，由调用者处理
     */
    public static Connection.Response post(Map<String, String> headers, String url, Map<String, String> paramMap) throws IOException {
        return doPostRequest(url, headers, paramMap, null);
    }

    /**
     * 带上传文件的post请求
     *
     * @param url 请求URL地址
     * @param paramMap 请求字符串参数集合
     * @param fileMap 请求文件参数集合
     * @return HTTP响应对象
     * @throws IOException 程序异常时抛出，由调用者处理
     */
    public static Connection.Response post(String url, Map<String, String> paramMap, Map<String, File> fileMap) throws IOException {
        return doPostRequest(url, null, paramMap, fileMap);
    }

    /**
     * 带请求头的上传文件post请求
     *
     * @param url 请求URL地址
     * @param headers 请求头参数
     * @param paramMap 请求字符串参数集合
     * @param fileMap 请求文件参数集合
     * @return HTTP响应对象
     * @throws IOException 程序异常时抛出，由调用者处理
     */
    public static Connection.Response post(String url, Map<String, String> headers, Map<String, String> paramMap, Map<String, File> fileMap) throws IOException {
        return doPostRequest(url, headers, paramMap, fileMap);
    }

    /**
     * 执行post请求
     *
     * @param url 请求URL地址
     * @param headers 请求头
     * @param jsonParams 请求JSON格式字符串参数
     * @return HTTP响应对象
     * @throws IOException 程序异常时抛出，由调用者处理
     */
    private static Connection.Response doPostRequest(String url, Map<String, String> headers, String jsonParams) throws IOException {
        if (null == url || url.isEmpty()) {
            throw new RuntimeException("The request URL is blank.");
        }

        // 如果是Https请求
        if (url.startsWith(HTTPS)) {
            getTrust();
        }

        Connection connection = Jsoup.connect(url);
        connection.method(Connection.Method.POST);
        connection.timeout(TIME_OUT);
        connection.ignoreHttpErrors(true);
        connection.ignoreContentType(true);
        connection.maxBodySize(0);

        if (null != headers) {
            connection.headers(headers);
        }

        connection.header(CONTENT_TYPE, JSON_TYPE);
        connection.requestBody(jsonParams);

        Connection.Response response = connection.execute();
        return response;
    }

    /**
     * 普通表单方式发送POST请求
     *
     * @param url 请求URL地址
     * @param headers 请求头
     * @param paramMap 请求字符串参数集合
     * @param fileMap 请求文件参数集合
     * @return HTTP响应对象
     * @throws IOException 程序异常时抛出，由调用者处理
     */
    private static Connection.Response doPostRequest(String url, Map<String, String> headers, Map<String, String> paramMap, Map<String, File> fileMap) throws IOException {
        if (null == url || url.isEmpty()) {
            throw new RuntimeException("The request URL is blank.");
        }

        // 如果是Https请求
        if (url.startsWith(HTTPS)) {
            getTrust();
        }

        Connection connection = Jsoup.connect(url);
        connection.method(Connection.Method.POST);
        connection.timeout(TIME_OUT);
        connection.ignoreHttpErrors(true);
        connection.ignoreContentType(true);
        connection.maxBodySize(0);

        if (null != headers) {
            connection.headers(headers);
        }

        // 收集上传文件输入流，最终全部关闭
        List<InputStream> inputStreamList = null;
        try {

            // 添加文件参数
            if (null != fileMap && !fileMap.isEmpty()) {
                inputStreamList = new ArrayList<InputStream>();
                InputStream in = null;
                File file = null;
                for (Map.Entry<String, File> e : fileMap.entrySet()) {
                    file = e.getValue();
                    in = new FileInputStream(file);
                    inputStreamList.add(in);
                    connection.data(e.getKey(), file.getName(), in);
                }
            }

            // 普通表单提交方式
            else {
                connection.header(CONTENT_TYPE, FORM_TYPE);
            }

            // 添加字符串类参数
            if (null != paramMap && !paramMap.isEmpty()) {
                connection.data(paramMap);
            }

            Connection.Response response = connection.execute();
            return response;
        }

        // 关闭上传文件的输入流
        finally {
            closeStream(inputStreamList);
        }
    }

    /**
     * 关流
     *
     * @param streamList 流集合
     */
    private static void closeStream(List<? extends Closeable> streamList) {
        if (null != streamList) {
            for (Closeable stream : streamList) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取服务器信任
     */
    private static void getTrust() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    /**
     * 发送https请求，返回二维码图片
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param data 提交的数据
     * @param savePath 图片保存路径
     * @param fileName 图片名称
     * @param fileType 图片类型
     */
    public static void httpsRequestPicture(String requestUrl, String requestMethod, String data, String savePath, String fileName, String fileType) {
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            //设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.connect();
            //当data不为null时向输出流写数据
            if (null != data) {
                //getOutputStream方法隐藏了connect()方法
                OutputStream outputStream = conn.getOutputStream();
                //注意编码格式
                outputStream.write(data.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            inputStream = conn.getInputStream();
            //logger.info("开始生成微信二维码...");
            inputStreamToMedia(inputStream, savePath, fileName, fileType);
           // logger.info("微信二维码生成成功!!!");
            conn.disconnect();
        } catch (Exception e) {
            //logger.error("发送https请求失败，失败", e);
        }finally {
            //释放资源
            try {
                if(null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
              //  logger.error("释放资源失败，失败", e);
            }
        }
    }
    
    
    /**
     * 将输入流转换为图片
     * @param input 输入流
     * @param savePath 图片需要保存的路径
     * @param fileType 图片类型
     */
    public static void inputStreamToMedia(InputStream input, String savePath, String fileName, String fileType) throws Exception {
        String filePath = savePath + "/" + fileName + "." + fileType;
        File file = new File(filePath);
        FileOutputStream outputStream = new FileOutputStream(file);
        int length;
        byte[] data = new byte[1024];
        while ((length = input.read(data)) != -1) {
            outputStream.write(data, 0, length);
        }
        outputStream.flush();
        outputStream.close();
    }

    
    /**
     * 发送https请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param data 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String data) {
        JSONObject jsonObject = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.connect();
            // 当data不为null时向输出流写数据
            if (null != data) {
                // getOutputStream方法隐藏了connect()方法
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(data.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            inputStream = conn.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
            return jsonObject;
        } catch (Exception e) {
            //logger.error("发送https请求失败，失败", e);
            return null;
        } finally {
            // 释放资源
            try {
                if(null != inputStream) {
                    inputStream.close();
                }
                if(null != inputStreamReader) {
                    inputStreamReader.close();
                }
                if(null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                //logger.error("释放资源失败，失败", e);
            }
        }
    }

    
}
