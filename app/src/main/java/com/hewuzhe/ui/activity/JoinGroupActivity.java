package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hewuzhe.R;
import com.hewuzhe.model.Address;
import com.hewuzhe.model.Group;
import com.hewuzhe.model.User;
import com.hewuzhe.presenter.JoinPresenter;
import com.hewuzhe.ui.adapter.AddressAdapter;
import com.hewuzhe.ui.adapter.JoinGroupAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.utils.SessionUtil;
import com.hewuzhe.view.JoinGroupView;

import java.util.ArrayList;

import materialdialogs.MaterialDialog;

public class JoinGroupActivity extends RecycleViewActivity<JoinPresenter, JoinGroupAdapter, Group> implements JoinGroupView {


    private LinearLayout _LaySearch;
    private TextView _TvProvince;
    private TextView _TvCity;
    private TextView _TvDistrict;


    private int mCityCode = 371302;

    private int provinceId;
    private int cityId;
    private int countyId;

    private AddressAdapter cityDialogAdapter;
    private AddressAdapter disctrictAdapter;
    private int areaId;
    private View header;
    private EditText _EdtSearchContent;

    public static boolean isFirst = true;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_join_group;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        _TvProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getProvinces();
            }
        });
        _TvCity.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           showListDialog(cityDialogAdapter, new MaterialDialog.ListCallback() {
                                               @Override
                                               public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                                   Address ad = cityDialogAdapter.dataList.get(which);
                                                   _TvCity.setText(ad.Name);
                                                   cityId = Integer.parseInt(ad.Code);
                                                   presenter.getDistricts(cityId);
                                                   dialog.dismiss();
                                               }
                                           });
                                       }
                                   }
        );
        _TvDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListDialog(disctrictAdapter, new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        Address ad = disctrictAdapter.dataList.get(which);
                        _TvDistrict.setText(ad.Name);
                        countyId = Integer.parseInt(ad.Code);
                        mCityCode = Integer.parseInt(ad.Code);
                        page = 1;
                        presenter.getData(page, count);
                        dialog.dismiss();
                    }
                });
            }
        });


        _EdtSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    showDialog();
                    page = 1;
                    hideSoftMethod(_EdtSearchContent);
                    presenter.getData(page, count);
                }
                return false;
            }
        });


        header.findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                hideSoftMethod(_EdtSearchContent);
                page = 1;
                presenter.getData(page, count);
            }
        });

    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        initHeader();
        User user = new SessionUtil(getContext()).getUser();
        presenter.getDatas();
    }

    private void initHeader() {
        _LaySearch = (LinearLayout) header.findViewById(R.id.lay_search);
        _TvProvince = (TextView) header.findViewById(R.id.tv_province);
        _TvCity = (TextView) header.findViewById(R.id.tv_city);
        _TvDistrict = (TextView) header.findViewById(R.id.tv_district);
        _EdtSearchContent = (EditText) header.findViewById(R.id.edt_search_content);
    }

    /**
     * 绑定Presenter
     */
    @Override
    public JoinPresenter createPresenter() {
        return new JoinPresenter();
    }


    @Override
    protected String provideTitle() {
        return "兴趣圈";
    }

    /**
     * 设置province
     *
     * @param address
     */
    @Override
    public void setProvinces(final ArrayList<Address> address) {
        Address address1 = new Address();
        address1.Id = 1000000000;
        address1.Name = "全部";
        address1.Code = "1000000000";
        address.add(0, address1);
        showListDialog(new AddressAdapter(getContext(), address), new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                Address ad = address.get(which);
                _TvProvince.setText(ad.Name);
                provinceId = Integer.parseInt(ad.Code);
                presenter.getCitys(provinceId);
                dialog.dismiss();

            }
        });
    }

    /**
     * 设置城市
     *
     * @param address
     */
    @Override
    public void setCitys(ArrayList<Address> address) {
        Address address1 = new Address();
        address1.Id = 1000000000;
        address1.Name = "全部";
        address1.Code = "1000000000";
        address.add(0, address1);

        cityDialogAdapter = new AddressAdapter(getContext(), address);
        _TvCity.setText(address.get(0).Name);
        if (address.size() > 1) {
            presenter.getDistricts(Integer.parseInt(address.get(1).Code));
        } else {
            setDistricts(new ArrayList<Address>());
        }
    }

    /**
     * 设置县区
     *
     * @param address
     */
    @Override
    public void setDistricts(ArrayList<Address> address) {
        Address address1 = new Address();
        address1.Id = 0;
        address1.Name = "全部";
        address1.Code = "0";
        address.add(0, address1);

        disctrictAdapter = new AddressAdapter(getContext(), address);
        _TvDistrict.setText(address.get(0).Name);
    }

    @Override
    public Group getData() {
        Group group = new Group();
        group.citycode = mCityCode;
        group.name = _EdtSearchContent.getText().toString().trim();
        return group;
    }

    @Override
    public void bindData(ArrayList<Group> data) {
        bd(data);
    }

    /**
     * @return 提供Adapter
     */
    @Override
    protected JoinGroupAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.header_join_groups, null);
        return new JoinGroupAdapter(getContext(), presenter, header);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Group item) {
        startActivity(GroupConditionActivity.class, new Bun().putInt("teamid", item.Id).ok());
    }

    /**
     * 更新Item
     *
     * @param b
     * @param pos
     */
    @Override
    public void updateItem(boolean b, int pos) {
//      adapter.data.get(pos).isJoined = b;
        snb(b ? "加入成功" : "退出成功", _TvCity);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void bdDatas(ArrayList<Group> data) {
        adapter.addDatas(data);
    }
}
