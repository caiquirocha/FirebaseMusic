package info.androidhive.firebase;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
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

public class HomeFragment extends Fragment {

    FloatingActionButton fabplay;

    String bla = null;

    int a=0,b=0;
    int length=0;

    public String oneri=null;
    public String oner;
    //rock[],pop[],turkce[],techno[];
    //int vrock, vpop, vturkce, vtechno, pop1, pop2, pop3, rock1, rock2, rock3, turkce1, turkce2, turkce3, techno1, techno2, techno3, vbig;
    //int rock[]=null, pop[]=null, turkce[]=null, techno[]=null, krock[], kpop[], kturkce[], ktechno[];
    int i = 0;
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference oneriii = FirebaseDatabase.getInstance().getReference().child("onerisong");
    public DatabaseReference drAudio;

    RecyclerView rvAudiolist;
    MediaPlayer mediaPlayer;

    public HomeFragment() {
        newvalue();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        drAudio=oneriii;

        rvAudiolist = (RecyclerView) rootView.findViewById(R.id.tv_oneri);
        rvAudiolist.setHasFixedSize(true);
        rvAudiolist.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    private void newvalue() {
        final String uid = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference ref = firebaseDatabase.getReference();
        final DatabaseReference friend = firebaseDatabase.getReference().child("users").child(uid);
        final FirebaseDatabase firebaseDatabasee = FirebaseDatabase.getInstance();
        final DatabaseReference son = firebaseDatabasee.getReference();


        son.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final int vrock, vpop, vturkce, vtechno, pop0,pop1,pop2,popv0,popv1,popv2,rockv0,rockv1,rockv2,rock0,rock1,rock2,rock3,turkcev0,turkcev1,turkcev2,turkce0,turkce1,turkce2,turkce3,technov0,technov1,technov2,techno0,techno1,techno2,techno3,vbig;
                int vbigg;
                String kilk = (String) dataSnapshot.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("friend").child("1").getValue();
                String kiki = (String) dataSnapshot.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("friend").child("2").getValue();

                pop0 = ((Long) dataSnapshot.child("count").child(kilk).child("pop").getValue()).intValue();
                rock0 = ((Long) dataSnapshot.child("count").child(kilk).child("rock").getValue()).intValue();
                techno0 = ((Long) dataSnapshot.child("count").child(kilk).child("turkce").getValue()).intValue();
                turkce0 = ((Long) dataSnapshot.child("count").child(kilk).child("techno").getValue()).intValue();

                pop1 = ((Long) dataSnapshot.child("count").child(kiki).child("pop").getValue()).intValue();
                rock1 = ((Long) dataSnapshot.child("count").child(kiki).child("rock").getValue()).intValue();
                techno1 = ((Long) dataSnapshot.child("count").child(kiki).child("turkce").getValue()).intValue();
                turkce1 = ((Long) dataSnapshot.child("count").child(kiki).child("techno").getValue()).intValue();

                pop2 = ((Long) dataSnapshot.child("count").child(uid).child("pop").getValue()).intValue();
                rock2 = ((Long) dataSnapshot.child("count").child(uid).child("rock").getValue()).intValue();
                techno2 = ((Long) dataSnapshot.child("count").child(uid).child("turkce").getValue()).intValue();
                turkce2 = ((Long) dataSnapshot.child("count").child(uid).child("techno").getValue()).intValue();

                popv0 = ((rock0 - turkce0) + (rock1 - turkce1)) / 2;
                popv1 = ((rock0 - pop0) + (rock1 - pop1)) / 2;
                popv2 = ((rock0 - techno0) + (rock1 - techno1)) / 2;

                vpop = ((popv0 + turkce2) + (popv1 + pop2) + (popv2 + techno2)) / 3;
                vbigg = vpop;
                oneri = ("pop");

                rockv0 = ((techno0 - turkce0) + (techno1 - turkce1)) / 2;
                rockv1 = ((techno0 - pop0) + (techno1 - pop1)) / 2;
                rockv2 = ((techno0 - rock0) + (techno1 - rock1)) / 2;

                vrock = ((rockv0 + turkce2) + (rockv1 + pop2) + (rockv2 + rock2)) / 3;

                if (vrock > vbigg) {
                    vbigg = vrock;
                    oneri = ("rock");
                }

                turkcev0 = ((pop0 - turkce0) + (pop1 - turkce1)) / 2;
                turkcev1 = ((pop0 - techno0) + (pop1 - techno1)) / 2;
                turkcev2 = ((pop0 - rock0) + (pop1 - rock1)) / 2;

                vturkce = ((turkcev0 + turkce2) + (turkcev1 + techno2) + (turkcev2 + rock2)) / 3;

                if (vturkce > vbigg) {
                    vbigg = vturkce;
                    oneri = ("turkce");
                }

                technov0 = ((turkce0 - pop0) + (turkce1 - pop1)) / 2;
                technov1 = ((turkce0 - techno0) + (turkce1 - techno1)) / 2;
                technov2 = ((turkce0 - rock0) + (turkce1 - rock1)) / 2;

                vtechno = ((technov0 + pop2) + (technov1 + techno2) + (technov2 + rock2)) / 3;

                if (vtechno > vbigg) {
                    vbigg = vtechno;
                    oneri = ("techno");
                }
                vbig = vbigg;
                son.child("degerler")
                        .child(firebaseAuth.getCurrentUser().getUid())
                        .child("deger").setValue(oneri);
                oneriii=oneriii.child(oneri);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        //if (drAudio.child("kind").toString().contains(oneri)) {
        super.onStart();
        FirebaseRecyclerAdapter<DownloadingAudioList, HomeFragment.DownloadingAudio> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DownloadingAudioList, HomeFragment.DownloadingAudio>(
                DownloadingAudioList.class,
                R.layout.downloadingaudiolist,
                HomeFragment.DownloadingAudio.class,
                drAudio
        ) {
            @Override
            protected void populateViewHolder(HomeFragment.DownloadingAudio viewHolder, DownloadingAudioList model, int position) {

                    final String audio_key = getRef(position).getKey();

                    viewHolder.setAudioTitle(model.getName());
                    viewHolder.setArtist(model.getArtist());
                    viewHolder.setKind(model.getKind());
                    viewHolder.setAudio(getActivity(), model.getAudioname());
                    viewHolder.setFav(getActivity(), model.getName(), model.getArtist(),model.getKind(),model.getAudioname());

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
                                    }} catch (IOException e) {
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
        }//}

    public static class DownloadingAudio extends RecyclerView.ViewHolder {

        View mView;

        public DownloadingAudio(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setAudioTitle(String audioTitle) {
            TextView AudioTitle = (TextView) mView.findViewById(R.id.tv_name);
            AudioTitle.setText(audioTitle);
        }

        public void setArtist(String artist) {
            TextView AudioArtist = (TextView) mView.findViewById(R.id.tv_artist);
            AudioArtist.setText(artist);
        }

        public void setKind(String kind) {
            TextView AudioKind = (TextView) mView.findViewById(R.id.tv_kind);
            AudioKind.setText(kind);
        }

        public void setAudio(Context context, String url) {
            FloatingActionButton fabPlay = (FloatingActionButton) mView.findViewById(R.id.fab_play);
        }
        public void setFav(final Context context, final String name,final String artist,final String kind,final String url) {
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

                            if (countt == 0) {
                                countt = countt + 1;
                                databaseReference.child("count")
                                        .child(firebaseAuth.getCurrentUser().getUid())
                                        .child(kind).setValue(countt);
                            } else if (countt == 1) {
                                countt = countt + 1;
                                databaseReference.child("count")
                                        .child(firebaseAuth.getCurrentUser().getUid())
                                        .child(kind).setValue(countt);
                            }
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

                            Toast.makeText(context, "Favorinize eklendi.", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(context, "HATA", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }
}



        /*
        ref.child("count").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot chatSnapshot) {
                            String key = chatSnapshot.getKey();
                                if(key != uid) {
                                    pop[0] = ((Long) chatSnapshot.child("count").child(key).child("pop").getValue()).intValue();
                                    rock[0] = ((Long) chatSnapshot.child("count").child(key).child("rock").getValue()).intValue();
                                    turkce[0] = ((Long) chatSnapshot.child("count").child(key).child("turkce").getValue()).intValue();
                                    techno[0] = ((Long) chatSnapshot.child("count").child(key).child("techno").getValue()).intValue();
                                    //i++;
                                }else{
                                    kpop[0] = ((Long) chatSnapshot.child("count").child(key).child("pop").getValue()).intValue();
                                    krock[0] = ((Long) chatSnapshot.child("count").child(key).child("rock").getValue()).intValue();
                                    kturkce[0] = ((Long) chatSnapshot.child("count").child(key).child("turkce").getValue()).intValue();
                                    ktechno[0] = ((Long) chatSnapshot.child("count").child(key).child("techno").getValue()).intValue();
                                    }
                         pop1 = (pop[0]-turkce[0]);
                        pop2 = (pop[0]-rock[0]);
                        pop3 = (pop[0]-techno[0]);

                        vpop = (pop1+kturkce[0] + pop2+krock[0] + pop3+ktechno[0])/3;
                        vbig=vpop;

                        rock1 = (rock[0]-turkce[0]);
                        rock2 = (rock[0]-pop[0]);
                        rock3 = (rock[0]-techno[0]);

                        vrock = (rock1+kturkce[0] + rock2+kpop[0] + rock3+ktechno[0])/3;

                        if(vrock>vbig){
                            vbig=vbig;
                        }

                        turkce1 = (turkce[0]-rock[0]);
                        turkce2 = (turkce[0]-pop[0]);
                        turkce3 = (turkce[0]-techno[0]);

                        vturkce = (turkce1+krock[0] + turkce2+kpop[0] + turkce3+ktechno[0])/3;

                        if(vturkce>vbig){
                            vbig=vturkce;
                        }

                        techno1 = (techno[0]-rock[0]);
                        techno2 = (techno[0]-pop[0]);
                        techno3 = (techno[0]-turkce[0]);

                        vtechno = (turkce1+krock[0] + turkce2+kpop[0] + turkce3+kturkce[0])/3;

                        if(vtechno>vbig){
                            vbig=vtechno;
                        }
                        son.child("degerler")
                                .child(firebaseAuth.getCurrentUser().getUid())
                                .child("deger").setValue(vbig);*/

