package org.ejmc.android.simplechat.net;


import java.util.logging.LogRecord;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.model.RequestError;

import android.os.Handler;


/**
 * Empty response handler.
 * 
 * Base class for Net Response handlers.
 * 
 * @author startic
 * 
 * @param <Response>
 */
public class NetResponseHandler<Response> extends Handler{

	private ChatList chatList;
	
	public NetResponseHandler(){
		chatList = new ChatList();
	}
	
	
	public ChatList getChatList() {
		return chatList;
	}

	public void setChatList(ChatList chatList) {
		this.chatList = chatList;
	}

	/**
	 * Handles a successful request
	 * */
	public void onSuccess(Response response) {

	}

	/**
	 * Handles a network error.
	 */
	public void onNetError() {

	}

	/**
	 * Handles a request error.
	 */
	public void onRequestError(RequestError error) {

	}


	public void handleMessage(String msg) {
		// TODO Auto-generated method stub
		
	}
}
