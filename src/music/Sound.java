package music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.net.URISyntaxException;

public class Sound {
	String musicFile;
	Media sound;
	MediaPlayer mediaPlayer;

	public Sound(String path) {
		this(path, 0.1);

	}

	public Sound(String path, double x) {

		musicFile = "music/" + path + ".wav"; // For exampl
		musicFile = ClassLoader.getSystemResource(musicFile).toString();
		sound = new Media(musicFile);
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setVolume(x);
		mediaPlayer.play();

	}

	public void stop() {
		mediaPlayer.stop();
	}

	public void start() {
		mediaPlayer.play();
	}

	public void setVolume(double x) {
		mediaPlayer.setVolume(x);
	}

	public void loop() {
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	}

}
