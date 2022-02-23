package com.byteplus.rec.sdk.retail.example;

import com.alibaba.fastjson.JSON;
import com.byteplus.rec.core.BizException;
import com.byteplus.rec.core.Option;
import com.byteplus.rec.core.StatusHelper;
import com.byteplus.rec.core.Utils;
import com.byteplus.rec.sdk.region.Region;
import com.byteplus.rec.sdk.retail.Constant;
import com.byteplus.rec.sdk.retail.RetailClient;
import com.byteplus.rec.sdk.retail.RetailClientBuilder;
import com.byteplus.rec.sdk.retail.example.entity.DemoProduct;
import com.byteplus.rec.sdk.retail.example.entity.DemoUser;
import com.byteplus.rec.sdk.retail.example.entity.DemoUserEvent;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.AckServerImpressionsRequest;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.AckServerImpressionsRequest.AlteredProduct;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.AckServerImpressionsResponse;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Device;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictRequest;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictResponse;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictResult;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictResult.ResponseProduct;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Product;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Scene;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.WriteDataRequest;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.WriteResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class Main {
    private static RetailClient client;

    private final static int DEFAULT_RETRY_TIMES = 2;

    private final static Duration DEFAULT_WRITE_TIMEOUT = Duration.ofMillis(800);

    private final static Duration DEFAULT_PREDICT_TIMEOUT = Duration.ofMillis(800);

    private final static Duration DEFAULT_ACK_IMPRESSIONS_TIMEOUT = Duration.ofMillis(800);

    // A unique identity assigned by Bytedance.
    public final static String PROJECT_ID = "***********";

    // Unique id for this model.The saas model id that can be used to get rec results from predict api, which is need to fill in URL.
    public final static String MODEL_ID = "***********";

    static {
        try {
            client = new RetailClientBuilder()
                    .tenantID("***********")  // Required
                    .projectID(PROJECT_ID)
                    .region(Region.SG)  // Required
                    .authAK("***********")  // Required
                    .authSK("***********")  // Required
//                    .schema("http") // Optional
//                    .hosts(Collections.singletonList("rec-api-sg1.recplusapi.com")) // Optional
                    .build();
        } catch (BizException e) {
            log.error("fail to create byteplus rec client", e);
        }
    }

    public static void main(String[] args) {
        // Write real-time user data
        writeUsersExample();

        // Write real-time product data
        writeProductsExample();

        // Write real-time user event data
        writeUserEventsExample();

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
            response = Utils.doWithRetry(client::writeUsers, request, opts, DEFAULT_RETRY_TIMES);
        } catch (BizException e) {
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
        WriteDataRequest.Builder requestBuilder = WriteDataRequest.newBuilder();
        requestBuilder.setProjectId(PROJECT_ID);
        requestBuilder.setStage(Constant.STAGE_TRIAL);
        for (DemoUser user : users) {
            requestBuilder.addData(JSON.toJSONString(user));
        }
        return requestBuilder.build();
    }

    public static void writeProductsExample() {
        // The "WriteXXX" api can transfer max to 2000 items at one request
        WriteDataRequest request = buildWriteProductsRequest(1);
        Option[] options = defaultOptions(DEFAULT_WRITE_TIMEOUT);
        WriteResponse response;
        try {
            response = Utils.doWithRetry(client::writeProducts, request, options, DEFAULT_RETRY_TIMES);
        } catch (BizException e) {
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
        WriteDataRequest.Builder requestBuilder = WriteDataRequest.newBuilder();
        requestBuilder.setProjectId(PROJECT_ID);
        requestBuilder.setStage(Constant.STAGE_TRIAL);
        for (DemoProduct product : products) {
            requestBuilder.addData(JSON.toJSONString(product));
        }
        return requestBuilder.build();
    }

    public static void writeUserEventsExample() {
        // The "WriteXXX" api can transfer max to 2000 items at one request
        WriteDataRequest request = buildWriteUserEventsRequest(1);
        Option[] options = defaultOptions(DEFAULT_WRITE_TIMEOUT);
        WriteResponse response;
        try {
            response = Utils.doWithRetry(client::writeUserEvents, request, options, DEFAULT_RETRY_TIMES);
        } catch (BizException e) {
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
        WriteDataRequest.Builder requestBuilder = WriteDataRequest.newBuilder();
        requestBuilder.setProjectId(PROJECT_ID);
        requestBuilder.setStage(Constant.STAGE_TRIAL);
        for (DemoUserEvent userEvent : userEvents) {
            requestBuilder.addData(JSON.toJSONString(userEvent));
        }
        return requestBuilder.build();
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
        Utils.Callable<AckServerImpressionsResponse, AckServerImpressionsRequest> call
                = (req, optList) -> client.ackServerImpressions(req, optList);
        try {
            Utils.doWithRetry(call, ackRequest, ack_opts, DEFAULT_RETRY_TIMES);
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

        PredictRequest.Context context = PredictRequest.Context.newBuilder()
                .setRootProduct(rootProduct)
                .setDevice(device)
                .addAllCandidateProductIds(Arrays.asList("632462", "632463"))
                .build();

        return PredictRequest.newBuilder()
                .setProjectId(PROJECT_ID)
                .setModelId(MODEL_ID)
                .setUserId("1457789")
                .setSize(20)
                .setScene(scene)
                .setContext(context)
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
                .setProjectId(predictRequest.getProjectId())
                .setModelId(predictRequest.getModelId())
                .setPredictRequestId(predictRequestId)
                .setUserId(predictRequest.getUserId())
                .setScene(predictRequest.getScene())
                .addAllAlteredProducts(alteredProducts)
                .build();
    }

    private static Option[] defaultOptions(Duration timeout) {
        return new Option[]{
                // Required，It is required that the Request-Id of each request is not repeated.
                // If it is not passed, the SDK will add it to each request by default
                Option.withRequestID(UUID.randomUUID().toString()),
                // Optional. request timeout
                Option.withTimeout(timeout),
                // Optional. Add a set of customer headers to the request, which will be overwritten by multiple calls.
                // Option.withHeaders(customerHeaders),
                // Optional. Add a set of customer queries to the request, which will be overwritten by multiple calls.
                // Option.withQueries(customerQueries),
                // Optional. Add a header to an existing custom header collection.
                // Option.withHeader("key", "value"),
                // Optional. Add a query to an existing custom query collection.
                // Option.withQuery("key", "value"),
                // Optional. It is expected that the server will process the data for the maximum time.
                // If the processing time exceeds this time, the server will return the result immediately,
                // regardless of whether there is any remaining data that has not been processed.
                // Option.withServerTimeout(Duration.ofMillis(5000))
        };
    }
}