package com.hci.activitydj;

import java.util.ArrayList;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class DJManager {
	
	/* record properties of a song */
	class Song {
		private final static int SONG_NOT_PLAYED = -1;
		
		private final int songId;
		private int streamId;		// used when being played, -1 for unplayed
		
		private double leftVolume, rightVolume;
		private int loopMode;
		private float playRate;
		
		public Song(int id) {
			songId = id;
			streamId = SONG_NOT_PLAYED;
			loopMode = 0;			// don't repeat
			playRate = 1.0f;
		}
		
		public void setStreamId(int id) {
			streamId = id;
		}
		
		public void setLoopMode(SoundPool pool, int loop) {
			this.loopMode = loop;
			if (streamId != SONG_NOT_PLAYED)
				pool.setLoop(streamId, loop);
		}
		
		public void setPlayRate(SoundPool pool, float rate) {
			this.playRate = rate;
			if (streamId != SONG_NOT_PLAYED)
				pool.setRate(streamId, rate);
		}
		
		/* play with current device volume */
		public void play(SoundPool pool, Context context) {
			AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		    float volume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		    this.play(pool, volume, volume);
		}
		
		/* play with volume specified */
		public void play(SoundPool pool, float left, float right) {
			streamId = pool.play(songId, left, right, 1, loopMode, playRate);
			this.leftVolume = left;
			this.rightVolume = right;
		}
		
		public void stop(SoundPool pool) {
			pool.stop(streamId);
			streamId = SONG_NOT_PLAYED;
		}
	}
	
	private SoundPool pool;		// object to keep and play audio
	private Song songPlaying;
	private ArrayList<Song> songList;
	
	private Context currentContext;

	
	/**
	 * public APIs
	 */
	
	public DJManager(Context context) {
		pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		songPlaying = null;
		songList = new ArrayList<Song>();
		currentContext = context;
		
		this.initSongList();
	}
	
	public void changeSong() {
		if (songPlaying != null)
			songPlaying.stop(pool);
		
		songPlaying = this.getNextSong();
		songPlaying.play(pool, currentContext);
	}
	
	public void changeSongLoopStatus(int loop) {
		if (songPlaying != null)
			songPlaying.setLoopMode(pool, loop);
	}
	
	public void stop() {
		if (songPlaying != null)
			songPlaying.stop(pool);
		songPlaying = null;
	}
	
	public void setContextChanged(Context newContext) {
		this.currentContext = newContext;
	}
	
	
	/**
	 * helper functions
	 */
	
	/* load previous prepared songs to own object
	 * add the song to pool and tracking list */
	private void initSongList() {
		songList.add(new Song(pool.load(currentContext, R.raw.sound1, 1)));
		songList.add(new Song(pool.load(currentContext, R.raw.sound2, 1)));
		songList.add(new Song(pool.load(currentContext, R.raw.sound3, 1)));
	}
	
	/* decide which song should be played next */
	private Song getNextSong() {
		if (songList.get(2) != songPlaying)
			return songList.get(2);
		else
			return songList.get(1);
	}
}
