package com.byteplus.rec.sdk.content.example.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class DemoUser {
    @JSONField(name = "user_id")
    private String userId;

    @JSONField(name = "user_id_type")
    private String userIdType;

    private String gender;

    private String age;

    // Json Array
    private String tags;

    private String language;

    @JSONField(name = "subscriber_type")
    private String subscriberType;

    private String network;

    private String platform;

    @JSONField(name = "os_type")
    private String osType;

    @JSONField(name = "app_version")
    private String appVersion;

    @JSONField(name = "device_model")
    private String deviceModel;

    @JSONField(name = "os_version")
    private String osVersion;

    @JSONField(name = "membership_level")
    private String membershipLevel;

    @JSONField(name = "registration_timestamp")
    private long registrationTimestamp;

    @JSONField(name = "update_timestamp")
    private long updateTimestamp;

    @JSONField(name = "last_login_timestamp")
    private long lastLoginTimestamp;

    private String country;

    private String province;

    private String city;

    private String district;

    private String area;

    @JSONField(name = "custom_field")
    private String customField;
}