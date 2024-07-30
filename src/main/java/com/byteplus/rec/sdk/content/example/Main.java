package com.byteplus.rec.sdk.content.example;

import com.alibaba.fastjson.JSON;
import com.byteplus.rec.core.BizException;
import com.byteplus.rec.core.NetException;
import com.byteplus.rec.core.Option;
import com.byteplus.rec.core.StatusHelper;
import com.byteplus.rec.sdk.content.ContentClient;
import com.byteplus.rec.sdk.content.ContentClientBuilder;
import com.byteplus.rec.sdk.region.Region;
import com.byteplus.rec.sdk.content.Constant;
import com.byteplus.rec.sdk.content.example.entity.DemoUser;
import com.byteplus.rec.sdk.content.example.entity.DemoUserEvent;
import com.byteplus.rec.sdk.content.example.entity.DemoContent;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.WriteDataRequest;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.WriteResponse;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.FinishWriteDataRequest;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.PredictRequest;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.Scene;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.Content;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.Device;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.PredictResponse;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.PredictResult;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.PredictFilterItem;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.PredictResult.ResponseContent;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.AckServerImpressionsRequest;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.AckServerImpressionsRequest.AlteredContent;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.AckServerImpressionsResponse;
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.Date;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Slf4j
public class Main {
    private static ContentClient client;

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
//                .keepAliveDuration(Duration.ofSeconds(60)) // OKHttpClient keepAliveDuration param.
//                .keepAlivePingInterval(Duration.ofSeconds(45)) // Only takes effect when contentClient.keepAlive(true), heartbeat packet sending interval.
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
            client = new ContentClientBuilder()
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

        // Write real-time content data
        writeContentsExample();

        // Finish write real-time content data
//        finishWriteContentsExample();

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
        FinishWriteDataRequest request = buildFinishUserRequest();
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

    public static void writeContentsExample() {
        // The "WriteXXX" api can transfer max to 2000 items at one request
        WriteDataRequest request = buildWriteContentsRequest(1);
        Option[] options = defaultOptions(DEFAULT_WRITE_TIMEOUT);
        WriteResponse response;
        try {
            response = client.writeContents(request, options);
            // response = Utils.doWithRetry(client::writeContents, request, options, DEFAULT_RETRY_TIMES);
        } catch (BizException | NetException e) {
            log.error("write content occur err, msg:{}", e.getMessage());
            return;
        }
        if (StatusHelper.isUploadSuccess(response.getStatus().getCode())) {
            log.info("write content success");
            return;
        }
        log.error("write content find failure info, msg:{} errItems:{}",
                response.getStatus(), response.getErrorsList());
    }

    private static WriteDataRequest buildWriteContentsRequest(int count) {
        List<DemoContent> contents = MockHelper.mockContents(count);
        WriteDataRequest.Builder requestBuilder = WriteDataRequest.newBuilder()
                .setStage(Constant.STAGE_INCREMENTAL);
        for (DemoContent content : contents) {
            requestBuilder.addData(JSON.toJSONString(content));
        }
        return requestBuilder.build();
    }

    public static void finishWriteContentsExample() {
        // The "FinishXXX" api can mark max to 100 dates at one request
        FinishWriteDataRequest request = buildFinishContentRequest();
        Option[] opts = defaultOptions(DEFAULT_FINISH_TIMEOUT);
        WriteResponse response;
        try {
            response = client.finishWriteContents(request, opts);
            // response = Utils.doWithRetry(client::finishWriteContents, request, opts, DEFAULT_RETRY_TIMES);
        } catch (BizException | NetException e) {
            log.error("run finish occur error, msg:{}", e.getMessage());
            return;
        }
        if (StatusHelper.isUploadSuccess(response.getStatus().getCode())) {
            log.info("finish write content data");
            return;
        }
        log.error("fail to finish write content data, msg:{} errItems:{}",
                response.getStatus(), response.getInitializationErrorString());
    }

    private static FinishWriteDataRequest buildFinishContentRequest() {
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
        LocalDate date = LocalDate.of(2022, 8, 1);
        return FinishWriteDataRequest.newBuilder()
                .setStage(Constant.STAGE_INCREMENTAL)
                .addAllDataDates(buildDateList(date)).build();
    }

    private static List<Date> buildDateList(LocalDate date) {
        List<LocalDate> dateList = Collections.singletonList(date);
        List<Date> dates = new ArrayList<>();
        for (LocalDate singleDay : dateList) {
            addFinishDate(dates, singleDay);
        }
        return dates;
    }

    private static void addFinishDate(List<Date> dateMapList, LocalDate date) {
        dateMapList.add(buildFinishDate(date));
    }

    private static Date buildFinishDate(LocalDate date) {
        return Date.newBuilder()
                .setYear(date.getYear())
                .setMonth(date.getMonthValue())
                .setDay(date.getDayOfMonth())
                .build();
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
        LocalDate date = LocalDate.of(2022, 8, 1);
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
        List<AlteredContent> alteredContents = doSomethingWithPredictResult(response.getContentValue());
        AckServerImpressionsRequest ackRequest =
                buildAckRequest(response.getRequestId(), predictRequest, alteredContents);
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
        Content rootContent = MockHelper.mockPredictContent();
        Device device = MockHelper.mockDevice();

        List<Content> candidateContents = Arrays.asList(
                MockHelper.mockPredictContent(),
                MockHelper.mockPredictContent()
        );

        PredictRequest.Context context = PredictRequest.Context.newBuilder()
                .setRootContent(rootContent)
                .setDevice(device)
                .addAllCandidateContents(candidateContents)
                .build();

//        // Specify the list of IDs that need to be filtered by Byteplus recommendation service.
//        List<PredictFilterItem> filterItems = Arrays.asList(
//                PredictFilterItem.newBuilder().setId("632461").build(),
//                PredictFilterItem.newBuilder().setId("632462").build()
//        );

        return PredictRequest.newBuilder()
                .setModelId(MODEL_ID)
                .setUserId("1457789")
                .setSize(20)
                .setScene(scene)
                .setContentContext(context)
                // .addAllFilterItems(filterItems)
                // .putExtra("extra_inio", "extra")
                .build();
    }

    private static List<AlteredContent> doSomethingWithPredictResult(PredictResult predictResult) {
        // You can handle recommend results here,
        // such as filter, insert other items, sort again, etc.
        // The list of goods finally displayed to user and the filtered goods
        // should be sent back to bytedance for deduplication
        return conv2AlteredContents(predictResult.getResponseContentsList());
    }

    @NotNull
    private static List<AlteredContent> conv2AlteredContents(List<ResponseContent> contents) {
        if (Objects.isNull(contents) || contents.isEmpty()) {
            return Collections.emptyList();
        }
        List<AlteredContent> alteredContents = new ArrayList<>(contents.size());
        for (int i = 0; i < contents.size(); i++) {
            ResponseContent responseContent = contents.get(i);
            AlteredContent alteredContent = AlteredContent.newBuilder()
                    .setAlteredReason("kept")
                    .setContentId(responseContent.getContentId())
                    .setRank(i + 1)
                    .build();

            alteredContents.add(alteredContent);
        }
        return alteredContents;
    }

    private static AckServerImpressionsRequest buildAckRequest(
            String predictRequestId,
            PredictRequest predictRequest,
            List<AlteredContent> alteredContents) {

        return AckServerImpressionsRequest.newBuilder()
                .setModelId(predictRequest.getModelId())
                .setPredictRequestId(predictRequestId)
                .setUserId(predictRequest.getUserId())
                .setScene(predictRequest.getScene())
                // If it is the recommendation result from byteplus, traffic_source is byteplus,
                // if it is the customer's own recommendation result, traffic_source is self.
                .setTrafficSource("byteplus")
                .addAllAlteredContents(alteredContents)
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
                Option.withHTTPHeader("X-TT-ENV", "preview_prod")
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
