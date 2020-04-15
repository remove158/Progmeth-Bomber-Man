package music;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	private Clip clip;
	private String filePath;
	private AudioInputStream audioInputStream;
	private boolean isPlaying;
	private long currentFrame;
	
	public AudioPlayer(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.filePath = filePath;
		this.audioInputStream =  AudioSystem.getAudioInputStream(new File(this.filePath).getAbsoluteFile()); 
		this.clip = AudioSystem.getClip();
	    this.clip.open(audioInputStream); 
	    this.currentFrame = 0;
	 } 
	
	public void loop() {
		this.clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void play() {
		this.clip.start();
		this.isPlaying = true;
	}
	
	 public void restart() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		 clip.stop(); 
		 clip.close(); 
		 resetAudioStream(); 
		 currentFrame = 0L; 
		 clip.setMicrosecondPosition(0); 
		 this.play(); 
	} 
	
	public void pause()  
	 { 
	     if (!isPlaying)  
	     { 
	         System.out.println("audio is already paused"); 
	         return; 
	     } 
	     this.currentFrame = this.clip.getMicrosecondPosition(); 
	     this.clip.stop(); 
	     this.isPlaying = false; 
	 } 
	 
	
	 public void resume() throws UnsupportedAudioFileException, IOException, LineUnavailableException { 
	     if (isPlaying)  
	     { 
	         System.out.println("Audio is already "+"being played"); 
	         return; 
	     } 
	     clip.close(); 
		 resetAudioStream();
	     clip.setMicrosecondPosition(currentFrame); 
	     this.play(); 
	 } 	
	
	
	
	public void stop() { 
	     this.currentFrame = 0L; 
	     this.clip.stop(); 
	     this.clip.close(); 
	 }
	
	public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException{ 
		this.audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
		this.clip.open(audioInputStream); 
		this.clip.loop(Clip.LOOP_CONTINUOUSLY); 
	} 	

}


