package com.byteplus.rec.sdk.retail;

import com.byteplus.rec.core.BizException;
import com.byteplus.rec.core.HTTPClient;
import com.byteplus.rec.core.NetException;
import com.byteplus.rec.core.Option;
import com.byteplus.rec.core.Utils;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.AckServerImpressionsRequest;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.AckServerImpressionsResponse;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictRequest;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictResponse;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.WriteDataRequest;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.WriteResponse;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.FinishWriteDataRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class RetailClientImpl implements RetailClient {
    private final static String ERR_MSG_TOO_MANY_WRITE_ITEMS =
            String.format("Only can receive max to %d items in one write request", Constant.MAX_WRITE_COUNT);

    private final static String ERR_MSG_TOO_MANY_FINISH_DATES =
            String.format("Only can receive max to %d dates in one finish request", Constant.MAX_FINISH_DATE_COUNT);

    private final HTTPClient httpClient;

    private final String projectID;

    protected RetailClientImpl(HTTPClient httpClient, String projectID) {
        this.httpClient = httpClient;
        this.projectID = projectID;
    }

    @Override
    public WriteResponse writeUsers(WriteDataRequest request, Option... opts) throws NetException, BizException {
        WriteDataRequest writeRequest = request.toBuilder().setTopic(Constant.TOPIC_USER).build();
        return doWriteData(writeRequest, Constant.USER_URI, opts);
    }

    @Override
    public WriteResponse finishWriteUsers(FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        FinishWriteDataRequest finishRequest = request.toBuilder().setTopic(Constant.TOPIC_USER).build();
        return doFinishData(finishRequest, Constant.FINISH_USER_URI, opts);
    }

    @Override
    public WriteResponse writeProducts(WriteDataRequest request, Option... opts) throws NetException, BizException {
        WriteDataRequest writeRequest = request.toBuilder().setTopic(Constant.TOPIC_PRODUCT).build();
        return doWriteData(writeRequest, Constant.PRODUCT_URI, opts);
    }

    @Override
    public WriteResponse finishWriteProducts(FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        FinishWriteDataRequest finishRequest = request.toBuilder().setTopic(Constant.TOPIC_PRODUCT).build();
        return doFinishData(finishRequest, Constant.FINISH_PRODUCT_URI, opts);
    }

    @Override
    public WriteResponse writeUserEvents(WriteDataRequest request, Option... opts) throws NetException, BizException {
        WriteDataRequest writeRequest = request.toBuilder().setTopic(Constant.TOPIC_USER_EVENT).build();
        return doWriteData(writeRequest, Constant.USER_EVENT_URI, opts);
    }

    @Override
    public WriteResponse finishWriteUserEvents(FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        FinishWriteDataRequest finishRequest = request.toBuilder().setTopic(Constant.TOPIC_USER_EVENT).build();
        return doFinishData(finishRequest, Constant.FINISH_USER_EVENT_URI, opts);
    }

    @Override
    public WriteResponse writeOthers(WriteDataRequest request, Option... opts) throws NetException, BizException {
        return doWriteData(request, Constant.OTHERS_URI, opts);
    }

    @Override
    public WriteResponse finishWriteOthers(FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        return doFinishData(request, Constant.FINISH_OTHERS_URI, opts);
    }

    private WriteResponse doWriteData(WriteDataRequest request,
                                      String path, Option... opts) throws NetException, BizException {
        if (Objects.nonNull(projectID) && request.getProjectId().length() == 0) {
            request = request.toBuilder().setProjectId(projectID).build();
        }
        checkWriteDataRequest(request);
        WriteResponse response = httpClient.doPBRequest(
                path,
                request,
                WriteResponse.parser(),
                Option.conv2Options(opts)
        );
        log.debug("[ByteplusSDK][WriteData] req:\n{} rsp:\n{}", request, response);
        return response;
    }

    private void checkWriteDataRequest(WriteDataRequest request) throws BizException {
        if (Utils.isEmptyString(request.getProjectId())) {
            throw new BizException("project id is empty");
        }
        if (Utils.isEmptyString(request.getStage())) {
            throw new BizException("stage is empty");
        }
        if (request.getDataCount() > Constant.MAX_WRITE_COUNT) {
            throw new BizException(ERR_MSG_TOO_MANY_WRITE_ITEMS);
        }
    }

    private WriteResponse doFinishData(FinishWriteDataRequest request,
                                       String path, Option... opts) throws NetException, BizException {
        if (Objects.nonNull(projectID) && request.getProjectId().length() == 0) {
            request = request.toBuilder().setProjectId(projectID).build();
        }
        checkFinishWriteRequest(request);
        WriteResponse response = httpClient.doPBRequest(
                path,
                request,
                WriteResponse.parser(),
                Option.conv2Options(opts)
        );
        log.debug("[ByteplusSDK][FinishWriteData] req:\n{} rsp:\n{}", request, response);
        return response;
    }

    private void checkFinishWriteRequest(FinishWriteDataRequest request) throws BizException {
        if (Utils.isEmptyString(request.getProjectId())) {
            throw new BizException("project id is empty");
        }
        if (Utils.isEmptyString(request.getStage())) {
            throw new BizException("stage is empty");
        }
        if (Utils.isEmptyString(request.getTopic())) {
            throw new BizException("topic is empty");
        }
        if (request.getDataDatesCount() > Constant.MAX_FINISH_DATE_COUNT) {
            throw new BizException(ERR_MSG_TOO_MANY_FINISH_DATES);
        }
    }

    @Override
    public PredictResponse predict(PredictRequest request,
                                   Option... opts) throws NetException, BizException {
        if (Objects.nonNull(projectID) && request.getProjectId().length() == 0) {
            request = request.toBuilder().setProjectId(projectID).build();
        }
        checkPredictRequest(request);
        PredictResponse response = httpClient.doPBRequest(
                Constant.PREDICT_URI,
                request,
                PredictResponse.parser(),
                Option.conv2Options(opts)
        );
        log.debug("[ByteplusSDK][Predict] req:\n{} rsp:\n{}", request, response);
        return response;
    }

    private void checkPredictRequest(PredictRequest request) throws BizException {
        if (Utils.isEmptyString(request.getProjectId())) {
            throw new BizException("project id is empty");
        }
        if (Utils.isEmptyString(request.getModelId())) {
            throw new BizException("model id is empty");
        }
    }

    @Override
    public AckServerImpressionsResponse ackServerImpressions(AckServerImpressionsRequest request,
                                                             Option... opts) throws NetException, BizException {
        if (Objects.nonNull(projectID) && request.getProjectId().length() == 0) {
            request = request.toBuilder().setProjectId(projectID).build();
        }
        checkAckRequest(request);
        AckServerImpressionsResponse response = httpClient.doPBRequest(
                Constant.ACK_SERVER_IMPRESSIONS_URI,
                request,
                AckServerImpressionsResponse.parser(),
                Option.conv2Options(opts)
        );
        log.debug("[ByteplusSDK][AckImpressions] req:\n{} rsp:\n{}", request, response);
        return response;
    }

    private void checkAckRequest(AckServerImpressionsRequest request) throws BizException {
        if (Utils.isEmptyString(request.getProjectId())) {
            throw new BizException("project id is empty");
        }
        if (Utils.isEmptyString(request.getModelId())) {
            throw new BizException("model id is empty");
        }
    }

    public final void release() {
        httpClient.shutdown();
    }
}
