package org.ejmc.android.simplechat.model;

import java.util.ArrayList;
import java.util.List;


/**
 * List off chat messages..
 * 
 * @author startic
 *
 */
public class ChatList {
	private List<Message> messages;
	int seq;
	
	public ChatList(){
		messages = new ArrayList<Message>();
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public void addMessage(Message auxMessage) {
		messages.add(auxMessage);		
	}
	
}
