package info.androidhive.firebase;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;

/**
 * Created by Can on 5.03.2018.
 */

public class DownloadingAudioList {

    String artist;
    String kind;
    String name;
    String url;
    Integer count;

    public DownloadingAudioList(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DownloadingAudioList(String artist, String kind, String name, String url, int count) {
        this.artist = artist;
        this.kind = kind;
        this.name = name;
        this.url = url;
        this.count = count;
    }

    public String getAudioname() {
        return url;
    }

    public void setAudioname(String url) {
        this.url = url;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
