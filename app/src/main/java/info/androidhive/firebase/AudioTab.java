package info.androidhive.firebase;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

/**
 * Created by Can on 5.03.2018.
 */

public class AudioTab extends Fragment {

   // TextView tvName,tvArtist,tvKind;


    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    DatabaseReference drAudio;
    RecyclerView rvAudiolist;
    MediaPlayer mediaPlayer;
    String bla = null;

    int a=0,b=0;
    int length=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_songs,container,false);

        drAudio = FirebaseDatabase.getInstance().getReference().child("songs");


        rvAudiolist = (RecyclerView) rootView.findViewById(R.id.rv_audio);
        rvAudiolist.setHasFixedSize(true);
        rvAudiolist.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<DownloadingAudioList,DownloadingAudio> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DownloadingAudioList, DownloadingAudio>(
                DownloadingAudioList.class,
                R.layout.downloadingaudiolist,
                DownloadingAudio.class,
                drAudio
        ) {
            @Override
            protected void populateViewHolder(DownloadingAudio viewHolder,DownloadingAudioList model, int position) {

                final String audio_key = getRef(position).getKey();

                viewHolder.setAudioTitle(model.getName());
                viewHolder.setArtist(model.getArtist());
                viewHolder.setKind(model.getKind());
                viewHolder.setAudio(getActivity(),model.getAudioname());
                viewHolder.setPause(getActivity());
                viewHolder.setFav(getActivity(),model.getName(),model.getArtist(),model.getKind(),model.getAudioname());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drAudio.child(audio_key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String audioFile = (String) dataSnapshot.child("url").getValue();
                                try {
                                    if(bla!=audio_key){
                                        if(b==1){
                                            mediaPlayer.reset();
                                            b = 0;
                                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                            mediaPlayer.setDataSource(audioFile);
                                            mediaPlayer.prepare();
                                            mediaPlayer.start();
                                            bla = audio_key;
                                            a = 1;
                                        }else if(b==0){
                                            mediaPlayer = new MediaPlayer();
                                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                            mediaPlayer.setDataSource(audioFile);
                                            mediaPlayer.prepare();
                                            mediaPlayer.start();
                                            bla = audio_key;
                                            a = 1;
                                            b = 1;
                                        }
                                    }else{
                                        if (a == 1 && bla == audio_key) {
                                            mediaPlayer.pause();
                                            length = mediaPlayer.getCurrentPosition();
                                            a = 0;
                                        } else if (bla == audio_key && a == 0) {
                                            mediaPlayer.seekTo(length);
                                            mediaPlayer.start();
                                            a = 1;
                                        }
                                    }
                                } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }
                });
            }
        };
        rvAudiolist.setAdapter(firebaseRecyclerAdapter);
    }

    public static class DownloadingAudio extends RecyclerView.ViewHolder{

        View mView;

        public DownloadingAudio(View itemView) {

            super(itemView);

            mView = itemView;
        }
        public void setAudioTitle(String audioTitle){
            TextView AudioTitle = (TextView) mView.findViewById(R.id.tv_name);
            AudioTitle.setText(audioTitle);
        }
        public void setArtist (String artist){
            TextView AudioArtist = (TextView) mView.findViewById(R.id.tv_artist);
            AudioArtist.setText(artist);
        }
        public void setKind (String kind){
            TextView AudioKind = (TextView) mView.findViewById(R.id.tv_kind);
            AudioKind.setText(kind);
        }
        public void setPause(Context context){

        }
        public void setAudio(Context context, String url){
            FloatingActionButton fabPlay = (FloatingActionButton) mView.findViewById(R.id.fab_play);

        }

        public void setFav(final Context context,final String name,final String artist,final String kind,final String url){
            FloatingActionButton fabfav = (FloatingActionButton) mView.findViewById(R.id.fab_fav);
            fabfav.setOnClickListener(new View.OnClickListener() {

                private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();

                @Override
                public void onClick(View v) {
                    databaseReference.child("count").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int countt = ((Long) dataSnapshot.
                                   child(firebaseAuth.getCurrentUser().getUid())
                                   .child(kind).getValue()).intValue();

                            if(countt == 0){
                               countt=countt+1;
                               databaseReference.child("count")
                                       .child(firebaseAuth.getCurrentUser().getUid())
                                       .child(kind).setValue(countt);
                           }
                           else if(countt == 1){
                               countt=countt+1;
                               databaseReference.child("count")
                                       .child(firebaseAuth.getCurrentUser().getUid())
                                       .child(kind).setValue(countt);
                           }
                            Toast.makeText(context,"Favorinize eklendi.", Toast.LENGTH_LONG).show();

                           databaseReference.child("favorilistesi")
                                    .child(firebaseAuth.getCurrentUser().getUid())
                                    .child(kind).child(name).setValue(name);
/*
                            databaseReference.child("favorilistesi")
                                    .child(firebaseAuth.getCurrentUser().getUid())
                                    .child(kind).child("artist").setValue(artist);

                            databaseReference.child("favorilistesi")
                                    .child(firebaseAuth.getCurrentUser().getUid())
                                    .child(kind).child("kind").setValue(kind);

                            databaseReference.child("favorilistesi")
                                    .child(firebaseAuth.getCurrentUser().getUid())
                                    .child(kind).child("url").setValue(url);*/
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(context,"HATA", Toast.LENGTH_LONG).show();
                        }
                    });
              }
            });
        }
    }
}
