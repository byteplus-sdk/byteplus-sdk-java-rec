package byteplus.rec.sdk.region;

import byteplus.rec.core.IRegion;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Region implements IRegion {
    SG(Collections.singletonList("rec-api-sg1.recplusapi.com"), "ap-singapore-1");

    private final List<String> hosts;

    private final String volcRegion;

    Region(List<String> hosts, String volcRegion) {
        this.hosts = hosts;
        this.volcRegion = volcRegion;
    }

    @Override
    public List<String> getHosts() {
        return hosts;
    }

    @Override
    public String getVolcCredentialRegion() {
        return volcRegion;
    }
}
