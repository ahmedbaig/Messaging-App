
        package com.myhexaville.login.views.lists.conversations;

        import android.content.Context;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.myhexaville.login.R;
        import com.myhexaville.login.views.renders.Message;

        import java.util.List;

        public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

            private static final String TAG = "RecyclerViewAdapter";

            private List<ListItem> listItems;
            private Context mContext;

            public RecyclerViewAdapter(List<ListItem> listItems, Context mContext) {
                this.listItems = listItems;
                this.mContext = mContext;
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent, false);
                ViewHolder holder = new ViewHolder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder( ViewHolder holder, int position) {
                Log.d(TAG, "onBindViewHolder: called");
                ListItem listItem = listItems.get(position);

                holder.heading.setText(listItem.getNum());
                holder.parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent messageWindow = new Intent(mContext, Message.class);
                        messageWindow.putExtra("number", listItem.getNum().toString());
                        mContext.startActivity(messageWindow);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return listItems.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder{

                TextView heading;
                RelativeLayout parent;

                public ViewHolder(View itemView){
                    super(itemView);
                    heading = itemView.findViewById(R.id.heading);
                    parent = itemView.findViewById(R.id.parent_layout);
                }
            }
        }