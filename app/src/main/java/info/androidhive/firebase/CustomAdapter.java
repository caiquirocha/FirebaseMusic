package info.androidhive.firebase;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
public class CustomAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<SongModel> tarifList;
    public CustomAdapter(Activity activity, ArrayList<SongModel> tarifList){
        layoutInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tarifList=tarifList;}
    @Override
    public int getCount() {
        return tarifList.size();
    }
    @Override
    public Object getItem(int position) {
        return tarifList.get(getCount()-position-1);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SongModel tarif = (SongModel) getItem(position);
        View satir = layoutInflater.inflate(R.layout.downloadingaudiolist, null);
        TextView name = (TextView) satir.findViewById(R.id.tv_name);
        TextView artist = (TextView) satir.findViewById(R.id.tv_artist);
        TextView kind = (TextView) satir.findViewById(R.id.tv_kind);
       // FloatingActionButton = (FloatingActionButton)satir.findViewById(R.id.fab_play);
        name.setText(tarif.getName());
        artist.setText(tarif.getArtist());
        kind.setText(tarif.getKind());
        return satir;
    }}
