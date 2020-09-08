package control;

import java.net.URL;
import java.util.LinkedList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
	
	public enum SoundEffect {
		NORMAL, CAPTURE
	};
	
	static LinkedList<Clip> list = new LinkedList<Clip>();
	
	public static void playSound(SoundEffect effect) {
		String path = null;
		switch(effect) {
			case NORMAL:
				path = "chess-move.wav";
				break;
			case CAPTURE:
				path = "chess-capture.wav";
				break;
		}
		
		try {
			URL is = ClassLoader.getSystemResource(path);
			AudioInputStream ais = AudioSystem.getAudioInputStream(is);
			
			Clip clip;
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			list.add(clip);
			if(list.size() >= 10)
				list.poll().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
