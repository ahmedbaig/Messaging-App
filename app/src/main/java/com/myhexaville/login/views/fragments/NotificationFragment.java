package com.myhexaville.login.views.fragments;

import android.app.Notification;
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
import com.google.firebase.database.ValueEventListener;
import com.myhexaville.login.R;
import com.myhexaville.login.controllers.ContactsController;
import com.myhexaville.login.controllers.NotificationController;
import com.myhexaville.login.controllers.VerificationController;
import com.myhexaville.login.models.Contacts;
import com.myhexaville.login.models.Notifications;
import com.myhexaville.login.views.lists.conversations.ListItem;
import com.myhexaville.login.views.lists.conversations.RecyclerViewAdapter;
import com.myhexaville.login.views.lists.notifications.NotificationItem;
import com.myhexaville.login.views.lists.notifications.NotificationRecyclerViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    private NotificationRecyclerViewAdapter adapter;
    Cursor res;
    DatabaseReference databaseUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        RecyclerView notificationRecycler = view.findViewById(R.id.notificationRecycler);
        notificationRecycler.setHasFixedSize(true);
        notificationRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        List<NotificationItem> NotificationItem = new ArrayList<>();


        NotificationController nd = new NotificationController(getContext());
        res = nd.getNotifications();
        if(res.getCount() > 0){

            while(res.moveToNext()){
                NotificationItem notificationItem = new NotificationItem(
                        res.getString(0)
                );
                NotificationItem.add(notificationItem);
            }

            adapter = new NotificationRecyclerViewAdapter(NotificationItem, this.getContext());

            notificationRecycler.setAdapter(adapter);
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
            databaseUser = FirebaseDatabase.getInstance().getReference("users").child(verificationCode).child("notifications");
            nd = new NotificationController(getContext());

            NotificationController finalNd = nd;
            databaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot notificationSnapshot : dataSnapshot.getChildren()) {
                        for(DataSnapshot innerSnapshot: notificationSnapshot.getChildren()){
                            if(innerSnapshot.getKey().equals("notification")){
                                finalNd.insertNotification(innerSnapshot.getValue().toString());
                            }
                        }
//                        Notifications notification = notificationSnapshot.getValue(Notifications.class);
                    }
                    NotificationController nd = new NotificationController(getContext());
                    res = nd.getNotifications();
                    if(res.getCount() > 0){

                        while(res.moveToNext()){
                            NotificationItem notificationItem = new NotificationItem(
                                    res.getString(0)
                            );
                            NotificationItem.add(notificationItem);
                        }

                        adapter = new NotificationRecyclerViewAdapter(NotificationItem, getContext());

                        notificationRecycler.setAdapter(adapter);
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
