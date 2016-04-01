package com.hewuzhe.presenter.base;

import com.hewuzhe.model.Res;
import com.hewuzhe.view.base.LoadMoreView;

/**
 * Created by zycom on 2016/3/2.
 */
public abstract class RefreshAndLoadMoreForListPresenter<V extends LoadMoreView> extends BasePresenterImp<V> {

    /**
     * 获取数据
     *
     * @param page
     * @param count
     */
    public abstract void getData(int page, int count,String search, int categoryId, int isPriceAsc, int isSalesAsc, int CommentAsc, int isNewAsc, int isCredit, int isRecommend);
    public abstract void getData(int page, int count,int userid, double lat, double lng, int lenght, int age, int sexuality);

    /**
     * 设置数据状态
     *
     * @param page
     * @param count
     * @param res
     */
    public void setDataStatus(int page, int count, Res res) {
        if (page * count >= res.recordcount) {
            //没有更多了
            view.noMore();
        } else {
            //还有更多
            view.hasMore();
        }

    }
}
