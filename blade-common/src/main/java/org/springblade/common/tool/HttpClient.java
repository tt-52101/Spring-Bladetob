package org.springblade.common.tool;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClient {

	private final static org.slf4j.Logger log = LoggerFactory.getLogger(HttpClient.class);
	
	public static JSONObject httpGet(String url, Map<String, Object> param) throws Exception {
		JSONObject result = null;
		try {
			StringBuilder paramStr = new StringBuilder();
			int index = 0;

			/** 构建GET请求参数格式 */
			Iterator<Entry<String, Object>> its = param.entrySet().iterator();
			while (its.hasNext()) {
				Entry<String, Object> entry = its.next();
				paramStr.append(entry.getKey());
				paramStr.append("=");
				paramStr.append(entry.getValue());
				if (param.size() - index != 1) {
					paramStr.append("&");
					index++;
				}
			}

			url = url + "?" + paramStr.toString();
			url = URLDecoder.decode(url, "UTF-8");

			SSLClient httpClient = new SSLClient();
			HttpGet request = new HttpGet(url);

			/**请求发送成功，并得到响应 */
			HttpResponse response = httpClient.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/**读取服务器返回过来的json字符串数据 */
				String strResult = EntityUtils.toString(response.getEntity(), "UTF-8");
				/**把json字符串转换成json对象 */
				result = JSONObject.parseObject(strResult);
				if(result.containsKey("errcode")) {
					System.out.println("调用接口出错： " + result.toString());
					int errcode = result.getInteger("errcode");
					if(errcode-42001==0) {
						log.error("重新获取token");
					}
				}
			} else {
				log.error("get请求提交失败:" + url);
			}

		} catch (IOException e) {
			log.error("get请求提交失败:" + url + e);
		}
		return result;
	}

	public static String httpGet(String url, Map<String, Object> param, String headerName, String headerVal) throws Exception {
		String strResult = null;
		try {
			StringBuilder paramStr = new StringBuilder();
			int index = 0;

			/** 构建GET请求参数格式 */
			Iterator<Entry<String, Object>> its = param.entrySet().iterator();
			while (its.hasNext()) {
				Entry<String, Object> entry = its.next();
				paramStr.append(entry.getKey());
				paramStr.append("=");
				paramStr.append(entry.getValue());
				if (param.size() - index != 1) {
					paramStr.append("&");
					index++;
				}
			}

			url = url + "?" + paramStr.toString();
			url = URLDecoder.decode(url, "UTF-8");

			SSLClient httpClient = new SSLClient();
			HttpGet request = new HttpGet(url);
			request.setHeader(headerName, headerVal);

			/**请求发送成功，并得到响应 */
			HttpResponse response = httpClient.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				/**读取服务器返回过来的json字符串数据 */
				strResult = EntityUtils.toString(response.getEntity(), "UTF-8");
				/**把json字符串转换成json对象 */
//				result = JSONObject.fromObject(strResult);

				return strResult;
			} else {
				log.error("get请求提交失败:" + url);
			}

		} catch (IOException e) {
			log.error("get请求提交失败:" + url + e);
		}
		return strResult;
	}

	public static JSONObject httpPost(String url, JSONObject param, boolean noNeedResponse) throws Exception {
		/**获取httpClient*/
		SSLClient httpClient = new SSLClient();
		JSONObject result = null;
		/**Post请求*/
		HttpPost method = new HttpPost(url);
		
		try {
			if (null != param) {
				/**设置http请求相关属性*/
				StringEntity entity = new StringEntity(param.toString(), "UTF-8");
				//entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
			
			System.out.println("所请求的url: " + url);
			System.out.println("请求的param: " + param.toString());
			
			/**发送请求并获取返回值*/
			HttpResponse response = httpClient.execute(method);
			
			/**请求发送成功，并得到响应 */
			if (response.getStatusLine().getStatusCode() == 200) {
				try {
					/**读取服务器返回过来的json字符串数据 */
					String str = EntityUtils.toString(response.getEntity());
					if (!noNeedResponse) {
						result = JSONObject.parseObject(str);
						return result;
					}
					
					/**把json字符串转换成json对象 */
					result = JSONObject.parseObject(str);
					
					System.out.println("result: " + result.toString());
					
					if(result.containsKey("errcode")) {
						System.out.println("调用接口出错： " + result.toString());
						int errcode = result.getInteger("errcode");
						if(errcode-42001==0) {
							System.out.println("重新获取token");
						}
					}
					
				} catch (Exception e) {
					log.error("post请求提交失败:" + url + e);
				}
			} else {
				log.error("响应失败 code :" + response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			log.error("post请求提交失败:" + url + e);
		}
		return result;
	}

	
	
	
	
	
	public static JSONObject httpPostJson(String url, net.sf.json.JSONObject param, boolean noNeedResponse) throws Exception {
		/**获取httpClient*/
		SSLClient httpClient = new SSLClient();
		JSONObject result = null;
		/**Post请求*/
		HttpPost method = new HttpPost(url);
		
		try {
			if (null != param) {
				/**设置http请求相关属性*/
				StringEntity entity = new StringEntity(param.toString(), "UTF-8");
				//entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
			
			System.out.println("所请求的url: " + url);
			System.out.println("请求的param: " + param.toString());
			
			/**发送请求并获取返回值*/
			HttpResponse response = httpClient.execute(method);
			
			/**请求发送成功，并得到响应 */
			if (response.getStatusLine().getStatusCode() == 200) {
				try {
					/**读取服务器返回过来的json字符串数据 */
					String str = EntityUtils.toString(response.getEntity());
					if (!noNeedResponse) {
						result = JSONObject.parseObject(str);
						return result;
					}
					
					/**把json字符串转换成json对象 */
					result = JSONObject.parseObject(str);
					
					System.out.println("result: " + result.toString());
					
					if(result.containsKey("errcode")) {
						System.out.println("调用接口出错： " + result.toString());
						int errcode = result.getInteger("errcode");
						if(errcode-42001==0) {
							System.out.println("重新获取token");
						}
					}
					
				} catch (Exception e) {
					log.error("post请求提交失败:" + url + e);
				}
			} else {
				log.error("响应失败 code :" + response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			log.error("post请求提交失败:" + url + e);
		}
		return result;
	}
	
	

	public static HttpEntity httpPostToEntity(String url, JSONObject param, boolean noNeedResponse) throws Exception {
		/**获取httpClient*/
		SSLClient httpClient = new SSLClient();
		JSONObject result = null;
		/**Post请求*/
		HttpPost method = new HttpPost(url);

		try {
			if (null != param) {
				/**设置http请求相关属性*/
				StringEntity entity = new StringEntity(param.toString(), "UTF-8");
				//entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}

			/**发送请求并获取返回值*/
			HttpResponse response = httpClient.execute(method);

			/**请求发送成功，并得到响应 */
			if (response.getStatusLine().getStatusCode() == 200) {
				try {
					/**读取服务器返回过来的json字符串数据 */

					HttpEntity entity = response.getEntity();
					return entity;


				} catch (Exception e) {
					log.error("post请求提交失败:" + url + e);
				}
			} else {
				log.error("响应失败 code :" + response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			log.error("post请求提交失败:" + url + e);
		}
		return null;
	}

	//由于这类方法经常被用到，因此建议写在一个工具类里面，设置为静态方法，方便调用。
	//url表示请求链接，param表示json格式的请求参数
	public static String sendPost(String url, Object param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性 注意Authorization生成
			// conn.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println(result);
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	//由于这类方法经常被用到，因此建议写在一个工具类里面，设置为静态方法，方便调用。
	//requestUrl表示请求链接
	public static String sendGet(String requestUrl) {
		StringBuffer buffer = null;

		try {
			// 建立连接
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");

			// 获取输入流
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			// 读取返回结果
			buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/* 发送 post请求 用HTTPclient 发送请求*/
	public static byte[] sendPostQRCode(String URL, String json) {
		String obj = null;
		InputStream inputStream = null;
		Buffer reader = null;
		byte[] data = null;
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(URL);
		httppost.addHeader("Content-type", "application/json; charset=utf-8");
		httppost.setHeader("Accept", "application/json");
		try {
			StringEntity s = new StringEntity(json, Charset.forName("UTF-8"));
			s.setContentEncoding("UTF-8");
			httppost.setEntity(s);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				// 获取相应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					inputStream = entity.getContent();
					data = readInputStream(inputStream);
				}
				return data;
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	/**  将流 保存为数据数组
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
}