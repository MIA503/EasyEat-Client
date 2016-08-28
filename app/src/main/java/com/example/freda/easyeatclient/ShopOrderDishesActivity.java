package com.example.freda.easyeatclient;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.freda.easyeatclient.adapter.ShopOrderDishesExpandListViewAdapter;
import com.example.freda.easyeatclient.adapter.ShopOrderDishesListViewAdapter;
import com.example.freda.easyeatclient.entity.Product;
import com.example.freda.easyeatclient.entity.ProductType;
import com.example.freda.easyeatclient.Utils.NumberUtils;
import com.example.freda.easyeatclient.widget.PinnedHeaderExpandableListView;
import com.example.freda.easyeatclient.widget.PinnedHeaderExpandableListView.OnHeaderUpdateListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class ShopOrderDishesActivity extends Activity implements
        OnClickListener, ExpandableListView.OnChildClickListener,
        ExpandableListView.OnGroupClickListener, OnHeaderUpdateListener {

    private ListView categoryList;
    private ShopOrderDishesListViewAdapter listViewAdapter;
    private PinnedHeaderExpandableListView expandableListView;
    private ShopOrderDishesExpandListViewAdapter expandListViewAdapter;
    private TextView tvTotalPrice;
    private TextView tvTotalNum;
    private Button btnFinish;
    private RelativeLayout restaurantHeader;
    private ImageView backButton;


    private RelativeLayout relativeBottom;//

    private int oldPositiion = 0;
    private double oldPrice;//获取在上次退出时，在该店预定的商品的总价格

    private int selectPosition = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.shop_order_dishes_activity);
        initView();
        List<ProductType> shopMenuList = initData();
        if (shopMenuList != null && shopMenuList.size() > 0) {
            //左侧菜单列表
            //listViewAdapter = new ShopOrderDishesListViewAdapter(shopMenuList, ShopOrderDishesActivity.this);
            listViewAdapter = new ShopOrderDishesListViewAdapter(shopMenuList, ShopOrderDishesActivity.this);
            categoryList.setAdapter(listViewAdapter);
            //右侧ExpandableListView
            expandListViewAdapter = new ShopOrderDishesExpandListViewAdapter(shopMenuList, ShopOrderDishesActivity.this);
            expandableListView.setAdapter(expandListViewAdapter);
            deployExpandableListView(shopMenuList.size());//配置ExpandableListView的相关参数的方法必须等到数据加载完成后执行
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        restaurantHeader = (RelativeLayout) findViewById(R.id.restaurant_header);
        restaurantHeader.setOnClickListener(this);
        backButton = (ImageView) findViewById(R.id.back_button);

        // 中间部分
        categoryList = (ListView) findViewById(R.id.lv_category_list);
        expandableListView = (PinnedHeaderExpandableListView) findViewById(R.id.expand_content_list);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        tvTotalNum = (TextView) findViewById(R.id.tv_total_num);
        if (oldPrice > 0) {
            tvTotalPrice.setText(String.valueOf(oldPrice));
        }
        btnFinish = (Button) findViewById(R.id.btn_order_finish);
        relativeBottom = (RelativeLayout) findViewById(R.id.relative_bottom);

        btnFinish.setOnClickListener(this);

        //left list view, onclick
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                selectPosition = position;
                expandableListView.setSelectedGroup(position);
            }
        });
    }


    /**
     * ExpandableListView
     */
    private void deployExpandableListView(int expandableListSize) {
        expandableListView.setOnHeaderUpdateListener(this);//
        expandableListView.setOnChildClickListener(this);
        expandableListView.setOnGroupClickListener(this);
        expandableListView.setGroupIndicator(null);// remove arrow

        for (int i = 0; i < expandableListSize; i++) {
            expandableListView.expandGroup(i);
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restaurant_header:
                // TODO: 12/05/16  跳转到餐厅信息界面
                break;
            case R.id.back_button:
                // TODO: 13/05/16  back
                break;
            case R.id.btn_order_finish:
                // TODO: 12/05/16// 选购完成，跳到确认订单界面
                break;

            default:
                break;
        }
    }

    /**
     * reset header of type
     */
    @Override
    public View getPinnedHeader() {
        View headerView = getLayoutInflater().inflate(R.layout.shop_order_dishes_father_item, null);
        headerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        return headerView;
    }

    /**
     * reset title
     */
    @Override
    public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {


        TextView textView = (TextView) headerView.findViewById(R.id.tv_category_name);
        ProductType productType = (ProductType) expandListViewAdapter.getGroup(firstVisibleGroupPos);
        textView.setText(productType.getTypeName());

        if (firstVisibleGroupPos < selectPosition) {
            firstVisibleGroupPos = selectPosition;
        }

        changeItemBackground(oldPositiion, firstVisibleGroupPos);

        oldPositiion = firstVisibleGroupPos;
        selectPosition = -1;
    }

    /**
     * left listView
     * change background color of the selected and unselected items
     *
     * @param oldPosition
     * @param newPosition
     */
    private void changeItemBackground(int oldPosition, int newPosition) {
        /*left part, change background color*/
        View oldCateView = getViewByPosition(oldPositiion, categoryList);// old
        View cateView = getViewByPosition(newPosition, categoryList);// new


        oldCateView.setBackgroundColor(ContextCompat.getColor(this, R.color.whitesmoke));//// TODO: 12/05/16

        cateView.setBackgroundResource(R.drawable.selector_choose_eara_ite);

        int lastVisibalePosition = categoryList.getLastVisiblePosition();
        int firstVisibalePosition = categoryList.getFirstVisiblePosition();
        if (newPosition >= lastVisibalePosition) {//back
            categoryList.setSelection(newPosition);
        } else if (firstVisibalePosition >= newPosition) {//forward
            categoryList.setSelection(newPosition);
        }
        listViewAdapter.setSelected(newPosition);//newPosition selected
    }

    /**
     * get listview
     *
     * @param pos      item number
     * @param listView target ListView
     * @return
     */
    public View getViewByPosition(int pos, ListView listView) {

        final int firstListItemPosition = listView.getFirstVisiblePosition();
        //
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    /**
     * ExpandableListView onclick
     */
    @Override
    public boolean onGroupClick(ExpandableListView parent, View v,
                                int groupPosition, long id) {
        return false;
    }

    /**
     * ExpandableListView sublist onclick
     */
    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        // 数据请求完成之后再弹出框
        return false;
    }


    /**
     * cart is hidden unless totalPrice > 0
     */
    public void updateBottomStatus(double totalPrice, int num) {

        if (totalPrice > 0 && num > 0) {
            relativeBottom.setVisibility(View.VISIBLE);
        } else {
            relativeBottom.setVisibility(View.GONE);
        }

        tvTotalPrice.setText(" €" + NumberUtils.format(totalPrice));
        //tvTotalPrice.setText(num);
        tvTotalNum.setText(num + "");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private List<ProductType> initData() {
        // TODO: 12/05/16   show list
        Product product = null;
        List<Product> productList = null;
        ProductType type = null;
        List<ProductType> productTypes = new ArrayList<ProductType>();
        String[] s = {"starter", "PRIMI PIATTI", "SECONDIPIATTI", "INSALATONE", "Dessert"};
        for (int i = 0; i < 5; i++) {
            type = new ProductType();
            productList = new ArrayList<Product>();
            type.setId(UUID.randomUUID().toString().replace("-", ""));
            for (int j = 0; j < 3; j++) {
                product = new Product();
                product.setId(UUID.randomUUID().toString().replace("-", ""));
                product.setName("product_" + i + "_" + j);
                product.setPrice(2.1 + i);
                productList.add(product);
            }
            type.setProductList(productList);
            type.setTypeName(s[i] + i);
            productTypes.add(type);
        }
        return productTypes;
    }
}
