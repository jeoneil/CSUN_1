package COMP380_MP_Final;

import java.io.File;
import java.lang.String;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;
import java.text.DecimalFormat;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/*
 * Class: MediaManager
 * Purpose: Implements main functionality of the 
 *  media player based on input information from 
 *  the UserInterface class.
 * Author: Jack O'Neil
 * Date Created: 14 March, 2016
 */
public class MediaManager {
    private Audio currentAudio;
    private Library library;
    private boolean isPlaying = false;

    public MediaManager()
    {
        this.currentAudio = null;
        this.library = new Library();
    }
    
    /*
	 * Method: add
	 * Purpose: adds user-chosen audio file to the library
	 * 	as audio object
	 * Input: void
	 * Output: the newly made audio object
	 * Return value: Audio newAudio
	 * Author: Jack O'Neil
	 * Date Created: 14 March, 2016
	 */
    public Audio add() throws IOException
    {
        Path path = chooseFile();
        if (path.equals(null)) {return null;}
        Path fileName = path.getFileName();
		String fileType = findFileExtension(fileName.toString());
      		
		if (!fileType.equals("mp3") && !fileType.equals("MP3"))
		{
			Converter converter = new Converter(path, fileType);

			//make converted file, make new path (at the location of source file)
			path = converter.convertFile();
		}
		
        Audio newAudio = new Audio(path);
        Controller.plmanager.addToPL(path.toString());
        library.add(newAudio);
        return newAudio;
    }
    
    /*
	 * Method: addMulti()
	 * Purpose: handles the adding of songs when from a playlist
	 * Input: String containing path of song
	 * Output: the newly made audio object
	 * Return value: Audio newAudio
	 * Author: Jesus Delgado
	 * Date Created: 2016
	 */
    
    public Audio addMulti(String newPath){
        Path path = findFile(newPath);
        if (path.equals(null)) {return null;}
  		  Path fileName = path.getFileName();
  		
      String fileType = findFileExtension(fileName.toString());
      		
		if (!fileType.equals("mp3") && !fileType.equals("MP3"))
		{
			Converter converter = new Converter(path, fileType);

			//make converted file, make new path (at the location of source file)
			path = converter.convertFile();
		}
      
        Audio newAudio = new Audio(path);
  		  library.add(newAudio);
  		  return newAudio;
     }
    
    /*
	 * Method: delete
	 * Purpose: deletes selected audio from the library
	 * Input: Audio deleted Audio
	 * Output: void
	 * Return value: void
	 * Author: Jack O'Neil
	 * Date Created: 17 April, 2016
	 */
    public void delete(Audio deletedAudio)
    {
        //stop the current song, if it is playing
        stop();
        
        //delete the song from the library
        library.delete(deletedAudio);

        //make the current song null (or next song, if playlist is implemented)
      	currentAudio = null;
    }
    
    public Path findFile(String path){
        File file = new File(path);
        if(file == null){
           System.out.println("Couldn't Find File!");
           return null;
        }
        return file.toPath();
     }

    /*
	 * Method: chooseFile
	 * Purpose: opens file chooser for user to choose audio file
	 * Input: void
	 * Output: file path
	 * Return value: String file.getAbsolutePath()
	 * Author: Jack O'Neil
	 * Date Created: 14 March, 2016
	 */
    public Path chooseFile()
    {
        //set extension filter
        String[] filters = {"*.mp3", "*.m4a", "*.flac", "*.wav"};
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("audio files", filters);

        //choose file with dialog
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(extFilter);
        chooser.setTitle("Audio Selection");
        File file = chooser.showOpenDialog(null);
        if (file == null)
        {
            System.out.println("No Selection!");
            return null;
        }
        return file.toPath();
    }
    
    /*
	 * Method: findFileExtension
	 * Purpose: retrieves the file extension from song's
	 * 	path string
	 * Input: String pathString
	 * Output: the file extension
	 * Return value: String extension;
	 * Author: Jack O'Neil
	 * Date Created: 17 April, 2016
	 */
    public String findFileExtension(String pathString)
    {
        int a = -1;
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
        if (a == -1) {return extension;}

		/*
		 * make string consisting only of the
		 * file extension at the end of the 
		 * path string
		 */
        for (int i = a + 1; i < pathString.length(); i++)
        {
            extension += pathString.charAt(i);
        }
        return extension;
    }

    /*
	 * Method: currentSongName
	 * Purpose: retrieves current song name from current song's
	 * 	audio object property
	 * Input: void
	 * Output: current song name
	 * Return value: String currentAudio.getSongName();
	 * Author: Jack O'Neil
	 * Date Created: 14 March, 2016
	 */
    public String currentSongName()
    {
        if (currentAudio == null)
            return "";
        else
            return currentAudio.getSongName();
    }
    
    /*
     * Method: currentTime
     * Purpose: returns a string representing the total
     * duration of the audio track
     * Input: void
     * Output: String currTimeStr
     * Return value: void
     * Author: Jack O'Neil
     * Date Created: 26 April, 2016
     */
    public String currentTime()
    {
    	String currTimeStr = "0:00";
    	String secStr = "";
    	Duration totalSecDuration = null;
    	Double totalSecDouble = 0.0;
    	int totalSec = 0, sec = 0, min = 0;
    	if (currentAudio != null)
    	{
    		//get the total duration in seconds
    		totalSecDuration = currentAudio.getMediaPlayer().getCurrentTime();
    		totalSecDouble = totalSecDuration.toSeconds();
    		
    		//round down the time to an integer
    		totalSecDouble = Math.floor(totalSecDouble);
    		
    		//convert from double to integer
    		totalSec = totalSecDouble.intValue();
    		
    		/*
    		 * calculate separate integer values for
    		 * the total minutes and remaining seconds
    		 */
    		sec = totalSec;
    		if (totalSec > 59)
    		{
    			min = Math.floorDiv(totalSec, 60);
    			sec = totalSec - (60 * min);
    		}
    		
    		/* 
    		 * add a zero if the seconds are under
    		 * ten to maintain the time format
    		 */
    		secStr = String.valueOf(sec);
    		if (sec < 10)
    			secStr = "0" + secStr;
    		
    		//put the integers together in time format
    		currTimeStr = String.valueOf(min) + ":" + secStr;
    	}
    	return currTimeStr;
    }

	/*
	public String checkFileType(Path path)
	{
		FileTypeDetector detector;
		detector.probeContentType(path);

	}
	*/

    /*
	 * Method: play
	 * Purpose: plays the current audio
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Jack O'Neil
	 * Date Created: 14 March, 2016
	 */
    public void play()
    {
        if (currentAudio == null)
            return;
        isPlaying = true;
        currentAudio.getMediaPlayer().play();
    }

    /*
	 * Method: stop
	 * Purpose: stops the current audio
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Jack O'Neil
	 * Date Created: 14 March, 2016
	 */
    public void stop()
    {
        if (currentAudio == null)
            return;
        isPlaying = false;
        currentAudio.getMediaPlayer().stop();
    }

    /*
	 * Method: pause
	 * Purpose: pauses the current audio
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Jack O'Neil
	 * Date Created: 14 March, 2016
	 */
    public void pause()
    {
        if (currentAudio == null)
            return;
        isPlaying = false;
        currentAudio.getMediaPlayer().pause();
    }
    
    /*
	 * Method: fForward
	 * Purpose: fast forwards the current audio
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Jack O'Neil
	 * Date Created: 14 March, 2016
	 */
    public void fForward()
    {
        if (currentAudio == null)
            return;

        Duration increment = new Duration(10000.0);
        Duration newTime = currentAudio.getMediaPlayer().getCurrentTime().add(increment);
        currentAudio.getMediaPlayer().seek(newTime);
    }
    
    /*
	 * Method: loopAudio
	 * Purpose: loops the current audio
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Jack O'Neil
	 * Date Created: 27 April, 2016
	 */
    public void loopAudio()
    {
    	
    }

    /*
	 * Method: rewind
	 * Purpose: rewinds the current audio
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Jack O'Neil
	 * Date Created: 14 March, 2016
	 */
    public void rewind()
    {
        if (currentAudio == null)
            return;
        Duration increment = new Duration(10000.0);
        Duration newTime = currentAudio.getMediaPlayer().getCurrentTime().subtract(increment);
        currentAudio.getMediaPlayer().seek(newTime);
    }
    
    /*
	 * Method: isEmpty
	 * Purpose: checks if the library is empty
	 * Input: void
	 * Output: boolean
	 * Return value: boolean
	 * Author: Jack O'Neil
	 * Date Created: 14 March, 2016
	 */
    public boolean isEmpty()
    {
    	if (library.getList().isEmpty())
    		return true;
    	else 
    		return false;
    }

    public Audio getCurrentAudio() {
        return currentAudio;
    }

    public void setCurrentAudio(Audio currentAudio) {
        stop();
        this.currentAudio = currentAudio;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
    
    public boolean checkPlaying(){
        return this.isPlaying;
     }
     
     public void resetLibrary(){
        this.library = new Library();
     }
}
