package com.sinothk.pay;

import android.content.Context;

import com.sinothk.pay.wx.WxPayUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

public class APay {

    private APay() {
    }

    public static IWXAPI initWxPay(Context mContext, String appId) {
        return WxPayUtil.createWXAPI(mContext, appId);
    }

    public static void sendWxReq(PayReq payReq) {
        WxPayUtil.sendReq(payReq);
    }

    public static boolean checkWxEnable() {
       return WxPayUtil.checkWxEnable();
    }
}
