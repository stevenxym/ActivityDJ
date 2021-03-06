package com.hci.activitydj;

import java.util.ArrayList;
import java.util.List;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {
	
//	List<Integer> streams;
	TextView text;
	
//	private DJManager dj;
	private boolean test = true;
	private actionState s;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final ApplicationCenter appCenter = (ApplicationCenter) getApplication();
		
//		dj = new DJManager(this);
		
		text = (TextView) findViewById(R.id.show_text);
		text.setText("sssss");
		s = actionState.KINETIC_REST;
		
//		final SoundPool pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
//		final int sound1 = pool.load(this, R.raw.sound1, 1);
//		final int sound2 = pool.load(this, R.raw.sound2, 1);
//		
//		streams = new ArrayList<Integer>();
		
		final MediaPlayer player = MediaPlayer.create(this, R.raw.sound1);
		
		Button b1 = (Button)findViewById(R.id.mButton1);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				appCenter.change(s);
				if (s == actionState.KINETIC_REST)
					s = actionState.KINETIC_ACT;
				else
					s = actionState.KINETIC_REST;
				
//				player.start();
			}
		});
		
		Button b2 = (Button)findViewById(R.id.mButton2);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				appCenter.stop();
//				player.stop();
			}
		});
		
		Button b3 = (Button)findViewById(R.id.mButton3);
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				player.start();
			}
		});
		
		
		/** message handler **/
		Handler handler = new Handler() {
			@Override
			public void handleMessage (Message msg) {
				text.setText((String)msg.obj);
				
//				String msgArray[] = ((String)msg.obj).split(" ");
//				double energy, prevEnergy = 0;
//				double x, prevX = -2;
//				
//				if (msgArray[0].equals("R")) {
//					energy = Double.valueOf(msgArray[3]);
//					x = Double.valueOf(msgArray[2]);
//					
//					if (prevEnergy < 100 && energy >= 100)
//						dj.changeSong();
//					
//					if ((x <= 1 && x >= -1) && (prevX > 1 || prevX < -1)) {
//						//dj.changeSongLoopStatus(-1);
//						if (!dj.isPlaying())
//							dj.changeSong();
//					} 
//					if ((prevX <= 1 && prevX >= -1) && (x > 1 || x < -1)) {
//						dj.changeSongLoopStatus(0);
//						System.out.println("test.........");
//					}
//					
//					prevEnergy = energy;
//					prevX = x;
//				}
			}
		};
		appCenter.setHandler(handler);
		appCenter.initApplication(this);
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
