package com.example.wangbotian;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
/**
 实体适配器类
 */
public class EntityViewAdapter extends FragmentStatePagerAdapter {


    public EntityViewAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new EntityPropertyFragment();
            case 1:
                return new EntityRelationFragment();
            case 2:
                return new EntityExamFragment();
            default:
                return new EntityPropertyFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
