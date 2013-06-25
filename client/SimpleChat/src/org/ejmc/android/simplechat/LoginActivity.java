package org.ejmc.android.simplechat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

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
		ipServer = (TextView) findViewById(R.id.ipField);

		/*SharedPreferences settings = getSharedPreferences("perfil",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("nick", nick.getText().toString());
		editor.commit();*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void join(View v) {
		Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
		intent.putExtra("nick", nick.getText().toString());
		intent.putExtra("ipServer", ipServer.getText().toString());
		startActivity(intent);
	}

}
