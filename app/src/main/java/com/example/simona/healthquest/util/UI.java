package com.example.simona.healthquest.util;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.simona.healthquest.fragment.BaseFragment;

/**
 * Created by Simona on 7/17/2017.
 */

public class UI {

    public static void addFragment(FragmentManager fm, int layout, BaseFragment fragment, boolean addToBackStack, int enterAnimation, int exitAnimation) {
        FragmentTransaction ft = fm.beginTransaction();
        if(enterAnimation != 0 || exitAnimation != 0){
            ft.setCustomAnimations(enterAnimation, exitAnimation, enterAnimation, exitAnimation);
        }
        ft.add(layout, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    public static void replaceFragment(FragmentManager fm, int layout, BaseFragment fragment, boolean addToBackStack, int enterAnimation, int exitAnimation) {
        FragmentTransaction ft = fm.beginTransaction();
        if(enterAnimation != 0 || exitAnimation != 0){
            ft.setCustomAnimations(enterAnimation, exitAnimation, enterAnimation, exitAnimation);
        }
        ft.replace(layout, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    public static void popUpBackstack(FragmentManager fm) {
        fm.popBackStackImmediate();
    }

    public static void clearBackstack(FragmentManager fm) {
        while (fm.getBackStackEntryCount() > 0) {
            fm.popBackStackImmediate();
        }
    }

    public static void backToMain(FragmentManager fm) {
        while (fm.getBackStackEntryCount() > 1) {
            fm.popBackStackImmediate();
        }
    }

}
