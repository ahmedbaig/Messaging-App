package com.myhexaville.login.views.renders.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.myhexaville.login.R;
import com.myhexaville.login.controllers.VerificationController;
import com.myhexaville.login.models.User;
import com.myhexaville.login.views.renders.FragmentView;

public class LoginFragment extends Fragment implements OnLoginListener{
    private static final String TAG = "LoginFragment";
    private String userid;
    private EditText phone;
    private EditText verification;
    DatabaseReference databaseUser;
    VerificationController db;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        inflate.findViewById(R.id.forgot_password).setOnClickListener(v ->
                Toast.makeText(getContext(), "Forgot password clicked", Toast.LENGTH_SHORT).show());
        phone = inflate.findViewById(R.id.phone);
        verification = inflate.findViewById(R.id.verificationCode);
        databaseUser = FirebaseDatabase.getInstance().getReference("users");
        db = new VerificationController(getContext());
        return inflate;
    }

    @Override
    public void login() {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Please Wait...", "Processing...", true);
//        Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
        if(!phone.getText().toString().isEmpty() && !verification.getText().toString().isEmpty()){
            try{
                Query query = databaseUser.orderByChild("phone");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (true) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                if(userSnapshot.exists()){
                                    User user = userSnapshot.getValue(User.class);
                                    if (user.getPass().equals(verification.getText().toString()) && user.getPhone().equals(phone.getText().toString())) {
                                        if(db.insertVerificationCode(user.getId())){
                                            Toast.makeText(getContext(), "Session Saved", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getContext(), "Database failure", Toast.LENGTH_SHORT).show();
                                        }
                                        userid = user.getId();
                                        Bundle data = new Bundle();
                                        data.putString("verificationCode", userid);
                                        progressDialog.dismiss();
                                        Intent fragmentView = new Intent(getContext(), FragmentView.class);
                                        fragmentView.putExtras(data);
                                        startActivity(fragmentView);
                                        getActivity().finish();
                                    }else{
                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(), "Network connection not found", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Network connection not found", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Network connection not found", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch(Exception e){
                progressDialog.dismiss();
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else{
            progressDialog.dismiss();
            Toast.makeText(getContext(), "Please fill fields", Toast.LENGTH_SHORT).show();
        }


    }


}
