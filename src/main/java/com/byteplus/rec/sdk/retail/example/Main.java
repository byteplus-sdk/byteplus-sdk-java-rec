package com.byteplus.rec.sdk.retail.example;

import com.alibaba.fastjson.JSON;
import com.byteplus.rec.core.*;
import com.byteplus.rec.core.HTTPCaller.Config;
import com.byteplus.rec.core.metrics.MetricsCollector.MetricsCfg;
import com.byteplus.rec.core.metrics.MetricsCollector;
import com.byteplus.rec.sdk.region.Region;
import com.byteplus.rec.sdk.retail.Constant;
import com.byteplus.rec.sdk.retail.RetailClient;
import com.byteplus.rec.sdk.retail.RetailClientBuilder;
import com.byteplus.rec.sdk.retail.example.entity.DemoProduct;
import com.byteplus.rec.sdk.retail.example.entity.DemoUser;
import com.byteplus.rec.sdk.retail.example.entity.DemoUserEvent;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.AckServerImpressionsRequest;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.AckServerImpressionsRequest.AlteredProduct;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Device;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictRequest;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictFilterItem;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictResponse;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictResult;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictResult.ResponseProduct;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Product;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Scene;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.WriteDataRequest;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.WriteResponse;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.FinishWriteDataRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {
    private static RetailClient client;

    private final static int DEFAULT_RETRY_TIMES = 2;

    private final static Duration DEFAULT_WRITE_TIMEOUT = Duration.ofMillis(800);

    private final static Duration DEFAULT_PREDICT_TIMEOUT = Duration.ofMillis(800);

    private final static Duration DEFAULT_FINISH_TIMEOUT = Duration.ofMillis(800);

    private final static Duration DEFAULT_ACK_IMPRESSIONS_TIMEOUT = Duration.ofMillis(800);

    // A unique identity assigned by Bytedance.
    public final static String PROJECT_ID = "*********";

    // Unique id for this model.The saas model id that can be used to get rec results from predict api, which is need to fill in URL.
    public final static String MODEL_ID = "*********";


    static {
//        // Customize the caller config, the parameters in Example are the parameters currently used by default,
//        // you can customize them according to your own needs.
//        Config callerConfig = new Config().toBuilder()
//                .maxIdleConnections(32) // OKHttpClient maxIdleConnections param.
//                .keepAliveDuration(Duration.ofSeconds(60)) // OKHttpClient keepAliveDuration param. The maximum idle time of the connection.
//                .keepAlivePingInterval(Duration.ofSeconds(45)) // Only takes effect when retailClient.keepAlive(true). Heartbeat packet sending interval.
//                .maxKeepAliveConnections(3) // Only takes effect when retailClient.keepAlive(true). The number of heartbeats sent by a single host at the same time, it means the maximum number of keepalive connections
//                .build();


//        // Metrics configuration, when Metrics and Metrics Log are turned on,
//        // the metrics and logs at runtime will be collected and sent to the byteplus server.
//        // During debugging, byteplus can help customers troubleshoot problems.
//        MetricsCfg metricsCfg = new MetricsCfg().toBuilder()
//                .enableMetrics(true) // enable metrics, default is false.
//                .enableMetricsLog(true) // enable metrics log, default is false.
//                // The time interval for reporting metrics to the byteplus server, the default is 15s.
//                // When the QPS is high, the value of the reporting interval can be reduced to prevent
//                // loss of metrics.
//                // The longest should not exceed 30s, otherwise it will cause the loss of metrics accuracy.
//                .reportInterval(Duration.ofSeconds(15))
//                .build();
        try {
            client = new RetailClientBuilder()
                    .accountID("*********")  // Required
                    .projectID(PROJECT_ID)
                    .region(Region.SG)  // Required
                    .authAK("*********")  // Required. Access Key, used to generate request signature. Saas Standard projects should use.
                    .authSK("*********")  // Required. Secure key, used to generate request signature. Saas Standard projects should use.
//                    .airAuthToken("*********") // Required. The token of this project. Saas Premium projects should use.
//                    .metricsConfig(metricsCfg) // Optional.
//                    .keepAlive(true) // Optional.
//                    .callerConfig(callerConfig) // Optional.
//                    .schema("http") // Optional.
//                    .hosts(Collections.singletonList("rec-api-sg1.recplusapi.com")) // Optional
//                    .mainHost("rec-ap-singapore-1.byteplusapi.com") // Optional. Set one of the hosts set by '.hosts()' as the primary one.
                    .build();
        } catch (BizException e) {
            log.error("fail to create byteplus rec client", e);
        }
    }


    public static void main(String[] args) {
        // Write real-time user data
        writeUsersExample();

        // Finish write real-time user data
//        finishWriteUsersExample();

        // Write real-time product data
        writeProductsExample();

        // Finish write real-time product data
//        finishWriteProductsExample();

        // Write real-time user event data
        writeUserEventsExample();

        // Finish write real-time user event data
//        finishWriteUserEventsExample();

        // Write self defined topic data
//        writeOthersExample();

        // Finish write self defined topic data
//        finishWriteOthersExample();

        // Get recommendation results
        recommendExample();

        try {
            // Pause for 5 seconds until the asynchronous import task completes
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {

        }
        client.release();
        System.exit(0);
    }

    public static void writeUsersExample() {
        // The "WriteXXX" api can transfer max to 2000 items at one request
        WriteDataRequest request = buildWriteUsersRequest(1);
        Option[] opts = defaultOptions(DEFAULT_WRITE_TIMEOUT);
        WriteResponse response;
        try {
            response = client.writeUsers(request, opts);
            // response = Utils.doWithRetry(client::writeUsers, request, opts, DEFAULT_RETRY_TIMES);
        } catch (NetException | BizException e) {
            log.error("write user occur err, msg:{}", e.getMessage());
            return;
        }
        if (StatusHelper.isUploadSuccess(response.getStatus().getCode())) {
            log.info("write user success");
            return;
        }
        log.error("write user find failure info, msg:{} errItems:{}",
                response.getStatus(), response.getErrorsList());
    }

    private static WriteDataRequest buildWriteUsersRequest(int count) {
        List<DemoUser> users = MockHelper.mockUsers(count);
        WriteDataRequest.Builder requestBuilder = WriteDataRequest.newBuilder()
                .setStage(Constant.STAGE_INCREMENTAL);
        for (DemoUser user : users) {
            requestBuilder.addData(JSON.toJSONString(user));
        }
        return requestBuilder.build();
    }

    public static void finishWriteUsersExample() {
        ByteplusSaasRetail.FinishWriteDataRequest request = buildFinishUserRequest();
        Option[] opts = defaultOptions(DEFAULT_FINISH_TIMEOUT);
        WriteResponse response;
        try {
            response = client.finishWriteUsers(request, opts);
            // response = Utils.doWithRetry(client::finishWriteUsers, request, opts, DEFAULT_RETRY_TIMES);
        } catch (NetException | BizException e) {
            log.error("run finish occur error, msg:{}", e.getMessage());
            return;
        }
        if (StatusHelper.isUploadSuccess(response.getStatus().getCode())) {
            log.info("finish write user data");
            return;
        }
        log.error("fail to finish write user data, msg:{} errItems:{}",
                response.getStatus(), response.getInitializationErrorString());
    }

    private static FinishWriteDataRequest buildFinishUserRequest() {
        return FinishWriteDataRequest.newBuilder()
                .setStage(Constant.STAGE_INCREMENTAL)
                .build();
    }

    private static void addFinishDate(List<ByteplusSaasRetail.Date> dateMapList, LocalDate date) {
        dateMapList.add(buildFinishDate(date));
    }

    private static ByteplusSaasRetail.Date buildFinishDate(LocalDate date) {
        return ByteplusSaasRetail.Date.newBuilder()
                .setYear(date.getYear())
                .setMonth(date.getMonthValue())
                .setDay(date.getDayOfMonth())
                .build();
    }

    public static void writeProductsExample() {
        // The "WriteXXX" api can transfer max to 2000 items at one request
        WriteDataRequest request = buildWriteProductsRequest(1);
        Option[] options = defaultOptions(DEFAULT_WRITE_TIMEOUT);
        WriteResponse response;
        try {
            response = client.writeProducts(request, options);
            // response = Utils.doWithRetry(client::writeProducts, request, options, DEFAULT_RETRY_TIMES);
        } catch (BizException | NetException e) {
            log.error("write product occur err, msg:{}", e.getMessage());
            return;
        }
        if (StatusHelper.isUploadSuccess(response.getStatus().getCode())) {
            log.info("write product success");
            return;
        }
        log.error("write product find failure info, msg:{} errItems:{}",
                response.getStatus(), response.getErrorsList());
    }

    private static WriteDataRequest buildWriteProductsRequest(int count) {
        List<DemoProduct> products = MockHelper.mockProducts(count);
        WriteDataRequest.Builder requestBuilder = WriteDataRequest.newBuilder()
                .setStage(Constant.STAGE_INCREMENTAL);
        for (DemoProduct product : products) {
            requestBuilder.addData(JSON.toJSONString(product));
        }
        return requestBuilder.build();
    }

    public static void finishWriteProductsExample() {
        // The "FinishXXX" api can mark max to 100 dates at one request
        ByteplusSaasRetail.FinishWriteDataRequest request = buildFinishProductRequest();
        Option[] opts = defaultOptions(DEFAULT_FINISH_TIMEOUT);
        WriteResponse response;
        try {
            response = client.finishWriteProducts(request, opts);
            // response = Utils.doWithRetry(client::finishWriteProducts, request, opts, DEFAULT_RETRY_TIMES);
        } catch (BizException | NetException e) {
            log.error("run finish occur error, msg:{}", e.getMessage());
            return;
        }
        if (StatusHelper.isUploadSuccess(response.getStatus().getCode())) {
            log.info("finish write product data");
            return;
        }
        log.error("fail to finish write product data, msg:{} errItems:{}",
                response.getStatus(), response.getInitializationErrorString());
    }

    private static FinishWriteDataRequest buildFinishProductRequest() {
        return FinishWriteDataRequest.newBuilder()
                .setStage(Constant.STAGE_INCREMENTAL)
                .build();
    }

    public static void writeUserEventsExample() {
        // The "WriteXXX" api can transfer max to 2000 items at one request
        WriteDataRequest request = buildWriteUserEventsRequest(1);
        Option[] options = defaultOptions(DEFAULT_WRITE_TIMEOUT);
        WriteResponse response;
        try {
            response = client.writeUserEvents(request, options);
            // response = Utils.doWithRetry(client::writeUserEvents, request, options, DEFAULT_RETRY_TIMES);
        } catch (BizException | NetException e) {
            log.error("write user events occur err, msg:{}", e.getMessage());
            return;
        }
        if (StatusHelper.isUploadSuccess(response.getStatus().getCode())) {
            log.info("write user events success");
            return;
        }
        log.error("write user events find failure info, msg:{} errItems:{}",
                response.getStatus(), response.getErrorsList());
    }

    private static WriteDataRequest buildWriteUserEventsRequest(int count) {
        List<DemoUserEvent> userEvents = MockHelper.mockUserEvents(count);
        WriteDataRequest.Builder requestBuilder = WriteDataRequest.newBuilder()
                .setStage(Constant.STAGE_INCREMENTAL);
        for (DemoUserEvent userEvent : userEvents) {
            requestBuilder.addData(JSON.toJSONString(userEvent));
        }
        return requestBuilder.build();
    }

    public static void finishWriteUserEventsExample() {
        // The "FinishXXX" api can mark max to 100 dates at one request
        FinishWriteDataRequest request = buildFinishUserEventRequest();
        Option[] opts = defaultOptions(DEFAULT_FINISH_TIMEOUT);
        WriteResponse response;
        try {
            response = client.finishWriteUserEvents(request, opts);
            // response = Utils.doWithRetry(client::finishWriteUserEvents, request, opts, DEFAULT_RETRY_TIMES);
        } catch (BizException | NetException e) {
            log.error("run finish occur error, msg:{}", e.getMessage());
            return;
        }
        if (StatusHelper.isUploadSuccess(response.getStatus().getCode())) {
            log.info("finish write user_event data");
            return;
        }
        log.error("fail to finish write user_event data, msg:{} errItems:{}",
                response.getStatus(), response.getInitializationErrorString());
    }

    private static FinishWriteDataRequest buildFinishUserEventRequest() {
        // dates should be passed when finishing user event
        LocalDate date = LocalDate.of(2022, 3, 1);
        return FinishWriteDataRequest.newBuilder()
                .setStage(Constant.STAGE_INCREMENTAL)
                .addAllDataDates(buildDateList(date)).build();
    }

    private static List<ByteplusSaasRetail.Date> buildDateList(LocalDate date) {
        List<LocalDate> dateList = Collections.singletonList(date);
        List<ByteplusSaasRetail.Date> dates = new ArrayList<>();
        for (LocalDate everyDay : dateList) {
            addFinishDate(dates, everyDay);
        }
        return dates;
    }

    public static void writeOthersExample() {
        // The "WriteXXX" api can transfer max to 2000 items at one request
        // The `topic` is datatype, which specify the type of data users are going to write.
        // It is temporarily set to "video", the specific value depends on your need.
        String topic = "video";
        WriteDataRequest request = buildWriteOthersRequest(topic);
        // request must contain topic in WriteDataRequest.
        Option[] opts = defaultOptions(DEFAULT_WRITE_TIMEOUT);
        WriteResponse response;
        try {
            response = client.writeOthers(request, opts);
            // response = Utils.doWithRetry(client::writeOthers, request, opts, DEFAULT_RETRY_TIMES);
        } catch (BizException | NetException e) {
            log.error("write other data occur err, msg:{}", e.getMessage());
            return;
        }
        if (StatusHelper.isUploadSuccess(response.getStatus().getCode())) {
            log.info("write other data success");
            return;
        }
        log.error("write other data find failure info, msg:{} errItems:{}",
                response.getStatus(), response.getErrorsList());
    }

    private static WriteDataRequest buildWriteOthersRequest(String topic) {
        Map<String, Object> data = new HashMap<>();
        data.put("field1", 1);
        data.put("field2", "value2");

        return WriteDataRequest.newBuilder()
                .setStage(Constant.STAGE_INCREMENTAL)
                .setTopic(topic)
                .addData(data.toString())
                .build();
    }

    public static void finishWriteOthersExample() {
        // The "FinishXXX" api can mark max to 100 dates at one request
        // The `topic` is datatype, which specify the type of data users are going to finish writing
        // It is temporarily set to "video", the specific value depends on your need.
        String topic = "video";
        FinishWriteDataRequest request = buildFinishOthersRequest(topic);
        Option[] opts = defaultOptions(DEFAULT_FINISH_TIMEOUT);
        WriteResponse response;
        try {
            response = client.finishWriteOthers(request, opts);
            // response = Utils.doWithRetry(client::finishWriteOthers, request, opts, DEFAULT_RETRY_TIMES);
        } catch (BizException | NetException e) {
            log.error("run finish occur error, msg:{}", e.getMessage());
            return;
        }
        if (StatusHelper.isUploadSuccess(response.getStatus().getCode())) {
            log.info("finish writing data");
            return;
        }
        log.error("fail to finish, msg:{} errItems:{}",
                response.getStatus(), response.getInitializationErrorString());
    }

    private static FinishWriteDataRequest buildFinishOthersRequest(String topic) {
        // dates should be passed when finishing user event
        LocalDate date = LocalDate.of(2022, 2, 1);
        return FinishWriteDataRequest.newBuilder()
                .setStage(Constant.STAGE_INCREMENTAL)
                .setTopic(topic)
                .addAllDataDates(buildDateList(date)).build();
    }

    public static void recommendExample() {
        PredictRequest predictRequest = buildPredictRequest();
        Option[] predict_opts = defaultOptions(DEFAULT_PREDICT_TIMEOUT);
        PredictResponse response;
        try {
            response = client.predict(predictRequest, predict_opts);
        } catch (Exception e) {
            log.error("predict occur error, msg:{}", e.getMessage());
            return;
        }
        if (!StatusHelper.isSuccess(response.getStatus().getCode())) {
            log.error("predict find failure info, msg:{}", response.getStatus());
            return;
        }
        log.info("predict success");
        // The items, which is eventually shown to user,
        // should send back to Bytedance for deduplication
        List<AlteredProduct> alteredProducts = doSomethingWithPredictResult(response.getValue());
        AckServerImpressionsRequest ackRequest =
                buildAckRequest(response.getRequestId(), predictRequest, alteredProducts);
        Option[] ack_opts = defaultOptions(DEFAULT_ACK_IMPRESSIONS_TIMEOUT);
        // Utils.Callable<AckServerImpressionsResponse, AckServerImpressionsRequest> call
        //        = (req, optList) -> client.ackServerImpressions(req, optList);
        try {
            // Utils.doWithRetry(call, ackRequest, ack_opts, DEFAULT_RETRY_TIMES);
            client.ackServerImpressions(ackRequest, ack_opts);
        } catch (Exception e) {
            log.error("[AckServerImpressions] occur error, msg:{}", e.getMessage());
        }
    }

    private static PredictRequest buildPredictRequest() {
        Scene scene = Scene.newBuilder()
                .setOffset(10)
                .build();
        Product rootProduct = MockHelper.mockPredictProduct();
        Device device = MockHelper.mockDevice();

        List<Product> candidateProducts = Arrays.asList(
                MockHelper.mockPredictProduct(),
                MockHelper.mockPredictProduct()
        );

//        // Specify the list of IDs that need to be filtered by Byteplus recommendation service.
//        List<PredictFilterItem> filterItems = Arrays.asList(
//                PredictFilterItem.newBuilder().setId("632461").build(),
//                PredictFilterItem.newBuilder().setId("632462").build()
//        );

        PredictRequest.Context context = PredictRequest.Context.newBuilder()
                .setRootProduct(rootProduct)
                .setDevice(device)
                .addAllCandidateProducts(candidateProducts)
                .build();

        return PredictRequest.newBuilder()
                .setModelId(MODEL_ID)
                .setUserId("1457789")
                .setSize(20)
                .setScene(scene)
                .setContext(context)
                // .addAllFilterItems(filterItems)
                // .putExtra("extra_inio", "extra")
                .build();
    }

    private static List<AlteredProduct> doSomethingWithPredictResult(PredictResult predictResult) {
        // You can handle recommend results here,
        // such as filter, insert other items, sort again, etc.
        // The list of goods finally displayed to user and the filtered goods
        // should be sent back to bytedance for deduplication
        return conv2AlteredProducts(predictResult.getResponseProductsList());
    }

    @NotNull
    private static List<AlteredProduct> conv2AlteredProducts(List<ResponseProduct> products) {
        if (Objects.isNull(products) || products.isEmpty()) {
            return Collections.emptyList();
        }
        List<AlteredProduct> alteredProducts = new ArrayList<>(products.size());
        for (int i = 0; i < products.size(); i++) {
            ResponseProduct responseProduct = products.get(i);
            AlteredProduct alteredProduct = AlteredProduct.newBuilder()
                    .setAlteredReason("kept")
                    .setProductId(responseProduct.getProductId())
                    .setRank(i + 1)
                    .build();

            alteredProducts.add(alteredProduct);
        }
        return alteredProducts;
    }

    private static AckServerImpressionsRequest buildAckRequest(
            String predictRequestId,
            PredictRequest predictRequest,
            List<AlteredProduct> alteredProducts) {

        return AckServerImpressionsRequest.newBuilder()
                .setModelId(predictRequest.getModelId())
                .setPredictRequestId(predictRequestId)
                .setUserId(predictRequest.getUserId())
                .setScene(predictRequest.getScene())
                // If it is the recommendation result from byteplus, traffic_source is byteplus,
                // if it is the customer's own recommendation result, traffic_source is self.
                .setTrafficSource("byteplus")
                .addAllAlteredProducts(alteredProducts)
                //.putExtra("ip", "127.0.0.1")
                .build();
    }

    private static Option[] defaultOptions(Duration timeout) {
        return new Option[]{
                // Required，It is required that the Request-Id of each request is not repeated.
                // If it is not passed, the SDK will add it to each request by default
                Option.withRequestID(UUID.randomUUID().toString()),
                // Optional. request timeout
                Option.withTimeout(timeout),
                // Optional. Add a header to a custom header collection.
                // Option.withHTTPHeader("key", "value"),
                // Optional. Add a query to a custom query collection.
                // Option.withHTTPQuery("key", "value"),
                // Optional. It is expected that the server will process the data for the maximum time.
                // If the processing time exceeds this time, the server will return the result immediately,
                // regardless of whether there is any remaining data that has not been processed.
                // Option.withServerTimeout(Duration.ofMillis(5000))
        };
    }
}
