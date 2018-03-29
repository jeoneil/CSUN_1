package COMP380_MP_Final;

import java.io.File;
import java.nio.file.Path;
import javafx.stage.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import javafx.stage.FileChooser;
import matador.*;

public class Converter
{
	private Path originalFilePath;
	private String fileType;
	
	public Converter(Path originalFilePath, String fileType)
	{
		this.originalFilePath = originalFilePath;
		this.fileType = fileType;
	}
	
	public Path convertFile()
	{
		
		File source = new File(originalFilePath.toString());
		System.out.println(originalFilePath.toString());
		File target = new File(removeFileExtension() + ".mp3");
		
		AudioAttributes audio = new AudioAttributes();
		audio.setCodec("libmp3lame");
		audio.setBitRate(new Integer(256000));
		audio.setChannels(new Integer(2));
		audio.setSamplingRate(new Integer(44100));
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		Encoder encoder = new Encoder(new MyFFMPEGExecutableLocator());
		
		try {
			encoder.encode(source, target, attrs);
			
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (InputFormatException e) {
			
			e.printStackTrace();
		} catch (EncoderException e) {
			
			e.printStackTrace();
		}
		
		return target.toPath();
	}
	
	public void getSupportedDecodingFormats(File file) {
		getSupportedDecodingFormats(file);
		
	}
	public String removeFileExtension() {
		
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Converted Files Location");
		File defaultDirectory = new File("C://");
		chooser.setInitialDirectory(defaultDirectory);
		File selectedDirectory = chooser.showDialog(null);
		
		int a = -1;
		String pathString = originalFilePath.getFileName().toString();
		String extension = "";
		
		/* finds the location of the last instance 
		 * of . in the path string
		 */
		for (int i = 0; i < pathString.length(); i++)
		{
			if (pathString.charAt(i) == '.')
			{
				a = i;
			}
		}
		
		//return something if the above fails
		if (a == -1) {return pathString;}
		
		for (int i = 0; i < a; i++)
		{
			extension += pathString.charAt(i);
		}
		
		return selectedDirectory.toString() + "/" + extension;
	}
}
