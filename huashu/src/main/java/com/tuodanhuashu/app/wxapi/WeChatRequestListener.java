package com.tuodanhuashu.app.wxapi;

import com.tuodanhuashu.app.wxapi.bean.WeChatUserInfoBean;

public interface WeChatRequestListener {

    public void OnWeChatSuccess(WeChatUserInfoBean rsp);

    public void OnWeChatFail(String msg);

    public void OnError();
}
