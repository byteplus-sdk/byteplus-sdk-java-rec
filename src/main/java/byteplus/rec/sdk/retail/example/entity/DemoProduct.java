package byteplus.rec.sdk.retail.example.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class DemoProduct {
    @JSONField(name = "product_id")
    private String productId;

    // Json Array
    private String category;

    private String brands;

    @JSONField(name = "is_recommendable")
    private boolean isRecommendable;

    private String title;

    @JSONField(name = "price_current_price")
    private long priceCurrentPrice;

    @JSONField(name = "price_origin_price")
    private long priceOriginPrice;

    @JSONField(name = "quality_score")
    private double qualityScore;

    // Json Array
    private String tags;

    @JSONField(name = "display_cover_multimedia_url")
    private String displayCoverMultimediaUrl;

    @JSONField(name = "display_listing_page_display_type")
    private String displayListingPageDisplayType;

    // Json Array
    @JSONField(name = "display_listing_page_display_tags")
    private String displayListingPageDisplayTags;

    // Json Array
    @JSONField(name = "display_detail_page_display_tags")
    private String displayDetailPageDisplayTags;

    @JSONField(name = "seller_id")
    private String sellerId;

    @JSONField(name = "seller_seller_level")
    private String sellerSellerLevel;

    @JSONField(name = "seller_seller_rating")
    private double sellerSellerRating;

    @JSONField(name = "product_spec_product_group_id")
    private String productSpecProductGroupId;

    @JSONField(name = "product_spec_user_rating")
    private double productSpecUserRating;

    @JSONField(name = "product_spec_comment_count")
    private int productSpecCommentCount;

    @JSONField(name = "product_spec_source")
    private String productSpecSource;

    @JSONField(name = "product_spec_publish_timestamp")
    private long productSpecPublishTimestamp;

    @JSONField(name = "custom_field")
    private String customField;
}
