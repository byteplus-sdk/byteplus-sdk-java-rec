package com.byteplus.rec.sdk.content;

import com.byteplus.rec.core.*;
import com.byteplus.rec.core.metrics.MetricsCollector;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@Setter
@Accessors(chain = true, fluent = true)
public class ContentClientBuilder {
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

    private HTTPCaller.Config callerConfig;

    private HostAvailablerFactory hostAvailablerFactory;

    private MetricsCollector.MetricsCfg metricsConfig;

    public ContentClientBuilder accountID(String accountID) {
        this.tenantID = accountID;
        return this;
    }

    public ContentClientImpl build() throws BizException {
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
                .callerConfig(callerConfig)
                .hostAvailablerFactory(hostAvailablerFactory)
                .metricsCfg(metricsConfig)
                .keepAlive(keepAlive)
                .build();
        return new ContentClientImpl(httpClient, projectID);
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
