package com.byteplus.rec.sdk.retail;

import com.byteplus.rec.core.BizException;
import com.byteplus.rec.core.NetException;
import com.byteplus.rec.core.Option;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RetailClientPool implements RetailClient {
    private final List<RetailClient> retailClients;

    private static final AtomicInteger globalCallCount = new AtomicInteger(0);

    private static int clientCount;

    public RetailClientPool(List<RetailClient> retailClients) {
        clientCount = retailClients.size();
        this.retailClients = retailClients;
    }

    public RetailClient getClient() {
        int index = globalCallCount.getAndIncrement() % clientCount;
        log.debug("[ByteplusSDK] use retailClient {}", index);
        return retailClients.get(index);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse writeUsers(ByteplusSaasRetail.WriteDataRequest request, Option... opts) throws NetException, BizException {
        return getClient().writeUsers(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse finishWriteUsers(ByteplusSaasRetail.FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        return getClient().finishWriteUsers(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse writeProducts(ByteplusSaasRetail.WriteDataRequest request, Option... opts) throws NetException, BizException {
        return getClient().writeProducts(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse finishWriteProducts(ByteplusSaasRetail.FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        return getClient().finishWriteProducts(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse writeUserEvents(ByteplusSaasRetail.WriteDataRequest request, Option... opts) throws NetException, BizException {
        return getClient().writeUserEvents(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse finishWriteUserEvents(ByteplusSaasRetail.FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        return getClient().finishWriteUserEvents(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse writeOthers(ByteplusSaasRetail.WriteDataRequest request, Option... opts) throws NetException, BizException {
        return getClient().writeOthers(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse finishWriteOthers(ByteplusSaasRetail.FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        return getClient().finishWriteUserEvents(request, opts);
    }

    @Override
    public ByteplusSaasRetail.PredictResponse predict(ByteplusSaasRetail.PredictRequest request, Option... opts) throws NetException, BizException {
        return getClient().predict(request, opts);
    }

    @Override
    public ByteplusSaasRetail.AckServerImpressionsResponse ackServerImpressions(ByteplusSaasRetail.AckServerImpressionsRequest request, Option... opts) throws NetException, BizException {
        return getClient().ackServerImpressions(request, opts);
    }

    @Override
    public void release() {
        for (int i = 0; i < clientCount; i++) {
            RetailClient retailClient = retailClients.get(i);
            retailClient.release();
        }
    }
}