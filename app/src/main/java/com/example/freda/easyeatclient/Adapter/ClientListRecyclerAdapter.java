package com.example.freda.easyeatclient.Adapter;

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
public class ClientListRecyclerAdapter extends RecyclerView.Adapter<ClientListRecyclerAdapter.ViewHolder>{

    private LayoutInflater inflater;
    private List<HashMap<String, Object>> myData;
    private OnItemClickListener mOnItemClickListener;

    public ClientListRecyclerAdapter(Context context, List<HashMap<String, Object>> aVoid) {
        inflater = LayoutInflater.from(context);
        myData = aVoid;
    }

    public interface OnItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

    @Override
    public ClientListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.client_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.pic = (ImageView) view.findViewById(R.id.client_item_pic);
        viewHolder.title = (TextView) view.findViewById(R.id.client_item_text);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ClientListRecyclerAdapter.ViewHolder holder, final int position) {
        holder.pic.setImageResource((Integer) myData.get(position).get("image"));
        holder.title.setText((CharSequence) myData.get(position).get("title"));

        if( mOnItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });

            holder.itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
        ImageView pic;
        TextView title;
    }
}
