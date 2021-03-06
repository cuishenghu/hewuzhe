package com.hewuzhe.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hewuzhe.R;
import com.hewuzhe.model.Address;
import com.hewuzhe.model.TrainerLessonInfo;
import com.hewuzhe.model.TrainerLessonTwo;
import com.hewuzhe.presenter.TrainerLessonPresenter;
import com.hewuzhe.ui.base.ToolBarActivity;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.view.CircleImageView;
import com.hewuzhe.view.TrainerLessonView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by zycom on 2016/3/18.
 */
public class TrainerLessonActivity extends ToolBarActivity<TrainerLessonPresenter> implements TrainerLessonView {
    @Bind(R.id.product_title)
    TextView product_title;
    @Bind(R.id.user_name)
    TextView user_name;
    @Bind(R.id.user_content)
    TextView user_content;
    @Bind(R.id.web_content)
    WebView web_content;
    @Bind(R.id.head_portrait)
    ImageView head_portrait;
    @Bind(R.id.baoming_btn)
    LinearLayout baoming_btn;
    @Bind(R.id.tv_sign)
    TextView tv_sign;

    private ArrayList<String> arrayList;
    @Override
    protected CharSequence provideTitle() {
        return "报名详情";
    }

    @Override
    public boolean canAction() {
        return true;
    }

    @Override
    protected void action() {
        startActivity(MySignLessonListActivity.class);
        finish();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_trainer_lesson;
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {
        super.initThings(savedInstanceState);
        arrayList = getIntent().getStringArrayListExtra("data");
        presenter.SelectLessonById(Integer.parseInt(arrayList.get(0)));
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;} body{ color:#ffffff; background-color: #3e3e40;font-size:large;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
//
    }

    @Override
    public void initListeners() {

    }

    @Override
    public TrainerLessonPresenter createPresenter() {
        return new TrainerLessonPresenter();
    }

    @Override
    public void bindData(TrainerLessonTwo trainerLessonTwo) {
        product_title.setText(trainerLessonTwo.Title);
        if(trainerLessonTwo.IsJoin==1){
            tv_sign.setText("已报名");
            baoming_btn.setBackgroundResource(R.color.grey_text);
        }else{
            tv_sign.setText("报名课程");
            baoming_btn.setBackgroundResource(R.color.colorYellow);
        }
        user_name.setText(arrayList.get(1));
        user_content.setText(arrayList.get(2));
        Picasso.with(this)
                .load(C.BASE_URL + arrayList.get(3))
                .placeholder(R.mipmap.img_avatar)
                .resize(50,50)
                .error(R.mipmap.img_avatar)
                .centerCrop()
                .into(head_portrait); //imagview 布局
//        Picasso.with(this)
//                .load(C.BASE_URL + arrayList.get(3))
//                .centerCrop()
////                .placeholder(R.mipmap.img_bg)
//                .error(R.mipmap.img_bg)
//                .into(head_portrait);
        web_content.loadDataWithBaseURL(C.BASE_URL, getHtmlData(trainerLessonTwo.Content), "text/html", "UTF-8", "");

    }

    @Override
    public void setProvinces(ArrayList<Address> address) {

    }

    @Override
    public void setCitys(ArrayList<Address> address) {

    }

    @Override
    public void setDistricts(ArrayList<Address> address) {

    }

    @Override
    public void bindInfo(TrainerLessonInfo trainerLessonInfo) {

    }

    @Override
    public void finishing() {

    }

    @OnClick(R.id.baoming_btn)
    public void baomingClick(){
        startActivity(new Intent(this, TrainerLessonTwoActivity.class).
                putStringArrayListExtra("data", arrayList));
        finish();
    }
}
