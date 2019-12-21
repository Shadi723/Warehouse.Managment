package com.example.warehousemanagment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager.widget.ViewPager;

import com.example.warehousemanagment.ali.fragments.MainOperationsFragment;
import com.example.warehousemanagment.ali.fragments.MainOverViewFragment;
import com.example.warehousemanagment.ali.fragments.ProductsFragment;
import com.example.warehousemanagment.ali.utils.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainFragment2 extends Fragment implements View.OnClickListener {

    private FragmentActivity fragmentActivity;

    private String firstTabName;
    private String secondTabName;
    private String thirdTabName;

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
        thirdTabName = "Products";
    }

    private void setUpViewPager(View view) {

        Fragment firstFragment = new MainOverViewFragment();
        Fragment secondFragment = new MainOperationsFragment();
        Fragment thirdFragment = new ProductsFragment();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(fragmentActivity.getSupportFragmentManager());

        sectionsPagerAdapter.addFragment(firstFragment);
        sectionsPagerAdapter.addFragment(secondFragment);
        sectionsPagerAdapter.addFragment(thirdFragment);


        ViewPager viewPager = view.findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(fragmentActivity.getResources().getColor(R.color.color_white));
        //tabLayout.setSelectedTabIndicator(fragmentActivity.getResources().getDrawable(R.drawable.button_secondary_enabled));
        tabLayout.setTabIndicatorFullWidth(false);

        TabLayout.Tab firstTab = tabLayout.getTabAt(0);
        TabLayout.Tab secondTab = tabLayout.getTabAt(1);
        TabLayout.Tab thirdTab = tabLayout.getTabAt(2);

        if (firstTab != null) {
            firstTab.setText(firstTabName);
        }

        if (secondTab != null) {
            secondTab.setText(secondTabName);
        }

        if (thirdTab != null) {
            thirdTab.setText(thirdTabName);
        }
    }

}
