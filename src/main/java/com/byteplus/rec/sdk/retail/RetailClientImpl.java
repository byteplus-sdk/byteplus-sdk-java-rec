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
import com.google.protobuf.Parser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetailClientImpl implements RetailClient {
    private final static String ERR_MSG_TOO_MANY_WRITE_ITEMS =
            String.format("Only can receive max to %d items in one write request", Constant.MAX_WRITE_COUNT);

    private final HTTPClient httpClient;

    protected RetailClientImpl(HTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public WriteResponse writeUsers(WriteDataRequest request, Option... opts) throws NetException, BizException {
        return doWriteData(request, "/RetailSaaS/WriteUsers", opts);
    }

    @Override
    public WriteResponse writeProducts(WriteDataRequest request, Option... opts) throws NetException, BizException {
        return doWriteData(request, "/RetailSaaS/WriteProducts", opts);
    }

    @Override
    public WriteResponse writeUserEvents(WriteDataRequest request, Option... opts) throws NetException, BizException {
        return doWriteData(request, "/RetailSaaS/WriteUserEvents", opts);
    }

    private WriteResponse doWriteData(WriteDataRequest request,
                                      String path, Option... opts) throws NetException, BizException {
        checkUploadDataRequest(request);
        if (request.getDataCount() > Constant.MAX_WRITE_COUNT) {
            throw new BizException(ERR_MSG_TOO_MANY_WRITE_ITEMS);
        }
        WriteResponse response = httpClient.doPBRequest(
                path,
                request,
                WriteResponse.parser(),
                Option.conv2Options(opts)
        );
        log.debug("[ByteplusSDK][WriteData] req:\n{} rsp:\n{}", request, response);
        return response;
    }

    private void checkUploadDataRequest(WriteDataRequest request) throws BizException {
        if (Utils.isEmptyString(request.getProjectId())) {
            throw new BizException("project id is empty");
        }
        if (Utils.isEmptyString(request.getStage())) {
            throw new BizException("stage is empty");
        }
    }

    @Override
    public PredictResponse predict(PredictRequest request,
                                   Option... opts) throws NetException, BizException {
        checkPredictRequest(request);
        PredictResponse response = httpClient.doPBRequest(
                "/RetailSaaS/Predict",
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
        checkAckRequest(request);
        AckServerImpressionsResponse response = httpClient.doPBRequest(
                "/RetailSaaS/AckServerImpressions",
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
