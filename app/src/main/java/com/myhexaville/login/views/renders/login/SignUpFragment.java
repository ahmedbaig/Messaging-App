package com.myhexaville.login.views.renders.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myhexaville.login.R;

public class SignUpFragment extends Fragment implements OnSignUpListener{
    private static final String TAG = "SignUpFragment";
    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_signup, container, false);


        return inflate;
    }

    @Override
    public void signUp() {
        Toast.makeText(getContext(), "Sign up", Toast.LENGTH_SHORT).show();
//        Intent register = new Intent(this, Register.class);
//        startActivity(register);
    }
}
