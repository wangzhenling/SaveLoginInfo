package com.example.savelogininfo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btnLogin;
	CheckBox cbRemPsd,cbAutoLogin;
	EditText etUserName,etPassword;
    TextView userInfo;
	private SharedPreferences loginPreferences;
	private SharedPreferences accessPreferences;
	private Editor loginEditor;
	private Editor accessEditor;
	private String userName;
	private String userPsd;
	private boolean isSavePsd;
	private boolean isAutoLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		loginPreferences=getSharedPreferences("login",Context.MODE_PRIVATE);
		accessPreferences=getSharedPreferences("access",Context.MODE_WORLD_READABLE);
		int count=accessPreferences.getInt("count", 1);
		Toast.makeText(MainActivity.this, "欢迎您，这是第"+count+"次访问！", Toast.LENGTH_LONG).show();
		loginEditor=loginPreferences.edit();
		accessEditor=accessPreferences.edit();
		accessEditor.putInt("count", ++count);
		accessEditor.commit();
		userName=loginPreferences.getString("name", null);
		userPsd=loginPreferences.getString("psd", null);
		isSavePsd=loginPreferences.getBoolean("isSavePsd", false);
		isAutoLogin=loginPreferences.getBoolean("isAutoLogin", false);
		if(isAutoLogin){
			this.setContentView(R.layout.activity_welcome);
			userInfo=(TextView)findViewById(R.id.userInfo);
			userInfo.setText("欢迎您："+userName+"，登录成功！");
		}else{
			loadActivity();
		}
		
	}

	private void loadActivity() {
		// TODO Auto-generated method stub
		
		this.setContentView(R.layout.activity_main);
		btnLogin=(Button) findViewById(R.id.btnLogin);
		cbRemPsd=(CheckBox) findViewById(R.id.cbRemPsd);
		cbAutoLogin=(CheckBox) findViewById(R.id.cbAutoLogin);
		etUserName=(EditText) findViewById(R.id.etUserName);
		etPassword=(EditText) findViewById(R.id.etPassword);
		if(isSavePsd){
			etPassword.setText(userPsd);
			etUserName.setText(userName);
			cbRemPsd.setChecked(true);
		}
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				loginEditor.putString("name", etUserName.getText().toString());
				loginEditor.putString("psd", etPassword.getText().toString());
				loginEditor.putBoolean("isSavePsd", cbRemPsd.isChecked());
				loginEditor.putBoolean("isAutoLogin", cbAutoLogin.isChecked());
				loginEditor.commit();
				MainActivity.this.setContentView(R.layout.activity_welcome);
				userInfo=(TextView)findViewById(R.id.userInfo);
				userInfo.setText("欢迎您："+etUserName.getText().toString()+"，登录成功！");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean OnOptionsItemSelected(MenuItem item){
		
		switch(item.getItemId()){
		case R.id.menu_settings:
			loginEditor.putBoolean("isAutoLogin",false);
			loginEditor.commit();
			onCreate(null);
			break;
		case R.id.exit:
			this.finish();
			break;
	    default:
			break;
		}
		return true;
		
	}
}
