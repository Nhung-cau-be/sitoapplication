package com.example.sitoapplication.controller.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sitoapplication.R;
import com.example.sitoapplication.adapter.CampaignArrayAdapter;
import com.example.sitoapplication.model.Campaign;
import com.example.sitoapplication.model.HomeSearchViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchCampaignFragment extends Fragment {
    private ListView listView;
    private CampaignArrayAdapter campaignArrayAdapter;
    private HomeSearchViewModel homeSearchViewModel;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_search_campaign, container, false);

        listView = view.findViewById(R.id.fragment_home_search_campaign_list_view);

        db = FirebaseFirestore.getInstance();
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
                            Log.d("HomeSearchCampaignFragment", "Error getting documents: ", task.getException());
                        }
                    }
                });

        homeSearchViewModel = new ViewModelProvider(requireActivity()).get(HomeSearchViewModel.class);
        homeSearchViewModel.getSearchString().observe(getViewLifecycleOwner(), item -> {
            db.collection("campaign")
                    .whereGreaterThanOrEqualTo("name", item)
                    .whereLessThanOrEqualTo("name", item + "\uf8ff")
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
                                Log.d("HomeSearchCampaignFragment", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        });
        return view;
    }
}