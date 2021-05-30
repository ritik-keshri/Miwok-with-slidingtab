package com.example.android.miwok;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class FragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {

    private Context context;

    public FragmentPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        this.context = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new NumbersFragment();
        else if (position == 1)
            return new FamilyFragment();
        else if (position == 2)
            return new ColorsFragment();
        else
            return new PhrasesFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return context.getString(R.string.category_numbers);
        else if (position == 1)
            return context.getString(R.string.category_family);
        else if (position == 2)
            return context.getString(R.string.category_colors);
        else
            return context.getString(R.string.category_phrases);
    }
}