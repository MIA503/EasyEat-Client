package com.example.freda.easyeatclient.ClientAdmin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.freda.easyeatclient.R;

import java.io.File;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by freda on 8/14/16.
 */
public class SelectPicPopupWindow extends PopupWindow{
   private Button takePhotoBtn,pickPhotoBtn,cancleBtn;
    private View mMenuView;

    public SelectPicPopupWindow(Context context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pic_dialog,null);

        takePhotoBtn = (Button) mMenuView.findViewById(R.id.takePhotoBtn);
        pickPhotoBtn = (Button) mMenuView.findViewById(R.id.choosePhotoBtn);
        cancleBtn = (Button) mMenuView.findViewById(R.id.cancleBtn);

        cancleBtn.setOnClickListener(itemsOnClick);
        pickPhotoBtn.setOnClickListener(itemsOnClick);
        takePhotoBtn.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.PopupAnimation);

        ColorDrawable dw = new ColorDrawable(0x80000000);
        this.setBackgroundDrawable(dw);

        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


}
