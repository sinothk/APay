package com.sinothk.pay.wx;

import android.content.Context;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WxPayUtil {
    private static IWXAPI api;

    private WxPayUtil() {
    }

    public static IWXAPI createWXAPI(Context mContext, String appId) {
        api = WXAPIFactory.createWXAPI(mContext, appId);
        return api;
    }

    public static void sendReq(PayReq payReq) {
        api.sendReq(payReq);
    }

    public static boolean checkWxEnable() {
        return api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }
}
