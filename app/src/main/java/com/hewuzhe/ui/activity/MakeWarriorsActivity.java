package com.hewuzhe.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

import butterknife.Bind;
import materialdialogs.MaterialDialog;

public class MakeWarriorsActivity extends RecycleViewActivity<MakeWarriorsPresenter, SearchWarriorsAdapter, Friend> implements MakeWarriorsView {

    @Bind(R.id.edt_search_content)
    EditText _EdtSearchContent;
    @Bind(R.id.tv_province)
    TextView _TvProvince;
    @Bind(R.id.tv_city)
    TextView _TvCity;
    @Bind(R.id.tv_district)
    TextView _TvDistrict;
    @Bind(R.id.tv_age)
    TextView _TvAge;
    @Bind(R.id.tv_gender)
    TextView _TvGender;

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

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_make_warriors;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        presenter.getData(page, count);
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
                    presenter.getData(page, count);
                }
                return false;
            }
        });

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
        return "结交武者";
    }


    /**
     * @return 提供Adapter
     */
    @Override
    protected SearchWarriorsAdapter provideAdapter() {
        return new SearchWarriorsAdapter(getContext(), presenter);
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
        friend.areaId = mAreaId;

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

