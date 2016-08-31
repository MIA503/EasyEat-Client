package com.example.freda.easyeatclient;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.freda.easyeatclient.Adapter.RestaurantViewHolder;
import com.example.freda.easyeatclient.ClientAdmin.ClientMainFragment;
import com.example.freda.easyeatclient.Utils.Constants;
import com.example.freda.easyeatclient.Utils.Restaurant;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends BaseActivity {

    public ProgressDialog mProgressDialog;

    private Toolbar toolbar;
    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private RecyclerView restList;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    private ClientMainFragment mClientMainFragment;



    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;

        Firebase.setAndroidContext(this);
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RESTAURANTS);

        initView();
        setupRecyclerview();
        setDefaultFragment();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());

        toolbar.setTitle("EasyEat");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

    }


    private void setupRecyclerview() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Restaurant,RestaurantViewHolder>(
                Restaurant.class,
                R.layout.rest_item,
                RestaurantViewHolder.class,
                databaseReference
        ){
            @Override
            protected void populateViewHolder(RestaurantViewHolder viewHolder,
                                              Restaurant model, int position) {
                viewHolder.bindRestaurant(model);
            }
        };
        restList.setHasFixedSize(true);
        restList.setLayoutManager(new LinearLayoutManager(this));
        restList.setAdapter(mFirebaseAdapter);
    }



    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mClientMainFragment = new ClientMainFragment();
        transaction.replace(R.id.client_fragment,mClientMainFragment);
        transaction.commit();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        restList = (RecyclerView) findViewById(R.id.restlist);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
