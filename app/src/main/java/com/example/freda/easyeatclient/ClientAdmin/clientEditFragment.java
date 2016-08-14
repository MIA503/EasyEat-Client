package com.example.freda.easyeatclient.ClientAdmin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.freda.easyeatclient.R;

/**
 * Created by freda on 8/14/16.
 */
public class ClientEditFragment extends Fragment implements View.OnClickListener{

    private ImageView backToMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.client_edit,container,false);
        backToMain = (ImageView) view.findViewById(R.id.back_clientMain);
        backToMain.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_clientMain:
                ClientMainFragment clientMainFragment = new ClientMainFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.client_fragment,clientMainFragment);
                ft.commit();
                break;
            default:
                break;
        }
    }
}
