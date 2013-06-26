package org.ejmc.android.simplechat;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.model.Message;
import org.ejmc.android.simplechat.net.NetRequests;
import org.ejmc.android.simplechat.net.NetResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Chat activity.
 * 
 * @author startic
 */
public class ChatActivity extends Activity {

	private EditText input;
	private UpdatesThread updates;
	private NetRequests netReq;
	private ChatList chatList;
	private NetResponseHandler<ChatList> handler;
	private NetResponseHandler<Message> handler2;
	private String nick;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> listItems = new ArrayList<String>();
	private ListView list;
	private Handler puente = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			try {
				adapter.notifyDataSetChanged();
			} catch (Exception e) {
				Log.e("Exception", e.getMessage());
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		Bundle bundle = getIntent().getExtras();

		handler = new NetResponseHandler<ChatList>();
		handler2 = new NetResponseHandler<Message>();
		netReq = new NetRequests();
		chatList = new ChatList();
		nick = bundle.getString("nick");
		input = (EditText) findViewById(R.id.userInput);
		input.setHint(nick);
		list = (ListView) findViewById(R.id.list);
		list.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		list.setStackFromBottom(true);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listItems);
		list.setAdapter(adapter);

		updates = new UpdatesThread();
		updates.start();

		// Show the Up button in the action bar.
		setupActionBar();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().hide();
	}

	public void exit(View v) {
		updates.stopTimer();
		try {
			updates.stop();
		} catch (Exception e) {
		}
		Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
		startActivity(intent);
	}

	public void send(View w) {
		SendThread thread = new SendThread();
		thread.start();
		EditText message = (EditText) findViewById(R.id.userInput);
		message.setText("");
	}

	public class UpdatesThread extends Thread {
		Timer timer;

		@Override
		public void run() {
			timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, 0, 500);
		}

		TimerTask timerTask = new TimerTask() {
			public void run() {
				boolean newMsgs = false;
				netReq.chatGET(handler.getChatList().getSeq(), handler);
				chatList = handler.getChatList();
				List<Message> messages = chatList.getMessages();
				for (int i = 0; i < messages.size(); i++) {
					listItems.add(messages.get(i).toString());
					newMsgs = true;
				}
				if (newMsgs) {
					puente.sendMessage(new android.os.Message());
				}
			}
		};

		public void stopTimer() {
			timer.cancel();
		}
	}

	public class SendThread extends Thread {
		@Override
		public void run() {
			EditText message = (EditText) findViewById(R.id.userInput);
			Message msg = new Message(nick, message.getText().toString());
			netReq.chatPOST(msg, handler2);
		}
	}
}
