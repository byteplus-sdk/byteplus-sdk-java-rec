package com.byteplus.rec.sdk.region;

import com.byteplus.rec.core.IRegion;

import java.util.Collections;
import java.util.List;

public enum Region implements IRegion {
    SG(Collections.singletonList("rec-api-sg1.recplusapi.com"), "ap-singapore-1"),
    ;

    private final List<String> hosts;

    private final String authRegion;

    Region(List<String> hosts, String authRegion) {
        this.hosts = hosts;
        this.authRegion = authRegion;
    }

    @Override
    public List<String> getHosts() {
        return hosts;
    }

    @Override
    public String getAuthRegion() {
        return authRegion;
    }
}
