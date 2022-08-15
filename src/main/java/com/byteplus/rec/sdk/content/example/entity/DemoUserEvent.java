package com.byteplus.rec.sdk.content.example.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class DemoUserEvent {
    @JSONField(name = "user_id")
    private String userId;

    @JSONField(name = "event_type")
    private String eventType;

    @JSONField(name = "event_timestamp")
    private int eventTimestamp;

    @JSONField(name = "content_id")
    private String contentId;

    @JSONField(name = "traffic_source")
    private String trafficSource;

    @JSONField(name = "request_id")
    private String requestId;

    @JSONField(name = "rec_info")
    private String recInfo;

    @JSONField(name = "attribution_token")
    private String attributionToken;

    @JSONField(name = "scene_name")
    private String sceneName;

    @JSONField(name = "page_number")
    private int pageNumber;

    @JSONField(name = "offset")
    private int offset;

    @JSONField(name = "play_duration")
    private int playDuration;

    @JSONField(name = "video_duration")
    private int videoDuration;

    @JSONField(name = "start_time")
    private int startTime;

    @JSONField(name = "end_time")
    private int endTime;

    @JSONField(name = "parent_content_id")
    private String parentContentId;

    @JSONField(name = "content_owner_id")
    private String contentOwnerId;

    @JSONField(name = "detail_stay_time")
    private int detailStayTime;

    @JSONField(name = "dislike_type")
    private String dislikeType;

    @JSONField(name = "dislike_value")
    private String dislikeValue;

    private String query;

    private String platform;

    @JSONField(name = "os_type")
    private String osType;

    @JSONField(name = "app_version")
    private String appVersion;

    @JSONField(name = "device_model")
    private String deviceModel;

    @JSONField(name = "os_version")
    private String osVersion;

    private String network;

    private String country;

    private String province;

    private String city;

    private String district;

    private String area;

    @JSONField(name = "custom_field")
    private String customField;
}
