package com.company.common.net;

public interface OnDownloadListener {
    public void onTaskStart();

    public void onProgeress(float frac);

    public void onSuccess();

    public void onError();

}
