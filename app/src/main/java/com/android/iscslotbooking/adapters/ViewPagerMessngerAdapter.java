package com.android.iscslotbooking.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.iscslotbooking.MWF_Fragment;
import com.android.iscslotbooking.TTS_Fragment;

public class ViewPagerMessngerAdapter extends FragmentPagerAdapter {
    public ViewPagerMessngerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0) {
            return new MWF_Fragment();
        }

        else
            return new TTS_Fragment();
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "M-W-F";
        }

        else
            return "T-T-S";
    }


}
