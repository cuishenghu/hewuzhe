package com.hewuzhe.view;

import com.hewuzhe.model.GetChargeRequest;
import com.hewuzhe.view.base.GetView;

/**
 * Created by xianguangjin on 15/12/30.
 */
public interface BuyView extends GetView<GetChargeRequest> {
    public void toPay(String charge);
}
