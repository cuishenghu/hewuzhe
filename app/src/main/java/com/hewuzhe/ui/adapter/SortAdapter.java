package com.hewuzhe.ui.adapter;
        import java.util.List;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.net.Uri;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.SectionIndexer;
        import android.widget.TextView;
        import com.alibaba.fastjson.JSONObject;
        import com.hewuzhe.R;
        import com.hewuzhe.model.SortModel;
        import com.hewuzhe.ui.http.HttpErrorHandler;
        import com.hewuzhe.ui.http.HttpUtils;
        import com.hewuzhe.utils.SessionUtil;
        import com.hewuzhe.utils.Tools;
        import com.loopj.android.http.AsyncHttpResponseHandler;
        import com.loopj.android.http.RequestParams;

public class SortAdapter extends BaseAdapter implements SectionIndexer{
    private List<SortModel> list = null;
    private Context mContext;

    public SortAdapter(Context mContext, List<SortModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     * @param list
     */
    public void updateListView(List<SortModel> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final SortModel mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_tongxunlu, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.tvEdit = (TextView) view.findViewById(R.id.edit);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(section)){
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());
        }else{
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(this.list.get(position).getName());
        String random = list.get(position).getState();
        viewHolder.tvEdit.setText("1".equals(random)?"已同意":"2".equals(random)?"添加":"邀请");
        viewHolder.tvEdit.setTextColor(Color.parseColor("1".equals(random)?"#AAAAAA":"#FFFFFF"));
        viewHolder.tvEdit.setBackgroundColor(Color.parseColor("1".equals(random)?"#FFFFFF":"2".equals(random)?"#01CF97":"#EA5414"));
        if("1".equals(random)){
            viewHolder.tvEdit.setOnClickListener(null);
        }else if("2".equals(random)){
            viewHolder.tvEdit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    RequestParams params = new RequestParams();
                    params.put("fid", list.get(position).getId());
                    params.put("uid", new SessionUtil(mContext).getUser().Id);
                    params.put("intro", "请求添加好友");
//                    HttpUtils.addFriend(res_addFriend, params);
                }
            });
        }else{
            viewHolder.tvEdit.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Uri uri = Uri.parse("smsto:" + list.get(position).getPhone());
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                    sendIntent.putExtra("sms_body", "http://www.xiaojia.com");
                    mContext.startActivity(sendIntent);
                }
            });
        }
        return view;
    }

    private AsyncHttpResponseHandler res_addFriend = new HttpErrorHandler() {
        @Override
        public void onRecevieSuccess(JSONObject json) {
            Tools.toast(mContext, json.getString("message"));
        }
        @Override
        public void onRecevieFailed(String status, JSONObject json) {
            Tools.toast(mContext, json.getString("message"));
        }
    };



    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
        TextView tvEdit;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
