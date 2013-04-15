package com.hci.activitydj;

import java.util.ArrayList;
import java.util.List;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	List<Integer> streams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
