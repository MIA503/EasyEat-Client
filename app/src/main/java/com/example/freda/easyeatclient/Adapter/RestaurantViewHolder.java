package com.example.freda.easyeatclient.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.freda.easyeatclient.R;
import com.example.freda.easyeatclient.Utils.Constants;
import com.example.freda.easyeatclient.Utils.Restaurant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by freda on 8/30/16.
 */
public class RestaurantViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    View mView;
    Context mContext;
    ImageView restPic;
    TextView restTitle;
    TextView  restMark;
    TextView restDistance;
    TextView restTime;
    CardView cardView;

    public RestaurantViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindRestaurant(Restaurant restaurant){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final long ONE_MEGABYTE = 1024 * 1024;

        restPic = (ImageView) itemView.findViewById(R.id.rest_photo);
        restTitle = (TextView) itemView.findViewById(R.id.rest_title);
        restMark = (TextView) itemView.findViewById(R.id.rest_mark);
        restDistance=(TextView) itemView.findViewById(R.id.rest_distance);
        restTime = (TextView) itemView.findViewById(R.id.rest_time);
        cardView = (CardView) itemView.findViewById(R.id.rest_card);

        StorageReference storageRef = storage.getReferenceFromUrl("gs://easyeatclient.appspot.com");
        StorageReference storageImg = storageRef.child("image/"+restaurant.getImg());

        storageImg.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>(){

            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                restPic.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener(){

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

            restDistance.setBackgroundColor(Color.argb(20,0,0,0));
            restTitle.setBackgroundColor(Color.argb(20,0,0,0));
            restMark.setBackgroundColor(Color.argb(20,0,0,0));

        restMark.setText("Rating: "+((double) restaurant.getMark())+"/5");
        restTime.setText(restaurant.getTimes());
        restTitle.setText(restaurant.getName());



    }


    @Override
    public void onClick(View v) {
        final ArrayList<Restaurant> restaurants = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RESTAURANTS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    restaurants.add(snapshot.getValue(Restaurant.class));
                }

                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("restaurants", Parcels.wrap(restaurants));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
