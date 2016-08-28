package com.example.freda.easyeatclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.freda.easyeatclient.R;
import com.example.freda.easyeatclient.entity.ProductType;


import java.util.List;

/**
 * Created by lq on 11/05/16.
 * left ListView Adapter
 */
@SuppressLint("InflateParams")
public class ShopOrderDishesListViewAdapter extends BaseAdapter implements ListAdapter {

    private List<ProductType> shopMenus;
    private LayoutInflater inflater;
    private Context mContext;
    private int selectedPosition = -1;

    public ShopOrderDishesListViewAdapter(List<ProductType> shopMenus, Context context) {
        this.shopMenus = shopMenus;
        this.mContext = context;
        inflater = LayoutInflater.from(this.mContext);

    }

    /**
     * 为外部提供接口设置item是否选中的状态
     *
     * @param selected
     */
    public void setSelected(int selected) {
        selectedPosition = selected;
    }

    @Override
    public int getCount() {
        return shopMenus.size();
    }

    @Override
    public Object getItem(int position) {
        return shopMenus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 80);
        textView.setLayoutParams(params);
        ;
        textView.setText(shopMenus.get(position).getTypeName());
        if (selectedPosition == position) {
            convertView.setBackgroundResource(R.drawable.selector_choose_eara_ite);
        } else {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.whitesmoke));
        }
        return convertView;
    }
}
