package com.example.sitoapplication.controller.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sitoapplication.R;
import com.example.sitoapplication.entity.Campaign;

import java.util.ArrayList;
import java.util.List;

public class CampaignArrayAdapter extends ArrayAdapter<Campaign> {
    Activity context;
    int idlayout;
    ArrayList<Campaign> mylist;

    public CampaignArrayAdapter(Activity context, int idlayout, List<Campaign> mylist) {
        super(context, idlayout, mylist);
        this.context = context;
        this.idlayout = idlayout;
        this.mylist = (ArrayList<Campaign>) mylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(idlayout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgChienDich = convertView.findViewById(R.id.imgChienDich);
            viewHolder.txtnameChienDich = convertView.findViewById(R.id.txtnameChienDich);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Campaign campaign = mylist.get(position);
        viewHolder.imgChienDich.setImageResource(R.drawable.ic_launcher_foreground);
        viewHolder.txtnameChienDich.setText(campaign.getName());

        return convertView;
    }

    public void setCampaigns(List<Campaign> campaigns) {
        this.mylist = (ArrayList<Campaign>) campaigns;
        this.notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView imgChienDich;
        TextView txtnameChienDich;
    }
}
