package Menu.src;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class Sound extends JFrame
{
	Clip ol;
	public Sound()
	{
		File sf=new File("src/Menu/data/Final.wav");
		AudioFileFormat aff;
		AudioInputStream ais;

		try
		{
			aff=AudioSystem.getAudioFileFormat(sf);
			ais=AudioSystem.getAudioInputStream(sf);	
			AudioFormat af=aff.getFormat();
			
			DataLine.Info info = new DataLine.Info(
			Clip.class,
			ais.getFormat(),
			((int) ais.getFrameLength() *
			af.getFrameSize()));
			
			ol = (Clip) AudioSystem.getLine(info);
			ol.open(ais);	
			
		}
		catch(UnsupportedAudioFileException ee){System.out.println("exc1");}
		catch(IOException ea){System.out.println("exc2");}
		catch(LineUnavailableException LUE){System.out.println("exc3");};
		show();
		
	}
		
	public void start(){
		ol.start();
	}
	
	public void stop(){
		ol.stop();
	}
	
	public void loop(){
		ol.loop(Clip.LOOP_CONTINUOUSLY);
	}
} 
