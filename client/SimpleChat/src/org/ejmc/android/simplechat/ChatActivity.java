package org.ejmc.android.simplechat;

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
import android.widget.Button;
import android.widget.EditText;

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
	private EditText conversation;
	private NetResponseHandler<ChatList> handler;
	private Handler puente = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			try {
				conversation.setText((String) msg.obj);
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
		netReq = new NetRequests();
		chatList = new ChatList();
		conversation = (EditText) findViewById(R.id.conversationField);

		input = (EditText) findViewById(R.id.userInput);
		input.setHint(bundle.getString("nick"));

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

	public void send() {

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
				netReq.chatGET(0, handler);
				chatList = handler.getChatList();
				List<Message> messages = chatList.getMessages();
				String text = "";
				for (int i = 0; i < messages.size(); i++) {
					text += messages.get(i)+"\n";
				}
				android.os.Message msg = new android.os.Message();
				msg.obj = text;
				puente.sendMessage(msg);
			}
		};

		public void stopTimer() {
			timer.cancel();
		}

	}
}
