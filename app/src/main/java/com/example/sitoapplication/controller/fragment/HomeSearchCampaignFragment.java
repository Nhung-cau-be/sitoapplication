package com.example.sitoapplication.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sitoapplication.R;
import com.example.sitoapplication.adapter.CampaignArrayAdapter;
import com.example.sitoapplication.adapter.HomeSearchUserArrayAdapter;
import com.example.sitoapplication.model.Campaign;
import com.example.sitoapplication.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchCampaignFragment extends Fragment {
    ListView listView;
    CampaignArrayAdapter campaignArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_search_campaign, container, false);

        listView = view.findViewById(R.id.fragment_home_search_campaign_list_view);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("campaign")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Campaign> campaigns = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                campaigns.add(document.toObject(Campaign.class));
                            }
                            campaignArrayAdapter = new CampaignArrayAdapter(getActivity(), R.layout.list_campaign_item, (ArrayList<Campaign>) campaigns);
                            listView.setAdapter(campaignArrayAdapter);
                        } else {
                            Log.d("HomeSearchUserFragment", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return view;
    }
}