package org.ejmc.android.simplechat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.ejmc.android.simplechat.model.ChatList;
import org.ejmc.android.simplechat.net.NetRequests;
import org.ejmc.android.simplechat.net.NetResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Chat activity.
 * 
 * @author startic
 */
public class ChatActivity extends Activity {

	private EditText input;
	private UpdatesThread updates;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		Bundle bundle = getIntent().getExtras();

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

		public void run() {
			timer = new Timer();
			timer.scheduleAtFixedRate(timerTask, 0, 1000);
		}

		TimerTask timerTask = new TimerTask() {
			public void run() {
				Log.e("", "------------");
				NetRequests netReq = new NetRequests();
				netReq.chatGET(0, new NetResponseHandler<ChatList>());
			}
		};

		public void stopTimer() {
			timer.cancel();
		}

	}

}
