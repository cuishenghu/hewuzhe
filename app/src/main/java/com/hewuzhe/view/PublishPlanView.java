package com.hewuzhe.view;

import com.hewuzhe.model.Plan;
import com.hewuzhe.model.common.DataModel;
import com.hewuzhe.model.common.PickImg;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;

/**
 * Created by xianguangjin on 16/1/2.
 */

public interface PublishPlanView extends GetView<DataModel<PickImg,Plan>>, ListView<PickImg> {

    public void publishSuccess();


}
