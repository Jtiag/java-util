package com.gome.wa.util;

import org.apache.commons.lang.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 发送http请求,支持https
 * @author shulei
 *
 */
public class HttpUtil {

	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 发送Get请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String get(String url) throws Exception {
		return get(url, false);
	}

	/**
	 * 发送Get请求
	 * @param url
	 * @param https
	 * @return
	 * @throws Exception
	 */
	public static String get(String url, Boolean https) throws Exception {
		if (https != null && https) {
			StringBuffer bufferRes = null;
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL urlGet = new URL(url);
			HttpsURLConnection http = (HttpsURLConnection) urlGet.openConnection();
			// 连接超时
			http.setConnectTimeout(25000);
			// 读取超时 --服务器响应比较慢，增大时间
			http.setReadTimeout(25000);
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setSSLSocketFactory(ssf);
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();

			InputStream in = http.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
			String valueString = null;
			bufferRes = new StringBuffer();
			while ((valueString = read.readLine()) != null) {
				bufferRes.append(valueString);
			}
			in.close();
			if (http != null) {
				// 关闭连接
				http.disconnect();
			}
			return bufferRes.toString();
		} else {
			StringBuffer bufferRes = null;
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			// 连接超时
			http.setConnectTimeout(25000);
			// 读取超时 --服务器响应比较慢，增大时间
			http.setReadTimeout(25000);
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();

			InputStream in = http.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
			String valueString = null;
			bufferRes = new StringBuffer();
			while ((valueString = read.readLine()) != null) {
				bufferRes.append(valueString);
			}
			in.close();
			if (http != null) {
				// 关闭连接
				http.disconnect();
			}
			return bufferRes.toString();
		}
	}

	/**
	 * 发送Get请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String get(String url, Map<String, String> params) throws Exception {
		return get(url, params, false);
	}
	
	/**
	 * 发送Get请求
	 * @param url
	 * @param params
	 * @param https
	 * @return
	 * @throws Exception
	 */
	public static String get(String url, Map<String, String> params,Boolean https) throws Exception {
		return get(initParams(url, params),https);
	}

	/**
	 * 发送Post请求
	 * @param url
	 * @param params
	 * @param https
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, String params,Boolean https) throws Exception {
		StringBuffer bufferRes = null;

		URL urlGet = new URL(url);
		if(https!=null&&https){
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			HttpsURLConnection http = (HttpsURLConnection) urlGet.openConnection();
			// 连接超时
			http.setConnectTimeout(25000);
			// 读取超时 --服务器响应比较慢，增大时间
			http.setReadTimeout(25000);
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.setSSLSocketFactory(ssf);
			
			http.connect();
			
			OutputStream out = http.getOutputStream();
			out.write(params.getBytes("UTF-8"));
			out.flush();
			out.close();
			
			InputStream in = http.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
			String valueString = null;
			bufferRes = new StringBuffer();
			while ((valueString = read.readLine()) != null) {
				bufferRes.append(valueString);
			}
			in.close();
			if (http != null) {
				// 关闭连接
				http.disconnect();
			}
			return bufferRes.toString();
		}else{
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			// 连接超时
			http.setConnectTimeout(25000);
			// 读取超时 --服务器响应比较慢，增大时间
			http.setReadTimeout(25000);
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			
			http.connect();
			
			OutputStream out = http.getOutputStream();
			out.write(params.getBytes("UTF-8"));
			out.flush();
			out.close();
			
			InputStream in = http.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
			String valueString = null;
			bufferRes = new StringBuffer();
			while ((valueString = read.readLine()) != null) {
				bufferRes.append(valueString);
			}
			in.close();
			if (http != null) {
				// 关闭连接
				http.disconnect();
			}
			return bufferRes.toString();
		}
	}
	
	/**
	 * 发送Post请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, String params) throws Exception {
		return post(url, params, false);
	}
	
	/**
	 * 发送Post请求
	 * @param url
	 * @param params
	 * @param https
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params,Boolean https) throws Exception {
		return post(url, initParams(params),https);
	}
	/**
	 * 发送Post请求
	 * @param url
	 * @param params
	 * @param https
	 * @return
	 * @throws Exception
	 */
	public static String post(String url, Map<String, String> params) throws Exception {
		return post(url, params,false);
	}


	/**
	 * 构造url
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private static String initParams(String url, Map<String, String> params) {
		if (null == params || params.isEmpty()) {
			return url;
		}
		StringBuilder sb = new StringBuilder(url);
		if (url.indexOf("?") == -1) {
			sb.append("?");
		} else {
			sb.append("&");
		}
		sb.append(initParams(params));
		return sb.toString();
	}
	
	/**
	 * 构造params
	 * @param params
	 * @return
	 */
	private static String initParams(Map<String, String> params) {
		if (null == params || params.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Entry<String, String> entry : params.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append("&");
			}
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key).append("=");
			if (StringUtils.isNotEmpty(value)) {
				try {
					sb.append(URLEncoder.encode(value, DEFAULT_CHARSET));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	// 获取链接地址文件的byte数据
	public static byte[] getUrlFileData(String fileUrl) throws Exception {
		URL url = new URL(fileUrl);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.connect();
		InputStream cin = httpConn.getInputStream();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = cin.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		cin.close();
		byte[] fileData = outStream.toByteArray();
		outStream.close();
		return fileData;
	}

}

/**
 * 证书管理
 */
class MyX509TrustManager implements X509TrustManager {

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}
}
