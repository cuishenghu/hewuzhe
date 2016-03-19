package com.hewuzhe.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.StudyOnlineCatItem;
import com.hewuzhe.presenter.StudyOnlineFragPresenter;
import com.hewuzhe.ui.activity.Videos_2Activity;
import com.hewuzhe.ui.base.BaseFragment;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.view.StudyOnlineFragView;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by zycom on 2016/3/19.
 */
public class SixFragment extends BaseFragment<StudyOnlineFragPresenter> implements StudyOnlineFragView {

//    @Bind(R.id.layout)
//    MosaicLayout _Layout;


    @Bind(R.id.lay_content)
    LinearLayout _LayContent;


//    BlockPattern.BLOCK_PATTERN pattern1[] = {BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.HORIZONTAL,
//            BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.HORIZONTAL};
//
//    BlockPattern.BLOCK_PATTERN pattern2[] = {BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.HORIZONTAL,
//            BlockPattern.BLOCK_PATTERN.HORIZONTAL, BlockPattern.BLOCK_PATTERN.BIG, BlockPattern.BLOCK_PATTERN.BIG};

    public static SixFragment newInstance() {
        SixFragment instance = new SixFragment();
        return instance;
    }

    public SixFragment() {

    }


    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {

    }

    /**
     * 初始化一些事情
     *
     * @param view
     */
    @Override
    protected void initThings(View view) {

        presenter.SelectCategory();
//        orderedSelectedPatterns();

    }

//    private void randomAllPatters() {
//        _Layout.chooseRandomPattern(true);
//
//    }
//
//    private void randomSelectedPatterns() {
//        _Layout.addPattern(pattern1);
//        _Layout.addPattern(pattern2);
//        _Layout.chooseRandomPattern(true);
//
//    }
//
//    private void orderedSelectedPatterns() {
//        _Layout.addPattern(pattern1);
//        _Layout.addPattern(pattern2);
//        _Layout.chooseRandomPattern(false);
//
//    }

    /**
     * @return 提供LayoutId
     */
    @Override
    public int provideLayoutId() {
        return R.layout.fragment_study_online_one;
    }

    /**
     * 绑定Presenter
     */
    @Override
    public StudyOnlineFragPresenter createPresenter() {
        return new StudyOnlineFragPresenter();
    }


    @Override
    public void bindData(final ArrayList<StudyOnlineCatItem> data) {

//        MyAdapter adapter = new MyAdapter(getContext());
//        adapter.setData(data);
//        _Layout.setAdapter(adapter);
//        _Layout.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onClick(int position) {
//                StudyOnlineCatItem item = data.get(position);
//                Intent intent = new Intent(getActivity(), Videos_2Activity.class);
//                intent.putExtra("title", item.Name);
//                intent.putExtra("id", item.Id);
//                getActivity().startActivity(intent);
//
//            }
//        });

        int size = data.size();

        for (int i = 0; i < 3; i++) {
//            int page = i % 3;
            LinearLayout child = (LinearLayout) _LayContent.getChildAt(i);

            if (i % 2 != 0) {
                LinearLayout childOne = (LinearLayout) child.getChildAt(0);
                FrameLayout childTwo = (FrameLayout) child.getChildAt(1);


                ViewGroup childThree = (ViewGroup) childOne.getChildAt(0);
                ViewGroup childFour = (ViewGroup) childOne.getChildAt(1);

                StudyOnlineCatItem itemOne = data.get(i * 3 + 1);
                StudyOnlineCatItem itemTwo = data.get(i * 3 + 2);

                setItemData(childThree, itemOne);
                setItemData(childFour, itemTwo);


                StudyOnlineCatItem item = data.get(i * 3);
                setItemData(childTwo, item);


            } else {

                FrameLayout childOne = (FrameLayout) child.getChildAt(0);
                LinearLayout childTwo = (LinearLayout) child.getChildAt(1);


                StudyOnlineCatItem item = data.get(i * 3);
                setItemData(childOne, item);

                ViewGroup childThree = (ViewGroup) childTwo.getChildAt(0);
                ViewGroup childFour = (ViewGroup) childTwo.getChildAt(1);


                StudyOnlineCatItem itemOne = data.get(i * 3 + 1);
                StudyOnlineCatItem itemTwo = data.get(i * 3 + 2);
                setItemData(childThree, itemOne);
                setItemData(childFour, itemTwo);
            }
        }
    }


    private void setItemData(ViewGroup child, final StudyOnlineCatItem item) {

        ImageView imageView = (ImageView) child.getChildAt(0);
        TextView textView = (TextView) child.getChildAt(1);
        textView.setText(item.Name);
//        textView.setText(item.Name);

        Glide.with(getContext())
                .load(C.BASE_URL + item.ImagePath)
                .crossFade()
                .placeholder(R.mipmap.img_bg_videio)
                .into(imageView);

        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Videos_2Activity.class);
                intent.putExtra("title", item.Name);
                intent.putExtra("id", item.Id);
                intent.putExtra("where","six");
                getActivity().startActivity(intent);
            }
        });

    }

}
