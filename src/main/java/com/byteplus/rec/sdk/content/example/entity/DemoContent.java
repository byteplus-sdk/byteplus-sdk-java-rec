package com.byteplus.rec.sdk.content.example.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class DemoContent {
    @JSONField(name = "content_id")
    private String contentId;

    @JSONField(name = "is_recommendable")
    private int isRecommendable;

    // Json Array
    private String categories;

    @JSONField(name = "content_type")
    private String contentType;

    @JSONField(name = "video_duration")
    private int videoDuration;

    @JSONField(name = "content_title")
    private String contentTitle;

    private String description;

    @JSONField(name = "content_owner_id")
    private String contentOwnerId;

    @JSONField(name = "collection_id")
    private String collectionId;

    // Json Array
    private String tags;

    // Json Array
    @JSONField(name = "image_urls")
    private String imageUrls;

    // Json Array
    @JSONField(name = "video_urls")
    private String videoUrls;

    @JSONField(name = "user_rating")
    private float userRating;

    @JSONField(name = "current_price")
    private float currentPrice;

    @JSONField(name = "original_price")
    private float originalPrice;

    @JSONField(name = "publish_timestamp")
    private long publishTimestamp;

    @JSONField(name = "is_paid_content")
    private boolean isPaidContent;

    private String language;

    // Json Array
    @JSONField(name = "linked_product_id")
    private String linkedProductId;

    private String source;

    @JSONField(name = "custom_field")
    private String customField;
}
