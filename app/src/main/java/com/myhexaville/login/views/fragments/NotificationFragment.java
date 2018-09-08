package com.myhexaville.login.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myhexaville.login.R;
import com.myhexaville.login.views.lists.notifications.NotificationRecyclerViewAdapter;


public class NotificationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        String[] data = {"Jhonny sent you a friend request", "Diana sent you a friend request", "Albert accepted you a friend request"};
        RecyclerView notificationRecycler = view.findViewById(R.id.notificationRecycler);
        notificationRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        notificationRecycler.setAdapter(new NotificationRecyclerViewAdapter(data));

        return view;
    }

}
