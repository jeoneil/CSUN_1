package COMP380_MP_Final;

import java.nio.file.Path;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/*
 * Class: Audio
 * Purpose: Creates an Audio object to represent each
 * 	audio track in the program, with the essential
 * 	properties and functions.
 * Author: Jack O'Neil
 * Date Created: 14 March, 2016
 */

import javafx.beans.property.*;

/*
 * Class: Audio
 * Purpose: Creates an Audio object to represent each
 * 	audio track in the program, with the essential 
 * 	properties and functions. 
 * Author: Jack O'Neil
 * Date Created: 14 March, 2016
 */

public class Audio {
    private Media media;
    private MediaPlayer mediaPlayer;
    private String uri;
    private int trackNum;
    private String artist;
    private String songName;
    private String album;
    private String genre;
    private String endTime;



    public Audio(Path path)
    {
        this.uri = createValidURI(path);
        this.songName = path.getFileName().toString();
        this.trackNum = 0;
        this.artist = "";
        this.album = "";
        this.genre = "";
        this.media = new Media(uri);
        this.mediaPlayer = new MediaPlayer(media);
        this.endTime = "";
        createEndTime();
    }

    /*
     * Method: createValidURI
     * Purpose: creates valid URI with given path to
     * 	pass to audio object constructor
     * Input: original file path
     * Output: corrected file path
     * Return value: String path;
     * Author: Jack O'Neil
     * Date Created: 5 April, 2016
     */
    public String createValidURI(Path path)
    {
        //transform path into valid URI string
        String pathString = path.toString();
        pathString = "file:///" + pathString;
        pathString = pathString.replace('\\','/');
        pathString = pathString.replace(" ","%20");
        return pathString;
    }
    
    /*
     * Method: createEndTime
     * Purpose: creates a string representing the total
     * duration of the audio track
     * Input: void
     * Output: void
     * Return value: void
     * Author: Jack O'Neil
     * Date Created: 26 April, 2016
     */
    public void createEndTime()
    {
    	/*
    	 * create a new thread that waits for the
    	 * mediaPlayer to be "ready" for the 
    	 * total duration property to be read
    	 */
    	mediaPlayer.setOnReady(new Runnable()
    	{
    		@Override
    		public void run()
    		{
    			int sec = 0, min = 0, totalSec = 0;
    			String secStr = "";
    			
    			//get the total duration in seconds
    			Double timeDouble = mediaPlayer.getTotalDuration().toSeconds();
    			
    			//round down the time to an integer
    			timeDouble = Math.floor(timeDouble);
    			
    			//convert from double to integer
        		totalSec = timeDouble.intValue();
        		
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
        		endTime = String.valueOf(min) + ":" + secStr;
    		}
    	});
    }

    /*
     * getters and setters
     */
    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public int getTrackNum() {
        return trackNum;
    }

    public void setTrackNum(int trackNum) {
        this.trackNum = trackNum;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String toString(){
        return songName;
    }
}
