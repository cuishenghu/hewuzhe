package com.hewuzhe.view;

import com.hewuzhe.MegaVideo;
import com.hewuzhe.view.base.GetView;

/**
 * Created by xianguangjin on 16/1/15.
 */
public interface MegaGameApplyView extends GetView<MegaVideo> {


    void publishSuccess();

    void setData(MegaVideo data);

}
