package com.cwks.bizcore.comm.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.properties")
public class MyProperties {

    @Value("${biz.cssnjworks.core.tykf.login.model.dev.open}")
    private boolean dev;
    @Value("${biz.cssnjworks.core.tykf.login.model.dev.tax.swry_dm}")
    private String swry_dm;
    @Value("${biz.cssnjworks.core.tykf.login.model.dev.tax.swrysf_dm}")
    private String swrysf_dm;

    public boolean isDev() {
        return dev;
    }

    public void setDev(boolean dev) {
        this.dev = dev;
    }

    public String getSwry_dm() {
        return swry_dm;
    }

    public void setSwry_dm(String swry_dm) {
        this.swry_dm = swry_dm;
    }

    public String getSwrysf_dm() {
        return swrysf_dm;
    }

    public void setSwrysf_dm(String swrysf_dm) {
        this.swrysf_dm = swrysf_dm;
    }
}

