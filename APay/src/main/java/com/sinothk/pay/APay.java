package com.sinothk.pay;

import android.content.Context;

import com.sinothk.pay.wx.WxPayUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;

public class APay {

    private APay() {
    }

    public static void initWxPay(Context mContext, String appId) {
        WxPayUtil.createWXAPI(mContext, appId);
    }

    public static void sendWxReq(PayReq payReq) {
        WxPayUtil.sendReq(payReq);
    }

    public static boolean checkWxEnable() {
       return WxPayUtil.checkWxEnable();
    }
}
