package info.androidhive.firebase;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class ProfileFragment extends Fragment {


    private FirebaseAuth auth;

    DatabaseReference favaudio,favaudiolist;
    String favsongs,favsong;
    RecyclerView fav_audio;
    MediaPlayer mediaPlayer;
    private String ad;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();
        final String firebaseUser = user.getUid();

       /* favaudio = FirebaseDatabase.getInstance().getReference().child("favorilistesi").child(firebaseUser).child("fav");

        fav_audio = (RecyclerView) v.findViewById(R.id.fav_audio);
        fav_audio.setHasFixedSize(true);
        fav_audio.setLayoutManager(new LinearLayoutManager(getActivity()));
*/
        ad = user.getDisplayName();
        ImageView ayar=v.findViewById(R.id.ayarlar);
        TextView kisi=v.findViewById(R.id.kisi);
        TextView sarki=v.findViewById(R.id.sarkisay);

        String kisii=user.getUid();
        String mail=user.getEmail();
        kisi.setText(ad);

        sarki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment to Fragment
                AudioTab songsFragment = new AudioTab();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.main_frame,songsFragment).commit();
            }
        });
        ayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fragment to Activity için
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
/*
        FirebaseRecyclerAdapter<DownloadingAudioList,ProfileFragment.DownloadingAudio> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DownloadingAudioList,ProfileFragment.DownloadingAudio>(
                DownloadingAudioList.class,
                R.layout.profile,
                ProfileFragment.DownloadingAudio.class,
                favaudio
        ) {
            @Override
            protected void populateViewHolder(ProfileFragment.DownloadingAudio viewHolder, DownloadingAudioList model, int position) {

                final String audio_key = getRef(position).getKey();

                viewHolder.setAudioTitle(model.getName());
                viewHolder.setArtist(model.getArtist());
                viewHolder.setKind(model.getKind());
                viewHolder.setAudio(getActivity(),model.getAudioname());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View v) {
                        favaudio.child(audio_key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String audioFile = (String) dataSnapshot.child("url").getValue();
                                try {
                                    mediaPlayer = new MediaPlayer();
                                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    mediaPlayer.setDataSource(audioFile);
                                    mediaPlayer.prepare();
                                    mediaPlayer.start();
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

        fav_audio.setAdapter(firebaseRecyclerAdapter);

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
        public void setAudio(Context context, String url){
            FloatingActionButton fabPlay = (FloatingActionButton) mView.findViewById(R.id.fab_play);
        }*/
    }
}
