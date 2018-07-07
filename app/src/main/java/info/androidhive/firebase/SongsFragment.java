/*package info.androidhive.firebase;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SongsFragment extends Fragment {
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    MediaPlayer mediaPlayer;


    ArrayList<SongModel> tarifList;
    ListView listView;

    FirebaseDatabase database;
    public SongsFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_songs, container, false);
        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        tarifList=new ArrayList<SongModel>();
        listView= (ListView) view.findViewById(R.id.list_songs);

        database=FirebaseDatabase.getInstance();
        final DatabaseReference dbRef=database.getReference("songs");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    String name=ds.child("name").getValue().toString();
                    String artist=ds.child("artist").getValue().toString();
                    String kind=ds.child("kind").getValue().toString();
                    String url=ds.child("url").getValue().toString();
                    tarifList.add(new SongModel(name,artist,kind,url));
                }
                CustomAdapter adapter=new CustomAdapter(getActivity(),tarifList);
                listView.setAdapter(adapter);
                dbRef.removeEventListener(this);}
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

}

        /*View v = inflater.inflate(R.layout.fragment_songs, container, false);


        auth = FirebaseAuth.getInstance();

        songs = v.findViewById(R.id.songs);
        songlist = new ArrayList<>();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot songsSnapshot : dataSnapshot.getChildren()){
                    SongModel song=new SongModel();
                    song.setName(songsSnapshot.getValue(SongModel.class).getName());
                    song.setArtist(songsSnapshot.getValue(SongModel.class).getArtist());
                    song.setKind(songsSnapshot.getValue(SongModel.class).getKind());

                    ArrayList<String> array = new ArrayList<>();
                    array.add(song.getName());
                    array.add(song.getArtist());
                    array.add(song.getKind());

                    /*SongsList adapter = new SongsList(getActivity(),songlist);
                    songs.setAdapter(adapter);*/

