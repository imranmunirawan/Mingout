package com.mingout.util;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.mingout.activities.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class ConnectionTaskSupportFragment extends AsyncTask<String, String, Object> {

	JSONObject obj;
	Fragment frag;
	private ProgressDialog progressDialog;

	public ConnectionTaskSupportFragment(Fragment frag, JSONObject obj) {
		this.frag = frag;
		this.obj = obj;
	}

	@Override
	protected Object doInBackground(String... url) {

		String response = null;

		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(url[0]);

		try {
			StringEntity se = new StringEntity(obj.toString());

			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			HttpResponse rrresponse = httpClient.execute(httpPost, httpContext);
			HttpParams httpParameters = httpClient.getParams();
			HttpConnectionParams.setTcpNoDelay(httpParameters, true);
			HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
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
		Toast.makeText(frag.getActivity(), s[0], Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPostExecute(Object object) {
		super.onPostExecute(object);
		try {
			progressDialog.dismiss();
			if (object != null) {
				((ResultJSON) frag).UpdateResult(object);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	@Override
	protected void onPreExecute() {
		try {
			progressDialog = ProgressDialog.show(frag.getActivity(), "",
					frag.getResources().getString(R.string.please_wait),
					true);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}
