package app.ui;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class SynthetizedVoice {

	//Create a Synthesizer instance
	private SynthesiserV2 synthesizer;
	private ConcurrentLinkedQueue<String> queue;
	
	private static SynthetizedVoice instance;

	/**
	 * Constructor
	 */
	private SynthetizedVoice(String languageCode) {
		synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
		synthesizer.setLanguage(languageCode);
		queue = new ConcurrentLinkedQueue<String>();
	}
	
	public static SynthetizedVoice getInstance() {
		if (instance == null)  {
			instance = new SynthetizedVoice("pt"); //TODO aspecj
			instance.start();
		}  
        return instance; 
	}
	
	public void playVoice(String text) {
		queue.add(text);
	}

	private void start() {
		Thread thread = new Thread(() -> {
			try {
				while(true) {
					if(!queue.isEmpty()) {
						//Create a JLayer instance
						AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(queue.poll()));
						player.play();
					}					
				}				
			} catch (IOException | JavaLayerException e) {
				e.printStackTrace(); //Print the exception ( we want to know , not hide below our finger , like many developers do...)
			}
		});
		thread.start();
	}

}
