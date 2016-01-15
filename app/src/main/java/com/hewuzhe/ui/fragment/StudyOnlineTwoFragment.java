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
public class StudyOnlineTwoFragment extends BaseFragment {


    private static StudyOnlineTwoFragment instance = null;
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

    public static StudyOnlineTwoFragment newInstance() {
        if (instance == null) {
            instance = new StudyOnlineTwoFragment();
        }
        return instance;
    }

    public StudyOnlineTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public void initListeners() {
        layOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "空翻");
                intent.putExtra("id", 23);
                getActivity().startActivity(intent);
            }
        });

        layTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "花技");
                intent.putExtra("id", 24);

                getActivity().startActivity(intent);
            }
        });

        layThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "战舞");
                intent.putExtra("id", 25);

                getActivity().startActivity(intent);
            }
        });
        layFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "格斗");
                intent.putExtra("id", 26);

                getActivity().startActivity(intent);
            }
        });
        layFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "养生");
                intent.putExtra("id", 27);

                getActivity().startActivity(intent);
            }
        });
        laySix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "斗秀");
                intent.putExtra("id", 28);

                getActivity().startActivity(intent);
            }
        });
        laySeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "影舞");
                intent.putExtra("id", 29);

                getActivity().startActivity(intent);
            }
        });
        layEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", "冷兵");
                intent.putExtra("id", 30);
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
        return R.layout.fragment_study_online_two;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }


}
