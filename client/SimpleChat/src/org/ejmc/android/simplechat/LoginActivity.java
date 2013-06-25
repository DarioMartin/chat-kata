package org.ejmc.android.simplechat;

import com.example.helloworldrest.R;
import com.example.helloworldrest.MainActivity.LongRunningGetIO;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Main activity.
 * 
 * Shows login config.
 * 
 * @author startic
 * 
 */
public class LoginActivity extends Activity implements OnClickListener  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Button joinBtn = (Button) findViewById(R.id.btnJoin);
		joinBtn.setOnClickListener(this);
		
		// String nick = (String)
		// findViewById(R.id.nickField)joinBtn.toString();

		/*joinBtn.setOnClickListener(new OnClickListener() {
			// @Override
			public void onClick(View v) {
				Intent intent = new Intent(ChatActivity.this,Panel.class);
				startActivity(intent);
			}
		});

		SharedPreferences settings = getSharedPreferences("perfil",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		// editor.putString("user",nameText);
		editor.commit();*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new 
		startActivity(intent);
	}

}
