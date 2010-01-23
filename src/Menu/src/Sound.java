package Menu.src;

import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;

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
