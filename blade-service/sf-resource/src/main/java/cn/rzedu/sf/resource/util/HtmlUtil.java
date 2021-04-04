package cn.rzedu.sf.resource.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\script>"; // 定义script的正则表达式
	private static final String regEx_style = "<style [^>]*?>[\\s\\S]*?<\\/style >"; // 定义style的正则表达式
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符
	private static final String regEx_special = "\\&[a-zA-Z]{1,10};";//定义特殊字符的正则表达式,如

	/**
	 * @param htmlStr
	 * @return
	 *  删除Html标签
	 *  用于短的数据，直接将文档复制粘贴，写入，但是每一个引号之前需要加“\”转义符，用起来不方便
	 */
	public static String delHTMLTag(String htmlStr) {

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签


		Pattern p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
		Matcher m_special = p_special.matcher(htmlStr);
		htmlStr = m_special.replaceAll(""); // 过滤特殊标签
		return htmlStr.trim(); // 返回文本字符串
	}

	public static String getTextFromHtml(String htmlStr){
		htmlStr = delHTMLTag(htmlStr);
		htmlStr = htmlStr.replaceAll(" ", "");
		return htmlStr;
	}

//	public static void main(String[] args) {
//		String str = "<span style=\"font-family:SimSun;font-size:18px;\"><strong>1、组词：十年，十足，十成。</strong></span><br/>\n" +
//				"<span style=\"font-family:SimSun;font-size:18px;\"><strong>2、成语：十冬腊月，十指连心，三十而立。</strong></span><br/>";
//		System.out.println(getTextFromHtml(str));
//	}
}
