package com.hci.activitydj;

import java.util.ArrayList;
import java.util.List;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
	
	List<Integer> streams;
	TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text = (TextView) findViewById(R.id.show_text);
		text.setText("sssss");
		
		final SoundPool pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		final int sound1 = pool.load(this, R.raw.sound1, 1);
		final int sound2 = pool.load(this, R.raw.sound2, 1);
		
		streams = new ArrayList<Integer>();
		
		
		Button b1 = (Button)findViewById(R.id.mButton1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			    float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				int streamId = pool.play(sound1, volume, volume, 1, 0, 1.0f);
				streams.add(streamId);
			}
		});
		
		Button b2 = (Button)findViewById(R.id.mButton2);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			    float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
				int streamId = pool.play(sound2, volume, volume, 1, 0, 1.0f);
				streams.add(streamId);
			}
		});
		
		
		/** message handler **/
		ApplicationCenter appCenter = (ApplicationCenter) getApplication();
		Handler handler = new Handler() {
			@Override
			public void handleMessage (Message msg) {
				text.setText((String)msg.obj);
			}
		};
		appCenter.setHandler(handler);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent toSetting = new Intent(getBaseContext(), SettingActivity.class);
		startActivity(toSetting);
		return true;
	}

}
