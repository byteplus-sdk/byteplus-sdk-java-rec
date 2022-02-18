package byteplus.rec.sdk.retail;

import byteplus.rec.core.HTTPClient;
import byteplus.rec.core.HTTPClientBuilder;
import byteplus.rec.core.IRegion;

import java.util.List;

public class RetailClientBuilder {
    private static final String BYTEPLUS_AUTH_SERVICE = "byteplus_recommend";

    private String tenantID;

    private String token;

    private String ak;

    private String sk;

    private String schema;

    private List<String> hosts;

    private String hostHeader;

    private IRegion region;

    public RetailClientBuilder tenantID(String tenantID) {
        this.tenantID = tenantID;
        return this;
    }

    public RetailClientBuilder token(String token) {
        this.token = token;
        return this;
    }

    public RetailClientBuilder ak(String ak) {
        this.ak = ak;
        return this;
    }

    public RetailClientBuilder sk(String sk) {
        this.sk = sk;
        return this;
    }

    public RetailClientBuilder schema(String schema) {
        this.schema = schema;
        return this;
    }

    public RetailClientBuilder hosts(List<String> hosts) {
        this.hosts = hosts;
        return this;
    }

    public RetailClientBuilder hostHeader(String hostHeader) {
        this.hostHeader = hostHeader;
        return this;
    }

    public RetailClientBuilder region(IRegion region) {
        this.region = region;
        return this;
    }

    public RetailClientImpl build() {
        HTTPClient httpClient = new HTTPClientBuilder()
                .tenantID(tenantID)
                .token(token)
                .ak(ak)
                .sk(sk)
                .schema(schema)
                .hosts(hosts)
                .hostHeader(hostHeader)
                .region(region)
                .useAirAuth(false)
                .authService(BYTEPLUS_AUTH_SERVICE)
                .build();
        return new RetailClientImpl(httpClient);
    }
}
