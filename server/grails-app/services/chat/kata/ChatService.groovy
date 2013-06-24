package chat.kata

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantReadWriteLock
import org.hibernate.loader.custom.Return;


class ChatService {

	private List<ChatMessage> messages = new ArrayList()
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock()
	private Lock wl = rwl.writeLock()
	private Lock rl = rwl.readLock()

	/**
	 * Collects chat messages in the provided collection
	 * 
	 * @param if specified messages are collected from the provided sequence (exclusive)
	 * @param messages the collection where to add collected messages
	 * 
	 * @return the sequence of the last message collected.
	 */	
	Integer collectChatMessages(Collection<ChatMessage> collector, Integer fromSeq = null){
		Integer init = fromSeq != null ? fromSeq+1 : 0
		Integer listSize = messages.size()

		wl.lock()
		for(Integer i=init; i<listSize; i++)
			collector.add(messages.get(i))
		wl.unlock()

		return listSize-1
	}

	/**
	 * Puts a new message at the bottom of the chat
	 * 
	 * @param message the message to add to the chat
	 */
	void putChatMessage(ChatMessage message){
		wl.lock()
		rl.lock()
		try{
			messages.add(message)
		}finally{
			wl.unlock()
			rl.unlock()
		}
	}
}
