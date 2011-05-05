package edu.sru.andgate.bitbot;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class SoundManager 
{
	public static final int MAX_BULLET_CHANNELS = 6;
	
	public static int SHOT;
	
	int songID;
	MediaPlayer mp;
	Context context;
	
	SoundPool sp = new SoundPool(MAX_BULLET_CHANNELS, AudioManager.STREAM_MUSIC, 0);
	
	public SoundManager(Context context, int songID)
	{
		this.context = context;
		this.songID = songID;
//		mp = MediaPlayer.create(context, songID);
//		mp.setVolume(0.25f, 0.25f);
		
		SHOT = sp.load(context, R.raw.ar, 1);
	}
	
	public void playAudio()
	{
		sp.play(SHOT, 0.5f, 0.5f, 1, 1, 1f);
//		mp.start();
	}
	
//	public void stopAudio(){
//		mp.stop();
//	}
//	
//	public void releaseAudio(){
//		mp.release();
//	}
//	
//	public void resetAudio(){
//		mp.reset();
//	}
//	
//	public void pauseAudio(){
//		mp.pause();
//	}
	
}
