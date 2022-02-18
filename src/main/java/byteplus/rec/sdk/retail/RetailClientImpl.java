package byteplus.rec.sdk.retail;

import byteplus.rec.core.BizException;
import byteplus.rec.core.HTTPClient;
import byteplus.rec.core.NetException;
import byteplus.rec.core.Option;
import byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.WriteDataRequest;
import byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.WriteResponse;
import byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictRequest;
import byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.PredictResponse;
import byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.AckServerImpressionsRequest;
import byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail.AckServerImpressionsResponse;
import com.google.protobuf.Parser;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static byteplus.rec.sdk.retail.Constant.MAX_WRITE_COUNT;

@Slf4j
public class RetailClientImpl implements RetailClient {
    private final static String ERR_MSG_TOO_MANY_WRITE_ITEMS =
            String.format("Only can receive max to %d items in one write request", MAX_WRITE_COUNT);

    private HTTPClient httpClient;

    protected RetailClientImpl(HTTPClient httpClient) {
        this.httpClient = httpClient;
    }

    private boolean noneEmptySting(String str) {
        if (str != null && str.length() != 0) {
            return true;
        }
        return false;
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

    private WriteResponse doWriteData(WriteDataRequest request, String path, Option... opts) throws NetException, BizException {
        checkUploadDataRequest(request.getProjectId(), request.getStage());
        if (request.getDataCount() > MAX_WRITE_COUNT) {
            throw new BizException(ERR_MSG_TOO_MANY_WRITE_ITEMS);
        }
        Option[] newOpts = addSaasFlag(opts);
        Parser<WriteResponse> parser = WriteResponse.parser();
        WriteResponse response = httpClient.doPbRequest(path, request, parser, Option.conv2Options(newOpts));
        log.debug("[ByteplusSDK][WriteData] rsp:\n{}", response);
        return response;
    }

    private void checkUploadDataRequest(String projectId, String stage) throws BizException {
        final String ERR_MSG_FORMAT = "%s,field can not empty";
        final String ERR_FIELD_PROJECT_ID = "projectId";
        final String ERR_FIELD_STAGE = "stage";
        if (noneEmptySting(projectId) && noneEmptySting(stage)) {
            return;
        }
        List<String> emptyParams = new ArrayList<>();
        if (noneEmptySting(projectId)) {
            emptyParams.add(ERR_FIELD_PROJECT_ID);
        }
        if (noneEmptySting(stage)) {
            emptyParams.add(ERR_FIELD_STAGE);
        }
        throw new BizException(String.format(ERR_MSG_FORMAT, String.join(",", emptyParams)));
    }

    private Option[] addSaasFlag(Option[] opts) {
        final String HTTPHeaderServerFrom = "Server-From";
        final String SaasFlag = "saas";
        Option[] newOpts = new Option[opts.length + 1];
        System.arraycopy(opts, 0, newOpts, 0, opts.length);
        newOpts[newOpts.length - 1] = Option.withHeader(HTTPHeaderServerFrom, SaasFlag);
        return newOpts;
    }

    @Override
    public PredictResponse predict(PredictRequest request, Option... opts) throws NetException, BizException {
        checkPredictRequest(request.getProjectId(), request.getModelId());
        Option[] newOpts = addSaasFlag(opts);
        Parser<PredictResponse> parser = PredictResponse.parser();
        PredictResponse response = httpClient.doPbRequest("/RetailSaaS/Predict", request, parser,
                Option.conv2Options(newOpts));
        log.debug("[ByteplusSDK][Predict] rsp:\n{}", response);
        return response;
    }

    private void checkPredictRequest(String projectId, String modelId) throws BizException {
        final String ERR_MSG_FORMAT = "%s,field can not empty";
        final String ERR_FIELD_PROJECT_ID = "projectId";
        final String ERR_FIELD_MODEL_ID = "modelId";
        if (noneEmptySting(projectId) && noneEmptySting(modelId)) {
            return;
        }
        List<String> emptyParams = new ArrayList<>();
        if (noneEmptySting(projectId)) {
            emptyParams.add(ERR_FIELD_PROJECT_ID);
        }
        if (noneEmptySting(modelId)) {
            emptyParams.add(ERR_FIELD_MODEL_ID);
        }
        throw new BizException(String.format(ERR_MSG_FORMAT, String.join(",", emptyParams)));
    }

    @Override
    public AckServerImpressionsResponse ackServerImpressions(AckServerImpressionsRequest request, Option... opts) throws NetException, BizException {
        checkPredictRequest(request.getProjectId(), request.getModelId());
        Parser<AckServerImpressionsResponse> parser = AckServerImpressionsResponse.parser();
        Option[] newOpts = addSaasFlag(opts);
        AckServerImpressionsResponse response = httpClient.doPbRequest("/RetailSaaS/AckServerImpressions", request,
                parser, Option.conv2Options(newOpts));
        log.debug("[ByteplusSDK][AckImpressions] rsp:\n{}", response);
        return response;
    }

    public final void release() {
        httpClient.Shutdown();
    }
}
