package com.mingout.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpManager {

	DefaultHttpClient httpClient;
	HttpContext httpContext;
	HttpResponse response;
	HttpPost httpPost;
	String webServiceUrl;
	HttpParams httpParams;
	private String ret;

	public HttpManager(String serviceName) {
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 10000);
		HttpConnectionParams.setSoTimeout(params, 10000);
		httpClient = new DefaultHttpClient(params);
		httpContext = new BasicHttpContext();
		webServiceUrl = serviceName;
	}

	public String webInvoke(String methodName, Map<String, Object> params) {
		// JSONObject jsonObject = new JSONObject();
		// jsonObject = getJSON(params);
		String data = params.toString();
		data = data.replace("=", ":");
		return webInvoke(methodName, data, "application/json");
	}

	private String webInvoke(String methodName, String data, String contentType) {
		List<NameValuePair> nameValuePairs = null;
		HttpEntity entity = null;
		HttpResponse response = null;
		HttpPost httpPost = null;
		try {
			httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.RFC_2109);
			httpPost = new HttpPost(webServiceUrl + methodName);
			// httpPost.setHeader(
			// "Accept",
			// "application/json,text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			// httpPost.setHeader("content-Type","application/x-www-form-urlencoded");
			ret = null;
			nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("requestJson", data));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			response = httpClient.execute(httpPost);
			entity = response.getEntity();
			ret = EntityUtils.toString(entity);
			System.out.println(ret);

		} catch (ClientProtocolException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			nameValuePairs = null;
			entity = null;
			response = null;
			httpPost = null;
		}
		return ret;
	}

	public String uploadImage(Map<String, Object> params, String url) {
		String webResponse = null;
		try {
			httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY,
					CookiePolicy.RFC_2109);
			httpPost = new HttpPost(url);
			httpPost.setHeader(
					"Accept",
					"application/json,text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");
			httpPost.setHeader("content-Type",
					"application/x-www-form-urlencoded");
			ret = null;

			String filename = (String) params.get("filename");
			String base64ImageData = (String) params.get("base64");
			if (base64ImageData != null && !base64ImageData.equals("null")) {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						2);
				nameValuePairs.add(new BasicNameValuePair("image_name",
						filename));
				nameValuePairs.add(new BasicNameValuePair("image_data",
						base64ImageData));

				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				webResponse = EntityUtils.toString(entity);
			}
			System.out.println(webResponse);

		} catch (ClientProtocolException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return webResponse;
	}

}
