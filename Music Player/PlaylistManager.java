package COMP380_MP_Final;

import christophedelory.playlist.*;
import java.lang.Runtime;
import java.io.*;
import java.io.IOException;
import java.lang.Exception;
import java.util.Arrays;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;

public class PlaylistManager {
	private SpecificPlaylist specPlaylist;
   private SpecificPlaylist newSpecPlaylist;
   private Playlist genPlaylist;
   private ByteArrayOutputStream buf;
   private String[] paths;
   private int count;
   private SpecificPlaylistProvider provider;
   public static File tempPL;
   
   /*
	 * Method: PlaylistManager()
	 * Purpose: handles the initialization of the playlist handler
	 * Input: void
	 * Output: void
	 * Return value: void
	 * Author: Jesus Delgado
	 * Date Created: 2016
	 */

   public PlaylistManager(){
      this.specPlaylist = null;
      this.newSpecPlaylist = null;
      this.genPlaylist = null;
      this.provider = SpecificPlaylistFactory.getInstance().findProviderById("m3u");
      this.paths = new String[0];
      this.count = 0;
      this.buf = new ByteArrayOutputStream();
      //create temporary playlist
      try{
         tempPL = new File("C://MatadorPlayer3/TEMP.m3u");
         if(!tempPL.exists()){
            tempPL.createNewFile();
         }
      }
      catch(IOException e){}
   }

   /*
	 * Method: readBuf()
	 * Purpose: Returns next file location read from playlist file
    *    Called as many times as there are audio files in the playlist
	 * Input: int pos = position of song in array of song locations
	 * Output: string of song location
	 * Return value: paths[pos]
	 * Author: Jesus Delgado
	 * Date Created: 2016
	 */

   public String readBuf(int pos){
      if(pos<0){
         return "";
      }
      else{
         paths[pos] = paths[pos].replace("\n"," ");
         paths[pos] = " "+paths[pos];
         paths[pos] = paths[pos].trim();
         return paths[pos];
      }
   }

//returns how many audio files have yet to be read from playlist
   public int getCount(){
      return this.count;
   }

   /*
	 * Method: addToPL()
	 * Purpose: Add songs to temporary playlist as they are added
	 * Input: String with location path of the added song
	 * Output: void
	 * Return value: void
	 * Author: Jesus Delgado
	 * Date Created: 2016
	 */

   public void addToPL(String addPath){
         //add song to the array of song locations
         int tempCount = paths.length;
         paths = Arrays.copyOf(paths,tempCount+1);
         paths[tempCount] = addPath;
         this.count++;
         //convert path location to usable format
         addPath = addPath.replace('\\','/');
         addPath = addPath.replace(":/","://");
         addPath = addPath.replace(" ","%20");
         //command line needed for adding to playlist using external code
         String temp = "C://MatadorPlayer3/RUN/AddToPlaylist.bat -m3u:ext -o C://MatadorPlayer3/TEMP.m3u "+addPath;
         //execute command to add song to playlist
         Runtime rt = Runtime.getRuntime();
         try{
            Process pr = rt.exec(temp);
         }
         catch(IOException e){
            System.out.println(temp);
         }
   }

   /*
	 * Method: importPL()
	 * Purpose: Handles the reading of a playlist file, 
    *    conversion to a generic playlist file,
    *    conversion of the generic playlist to a M3U file playlist,
    *    and parsing of the various audio file paths specified by 
    *    the playlist
	 * Input: boolean to determine whether importing Default or 
    *    user-choice playlist
	 * Output: boolean to show that playlist was loaded
	 * Return value: boolean
	 * Author: Jesus Delgado
	 * Date Created: 2016
	 */

   public boolean importPL(boolean choice){      
      String path = null;
      //option allowing user to import playlist file of their choice
      if(choice){
         path = chooseFile();
         if(path.equals("")){
            return false;
         }
      }
      //option for importing default playlist
      else{
         path = "C://MatadorPlayer3/Default/DEFAULT.m3u";
      }
      
      //clear input stream and access playlist file
      buf.reset();
      File file = new File(path);
      
      //delete current temporary playlist, copy new playlist, and rename
      try{
         tempPL.delete();
         Files.copy(file.toPath(),tempPL.toPath());
         file.renameTo(tempPL);
      }
      catch(Exception e){}
      
      //read from playlist file
      try{
         specPlaylist = SpecificPlaylistFactory.getInstance().readFrom(file);}
      catch(IOException e){}
      //null playlist
      if(specPlaylist == null){
         System.out.println("Invalid Playlist Format");}
      
      //simplify playlist info for easy retrieval of song locations
      // and write data to buffer
      this.genPlaylist = specPlaylist.toPlaylist();
      try{
         newSpecPlaylist = provider.toSpecificPlaylist(genPlaylist);      
         newSpecPlaylist.writeTo(buf, null);
      }
      catch(Exception e){}
      
      //read from buffer and split locations into an array of strings
      String data = buf.toString();
      paths = data.split("\n");
      this.count = paths.length;
      return true;
   }

   /*
	 * Method: chooseFile()
	 * Purpose: opens file chooser adapted for the user to 
    *    choose playlist file
	 * Input: void
	 * Output: file path
	 * Return value: String file.getAbsolutePath()
	 * Author: Jack O'Neil
    * Adapted by: Jesus Delgado
	 * Date Created: 2016
	 */
    
   public String chooseFile(){
		//set extension filter
		String[] filters = {"*.m3u"};
      FileChooser.ExtensionFilter extFilter = 
            new FileChooser.ExtensionFilter("M3U (*.m3u)", filters);
		//choose file with dialog
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(extFilter);
		chooser.setTitle("Playlist Selection");
		File file = chooser.showOpenDialog(null);
		if (file == null){
			System.out.println("No Selection!");
			return "";
		}
		return file.getAbsolutePath();
	}
   
   /*
	 * Method: saveFile()
	 * Purpose: Allows user to save playlist (M3U) file to 
    *    location of their choice
	 * Input: void
	 * Output: boolean of success or failure
	 * Return value: boolean
	 * Author: Jesus Delgado
	 * Date Created: 2016
	 */
   
   public boolean saveFile(){
      //choose save location with dialog
      FileChooser chooser = new FileChooser();
      chooser.setTitle("Save Playlist");
      chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("M3U (.m3u)","*.m3u"));
      File file = chooser.showSaveDialog(null);
      
      if (file != null) {
         //overwrite function (deletes previous to allow save)
         if(file.exists()){
            file.delete();
         }
         //copy temporary playlist file to chosen location and rename
         try{
            File file2 = new File("C://MatadorPlayer3/TEMP.m3u");
            Files.copy(file2.toPath(),file.toPath());
            file.renameTo(file);
            return true;
         }
         catch(Exception e){
            System.out.println("Playlist Not Saved.");
         }
      }
      return false;
   }
   
   /*
	 * Method: saveDefFile()
	 * Purpose: Allows user to set current playlist as Default 
    *    which would be loaded upon next startup of the program
	 * Input: void
	 * Output: boolean of success or failure
	 * Return value: boolean
	 * Author: Jesus Delgado
	 * Date Created: 2016
	 */
   
   public boolean saveDefFile(){
      try{
         //create default playlist save location
         File def = new File("C://MatadorPlayer3/Default");
         if(!def.exists()){
            def.mkdir();
         }
         
         def = new File("C://MatadorPlayer3/Default/DEFAULT.m3u");
         //overwrite function (deletes previous default to allow save)
         if(def.exists()){
            def.delete();
         }
         
         //copy temporary playlist file to default file location
         //  and rename
         Files.copy(tempPL.toPath(),def.toPath());
         File file = new File("C://MatadorPlayer3/Default/TEMP.m3u");
         file.renameTo(def);
         return true;
      }
      catch(Exception e){
         System.out.println("Playlist Not Saved.");
      }
      return false;
   }
}
