package com.example.sitoapplication.controller.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sitoapplication.R;
import com.example.sitoapplication.adapter.HomeSearchUserArrayAdapter;
import com.example.sitoapplication.model.HomeSearchViewModel;
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

public class HomeSearchUserFragment extends Fragment {
    private HomeSearchUserArrayAdapter homeSearchUserArrayAdapter;
    private GridView gridView;
    private HomeSearchViewModel homeSearchViewModel;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_search_user, container, false);

        gridView = view.findViewById(R.id.fragment_home_search_user_grid_view);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<User> users = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                if((currentUser == null || !user.getId().equals(currentUser.getUid()))) {
                                    users.add(user);
                                }
                            }
                            homeSearchUserArrayAdapter = new HomeSearchUserArrayAdapter(getContext(), (ArrayList<User>) users);
                            gridView.setAdapter(homeSearchUserArrayAdapter);
                        } else {
                            Log.d("HomeSearchUserFragment", "Error getting documents: ", task.getException());
                        }
                    }
                });

        homeSearchViewModel = new ViewModelProvider(requireActivity()).get(HomeSearchViewModel.class);
        homeSearchViewModel.getSearchString().observe(getViewLifecycleOwner(), item -> {
            db.collection("user")
                    .whereGreaterThanOrEqualTo("name", item)
                    .whereLessThanOrEqualTo("name", item + "\uf8ff")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<User> users = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    if((currentUser == null || !user.getId().equals(currentUser.getUid()))) {
                                        users.add(user);
                                    }
                                }
                                homeSearchUserArrayAdapter = new HomeSearchUserArrayAdapter(getContext(), (ArrayList<User>) users);
                                gridView.setAdapter(homeSearchUserArrayAdapter);
                            } else {
                                Log.d("HomeSearchUserFragment", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        });
        return view;
    }
}