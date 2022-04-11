package com.byteplus.rec.sdk.retail;

import com.byteplus.rec.core.BizException;
import com.byteplus.rec.core.HTTPClient;
import com.byteplus.rec.core.IRegion;
import com.byteplus.rec.core.Utils;
import lombok.Setter;
import lombok.experimental.Accessors;

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

    public RetailClientBuilder AccountID(String accountID) {
        this.tenantID = accountID;
        return this;
    }

    public RetailClientBuilder ProjectID(String projectID) {
        this.projectID = projectID;
        return this;
    }

    public RetailClientBuilder Region(IRegion region) {
        this.region = region;
        return this;
    }

    public RetailClientBuilder AuthAK(String authAK) {
        this.authAK = authAK;
        return this;
    }

    public RetailClientBuilder AuthSK(String authSK) {
        this.authSK = authSK;
        return this;
    }

    public RetailClientBuilder Schema(String schema) {
        this.schema = schema;
        return this;
    }

    public RetailClientBuilder Hosts(List<String> hosts) {
        this.hosts = hosts;
        return this;
    }

    public RetailClientImpl build() throws BizException {
        checkRequiredField();
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
                .build();
        return new RetailClientImpl(httpClient);
    }

    private boolean isUseAirAuth() {
        return Utils.isAllEmptyString(authAK, authSK) && Utils.noneEmptyString(airAuthToken);
    }

    private void checkRequiredField() throws BizException {
        if (Objects.isNull(projectID)) {
            throw new BizException("project id is empty");
        }
    }
}
