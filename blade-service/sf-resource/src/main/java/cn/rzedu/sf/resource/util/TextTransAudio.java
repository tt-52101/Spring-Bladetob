package cn.rzedu.sf.resource.util;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class TextTransAudio {
	//设置APPID/AK/SK
	public static final String APP_ID = "23542473";
	public static final String API_KEY = "u5wnuysuiNa2as4RFWqrGoDS";
	public static final String SECRET_KEY = "eQu0FxqwkRtDGRmtNy9PoLoe1CsiyptU";

	public static void trans(String text, String fileName, String path){
		// 初始化一个AipSpeech
		AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		// 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//		client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//		client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

		// 可选：设置log4j日志输出格式，若不设置，则使用默认配置
		// 也可以直接通过jvm启动参数设置此环境变量
//		System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

		// 调用接口
		TtsResponse res = client.synthesis(text, "zh", 1, null);
		System.out.println(res);
		byte[] data = res.getData();
		JSONObject res1 = res.getResult();
		if (data != null) {
			try {
				Util.writeBytesToFileSystem(data, path + File.separator + fileName + ".mp3");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (res1 != null) {
			System.out.println(res1.toString(2));
		}
	}
}
