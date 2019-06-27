package com.company.common.net;

public interface OnRequestListener {
    public void OnSuccess(ServerResponse serverResponse, int tag);
    public void onFail(String msg, int code, int tag);
    public void onError(int tag);
}
