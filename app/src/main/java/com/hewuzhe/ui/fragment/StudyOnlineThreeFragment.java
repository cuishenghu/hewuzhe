package com.hewuzhe.ui.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hewuzhe.R;
import com.hewuzhe.presenter.base.BasePresenterImp;
import com.hewuzhe.ui.activity.Videos_2Activity;
import com.hewuzhe.ui.base.BaseFragment;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudyOnlineThreeFragment extends BaseFragment {


    private static StudyOnlineThreeFragment instance = null;
    @Bind(R.id.lay_one)
    FrameLayout layOne;
    @Bind(R.id.lay_two)
    FrameLayout layTwo;
    @Bind(R.id.lay_three)
    FrameLayout layThree;
    @Bind(R.id.lay_four)
    FrameLayout layFour;
    @Bind(R.id.lay_five)
    FrameLayout layFive;
    @Bind(R.id.lay_six)
    FrameLayout laySix;
    @Bind(R.id.lay_seven)
    FrameLayout laySeven;
    @Bind(R.id.lay_eight)
    FrameLayout layEight;
    @Bind(R.id.lay_view_group)
    LinearLayout layViewGroup;
    private View view;
    private Intent intent;

    public static StudyOnlineThreeFragment newInstance() {
        if (instance == null) {
            instance = new StudyOnlineThreeFragment();
        }
        return instance;
    }

    public StudyOnlineThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public void initListeners() {
        layOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "承诺");
                intent.putExtra("id", 31);
                getActivity().startActivity(intent);
            }
        });

        layTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "责任");
                intent.putExtra("id", 32);

                getActivity().startActivity(intent);
            }
        });

        layThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "沟通");
                intent.putExtra("id", 33);

                getActivity().startActivity(intent);
            }
        });
        layFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "演说");
                intent.putExtra("id", 34);

                getActivity().startActivity(intent);
            }
        });
        layFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "创新");
                intent.putExtra("id", 35);

                getActivity().startActivity(intent);
            }
        });
        laySix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "共赢");
                intent.putExtra("id", 36);

                getActivity().startActivity(intent);
            }
        });
        laySeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "赞赏");
                intent.putExtra("id", 37);

                getActivity().startActivity(intent);
            }
        });
        layEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "魅力");
                intent.putExtra("id", 38);
                getActivity().startActivity(intent);
            }
        });
    }

    /**
     * 初始化一些事情
     *
     * @param v
     */
    @Override
    protected void initThings(View v) {

    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_study_online_three;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


}
