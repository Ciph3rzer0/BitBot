package edu.sru.andgate.bitbot;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
	int songID;
	MediaPlayer mp;
	Context context;
	
	public SoundManager(Context context, int songID){
		this.context = context;
		this.songID = songID;
		mp = MediaPlayer.create(context, songID);
	}
	
	public void playAudio(){
		mp.start();
	}
	
	public void stopAudio(){
		mp.stop();
	}
	
	public void releaseAudio(){
		mp.release();
	}
	
	public void resetAudio(){
		mp.reset();
	}
	
	public void pauseAudio(){
		mp.pause();
	}
	
}
