package com.myhexaville.login.views.lists.notifications;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myhexaville.login.R;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.NotificationViewHolder>{

    public String[] data;
    public NotificationRecyclerViewAdapter(String[] data){
        this.data = data;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notificationitem,parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        String title = data[position];
        holder.notification.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{
        ImageView userImg;
        TextView notification;
        public NotificationViewHolder(View itemView) {
            super(itemView);
            userImg = (ImageView) itemView.findViewById(R.id.userImg);
            notification = (TextView) itemView.findViewById(R.id.notification);

        }
    }
}
