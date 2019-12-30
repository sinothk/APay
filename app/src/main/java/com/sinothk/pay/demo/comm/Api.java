package com.sinothk.pay.demo.comm;

public interface Api {

//    /**
//     * 获取设备列表 》 通过IMEI
//     *
//     * @param imei
//     * @return
//     */
//    @GET("app/queryMachByImei")
//    Observable<ResultInfo<ArrayList<WashingEntity>>> getWashingMacList(@Query("imei") String imei);
//
//    /**
//     * 获取设备消费模式列表 》 通过placeId
//     *
//     * @param placeId
//     * @return
//     */
//    @GET("app/queryByPlaceId")
//    Observable<ResultInfo<ArrayList<WashingModelEntity>>> getWashingModelList(@Query("placeId") int placeId);
//
//    /**
//     * 获取支付凭证
//     *
//     * @param rawData
//     * @param machineCode
//     * @return
//     */
//    @POST("app/getWxPayFaceAuthInfo")
//    Observable<ResultInfo<PayFaceAuthInfoEntity>> getWxPayFaceAuthInfo(@Query("rawData") String rawData, @Query("machineCode") String machineCode);
//
//    /**
//     * 提交订单支付信息
//     *
//     * @param order
//     * @return
//     */
//    @POST("app/saveOrder")
//    Observable<ResultInfo<PayResultEntity>> submitWxPayOrder(@Body PayFaceOrderEntity order);
}
