package com.example.freda.easyeatclient.ClientAdmin;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freda.easyeatclient.R;
import com.example.freda.easyeatclient.Utils.CircleImg;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by freda on 8/14/16.
 */
public class ClientMainFragment extends Fragment implements View.OnClickListener{

    private static final int REQUESTCODE_PICK = 0;
    private static final int REQUESTCODE_TAKE = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    private static final String IMAGE_FILE_NAME = "clientPic.jpg";
    private static final String TAG = "FacebookLogin";

    private RecyclerView clientList;
    private CircleImg clientPicImg;
    private ImageView editImg;
    private TextView username;
    private TextView email;

    private Button signOutButton;
    private LoginButton loginButton;

    private MyRecyclerAdapter clientAdapter;
    private SelectPicPopupWindow menuWindow;
    private List<HashMap<String, Object>> mHashMaps;
    private HashMap<String, Object> map;
    private View view;
    private Inflater mInflater;

    private ProgressDialog mProgressDialog;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;
    // [END declare_auth_listener]
    private CallbackManager mCallbackManager;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view =  inflater.inflate(R.layout.client_drawer_main,container,false);
        clientList = (RecyclerView) view.findViewById(R.id.clientList);
        clientPicImg = (CircleImg) view.findViewById(R.id.clientPic);
        editImg = (ImageView) view.findViewById(R.id.edit_img);
        username = (TextView) view.findViewById(R.id.client_name);
        email = (TextView) view.findViewById(R.id.client_email);


        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "facebook profile: " + user.getPhotoUrl());
                    Log.d(TAG, "user Email:" + user.getEmail());
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                updateUI(user);
                // [END_EXCLUDE]
            }
        };
        // [END auth_state_listener]


        // [START initialize_fblogin]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) view.findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.setFragment(this);
        signOutButton = (Button) view.findViewById(R.id.button_facebook_signout);
        signOutButton.setOnClickListener(this);


        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });
        // [END initialize_fblogin]

        initView();

        return view;


    }


    private void initView() {


        clientPicImg.setOnClickListener(this);
        editImg.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        clientList.setLayoutManager(linearLayoutManager);
        clientAdapter = new MyRecyclerAdapter(this.getActivity(),initClientData());
        clientAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                switch (position){
                    case 0://booking
                        ClientOrderFragment clientOrderFragment = new ClientOrderFragment();
                        ft.replace(R.id.client_fragment,clientOrderFragment);
                        ft.commit();
                        break;
                    case 1://review
                        ClientReviewFragment clientReviewFragment = new ClientReviewFragment();
                        ft.replace(R.id.client_fragment,clientReviewFragment);
                        ft.commit();
                        break;
                    case 2://favorite
                        ClientFavoriteFragment clientFavoriteFragment = new ClientFavoriteFragment();
                        ft.replace(R.id.client_fragment,clientFavoriteFragment);
                        ft.commit();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onLongClick(int position) {

            }
        });
        clientList.setAdapter(clientAdapter);
    }

    private List<HashMap<String,Object>> initClientData() {
        mHashMaps = new ArrayList<HashMap<String,Object>>();
        map = new HashMap<String, Object>();

        map.put("image",R.drawable.booking64);
        map.put("title","My booking");
        mHashMaps.add(map);

        map = new HashMap<String, Object>();
        map.put("image",R.drawable.review64);
        map.put("title","My reviews");
        mHashMaps.add(map);

        map = new HashMap<String, Object>();
        map.put("image",R.drawable.favorite64);
        map.put("title","My favorite");
        mHashMaps.add(map);

        return mHashMaps;
    }


    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]


    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();

                        // [END_EXCLUDE]
                    }


                });
    }


    // [END auth_with_facebook]

    public void signOut() {
        mAuth.signOut();
        LoginManager.getInstance().logOut();

        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        // hideProgressDialog();
        if (user != null) {
            username.setText(user.getDisplayName());
            email.setText(user.getEmail());
            //clientPicImg.setImageURI(null);
            String photo = user.getPhotoUrl().toString();
            // clientPicImg.setImageURI(user.getPhotoUrl());


            if (photo != null && !photo.isEmpty()) {
                Picasso.with(getActivity().getApplicationContext())
                        .load(photo)
                        .into(clientPicImg);
            } else {
                Picasso.with(getActivity().getApplicationContext()).load(R.drawable.default_useravatar).into(clientPicImg);
            }

            loginButton.setVisibility(View.GONE);
            signOutButton.setVisibility(View.VISIBLE);
        } else {
            username.setText("UserName");
            email.setText("Email");
            Picasso.with(getActivity().getApplicationContext()).load(R.drawable.default_useravatar).into(clientPicImg);
            getView().findViewById(R.id.button_facebook_login).setVisibility(View.VISIBLE);
            signOutButton.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clientPic:
                menuWindow = new SelectPicPopupWindow(this.getActivity(),itemsOnClick);

                menuWindow.showAtLocation(getActivity().findViewById(R.id.dl_left), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                break;
            case R.id.edit_img:
                ClientEditFragment clientEditFragment = new ClientEditFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.client_fragment,clientEditFragment);
                ft.commit();
                break;
            case R.id.button_facebook_signout:
                signOut();
            default:
                break;
        }

    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // take photos
                case R.id.takePhotoBtn:
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                    break;
                // choose photos
                case R.id.choosePhotoBtn:
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    // image/jpeg 、 image/png...
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUESTCODE_PICK:// get pics from album
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();//
                }
                break;
            case REQUESTCODE_TAKE:// take photos
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:// get the pic
                if (data != null) {

                    setPicToView(data);

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY height width
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    private void setPicToView(Intent picdata)  {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // SDCard path
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);

            //     urlpath = FileUtil.saveFile(mContext,"temphead.jpg", photo);
            clientPicImg.setImageDrawable(drawable);

            //   Storage.saveString(this,"url",urlpath);

//            SaveImage(photo);

        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
