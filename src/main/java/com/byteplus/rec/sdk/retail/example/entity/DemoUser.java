package com.byteplus.rec.sdk.retail.example.entity;

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

    @JSONField(name = "activation_channel")
    private String activationChannel;

    @JSONField(name = "membership_level")
    private String membershipLevel;

    @JSONField(name = "registration_timestamp")
    private long registrationTimestamp;

    @JSONField(name = "location_city")
    private String locationCity;

    @JSONField(name = "location_country")
    private String locationCountry;

    @JSONField(name = "location_district_or_area")
    private String locationDistrictOrArea;

    @JSONField(name = "location_postcode")
    private String locationPostcode;

    @JSONField(name = "custom_field")
    private String customField;
}
