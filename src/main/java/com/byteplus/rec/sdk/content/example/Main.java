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
import com.byteplus.rec.sdk.content.protocol.ByteplusSaasContent.Date;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Slf4j
public class Main {
    private static ContentClient client;

    private final static int DEFAULT_RETRY_TIMES = 2;

    private final static Duration DEFAULT_WRITE_TIMEOUT = Duration.ofMillis(8000);

    private final static Duration DEFAULT_FINISH_TIMEOUT = Duration.ofMillis(800);

    // A unique identity assigned by Bytedance.
    public final static String PROJECT_ID = "*********";

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
                    .authAK("*********")  // Required
                    .authSK("*********")  // Required
//                    .metricsConfig(metricsCfg) // Optional.
//                    .keepAlive(true) // Optional.
//                    .callerConfig(callerConfig) // Optional.
//                    .schema("http") // Optional.
//                    .hosts(Collections.singletonList("rec-api-sg1.recplusapi.com")) // Optional
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
