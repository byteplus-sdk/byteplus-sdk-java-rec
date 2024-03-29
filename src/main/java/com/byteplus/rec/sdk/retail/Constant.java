package com.byteplus.rec.sdk.retail;

public final class Constant {
    public final static int MAX_WRITE_COUNT = 2000;

    public final static int MAX_FINISH_DATE_COUNT = 100;

    // STAGE_TRIAL In this stage, data will be only used to test
    public final static String STAGE_TRIAL = "trial";

    // STAGE_PRODUCTION In this stage, data will be used to train model
    public final static String STAGE_PRODUCTION = "production";

    // STAGE_INCREMENTAL In this stage, data will be used to update model
    public final static String STAGE_INCREMENTAL = "incremental_sync_streaming";

    // STAGE_INCREMENTAL_REALTIME In this stage, data will be used to realtime update model
    // Please use `STAGE_INCREMENTAL` instead `STAGE_INCREMENTAL_REALTIME` in most cases
    public final static String STAGE_INCREMENTAL_REALTIME = "incremental_sync_streaming";

    // STAGE_INCREMENTAL_DAILY In this stage, data will be used to daily update model
    // Please use `STAGE_INCREMENTAL` instead `STAGE_INCREMENTAL_DAILY` in most cases
    public final static String STAGE_INCREMENTAL_DAILY = "incremental_sync_daily";

    // TOPIC_USER is the type of data when writeUsers or FinishWriteUsers
    public final static String TOPIC_USER = "user";

    // TOPIC_PRODUCT is the type of data when WriteProducts or FinishWriteProducts
    public final static String TOPIC_PRODUCT = "goods";

    // TOPIC_USER_EVENT is the type of data when WriteUserEvents or FinishWriteUserEvents
    public final static String TOPIC_USER_EVENT = "behavior";

    // USER_URI in user topic, url path is end with WriteUsers
    public final static String USER_URI = "/RetailSaaS/WriteUsers";

    // FINISH_USER_URI The URL format of finish information
    public final static String FINISH_USER_URI = "/RetailSaaS/FinishWriteUsers";

    public final static String PRODUCT_URI = "/RetailSaaS/WriteProducts";

    public final static String FINISH_PRODUCT_URI = "/RetailSaaS/FinishWriteProducts";

    public final static String USER_EVENT_URI = "/RetailSaaS/WriteUserEvents";

    public final static String FINISH_USER_EVENT_URI = "/RetailSaaS/FinishWriteUserEvents";

    public final static String OTHERS_URI = "/RetailSaaS/WriteOthers";

    public final static String FINISH_OTHERS_URI = "/RetailSaaS/FinishWriteOthers";

    public final static String PREDICT_URI = "/RetailSaaS/Predict";

    public final static String ACK_SERVER_IMPRESSIONS_URI = "/RetailSaaS/AckServerImpressions";
}
