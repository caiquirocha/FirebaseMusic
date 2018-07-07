package info.androidhive.firebase;

public class SongModel {
    private String name;
    private String artist;
    private String kind;
    private String url;

    public SongModel(String name,String artist,String kind,String url){
        this.name=name;
        this.artist=artist;
        this.kind=kind;
        this.url=url;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getArtist(){
        return artist;
    }
    public void setArtist(String artist){
        this.artist=artist;
    }
    public String getKind(){
        return kind;
    }
    public void setKind(String kind){
        this.kind=kind;
    }
}
