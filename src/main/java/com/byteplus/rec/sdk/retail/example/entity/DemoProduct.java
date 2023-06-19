package com.byteplus.rec.sdk.retail.example.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class DemoProduct {
    @JSONField(name = "product_id")
    private String productId;

    // Json Array
    private String categories;

    private String brands;

    @JSONField(name = "is_recommendable")
    private int isRecommendable;

    private String title;

    @JSONField(name = "current_price")
    private float currentPrice;

    @JSONField(name = "original_price")
    private float originalPrice;

    // Json Array
    private String tags;

    // Json Array
    @JSONField(name = "display_cover_multimedia_url")
    private String displayCoverMultimediaUrl;

    @JSONField(name = "seller_id")
    private String sellerId;

    @JSONField(name = "seller_level")
    private String sellerLevel;

    @JSONField(name = "seller_rating")
    private float sellerRating;

    @JSONField(name = "product_group_id")
    private String productGroupId;

    @JSONField(name = "user_rating")
    private float userRating;

    @JSONField(name = "comment_count")
    private int commentCount;

    @JSONField(name = "sold_count")
    private int soldCount;

    private String source;

    @JSONField(name = "publish_timestamp")
    private long publishTimestamp;

    @JSONField(name = "custom_field")
    private String customField;
}
