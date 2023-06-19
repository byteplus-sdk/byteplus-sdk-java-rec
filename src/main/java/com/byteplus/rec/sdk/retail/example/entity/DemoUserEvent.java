package com.byteplus.rec.sdk.retail.example.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class DemoUserEvent {
    @JSONField(name = "user_id")
    private String userId;

    @JSONField(name = "event_type")
    private String eventType;

    @JSONField(name = "event_timestamp")
    private long eventTimestamp;

    @JSONField(name = "scene_name")
    private String sceneName;

    @JSONField(name = "page_number")
    private int pageNumber;

    @JSONField(name = "offset")
    private int offset;

    @JSONField(name = "product_id")
    private String productId;

    private String platform;

    @JSONField(name = "os_type")
    private String osType;

    @JSONField(name = "app_version")
    private String appVersion;

    @JSONField(name = "device_model")
    private String deviceModel;

    @JSONField(name = "os_version")
    private String osVersion;

    @JSONField(name = "network")
    private String network;

    @JSONField(name = "query")
    private String query;

    @JSONField(name = "parent_product_id")
    private String parentProductId;

    @JSONField(name = "attribution_token")
    private String attributionToken;

    @JSONField(name = "traffic_source")
    private String trafficSource;

    @JSONField(name = "purchase_count")
    private int purchaseCount;

    @JSONField(name = "paid_price")
    private float paidPrice;

    private String currency;

    private String city;

    private String country;

    private String district;

    private String province;

    @JSONField(name = "custom_field")
    private String customField;
}
