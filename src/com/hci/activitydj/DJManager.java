package com.hci.activitydj;

import android.media.AudioManager;
import android.media.SoundPool;

public class DJManager {
	
	/* record properties of a song */
	class Song {
		private final static int SONG_NOT_PLAYED = -1;
		
		private final int songId;
		private int streamId;		// used when being played, -1 for unplayed
		
		public Song(int id) {
			songId = id;
			streamId = SONG_NOT_PLAYED;
		}
		
		public void setStreamId(int id) {
			streamId = id;
		}
	}
	
	private SoundPool pool;

	public DJManager() {
		pool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	}
}
