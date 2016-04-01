package com.hewuzhe.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hewuzhe.R;
import com.hewuzhe.model.NearPeople;
import com.hewuzhe.model.Product;
import com.hewuzhe.presenter.NearPeoplePresenter;
import com.hewuzhe.presenter.ProductListPresenter;
import com.hewuzhe.ui.activity.ConditionDetialTwoActivity;
import com.hewuzhe.ui.activity.FriendProfileActivity;
import com.hewuzhe.ui.activity.StrangerProfileSettingsActivity;
import com.hewuzhe.ui.adapter.base.BaseAdapter;
import com.hewuzhe.ui.cons.C;
import com.hewuzhe.ui.widget.GlideCircleTransform;
import com.hewuzhe.utils.Bun;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zycom on 2016/3/31.
 */
public class NearPeopleAdapter extends BaseAdapter<NearPeopleAdapter.VHolder, NearPeople, NearPeoplePresenter> {

    public NearPeopleAdapter(Context context, NearPeoplePresenter nearPeoplePresenter) {
        super(context, nearPeoplePresenter);
        isAddFooter= false;
    }

    @Override
    public int provideItemLayoutId() {
        return R.layout.near_people_item;
    }

    @Override
    public VHolder createVH(ViewGroup parent, int viewType, View view) {
        return new VHolder(view);
    }

    @Override
    public void bindData(VHolder holder, int position) {
        final NearPeople nearPeople = data.get(position);

        Glide.with(context)
                .load(C.BASE_URL + nearPeople.PhotoPath)
                .centerCrop()
                .crossFade()
                .transform(new GlideCircleTransform(context))
                .placeholder(R.mipmap.img_avatar)
                .into(holder.np_u_icon);

        holder.np_name.setText(nearPeople.NicName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(nearPeople.Birthday);

        }catch (Exception exception){
            date = null;
        }
        String ages = date!=null?getAgeByBirthday(date)+"":"";
        holder.up_sa.setText(nearPeople.Sexuality+"  "+ages);
        holder.np_length.setText(nearPeople.Distance+"米");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        holder.up_m.setText(getCompareDate(nearPeople.UpdateTime,str));

        holder.np_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nearPeople.IsFriend) {
                    context.startActivity(new Intent(context, FriendProfileActivity.class).putExtra("data", new Bun().putInt("id", nearPeople.Id).ok()));

                } else {
                    context.startActivity(new Intent(context, StrangerProfileSettingsActivity.class).putExtra("data", new Bun().putInt("id", nearPeople.Id).ok()));
                }
            }
        });

    }

    public static String getCompareDate(String startDate,String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1=sdf.parse(startDate);
            Date date2=sdf.parse(endDate);
            long l = date2.getTime() - date1.getTime();
            long d = l/(24*60*60*1000);
            if(Math.floor(l/(24*60*60*1000))>0)
                return Math.floor(l/(24*60*60*1000))+"天";
            else if(Math.floor(l/(60*60*1000))>0)
                return Math.floor(l/(60*60*1000))+"小时";
            else if(Math.floor(l/(60*1000))>0)
                return Math.floor(l/(60*1000))+"分钟";
            else if(Math.floor(l/(1000))>0)
                return Math.floor(l/(1000))+"秒";
            else
                return "";

        }catch (Exception e){
            return "";
        }


    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }



    class VHolder extends RecyclerView.ViewHolder {

        @Nullable @Bind(R.id.np_u_icon) ImageView np_u_icon;
        @Nullable @Bind(R.id.np_name) TextView np_name;
        @Nullable @Bind(R.id.up_sa) TextView up_sa;
        @Nullable @Bind(R.id.np_length) TextView np_length;
        @Nullable @Bind(R.id.up_m) TextView up_m;
        @Nullable @Bind(R.id.np_user)
        LinearLayout np_user;


        VHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
