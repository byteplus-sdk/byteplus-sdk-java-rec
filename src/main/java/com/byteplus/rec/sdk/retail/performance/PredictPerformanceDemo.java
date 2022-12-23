package com.byteplus.rec.sdk.retail.performance;

import com.byteplus.rec.core.BizException;
import com.byteplus.rec.core.Option;
import com.byteplus.rec.sdk.retail.RetailClientBuilder;
import com.byteplus.rec.sdk.region.Region;
import com.byteplus.rec.sdk.retail.example.MockHelper;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail;
import lombok.extern.slf4j.Slf4j;
import com.byteplus.rec.sdk.retail.RetailClient;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictRequest;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.Scene;

import java.time.Duration;
import java.util.UUID;

@Slf4j
public class PredictPerformanceDemo extends AbstractJavaSamplerClient {
    private static RetailClient client;
    private static final Duration DEFAULT_PREDICT_TIMEOUT = Duration.ofMillis(2000L);
    public final static String PROJECT_ID = "*********";
    public final static String MODEL_ID = "*********";

    public void setupTest(JavaSamplerContext arg0) {
        //optional. Execute before testing, do some initialization work.
        try {
            client = new RetailClientBuilder()

                    .accountID("*********")  // Required
                    .projectID(PROJECT_ID)
                    .region(Region.SG)  // Required
                    .authAK("*********")  // Required
                    .authSK("*********")  // Required
//                    .metricsConfig(metricsCfg) // Optional.
                    .keepAlive(true) // Optional.
//                    .callerConfig(callerConfig) // Optional.
//                    .schema("http") // Optional.
//                    .hosts(Collections.singletonList("rec-api-sg1.recplusapi.com")) // Optional
//                    .hosts(Collections.singletonList("rec-api-sg1.recplusapi.com/sg2"))
//                    .hostAvailablerFactory(hostAvailablerFactory)
                    .build();
        } catch (BizException e) {
            log.error("fail to create byteplus rec client", e);
        }
    }

    public void teardownTest(JavaSamplerContext arg0) {
        //Optional. Called when the test ends.
        client.release();
    }

    private static Option[] defaultOptions(Duration timeout) {
        return new Option[]{Option.withRequestID(UUID.randomUUID().toString()), Option.withTimeout(timeout)};
    }

    public PredictRequest buildPredictRequest() {
        //Set the predict requests body.
        ByteplusSaasRetail.Scene scene = Scene.newBuilder().setOffset(10).build();
//        ByteplusSaasRetail.Product rootProduct = MockHelper.mockPredictProduct();
        ByteplusSaasRetail.Device device = MockHelper.mockDevice();
//        List<Product> candidateProducts = Arrays.asList(MockHelper.mockPredictProduct(), MockHelper.mockPredictProduct());
        ByteplusSaasRetail.PredictRequest.Context context = PredictRequest.Context.newBuilder().setDevice(device).build();
        return PredictRequest.newBuilder().
                setModelId(MODEL_ID).
                setUserId("*********").
                setSize(20).
                setScene(scene).
                setContext(context).build();
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        //Required. Implements customize requests.
        SampleResult sr = new SampleResult();
        sr.setSampleLabel("Predict Sampler"); //Set the sampler name.
        ByteplusSaasRetail.PredictRequest predictRequest = buildPredictRequest();
        Option[] predict_opts = defaultOptions(DEFAULT_PREDICT_TIMEOUT);
        ByteplusSaasRetail.PredictResponse response;
        try {
            sr.sampleStart(); // Begin to count the response time.
            response = client.predict(predictRequest, predict_opts); // Send a predict request through sdk.
            sr.setResponseData(response.toString(), "utf-8");
            sr.setSamplerData(predictRequest.toString());
            sr.setDataType(SampleResult.TEXT);
            if (response.getStatus().getCode() == 0 && response.getValue().getResponseProductsCount() > 0) {
                sr.setSuccessful(true); // Set the request result is successful.
            } else {
                sr.setSuccessful(false);  // Set the request result is failed.
            }
        } catch (Throwable e) {
            sr.setSuccessful(false);
            e.printStackTrace();
        } finally {
            sr.sampleEnd(); //Finish statistics for this request.
        }
        return sr;
    }
}