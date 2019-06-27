package com.tuodanhuashu.app.download;

import android.os.Build;
import android.util.Log;

import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.BuildConfig;
import com.aliyun.vodplayer.utils.HttpClientUtil;

import org.json.JSONObject;

import java.util.UUID;

public class VidStsUtil {
    private static String TAG = "AudioPlayerActivity";
    private static String baseUrl = "https://demo-vod.cn-shanghai.aliyuncs.com/";

    public static AliyunVidSts getVidSts(String videoId) {
        Log.d(TAG,"getVidSts");
//        if (!BuildConfig.DEBUG) {
//            Log.d("111","return null");
//            //Do not get STS in debug mode, developers fill in their own STS
//            return null;
//        } else {
            try {
                //以前的连接地址"https://demo-vod.cn-shanghai.aliyuncs.com/voddemo/CreateSecurityToken?BusinessType=vodai&TerminalType=pc&DeviceModel=iPhone9,2&UUID=59ECA-4193-4695-94DD-7E1247288&AppVersion=1.0.0&VideoId=" + videoId"
                String stsUrl = baseUrl +
                        "voddemo/CreateSecurityToken?BusinessType=vodai&TerminalType=pc&DeviceModel=" +
                        Build.DEVICE + "&UUID=" + generateRandom() + "&AppVersion=" + BuildConfig.VERSION_NAME +
                        "&VideoId=" + videoId;
                String response = HttpClientUtil.doGet(stsUrl);

                JSONObject jsonObject = new JSONObject(response);

                JSONObject securityTokenInfo = jsonObject.getJSONObject("SecurityTokenInfo");
                if (securityTokenInfo == null) {
                    Log.d(TAG,"securityTokenInfo == null");
                    return null;
                }

                String accessKeyId = securityTokenInfo.getString("AccessKeyId");
                String accessKeySecret = securityTokenInfo.getString("AccessKeySecret");
                String securityToken = securityTokenInfo.getString("SecurityToken");
                String expiration = securityTokenInfo.getString("Expiration");

                Log.d(TAG,"accessKeyId:"+accessKeyId);
                Log.d(TAG,"accessKeySecret:"+accessKeySecret);
                Log.d(TAG,"securityToken:"+securityToken);


                AliyunVidSts vidSts = new AliyunVidSts();

                vidSts.setVid(videoId);
                vidSts.setAcId(accessKeyId);
                vidSts.setAkSceret(accessKeySecret);
                vidSts.setSecurityToken(securityToken);

                return vidSts;

            } catch (Exception e) {
                return null;
            }
//        }


    }

    public static String generateRandom() {
        String signatureNonce = UUID.randomUUID().toString();
        return signatureNonce;
    }

}
