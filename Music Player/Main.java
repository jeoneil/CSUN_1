/*
Name: Main Class
Purpose: This class is used to run the application, it
when the main class is run, it loads the fxml file to
display the Graphical User Interface.
Author: Armando Freire
Date: 03/27/2016
*/

package COMP380_MP_Final;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Matador Music Player");
        primaryStage.setScene(new Scene(root, 725, 475));
        primaryStage.setMinWidth(745);
        primaryStage.setMinHeight(520);
        primaryStage.show();
        
        //deletes temporary playlist upon closing the program
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              PlaylistManager.tempPL.delete();
          }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
