package cn.rzedu.sf.resource.util;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 获取文件MD5码工具
 *
 * @author panweiliang
 */
public class FileMd5Util {

	/**
	 * 默认的密码字符串组合，apache校验下载的文件的正确性用的就是默认的这个组合
	 */
	protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	protected static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {

		}
	}

	/**
	 * 通过文件路径,直接获取文件MD5码
	 *
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String getFileMd5String(String filePath)
		throws FileNotFoundException, IOException {
		InputStream fis;
		fis = new FileInputStream(filePath);
		return getFileMd5String(fis);
	}

	/**
	 * 获取文件MD5码
	 *
	 * @param file
	 * @return 文件MD5码
	 * @throws IOException
	 */
	public static String getFileMd5String(File file) throws IOException {
		return getFileMd5String(new FileInputStream(file));
	}

	/**
	 * 通过输入流获得MD5码
	 *
	 * @param fis
	 * @return 文件MD5码
	 * @throws IOException
	 */
	public static String getFileMd5String(InputStream fis) throws IOException {

		byte[] buffer = new byte[2048];
		int length = -1;
		while ((length = fis.read(buffer)) != -1) {
			messagedigest.update(buffer, 0, length);
		}
		return bufferToHex(messagedigest.digest());
	}

	public static String getFileMd5String(InputStream fis, Integer pre) throws IOException {
		Integer maxByte = pre * 1024 * 1024;
		Integer readedByte = 0;
		byte[] buffer = new byte[2048];
		int length = -1;
		try {
			while ((length = fis.read(buffer)) != -1) {
				readedByte += 2048;
				if (readedByte <= maxByte) {
					messagedigest.update(buffer, 0, length);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			throw new IOException();
		} finally {
			if (fis != null) {
				fis.close();
				fis = null;
			}
		}
		return bufferToHex(messagedigest.digest());
	}

	public static String getFileMd5(InputStream fis, Integer pre) throws IOException {
		Integer maxByte = pre * 1024 * 1024;
		Integer readedByte = 0;
		byte[] buffer = new byte[2048];
		int length = -1;
		try {
			while ((length = fis.read(buffer)) != -1) {
				readedByte += 2048;
				if (readedByte <= maxByte) {
					messagedigest.update(buffer, 0, length);
				} else {
					break;
				}
			}
		} catch (IOException e) {
			throw new IOException();
		}
		return bufferToHex(messagedigest.digest());
	}

	/**
	 * 获取字符串MD5码
	 *
	 * @param s
	 * @return 字符串MD5码
	 */
	public static String getMd5String(String s) {
		return getMd5String(s.getBytes());
	}

	/**
	 * 获取字节MD5
	 *
	 * @param bytes
	 * @return 字节MD5码
	 */
	public static String getMd5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static void main(String[] args) throws IOException, Exception {
		FileInputStream fis = new FileInputStream("C:\\Users\\80969\\Desktop\\b.jpg");
		String md5 = getFileMd5String(fis, 5);
		System.out.println(md5);
	}
}
