package com.example.sitoapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sitoapplication.controller.fragment.HomeSearchCampaignFragment;
import com.example.sitoapplication.controller.fragment.HomeSearchUserFragment;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class HomeSearchViewPaperAdapter extends FragmentStateAdapter {
    private final ArrayList<Fragment> fragments = new ArrayList<>();

    public HomeSearchViewPaperAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments.add(new HomeSearchUserFragment());
        fragments.add(new HomeSearchCampaignFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
