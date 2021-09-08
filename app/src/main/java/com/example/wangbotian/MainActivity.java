package com.example.wangbotian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.*;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;


import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import io.github.vejei.bottomnavigationbar.BottomNavigationBar;
/**
 MainActivity
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private ViewPagerAdapter viewPagerAdapter;
    private MaterialToolbar toolbar;
    int fragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle("首页");
        viewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

//        int menuItemId = bottomNavigationView.getMenu().getItem(2).getItemId();
//        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(menuItemId);
//        badge.setNumber(99);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(5);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        toolbar.setTitle("首页");
                        fragmentId = 0;
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.question).setChecked(true);
                        toolbar.setTitle("发现");
                        fragmentId = 1;
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.account).setChecked(true);
                        toolbar.setTitle("我");
                        fragmentId = 2;
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        viewPager.setCurrentItem(0);
                        toolbar.setTitle("首页");
                        fragmentId = 0;
                        break;
                    case R.id.question:
                        viewPager.setCurrentItem(1);
                        toolbar.setTitle("发现");
                        fragmentId = 1;
                        break;
                    case R.id.account:
                        viewPager.setCurrentItem(2);
                        toolbar.setTitle("我");
                        fragmentId = 2;
                        break;
                }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        toolbar.setOnMenuItemClickListener(new MaterialToolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.search_on_topbar:
                        Intent intent1 = new Intent();
                        intent1.setClass(MainActivity.this, SearchActivity.class);
                        intent1.putExtra("fragment_id", fragmentId);
                        MainActivity.this.startActivity(intent1);
                        MainActivity.this.finish();
                        break;
                    case R.id.link_on_topbar:
                        Intent intent2 = new Intent();
                        intent2.setClass(MainActivity.this, LinkActivity.class);
                        intent2.putExtra("fragment_id", fragmentId);
                        MainActivity.this.startActivity(intent2);
                        MainActivity.this.finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        int id = getIntent().getIntExtra("id", -1);
        if (id == 1){
            Fragment fragment = new QuestionFragment();
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            transaction.replace(R.id.viewpager, fragment);
            transaction.commit();
            viewPager.setCurrentItem(id);
        }
        else if (id == 0){
            Fragment fragment = new HomeFragment();
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            transaction.replace(R.id.viewpager, fragment);
            transaction.commit();
            viewPager.setCurrentItem(id);
        }else if(id == 2){
            Fragment fragment = new AccountFragment();
            FragmentManager manger = getSupportFragmentManager();
            FragmentTransaction transaction = manger.beginTransaction();
            transaction.replace(R.id.viewpager, fragment);
            transaction.commit();
            viewPager.setCurrentItem(id);
        }
        super.onResume();
    }

    public static String convertI2C(int id) {
        String title = "";
        switch (id) {
            case 0:
                title = "首页";
                break;
            case 1:
                title = "发现";
                break;
            case 2:
                title = "我";
                break;
            default:
                break;
        }
        return title;
    }
}

