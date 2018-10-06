package com.myhexaville.login.views.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.myhexaville.login.R;
import com.myhexaville.login.controllers.ContactsController;
import com.myhexaville.login.controllers.VerificationController;
import com.myhexaville.login.models.Contacts;
import com.myhexaville.login.models.User;
import com.myhexaville.login.views.lists.conversations.ListItem;
import com.myhexaville.login.views.lists.conversations.RecyclerViewAdapter;
import com.myhexaville.login.views.renders.FragmentView;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<ListItem> listItems;

    ContactsController cd;
    Cursor res;

    DatabaseReference databaseUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listItems = new ArrayList<>();


        cd = new ContactsController(getContext());
         res = cd.getContacts();
        if(res.getCount() > 0){

            while(res.moveToNext()){
                ListItem listItem = new ListItem(
                        res.getString(0),
                        "Lorem Ipsum"
                );
                listItems.add(listItem);
            }

            adapter = new RecyclerViewAdapter(listItems, this.getContext());

            recyclerView.setAdapter(adapter);
        }else{
            VerificationController db = new VerificationController(getContext());
            String verificationCode = "";
             res = db.getVerifiedUser();
            if(res.getCount() == 1){
                while(res.moveToNext()){
                    verificationCode = res.getString(0);
                }
            }

//                Add to firebase contact
            databaseUser = FirebaseDatabase.getInstance().getReference("users").child(verificationCode).child("contacts");
            String finalVerificationCode = verificationCode;
            cd = new ContactsController(getContext());

            databaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot contactSnapshot : dataSnapshot.getChildren()) {
                        Contacts contacts = contactSnapshot.getValue(Contacts.class);
                        cd.insertContact(contacts.getId(), contacts.getNumber());
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Toast.makeText(getContext(), "Network connection not found", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }
}
