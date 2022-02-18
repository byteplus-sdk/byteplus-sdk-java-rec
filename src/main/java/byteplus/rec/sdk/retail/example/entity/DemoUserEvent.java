package byteplus.rec.sdk.retail.example.entity;

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

    private String scene;

    @JSONField(name = "scene_page_number")
    private int scenePageNumber;

    @JSONField(name = "scene_offset")
    private int sceneOffset;

    @JSONField(name = "product_id")
    private String productId;

    @JSONField(name = "device_platform")
    private String devicePlatform;

    @JSONField(name = "device_os_type")
    private String deviceOsType;

    @JSONField(name = "device_app_version")
    private String deviceAppVersion;

    @JSONField(name = "device_device_model")
    private String deviceDeviceModel;

    @JSONField(name = "device_device_brand")
    private String deviceDeviceBrand;

    @JSONField(name = "device_os_version")
    private String deviceOsVersion;

    @JSONField(name = "device_browser_type")
    private String deviceBrowserType;

    @JSONField(name = "device_user_agent")
    private String deviceUserAgent;

    @JSONField(name = "device_network")
    private String deviceNetwork;

    @JSONField(name = "context_query")
    private String contextQuery;

    @JSONField(name = "context_root_product_id")
    private String contextRootProductId;

    @JSONField(name = "attribution_token")
    private String attributionToken;

    @JSONField(name = "rec_info")
    private String recInfo;

    @JSONField(name = "traffic_source")
    private String trafficSource;

    @JSONField(name = "purchase_count")
    private int purchaseCount;

    @JSONField(name = "detail_page_stay_time")
    private int detailPageStayTime;

    @JSONField(name = "custom_field")
    private String customField;
}
