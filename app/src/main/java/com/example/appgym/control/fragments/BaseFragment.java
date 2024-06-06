package com.example.appgym.control.fragments;

import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.appgym.control.MainActivity;

public abstract class BaseFragment extends Fragment{
    private String title = null;
    private int menu = 0;

    public BaseFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (this.menu != 0){
            inflater.inflate(this.menu, menu);
        }
        ((MainActivity) getActivity()).getActivityToolbar().setTitle(title);
    }

    public void setMenu(String titulo, int menu) {
        this.title = titulo;
        this.menu = menu;
    }

}
