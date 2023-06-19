package com.byteplus.rec.sdk.content.example.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class DemoUser {
    @JSONField(name = "user_id")
    private String userId;

    private String gender;

    private String age;

    // Json Array
    private String tags;

    private String language;

    @JSONField(name = "subscriber_type")
    private String subscriberType;

    @JSONField(name = "membership_level")
    private String membershipLevel;

    @JSONField(name = "registration_timestamp")
    private long registrationTimestamp;

    private String country;

    private String province;

    private String city;

    private String district;

    @JSONField(name = "custom_field")
    private String customField;
}