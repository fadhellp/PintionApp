package com.example.finalproject.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalproject.LoginActivity;
import com.example.finalproject.R;

public class ProfileFragment extends Fragment {

    private TextView tv_nama2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tv_nama2 = view.findViewById(R.id.tv_nama2); // Assuming you have a TextView with id tv_nama2 in your fragment_profile layout

        // Set up logout Button click listener
        Button logoutButton = view.findViewById(R.id.btnLogout);
        logoutButton.setOnClickListener(v -> logout());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get username from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("USER", MODE_PRIVATE);
        // Set username to TextView

        tv_nama2.setText(sharedPreferences.getString("nim",""));
    }

    private void logout() {
        // Clear shared preferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Redirect to login activity
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}
