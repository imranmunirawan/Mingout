package com.mingout.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mingout.activities.R;

public class ConnectionTask extends AsyncTask<String, String, Object> {

	private Activity context;
	private ProgressDialog progressDialog;
	JSONObject obj;
	Fragment f;

	Boolean waitingDialog = true, fragFlag = false;

	public ConnectionTask(Activity context, JSONObject obj) {
		this.context = context;
		this.obj = obj;
	}

	public ConnectionTask(Fragment frag, JSONObject jsonobj) {
		// TODO Auto-generated constructor stub
		this.obj = jsonobj;
		this.f = frag;
		fragFlag = true;
	}

	public ConnectionTask(Activity context, JSONObject obj,
			Boolean waitingDialog) {
		this.context = context;
		this.obj = obj;
		this.waitingDialog = waitingDialog;
	}

	public ConnectionTask(Fragment frag, JSONObject jsonobj,
			Boolean waitingDialog) {
		// TODO Auto-generated constructor stub
		this.obj = jsonobj;
		this.f = frag;
		fragFlag = true;
		this.waitingDialog = waitingDialog;
	}

	@Override
	protected Object doInBackground(String... url) {

		String response = null;

		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);


		HttpClient httpClient = new DefaultHttpClient(params);
		HttpParams httpParameters = httpClient.getParams();
		HttpConnectionParams.setTcpNoDelay(httpParameters, true);
		HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(url[0]);


		try {
			StringEntity se = new StringEntity(obj.toString(), "UTF-8");

			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			HttpResponse rrresponse = httpClient.execute(httpPost, httpContext);
			HttpEntity entity = rrresponse.getEntity();

			response = EntityUtils.toString(entity);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	protected void onProgressUpdate(String... s) {
		super.onProgressUpdate(s);
		Toast.makeText(context, s[0], Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPostExecute(Object object) {
		super.onPostExecute(object);
		try {
			if (fragFlag) {
				if (waitingDialog)
					progressDialog.dismiss();
			} else {
				if (waitingDialog)
					progressDialog.dismiss();
			}
			if (fragFlag) {
				if (object != null) {
					((ResultJSON) f).UpdateResult(object);
				} else {

				}
			} else {
				if (object != null) {
					((ResultJSON) context).UpdateResult(object);
				} else {

				}
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	@Override
	protected void onPreExecute() {
		try {
			if (fragFlag) {
				if (waitingDialog)
					progressDialog = ProgressDialog.show(
							f.getActivity(),
							"",
							f.getActivity().getResources()
									.getString(R.string.please_wait), true);
			} else {
				if (waitingDialog)
					progressDialog = ProgressDialog.show(context, "", context
							.getResources().getString(R.string.please_wait),
							true);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}