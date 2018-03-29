/*
Name: Controller Class
Purpose: The controller class handles all of the
functionality for the user interface. All fxml files
are tied to only 1 Controller class for its functionality.
As functions are implemented, they will be called in this class.
Author: Armando Freire
Date: 03/27/2016
*/
package COMP380_MP_Final;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;


public class Controller {

    private static MediaManager manager;
    private static ObservableList<AudioInfo> songList;
    public static PlaylistManager plmanager;
    private static InvalidationListener progressListener;
    private int audioState;

    public Controller() {
        this.manager = new MediaManager();
        this.plmanager = new PlaylistManager();
        this.songList = FXCollections.observableArrayList();
        this.progressListener = null;
        this.audioState = 0;
    }

    //File Menu
    @FXML
    private MenuItem add;

    @FXML
    private MenuItem remove;

    @FXML
    private MenuItem createPlaylist;

    @FXML
    private MenuItem importPlaylist;

    @FXML
    private MenuItem exportPlaylist;

    //Edit Menu
    @FXML
    private MenuItem editMetadata;

    @FXML
    private MenuItem convert;

    //Help Menu
    @FXML
    private MenuItem about;

    //Table-view of current playlist
    @FXML
    private TableView<AudioInfo> currPlaylist;

    //Tree-view of library
    @FXML
    private TreeView libraryList;

    //Basic Feature Buttons
    @FXML
    private Button rwd;

    @FXML
    private Button play;

    @FXML
    private Button pause;

    @FXML
    private Button stop;

    @FXML
    private Button fwd;
    
    @FXML
    private Button nextAF;

    @FXML
    private Button prevAF;
    
    //Time display
    @FXML
    private Label timeDisplayLabel;

    //Time/Volume sliders
    @FXML
    private Slider timeProgress;

    @FXML
    private Slider volume;

    //Tables
    @FXML
    private TableColumn<AudioInfo, String> trackN;

    @FXML
    private TableColumn<AudioInfo, String> artist;

    @FXML
    private TableColumn<AudioInfo, String> name;

    @FXML
    private TableColumn<AudioInfo, String> album;

    @FXML
    private TableColumn<AudioInfo, String> genre;

    @FXML
    private TableColumn<AudioInfo, String> time;

    /*
	 * Method: initialize
	 * Purpose: initializes control class
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Armando Freire
	 * Date Created: 2016
	 */
    @FXML
    private void initialize() {
        //Sets values for the table columns
        trackN.setCellValueFactory(celldata -> celldata.getValue().getTrackN());
        artist.setCellValueFactory(celldata -> celldata.getValue().getArtist());
        name.setCellValueFactory(celldata -> celldata.getValue().getSongNameProp());
        album.setCellValueFactory(celldata -> celldata.getValue().getAlbum());
        genre.setCellValueFactory(celldata -> celldata.getValue().getGenre());
        time.setCellValueFactory(celldata -> celldata.getValue().getEndTime());

        //Controls volume slider
        volume.setValue(100);

        //Controls time progress slider
        timeProgress.setMin(0.0);
        
        //creates programs default location
        try{
            File setup = new File("C://MatadorPlayer3");
            if(!setup.exists()){
               setup.mkdir();
            }
        }
        catch(Exception e){}
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Important Information");
        alert.setHeaderText(null);
        alert.setContentText("Please allow a small fraction of time for importing of playlists (both chosen and default) and conversion of music. The process hasn't been refined yet. Thank you!");
        alert.showAndWait();
        
        //checks for default playlist upon startup
        try{
            File checkDefault = new File("C://MatadorPlayer3/Default/DEFAULT.m3u");
            if (checkDefault.exists()){
               importPlaylistHelper(false);
            }
         }
         catch (Exception e){}
    }
    
    /*
	 * Method: audioTimeListener
	 * Purpose: creates a new invalidation listener responsible
	 * 			for updating the track time
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Jack O'Neil
	 * Date Created: 26 April, 2016
	 */
    public void audioTimeListener()
    {
        manager.getCurrentAudio().getMediaPlayer().currentTimeProperty().addListener(progressListener = new InvalidationListener() 
        {
            @Override
            public void invalidated(Observable observable) {
            	timeDisplayLabel.setText(manager.currentTime() 
            			+ " / " + manager.getCurrentAudio().getEndTime());
            }
        });
    }

    //Functionality event handlers
    
    /*
	 * Method: addSong
	 * Purpose: adds selected song to library and
	 * 			updates UI list
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Jack O'Neil
	 * Date Created: 26 April, 2016
	 */
    public void addSong() {
        Audio addedSong = null;
        //update song list, if a valid audio track was added
        try
        {
            addedSong = manager.add();
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        if (addedSong != null)
        {
            AudioInfo newSong = new AudioInfo(addedSong);
            songList.add(newSong);
            currPlaylist.setItems(songList);
            
        }
    }

    //File menu functionality
    
    /*
	 * Method: delete
	 * Purpose: deletes selected song from library and
	 * 			updates UI list
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Armando Freire
	 * Date Created: 2016
	 */
    public void delete() 
    {
    	if (currPlaylist.getItems().isEmpty())
	        	manager.getLibrary().getList().clear();
    	if (!manager.isEmpty())
        {
    		Audio deletedAudio = null;
    		
    		AudioInfo selectedItem = currPlaylist.getSelectionModel().getSelectedItem();
            Audio currAudio = manager.getCurrentAudio();
            int index = currPlaylist.getSelectionModel().getSelectedIndex();
            if (progressListener != null)
	        {
	        	manager.getCurrentAudio().getMediaPlayer().currentTimeProperty().removeListener(progressListener);
	        	progressListener = null;
	        }
            
            if (selectedItem.getCurr().equals(currAudio)) {
                manager.stop();
                manager.setCurrentAudio(null);
            }
	        deletedAudio = currPlaylist.getSelectionModel().getSelectedItem().getCurr();
	        currPlaylist.getItems().remove(index);
	        manager.delete(deletedAudio);

            if (songList.size() > 0) {
                currPlaylist.getSelectionModel().selectFirst();
                selectedItem = currPlaylist.getSelectionModel().getSelectedItem();
                manager.setCurrentAudio(selectedItem.getCurr());
            }
        }
    }

    //Playlist functionality, part of File menu

   /*
	 * Method: imPlay(),exPlay(),dfPlay()
	 * Purpose: handle import, export, and set default playlist functionality
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Jesus Delgado
	 * Date Created: 2016
	 */

    public void imPlay() {
    	importPlaylistHelper(true);
    }

    public void exPlay() {
        //saveFile handles the saving of the new playlist
        boolean saved = plmanager.saveFile();
        //alert user of success or failure
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        if(saved){
            alert.setContentText("Playlist Saved Successfully!");
        }
        else{
            alert.setContentText("Something Went Wrong. Playlist Not Saved!");
        }
        alert.showAndWait();
    }
    
    public void dfPlay() {
        //saveDefFile handles the setting of the default playlist for startup
        boolean saved = plmanager.saveDefFile();
        //alert user of success or failure
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        if(saved){
            alert.setContentText("Playlist Set As Default!");
        }
        else{
            alert.setContentText("Something Went Wrong. Playlist Not Set As Default!");
        }
        alert.showAndWait();
      }

    //Edit menu functionality
    public void editM() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Unfortunately, Metadata Editing has not been implemented yet.");
        alert.showAndWait();
      }

    //Help menu functionality
    
    /*
	 * Method: verNum
	 * Purpose: displays version number in UI
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Armando Freire
	 * Date Created: 2016
	 */
    public void verNum() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Version: 0.5.8");

        alert.showAndWait();
    }

    //Media manager
    public void rewind() {
        manager.rewind();
    }

    public void playAudio() {
        manager.play();
        audioState = 1;
    }

    public void pauseAudio() {
        manager.pause();
        audioState = 0;
    }

    public void stopAudio() {
        manager.stop();
        audioState = 0;
    }

    public void forward() {
        manager.fForward();
    }
    
    /*
	 * Method: nextSong
	 * Purpose: goes to the next song in the library
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Armando Freire
	 * Date Created: 27 April, 2016
	 */
    public void nextSong() {
        int index = currPlaylist.getSelectionModel().getSelectedIndex();
        if (index + 1 < songList.size() && index >= 0) {
            AudioInfo next = songList.get(currPlaylist.getSelectionModel().getSelectedIndex() + 1);
            currPlaylist.getSelectionModel().select(next);
            Audio nowPlaying = next.getCurr();

            setAudio(nowPlaying);

            if (audioState == 1) {
                manager.play();
            }
        }
    }
    
    /*
	 * Method: prevSong
	 * Purpose: goes to the previous song in the library
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Armando Freire
	 * Date Created: 27 April, 2016
	 */
    public void prevSong() {
        int index = currPlaylist.getSelectionModel().getSelectedIndex();
        if (index != 0) {
            AudioInfo next = songList.get(currPlaylist.getSelectionModel().getSelectedIndex() - 1);
            currPlaylist.getSelectionModel().select(next);
            Audio nowPlaying = next.getCurr();

            setAudio(nowPlaying);

            if (audioState == 1) {
                manager.play();
            }
        }
    }

    /*
	 * Method: addAndUpdate
	 * Purpose: updates and initializes new current audio 
	 * 			to the currently selected song in the UI
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Jack O'Neil
	 * Date Created: 5 April, 2016
	 */
    public void addAndUpdate() {
        AudioInfo curr = currPlaylist.getSelectionModel().getSelectedItem();
        Audio nowPlaying = curr.getCurr();
        
        setAudio(nowPlaying);
    }

    /*
	 * Method: changeTime
	 * Purpose: controls the song progress slider
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Armando Freire
	 * Date Created: April 25, 2016
	 */
    public void changeTime() {
        MediaPlayer fn = manager.getCurrentAudio().getMediaPlayer();
        fn.seek(Duration.seconds(timeProgress.getValue()));
    }

    /*
	 * Method: volumeChange
	 * Purpose: controls the volume slider
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Armando Freire
	 * Date Created: April 25, 2016
	 */
    public void volumeChange() {
        MediaPlayer fn = manager.getCurrentAudio().getMediaPlayer();
        volume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                fn.setVolume(volume.getValue() / 100);
            }
        });
    }
    
    public void setAudio(Audio nowPlaying) {
        if (nowPlaying != null && nowPlaying != manager.getCurrentAudio()) {
            manager.setCurrentAudio(nowPlaying);
            audioTimeListener();

            //Set volume for song selected
            MediaPlayer fn = manager.getCurrentAudio().getMediaPlayer();
            fn.setVolume(volume.getValue() / 100);

            //Set time slider for song selected
            timeProgress.setMax(fn.getTotalDuration().toSeconds());
            fn.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration curr) {
                    timeProgress.setValue(curr.toSeconds());
                }
            });
        }
    }
        
   /*
	 * Method: importPlaylistHelper()
	 * Purpose: Helper class for both Import Playlist Button and Loading the Default Playlist
	 * Input: boolean
	 * Output: void
	 * Return value: void
	 * Author: Jesus Delgado
	 * Date Created: 2016
	 */
    
    private void importPlaylistHelper(boolean choice){
      //importPL handles the decoding of the M3U file in an array of song locations
      boolean emptyPath = plmanager.importPL(choice);
      
      if(emptyPath){
         //stop music and reset songlist
         manager.stop();
         songList.removeAll(songList);
         manager.resetLibrary();
      
         String newPath;
         Audio addedSong = null;
         int count = plmanager.getCount();
         //traverse array of songs and add to songlist
         while(count>=0){
            count--;
            newPath = plmanager.readBuf(count);
            if(!newPath.equals("")){
               addedSong = manager.addMulti(newPath);
		         if (addedSong != null){
			         AudioInfo newSong = new AudioInfo(addedSong);
                  songList.add(newSong);
                  currPlaylist.setItems(songList);
		         }
            }
         }
      }
   }
}

