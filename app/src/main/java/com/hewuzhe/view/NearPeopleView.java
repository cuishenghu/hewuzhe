package com.hewuzhe.view;

import com.hewuzhe.model.NearPeople;
import com.hewuzhe.view.base.LoadMoreView;

import java.util.ArrayList;

/**
 * Created by zycom on 2016/3/31.
 */
public interface NearPeopleView extends LoadMoreView{
    void bindNearPeople(ArrayList<NearPeople> nearPeoples);
}
