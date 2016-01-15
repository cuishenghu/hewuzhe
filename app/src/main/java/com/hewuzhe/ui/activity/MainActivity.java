package com.hewuzhe.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.base.BaseActivity;
import com.hewuzhe.ui.fragment.EquipmentFragment;
import com.hewuzhe.ui.fragment.FederalFragment;
import com.hewuzhe.ui.fragment.MoreFragment;
import com.hewuzhe.ui.fragment.PowerFragment;
import com.hewuzhe.ui.fragment.WarriorFragment;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.fram_content)
    FrameLayout framContent;
    @Bind(R.id.menu_one)
    LinearLayout menuOne;
    @Bind(R.id.menu_two)
    LinearLayout menuTwo;
    @Bind(R.id.menu_three)
    LinearLayout menuThree;
    @Bind(R.id.menu_four)
    LinearLayout menuFour;
    @Bind(R.id.menu_five)
    LinearLayout menuFive;

    private int yellow = Color.parseColor("#ef9c00");
    private int primary = Color.parseColor("#1e1d22");
    private WarriorFragment homeFragment;
    private Fragment curFragment;
    private FederalFragment federalFragment;
    private PowerFragment powerFragment;
    private EquipmentFragment equipmentFragment;
    private MoreFragment moreFragment;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {

        setMenu();
        menuOne.performClick();

        if (null == homeFragment) {
            homeFragment = new WarriorFragment();
        }

        FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
        localFragmentTransaction.replace(R.id.fram_content, homeFragment, "tab_one");
        localFragmentTransaction.commit();
        curFragment = homeFragment;

    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


    private void setMenu() {
        menuOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuOne.setBackgroundColor(yellow);
                menuTwo.setBackgroundColor(primary);
                menuThree.setBackgroundColor(primary);
                menuFour.setBackgroundColor(primary);
                menuFive.setBackgroundColor(primary);

                switchContent(curFragment, homeFragment);
            }
        });


        menuTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuTwo.setBackgroundColor(yellow);
                menuOne.setBackgroundColor(primary);
                menuThree.setBackgroundColor(primary);
                menuFour.setBackgroundColor(primary);
                menuFive.setBackgroundColor(primary);


                if (null == federalFragment) {
                    federalFragment = new FederalFragment();
                }

                switchContent(curFragment, federalFragment);


            }
        });


        menuThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuThree.setBackgroundColor(yellow);

                menuTwo.setBackgroundColor(primary);
                menuOne.setBackgroundColor(primary);
                menuFour.setBackgroundColor(primary);
                menuFive.setBackgroundColor(primary);

                if (null == powerFragment) {
                    powerFragment = new PowerFragment();
                }

                switchContent(curFragment, powerFragment);
            }
        });


        menuFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuFour.setBackgroundColor(yellow);

                menuTwo.setBackgroundColor(primary);
                menuThree.setBackgroundColor(primary);
                menuOne.setBackgroundColor(primary);
                menuFive.setBackgroundColor(primary);


                if (null == equipmentFragment) {
                    equipmentFragment = new EquipmentFragment();
                }

                switchContent(curFragment, equipmentFragment);


            }
        });


        menuFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuFive.setBackgroundColor(yellow);

                menuTwo.setBackgroundColor(primary);
                menuThree.setBackgroundColor(primary);
                menuFour.setBackgroundColor(primary);
                menuOne.setBackgroundColor(primary);


                if (null == moreFragment) {
                    moreFragment = new MoreFragment();
                }

                switchContent(curFragment, moreFragment);


            }
        });
    }


    public void switchContent(Fragment from, Fragment to) {
        if (curFragment != to) {
            curFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.fram_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }


}


