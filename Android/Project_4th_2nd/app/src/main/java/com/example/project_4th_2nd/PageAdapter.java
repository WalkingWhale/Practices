package com.example.project_4th_2nd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    String id;
    String username;
    String docNum;

    public PageAdapter(FragmentManager fm, int NumOfTabs, String id, String userName, String docNum){
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.id = id;
        this.username = userName;
        this.docNum = docNum;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        Bundle bundle;

        switch (position){
            case 0:
                bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("username", username);
                bundle.putString("docNum", docNum);
                MainFragment mainFragment = new MainFragment();
                mainFragment.setArguments(bundle);

                fragment = mainFragment;
                break;
            case 1:
                bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("username", username);
                bundle.putString("docNum", docNum);
                LocationSearchFragment locationSearchFragment = new LocationSearchFragment();
                locationSearchFragment.setArguments(bundle);

                fragment = locationSearchFragment;
                break;
            case 2:
                bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("username", username);
                bundle.putString("docNum", docNum);
                SearchNameFragment searchNameFragment = new SearchNameFragment();
                searchNameFragment.setArguments(bundle);

                fragment = searchNameFragment;
                break;
            case 3:
                bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("username", username);
                bundle.putString("docNum", docNum);
                MypageFragment mypageFragment = new MypageFragment();
                mypageFragment.setArguments(bundle);

                fragment = mypageFragment;
                break;
            default:
                return null;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
