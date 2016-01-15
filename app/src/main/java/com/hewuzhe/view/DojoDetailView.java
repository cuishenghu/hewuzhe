package com.hewuzhe.view;

import com.hewuzhe.model.Dojo;
import com.hewuzhe.model.OtherImage;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.SetView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 15/12/25.
 */
public interface DojoDetailView extends SetView<Dojo>,GetView<Integer> {

    void setOthers(ArrayList<OtherImage> data);
}
