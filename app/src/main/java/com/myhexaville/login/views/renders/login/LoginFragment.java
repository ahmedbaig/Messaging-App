package com.myhexaville.login.views.renders.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.myhexaville.login.R;
import com.myhexaville.login.views.renders.FragmentView;

public class LoginFragment extends Fragment implements OnLoginListener{
    private static final String TAG = "LoginFragment";

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        inflate.findViewById(R.id.forgot_password).setOnClickListener(v ->
                Toast.makeText(getContext(), "Forgot password clicked", Toast.LENGTH_SHORT).show());

        return inflate;
    }

    @Override
    public void login() {
        Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
        //TODO: Firebase login check and execute
        Intent fragmentView = new Intent(this.getContext(), FragmentView.class);
        //TODO: Get the document ID from firebase too and push to next intent.
        startActivity(fragmentView);
    }
}
