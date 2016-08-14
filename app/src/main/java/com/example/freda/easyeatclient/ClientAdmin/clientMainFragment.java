package com.example.freda.easyeatclient.ClientAdmin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.freda.easyeatclient.R;
import com.example.freda.easyeatclient.Utils.CircleImg;

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

    private RecyclerView clientList;
    private CircleImg clientPicImg;
    private ImageView editImg;

    private MyRecyclerAdapter clientAdapter;
    private SelectPicPopupWindow menuWindow;
    private List<HashMap<String, Object>> mHashMaps;
    private HashMap<String, Object> map;
    private View view;
    private Inflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view =  inflater.inflate(R.layout.client_drawer_main,container,false);
        clientList = (RecyclerView) view.findViewById(R.id.clientList);
        clientPicImg = (CircleImg) view.findViewById(R.id.clientPic);
        editImg = (ImageView) view.findViewById(R.id.edit_img);
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
}
