package com.myhexaville.login.views.lists.notifications;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myhexaville.login.R;
import com.myhexaville.login.views.lists.conversations.ListItem;
import com.myhexaville.login.views.lists.conversations.RecyclerViewAdapter;

import java.util.List;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder>{

    private List<NotificationItem> notificationItems;
    private Context mContext;
    public String[] data;
    public NotificationRecyclerViewAdapter(List<NotificationItem> notificationItems, Context mContext){
        this.notificationItems = notificationItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NotificationRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notificationitem,parent, false);
        NotificationRecyclerViewAdapter.ViewHolder holder = new NotificationRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NotificationRecyclerViewAdapter.ViewHolder holder, int position) {
        NotificationItem notificationItem = notificationItems.get(position);

        holder.heading.setText(notificationItem.getNotification());
    }

    @Override
    public int getItemCount() {
        return notificationItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView heading;
        RelativeLayout parent;

        public ViewHolder(View itemView){
            super(itemView);
            heading = itemView.findViewById(R.id.notification);
            parent = itemView.findViewById(R.id.parent_layout_notification);
        }
    }
}
