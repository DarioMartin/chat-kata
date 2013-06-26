package org.ejmc.android.simplechat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main activity.
 * 
 * Shows login config.
 * 
 * @author startic
 * 
 */
public class LoginActivity extends Activity {

	private TextView nick, ipServer;
	private Button btnJoin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		btnJoin = (Button) findViewById(R.id.btnJoin);
		nick = (TextView) findViewById(R.id.nickField);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void join(View v) {
		String nickname = nick.getText().toString();
		if (nickname.equals("")) {
			Toast toast = Toast.makeText(this, "Nick incorrecto", 1000);
			toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 100);
			toast.show();
		} else {
			Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
			intent.putExtra("nick", nickname);
			startActivity(intent);
		}
	}
}
