package org.ejmc.android.simplechat.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Proxy to remote API.
 * 
 * @author startic
 * 
 */
public class NetRequests extends AsyncTask<Void, Void, String> {

	/**
	 * Gets chat messages from sequence number.
	 * 
	 * @param seq
	 * @param handler
	 */

	public void chatGET(int seq, NetResponseHandler<ChatList> handler) {

		String url = "http://172.20.0.9/chat-kata/api/chat?seq=" + seq;

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;

		try {

			response = httpclient.execute(httpget);

			if (response.getStatusLine().getStatusCode()==200){
				
				HttpEntity entity = response.getEntity();
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				instream.close();
				
				ChatList chatlist= getChatList(result);
				chatlist.setSeq(getSeq(result));

				handler.onSuccess(getChatList(result));

			}

		} catch (Exception e) {
			Log.e("Exception: ", e.toString());

		}

	}

	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private static ChatList getChatList(String result) throws JSONException {

		JSONObject jsonObject = new JSONObject(result);
		JSONArray jsonMainArr = new JSONArray();
		jsonMainArr = jsonObject.getJSONArray("messages");

		Message auxMessage;
		ChatList listaMensajes = new ChatList();

		for (int i = 0; i < jsonMainArr.length(); i++) {
			JSONObject childJson = jsonMainArr.getJSONObject(i);
			auxMessage = new Message(childJson.getString("nick"),
					childJson.getString("message"));
			listaMensajes.addMessage(auxMessage);
		}

		return listaMensajes;
	}

	private static int getSeq(String result) throws JSONException {
		JSONObject jsonObject = new JSONObject(result);
		return Integer.parseInt(jsonObject.getString("last_seq"));
	}

	/**
	 * POST message to chat.
	 * 
	 * @param message
	 * @param handler
	 */
	public void chatPOST(Message message, NetResponseHandler<Message> handler) {

	}

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

}