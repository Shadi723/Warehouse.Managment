package com.example.warehousemanagment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.warehousemanagment.ali.MainOperationsFragment;
import com.example.warehousemanagment.ali.MainOverViewFragment;
import com.example.warehousemanagment.ali.utils.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainFragment2 extends Fragment implements View.OnClickListener {

    NavController navController;

    private FragmentActivity fragmentActivity;

    private String firstTabName;
    private String secondTabName;

    private Fragment firstFragment;
    private Fragment secondFragment;

    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_main,container,false);
        //navController = Navigation.findNavController(fragmentActivity, R.id.nav_host_fragment);
        //return view;
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        setUpViewPager(view);
    }

    @Override
    public void onClick(View v) {


    }

    private void initViews(View view) {

        fragmentActivity = getActivity();

        tabLayout = view.findViewById(R.id.tabLayout);

        firstTabName = fragmentActivity.getResources().getString(R.string.tab_over_view);
        secondTabName = fragmentActivity.getResources().getString(R.string.tab_operations);
    }

    private void setUpViewPager(View view) {

        firstFragment = new MainOverViewFragment();
        secondFragment = new MainOperationsFragment();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(fragmentActivity.getSupportFragmentManager());

        if (firstFragment != null) {

            sectionsPagerAdapter.addFragment(firstFragment);
        }
        if (secondFragment != null) {

            sectionsPagerAdapter.addFragment(secondFragment);
        }

        ViewPager viewPager = view.findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(fragmentActivity.getResources().getColor(R.color.color_white));
        //tabLayout.setSelectedTabIndicator(fragmentActivity.getResources().getDrawable(R.drawable.button_secondary_enabled));
        tabLayout.setTabIndicatorFullWidth(false);

        TabLayout.Tab firstTab = tabLayout.getTabAt(0);
        TabLayout.Tab secondTab = tabLayout.getTabAt(1);

        if (firstTab != null) {
            firstTab.setText(firstTabName);
        }

        if (secondTab != null) {
            secondTab.setText(secondTabName);
        }

    }

}
