package com.hewuzhe.view;

import com.hewuzhe.model.Record;
import com.hewuzhe.view.base.GetView;
import com.hewuzhe.view.base.ListView;
import com.hewuzhe.view.base.LoadMoreView;
import com.hewuzhe.view.common.SwipeRefreshView;

/**
 * Created by xianguangjin on 15/12/25.
 */
public interface RecordView extends LoadMoreView,SwipeRefreshView,GetView<Integer>,ListView<Record> {
    void deleteFinished();

    void removeItem(Record record);
}
