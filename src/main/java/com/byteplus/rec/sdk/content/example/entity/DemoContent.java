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

    @JSONField(name = "content_owner")
    private String contentOwner;

    @JSONField(name = "content_owner_followers")
    private int contentOwnerFollowers;

    @JSONField(name = "content_owner_rating")
    private float contentOwnerRating;

    @JSONField(name = "content_owner_name")
    private String contentOwnerName;

    @JSONField(name = "collection_id")
    private String collectionId;

    // Json Array
    private String tags;

    // Json Array
    @JSONField(name = "topic_tags")
    private String topicTags;

    // Json Array
    @JSONField(name = "image_urls")
    private String imageUrls;

    @JSONField(name = "detail_pic_num")
    private int detailPicNum;

    // Json Array
    @JSONField(name = "video_urls")
    private String videoUrls;

    @JSONField(name = "user_rating")
    private float userRating;

    @JSONField(name = "views_count")
    private int viewsCount;

    @JSONField(name = "comments_count")
    private int commentsCount;

    @JSONField(name = "likes_count")
    private int likesCount;

    @JSONField(name = "shares_count")
    private int sharesCount;

    @JSONField(name = "save_count")
    private int saveCount;

    @JSONField(name = "current_price")
    private int currentPrice;

    @JSONField(name = "original_price")
    private int originalPrice;

    // Json Array
    @JSONField(name = "available_location")
    private String availableLocation;

    @JSONField(name = "publish_timestamp")
    private int publishTimestamp;

    @JSONField(name = "update_timestamp")
    private int updateTimestamp;

    @JSONField(name = "copyright_start_timestamp")
    private int copyrightStartTimestamp;

    @JSONField(name = "copyright_end_timestamp")
    private int copyrightEndTimestamp;

    @JSONField(name = "is_paid_content")
    private boolean isPaidContent;

    private String language;

    // Json Array
    @JSONField(name = "related_content_ids")
    private String relatedContentIds;

    @JSONField(name = "sold_count")
    private int soldCount;

    private String source;

    @JSONField(name = "custom_field")
    private String customField;
}
