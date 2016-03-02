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
import com.hewuzhe.model.Friend;
import com.hewuzhe.presenter.MakeWarriorsPresenter;
import com.hewuzhe.ui.adapter.AddressAdapter;
import com.hewuzhe.ui.adapter.SearchWarriorsAdapter;
import com.hewuzhe.ui.base.RecycleViewActivity;
import com.hewuzhe.utils.Bun;
import com.hewuzhe.view.MakeWarriorsView;

import java.util.ArrayList;

import materialdialogs.MaterialDialog;

public class MakeWarriorsActivity extends RecycleViewActivity<MakeWarriorsPresenter, SearchWarriorsAdapter, Friend> implements MakeWarriorsView {

    private LinearLayout lay_select_condition;
    private LinearLayout lay_select_condition_content;
    private int state;//0显示,1为隐藏
    private TextView _TvProvince;
    private TextView _TvCity;
    private TextView _TvDistrict;
    private TextView _TvAge;
    private TextView _TvGender;
    private EditText _EdtSearchContent;


    private int provinceId;
    private int cityId;
    private int countyId;
    private int age = 0;
    private int gender = 0;
    private AddressAdapter cityDialogAdapter;
    private AddressAdapter disctrictAdapter;
    private int mAreaId = 0;
    private Friend _item;
    private boolean isFirstRun = true;
    private View header;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_make_warriors;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        initHeader();
        presenter.getData(page, count);
    }

    private void initHeader() {
        lay_select_condition = (LinearLayout) header.findViewById(R.id.lay_select_condition);
        lay_select_condition_content = (LinearLayout) header.findViewById(R.id.lay_select_condition_content);

        _TvProvince = (TextView) header.findViewById(R.id.tv_province);
        _TvCity = (TextView) header.findViewById(R.id.tv_city);
        _TvDistrict = (TextView) header.findViewById(R.id.tv_district);
        _TvAge = (TextView) header.findViewById(R.id.tv_age);
        _TvGender = (TextView) header.findViewById(R.id.tv_gender);
        _EdtSearchContent = (EditText) header.findViewById(R.id.edt_search_content);
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
                        mAreaId = ad.Id;

                        page = 1;
                        presenter.getData(page, count);

                        dialog.dismiss();
                    }
                });
            }
        });

        _TvAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListDialog(R.array.age, new MaterialDialog.ListCallback() {

                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                MakeWarriorsActivity.this.age = which;
                                _TvAge.setText(text);
                                page = 1;
                                presenter.getData(page, count);
                            }
                        }
                );
            }
        });

        _TvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showListDialog(R.array.gender, new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                {
                                    MakeWarriorsActivity.this.gender = which;
                                    _TvGender.setText(text);

                                    page = 1;
                                    presenter.getData(page, count);

                                }
                            }
                        }


                );
            }
        });

        _EdtSearchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    showDialog();
                    hideSoftMethod(_EdtSearchContent);
                    page = 1;
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
//        header.findViewById(R.id.lay_select_condition).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewContentByState(state);
//            }
//        });
        lay_select_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewContentByState(state);
            }
        });
    }

    public void viewContentByState(int state) {
        if (state == 0) {
            lay_select_condition_content.setVisibility(View.VISIBLE);
            header.findViewById(R.id.img_right).setBackgroundResource(R.drawable.symbol_below);
            this.state = 1;
        } else {
            lay_select_condition_content.setVisibility(View.GONE);
            header.findViewById(R.id.img_right).setBackgroundResource(R.drawable.symbol_right);
            this.state = 0;
        }
    }


    /**
     * 绑定Presenter
     */
    @Override
    public MakeWarriorsPresenter createPresenter() {
        return new MakeWarriorsPresenter();
    }


    @Override
    protected String provideTitle() {
        return "交友";
    }


    /**
     * @return 提供Adapter
     */
    @Override
    protected SearchWarriorsAdapter provideAdapter() {
        header = getLayoutInflater().inflate(R.layout.header_make_warriors, null);
        return new SearchWarriorsAdapter(getContext(), presenter, header);
    }

    /**
     * @return 提供LayoutManager
     */
    @Override
    protected RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    public void bindData(ArrayList<Friend> data) {
        bd(data);
    }

    /**
     * @param view
     * @param pos
     * @param item
     */
    @Override
    public void onItemClick(View view, int pos, Friend item) {
        _item = item;
        presenter.isWuyou(item.Id);
    }


    @Override
    public Friend getData() {
        Friend friend = new Friend();
        friend.nicName = _EdtSearchContent.getText().toString().trim();
        friend.age = age;
        friend.sexuality = gender;
        friend.areaId = countyId;
        return friend;
    }


    @Override
    public void updatePosItem(int pos, boolean IsFriend) {
        adapter.data.get(pos).IsFriend = IsFriend;
        adapter.notifyItemChanged(pos);
    }

    @Override
    public void isWuYou(Boolean data, int userid) {
        if (data) {
            startActivity(FriendProfileActivity.class, new Bun().putInt("id", _item.Id).ok());
        } else {
            startActivity(StrangerProfileSettingsActivity.class, new Bun().putInt("id", _item.Id).ok());
        }
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
    protected void onResume() {
        super.onResume();
        if (!isFirstRun) {
            page = 1;
            presenter.getData(page, count);
        } else {
            isFirstRun = false;
        }
    }
}

