package com.myhexaville.login.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myhexaville.login.R;
import com.myhexaville.login.views.lists.conversations.ListItem;
import com.myhexaville.login.views.lists.conversations.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<ListItem> listItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);


        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listItems = new ArrayList<>();

        for(int i = 0; i <=10; i++){
            ListItem listItem = new ListItem(
                    "heading "+i,
                    "Lorem Ipsum"
            );
            listItems.add(listItem);
        }
        adapter = new RecyclerViewAdapter(listItems, this.getContext());

        recyclerView.setAdapter(adapter);

        return view;
    }
}
