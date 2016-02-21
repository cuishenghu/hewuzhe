package com.hewuzhe.view;

import com.hewuzhe.model.Site;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.base.SetView;
import com.hewuzhe.view.common.AreaView;

/**
 * Created by Administrator on 2016/2/1.
 */
public interface SiteView extends LoadMoreView,GetView<Integer>,ListView<Site>,SetView<Site>,AreaView {

}
