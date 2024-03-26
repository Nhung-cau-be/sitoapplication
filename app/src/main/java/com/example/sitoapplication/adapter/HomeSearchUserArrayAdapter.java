package com.example.sitoapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sitoapplication.R;
import com.example.sitoapplication.model.User;

import java.util.ArrayList;

public class HomeSearchUserArrayAdapter extends ArrayAdapter<User> {
    public HomeSearchUserArrayAdapter(@NonNull Context context, ArrayList<User> userArrayList) {
        super(context, 0, userArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_search_user_item, parent, false);
        }

        User user = getItem(position);
        TextView txtUserName = listitemView.findViewById(R.id.fmt_home_search_user_item_name);

        txtUserName.setText(user.getName());
        return listitemView;
    }
}
