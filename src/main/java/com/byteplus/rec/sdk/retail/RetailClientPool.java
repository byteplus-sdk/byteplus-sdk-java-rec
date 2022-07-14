package com.byteplus.rec.sdk.retail;

import com.byteplus.rec.core.BizException;
import com.byteplus.rec.core.HTTPClient;
import com.byteplus.rec.core.NetException;
import com.byteplus.rec.core.Option;
import com.byteplus.rec.sdk.retail.protocol.ByteplusSaasRetail;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RetailClientPool implements RetailClient {
    private final List<RetailClient> retailClients;

    private static final AtomicInteger globalCallCount = new AtomicInteger(0);

    private static int clientCount;

    public RetailClientPool(List<RetailClient> retailClients) {
        clientCount = retailClients.size();
        this.retailClients = retailClients;
    }

    @Override
    public ByteplusSaasRetail.WriteResponse writeUsers(ByteplusSaasRetail.WriteDataRequest request, Option... opts) throws NetException, BizException {
        RetailClient retailClient = retailClients.get(globalCallCount.getAndIncrement() % clientCount);
        return retailClient.writeUsers(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse finishWriteUsers(ByteplusSaasRetail.FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        RetailClient retailClient = retailClients.get(globalCallCount.getAndIncrement() % clientCount);
        return retailClient.finishWriteUsers(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse writeProducts(ByteplusSaasRetail.WriteDataRequest request, Option... opts) throws NetException, BizException {
        RetailClient retailClient = retailClients.get(globalCallCount.getAndIncrement() % clientCount);
        return retailClient.writeProducts(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse finishWriteProducts(ByteplusSaasRetail.FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        RetailClient retailClient = retailClients.get(globalCallCount.getAndIncrement() % clientCount);
        return retailClient.finishWriteProducts(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse writeUserEvents(ByteplusSaasRetail.WriteDataRequest request, Option... opts) throws NetException, BizException {
        RetailClient retailClient = retailClients.get(globalCallCount.getAndIncrement() % clientCount);
        return retailClient.writeUserEvents(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse finishWriteUserEvents(ByteplusSaasRetail.FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        RetailClient retailClient = retailClients.get(globalCallCount.getAndIncrement() % clientCount);
        return retailClient.finishWriteUserEvents(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse writeOthers(ByteplusSaasRetail.WriteDataRequest request, Option... opts) throws NetException, BizException {
        RetailClient retailClient = retailClients.get(globalCallCount.getAndIncrement() % clientCount);
        return retailClient.writeOthers(request, opts);
    }

    @Override
    public ByteplusSaasRetail.WriteResponse finishWriteOthers(ByteplusSaasRetail.FinishWriteDataRequest request, Option... opts) throws NetException, BizException {
        RetailClient retailClient = retailClients.get(globalCallCount.getAndIncrement() % clientCount);
        return retailClient.finishWriteUserEvents(request, opts);
    }

    @Override
    public ByteplusSaasRetail.PredictResponse predict(ByteplusSaasRetail.PredictRequest request, Option... opts) throws NetException, BizException {
        int index = globalCallCount.getAndIncrement();
        System.out.println(index % clientCount);
        RetailClient retailClient = retailClients.get(index % clientCount);
        System.out.println(retailClient);
        return retailClient.predict(request, opts);
    }

    @Override
    public ByteplusSaasRetail.AckServerImpressionsResponse ackServerImpressions(ByteplusSaasRetail.AckServerImpressionsRequest request, Option... opts) throws NetException, BizException {
        int index = globalCallCount.getAndIncrement();
        System.out.println(index % clientCount);
        RetailClient retailClient = retailClients.get(index % clientCount);
        System.out.println(retailClient);
        return retailClient.ackServerImpressions(request, opts);
    }

    @Override
    public void release() {
        for (int i = 0; i < clientCount; i++) {
            RetailClient retailClient = retailClients.get(i);
            retailClient.release();
        }
    }
}
