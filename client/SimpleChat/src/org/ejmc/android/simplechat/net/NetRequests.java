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

import android.net.http.AndroidHttpClient;
import android.util.Log;

/**
 * Proxy to remote API.
 * 
 * @author startic
 * 
 */
public class NetRequests {

	/**
	 * Gets chat messages from sequence number.
	 * 
	 * @param seq
	 * @param handler
	 */
	public void chatGET(int seq, NetResponseHandler<ChatList> handler) {

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(
					"http://172.20.0.9/chat-kata/api/chat?seq=0");
			HttpResponse response = httpclient.execute(httpget);
			
		} catch (Exception e) {
			Log.e("demo", e.getMessage());
		}
	}

	/**
	 * POST message to chat.
	 * 
	 * @param message
	 * @param handler
	 */
	public void chatPOST(Message message, NetResponseHandler<Message> handler) {

	}

}
