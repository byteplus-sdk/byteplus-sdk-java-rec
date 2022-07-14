package com.byteplus.rec.sdk.retail;

import com.byteplus.rec.core.BizException;
import com.byteplus.rec.core.HTTPClient;
import com.byteplus.rec.core.IRegion;
import com.byteplus.rec.core.Utils;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Accessors(chain = true, fluent = true)
public class RetailClientBuilder {
    private static final String BYTEPLUS_AUTH_SERVICE = "byteplus_recommend";

    private String tenantID;

    private String projectID;

    private String airAuthToken;

    private String authAK;

    private String authSK;

    private String schema;

    private List<String> hosts;

    private IRegion region;

    private boolean keepAlive;

    private OkHttpClient callerClient;

    private int clientCount;

    public RetailClientBuilder accountID(String accountID) {
        this.tenantID = accountID;
        return this;
    }

    public RetailClient build() throws BizException {
        checkRequiredField();
        if (clientCount > 1) {
            return buildRetailClientPool();
        }
        HTTPClient httpClient = HTTPClient.builder()
                .tenantID(tenantID)
                .projectID(projectID)
                .airAuthToken(airAuthToken)
                .authAK(authAK)
                .authSK(authSK)
                .schema(schema)
                .hosts(hosts)
                .region(region)
                .useAirAuth(isUseAirAuth())
                .authService(BYTEPLUS_AUTH_SERVICE)
                .keepAlive(keepAlive)
                .callerClient(callerClient)
                .build();
        return new RetailClientImpl(httpClient, projectID);
    }

    private boolean isUseAirAuth() {
        return Utils.isAllEmptyString(authAK, authSK) && Utils.noneEmptyString(airAuthToken);
    }

    private void checkRequiredField() throws BizException {
        if (Objects.isNull(projectID)) {
            throw new BizException("project id is empty");
        }
    }

    private RetailClient buildRetailClientPool() throws BizException {
        List<RetailClient> retailClients = new ArrayList<>();
        for (int i = 0; i < clientCount; i++) {
            HTTPClient httpClient = HTTPClient.builder()
                    .tenantID(tenantID)
                    .projectID(projectID)
                    .airAuthToken(airAuthToken)
                    .authAK(authAK)
                    .authSK(authSK)
                    .schema(schema)
                    .hosts(hosts)
                    .region(region)
                    .useAirAuth(isUseAirAuth())
                    .authService(BYTEPLUS_AUTH_SERVICE)
                    .keepAlive(keepAlive)
                    .callerClient(callerClient)
                    .build();
            retailClients.add(new RetailClientImpl(httpClient, projectID));
        }
        return new RetailClientPool(retailClients);
    }
}
