package com.hewuzhe.ui.http;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by ss on 15-4-17.
 */
public abstract class EntityHandler<T> extends HttpErrorHandler{

    private  Class<T> c;

    public EntityHandler(Class<T> clzz){
        this.c=clzz;
    }

    @Override
    public void onRecevieSuccess(JSONObject json) {
        String test = json.toString();
        String test2=json.get(UrlContants.jsonData).toString();
    	Object object = json.get(UrlContants.jsonData);
    	if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray)object;
            try {
                List<T> list=JSONArray.parseArray(jsonArray.toString(), c);
                onReadSuccess(list);
            }catch (Exception e){
                e.toString();
            }

    	} else if (object instanceof JSONObject) {
    		JSONObject jsonObject = (JSONObject)object;
    		JSONArray jsonArray = jsonObject.getJSONArray("list");
            List<T> list=JSONArray.parseArray(jsonArray.toString(), c);
            onReadSuccess(list);
    	} else {
            onReadSuccess(new ArrayList<T>());
    	}
    }

	@Override
	public void onRecevieFailed(String status, JSONObject json) {
        onReadSuccess(new ArrayList<T>());
	}

    public abstract void  onReadSuccess(List<T> list);
}

