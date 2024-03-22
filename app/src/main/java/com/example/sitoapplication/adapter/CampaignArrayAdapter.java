package com.example.sitoapplication.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sitoapplication.R;
import com.example.sitoapplication.common.DateSupport;
import com.example.sitoapplication.model.Campaign;
import com.example.sitoapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
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
            viewHolder.txtRemainDays = convertView.findViewById(R.id.txtRemainDays);
            viewHolder.txtCreatedUser = convertView.findViewById(R.id.txtCreatedUserListCampaignItem);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Campaign campaign = mylist.get(position);
        viewHolder.imgChienDich.setImageResource(R.drawable.ic_launcher_foreground);
        viewHolder.txtnameChienDich.setText(campaign.getName());

        viewHolder.txtRemainDays.setText("Còn " + DateSupport.getInstance().getRemainDays(new Date(), campaign.getDeadline()) + " ngày");
        
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("user").document(campaign.getCreatedUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User createdUser = document.toObject(User.class);
                        viewHolder.txtCreatedUser.setText(createdUser != null ? createdUser.getName() : "");
                    } else {
                        Log.e("TAG", "No such document");
                    }
                } else {
                    Log.e("TAG", "get failed with ", task.getException());
                }
            }
        });
        return convertView;
    }

    public void setCampaigns(List<Campaign> campaigns) {
        this.mylist = (ArrayList<Campaign>) campaigns;
        this.notifyDataSetChanged();
    }

    static class ViewHolder {
        ImageView imgChienDich;
        TextView txtnameChienDich;
        TextView txtRemainDays;
        TextView txtCreatedUser;
    }
}
