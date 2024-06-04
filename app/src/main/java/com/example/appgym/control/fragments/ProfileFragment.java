package com.example.appgym.control.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.appgym.R;
import com.example.appgym.control.LoginActivity;
import com.example.appgym.utils.SessionManager;

public class ProfileFragment extends BaseFragment {
    private TextView username, userEmail, userHeight, userWeight;
    private Button btnLogout;
    private SessionManager sessionManager;
    private int title = R.string.title_profile;
    private int menu = 0;
    public ProfileFragment() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setMenu(getString(title), menu);

        username = view.findViewById(R.id.username);
        userEmail = view.findViewById(R.id.userEmail);
        userHeight = view.findViewById(R.id.userHeight);
        userWeight = view.findViewById(R.id.userWeight);
        btnLogout = view.findViewById(R.id.btnLogout);
        sessionManager = new SessionManager(requireContext());

        loadData();

        btnLogout.setOnClickListener(view1 -> {
            logout();
        });

    }

    private void loadData() {
        username.setText(sessionManager.getUsername());
        userEmail.setText(sessionManager.getEmail());
        userHeight.setText(String.valueOf(sessionManager.getHeight()));
        userWeight.setText(String.valueOf(sessionManager.getWeight()));
    }

    private void logout() {
        sessionManager.logout();
        if (!sessionManager.isLogged()){
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}