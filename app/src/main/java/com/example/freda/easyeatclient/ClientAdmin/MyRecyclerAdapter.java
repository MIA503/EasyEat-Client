package com.example.freda.easyeatclient.ClientAdmin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.freda.easyeatclient.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by freda on 8/14/16.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<HashMap<String, Object>> myData;

    public MyRecyclerAdapter(Context context, List<HashMap<String, Object>> aVoid) {
        inflater = LayoutInflater.from(context);
        myData = aVoid;
    }

    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.client_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.pic = (ImageView) view.findViewById(R.id.client_item_pic);
        viewHolder.title = (TextView) view.findViewById(R.id.client_item_text);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, int position) {
        holder.pic.setImageResource((Integer) myData.get(position).get("image"));
        holder.title.setText((CharSequence) myData.get(position).get("title"));
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        ImageView pic;
        TextView title;
    }
}
