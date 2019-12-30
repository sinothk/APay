package com.sinothk.pay.demo.wxDemo;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sinothk.pay.APay;
import com.sinothk.pay.demo.R;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class PayActivity extends AppCompatActivity {
    private static String TAG = "PayActivity";
//    private IWXAPI api;

//    https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wx_pay);

        APay.initWxPay(this, Constants.APP_ID);


//        https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5

//        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        findViewById(R.id.check_pay_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(PayActivity.this, APay.checkWxEnable() ? "版本支持支付" : "版本不支持", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.appay_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(PayActivity.this, "获取订单中...", Toast.LENGTH_SHORT).show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int resCode;
                        InputStream in;
                        String httpResult = null;

                        String httpsUrl = "https://wxpay.wxutil.com/pub_v2/app/app_pay.php";

                        try {
                            URL url = new URL(httpsUrl);
//                            URLConnection urlConnection = url.openConnection();
//                            HttpsURLConnection httpsConn = (HttpsURLConnection) urlConnection;
                            HttpURLConnection httpsConn;
                            //关键代码
                            //ignore https certificate validation |忽略 https 证书验证
                            if (url.getProtocol().toUpperCase().equals("HTTPS")) {
                                trustAllHosts();
                                HttpsURLConnection https = (HttpsURLConnection) url
                                        .openConnection();
                                https.setHostnameVerifier(PayActivity.DO_NOT_VERIFY);
                                httpsConn = https;
                            } else {
                                httpsConn = (HttpURLConnection) url.openConnection();
                            }

                            httpsConn.setAllowUserInteraction(false);
                            httpsConn.setInstanceFollowRedirects(true);
                            httpsConn.setRequestMethod("GET");
                            httpsConn.connect();
                            resCode = httpsConn.getResponseCode();

                            if (resCode == HttpURLConnection.HTTP_OK) {
                                in = httpsConn.getInputStream();

                                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "iso-8859-1"), 8);
                                StringBuilder sb = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line).append("\n");
                                }
                                in.close();
                                httpResult = sb.toString();
                                Log.i(TAG, httpResult);

                                JSONObject json = new JSONObject(httpResult);
//
                                if (!json.has("retcode")) {

                                    PayReq req = new PayReq();
                                    //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                                    req.appId = json.getString("appid");

                                    req.partnerId = json.getString("partnerid");
                                    req.prepayId = json.getString("prepayid");
                                    req.nonceStr = json.getString("noncestr");
                                    req.timeStamp = json.getString("timestamp");
                                    req.packageValue = json.getString("package");
                                    req.sign = json.getString("sign");
                                    req.extData = "app data"; // optional

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//                                    api.sendReq(req);
                                    APay.sendWxReq(req);
                                } else {
                                    Log.d("PAY_GET", "服务器请求错误");
                                    Toast.makeText(PayActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
                                }

//                     {"appid":"wxb4ba3c02aa476ea1","partnerid":"1900006771","package":"Sign=WXPay","noncestr":"4b5a09a6e1457fd76999b6f695cef5c5","timestamp":1577690868,"prepayid":"wx30152748534403a7311e681b1710750332","sign":"1CBC0F471DC324E7DA29C144E40D9FB4"}
                            }
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                }).start();
            }
        });
    }

    public static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        // Android use X509 cert
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };
}
