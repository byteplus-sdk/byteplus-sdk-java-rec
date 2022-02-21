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
}
