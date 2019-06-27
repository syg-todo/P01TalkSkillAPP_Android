package com.tuodanhuashu.app.home.biz;

import com.company.common.net.OnRequestListener;

import java.util.HashMap;
import java.util.Map;

public class HuaShuFragmentBiz {

    private OnRequestListener listener;

    public HuaShuFragmentBiz(OnRequestListener listener) {
        this.listener = listener;
    }

    public void requestHuaShuFragment(int tag){
        Map<String,String> params = new HashMap<>();
        //OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.HUASHU_PAGE_URL,params,listener);
    }
}
