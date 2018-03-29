package COMP380_MP_Final;

import javafx.beans.property.*;

public class AudioInfo {
    private final StringProperty trackN = new SimpleStringProperty("");
    private final StringProperty artist = new SimpleStringProperty("");
    private final StringProperty songName = new SimpleStringProperty("");
    private final StringProperty album = new SimpleStringProperty("");
    private final StringProperty genre = new SimpleStringProperty("");
    private final StringProperty endTime = new SimpleStringProperty("");

    private Audio curr;


    public AudioInfo() {
        this(null);
    }

    public AudioInfo(Audio fname) {
        trackN.set("0");
        artist.set("a");
        setSongName(fname.getSongName());
        album.set("a");
        genre.set("a");
        endTime.set("0");

        setCurr(fname);
    }

    public StringProperty getTrackN() {
        return trackN;
    }

    public StringProperty getArtist() {
        return artist;
    }

    public StringProperty getSongNameProp() {
        return songName;
    }

    public String getSongName() {
        return songName.get();
    }

    public void setSongName(String name) {
        this.songName.set(name);
    }

    public StringProperty getAlbum() {
        return album;
    }

    public StringProperty getGenre() {
        return genre;
    }

    public StringProperty getEndTime() {
        return endTime;
    }

    public void setCurr(Audio curr) {
        this.curr = curr;
    }

    public Audio getCurr() {
        return curr;
    }
}
	