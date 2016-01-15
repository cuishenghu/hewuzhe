package com.hewuzhe.view;

import com.hewuzhe.model.common.DataModel;
import com.hewuzhe.model.common.PickImg;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.common.PicImgView;

/**
 * Created by xianguangjin on 16/1/2.
 */

public interface PublishConditionView extends GetView<DataModel<PickImg,Object>>, PicImgView, ListView<PickImg> {

    public void publishSuccess();

}
