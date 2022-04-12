package com.byteplus.rec.sdk.retail;

public final class Constant {
    public final static int MAX_WRITE_COUNT = 2000;

    // STAGE_TRIAL In this stage, data will be only used to test
    public final static String STAGE_TRIAL = "trial";

    // STAGE_PRODUCTION In this stage, data will be used to train model
    public final static String STAGE_PRODUCTION = "production";

    // STAGE_INCREMENTAL_REALTIME In this stage, data will be used to realtime update model
    public final static String STAGE_INCREMENTAL_REALTIME = "incremental_sync_streaming";

    // STAGE_INCREMENTAL_DAILY In this stage, data will be used to daily update model
    public final static String STAGE_INCREMENTAL_DAILY = "incremental_sync_daily";

    // TOPIC_USER Topic if write users and finish write users
    public final static String TOPIC_USER = "user";

    // TOPIC_PRODUCT Topic if write products and finish write products
    public final static String TOPIC_PRODUCT = "goods";

    // TOPIC_USER_EVENT Topic if write user events and finish write user events
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
}
