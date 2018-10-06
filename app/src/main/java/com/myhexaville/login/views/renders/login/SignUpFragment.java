package com.myhexaville.login.views.renders.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myhexaville.login.R;
import com.myhexaville.login.models.User;

public class SignUpFragment extends Fragment implements OnSignUpListener{
    private static final String TAG = "SignUpFragment";

    private EditText phone;

    private EditText pass;

    private EditText passCon;

    DatabaseReference databaseUsers;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_signup, container, false);

        phone = inflate.findViewById(R.id.phoneSignup);        phone = inflate.findViewById(R.id.phoneSignup);
        pass = inflate.findViewById(R.id.password);
        passCon = inflate.findViewById(R.id.passwordCon);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        return inflate;
    }

    @Override
    public void signUp() {
//        Toast.makeText(getContext(), "Sign up", Toast.LENGTH_SHORT).show();
//        Intent register = new Intent(this, Register.class);
//        startActivity(register);
        if(!phone.getText().toString().isEmpty() && !pass.getText().toString().isEmpty() && !passCon.getText().toString().isEmpty()){
            if(pass.getText().toString().equals(passCon.getText().toString())){
                String id = databaseUsers.push().getKey();
                User user = new User(id, phone.getText().toString(), pass.getText().toString());
                databaseUsers.child(id).setValue(user);
                Toast.makeText(getContext(), "Registration Completed", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Passwords not equal", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(), "Please fill fields", Toast.LENGTH_SHORT).show();
        }
    }
}
