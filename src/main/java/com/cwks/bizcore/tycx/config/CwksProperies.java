package com.cwks.bizcore.tycx.config;

import com.cwks.common.domain.ValidateCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(
    prefix = "cwks"
)
public class CwksProperies {
    private ValidateCodeProperties validateCode = new ValidateCodeProperties();
    private String timeFormat = "yyyy-MM-dd HH:mm:ss";
    private boolean openAopLog = true;

    public CwksProperies() {
    }

    public ValidateCodeProperties getValidateCode() {
        return this.validateCode;
    }

    public void setValidateCode(ValidateCodeProperties validateCode) {
        this.validateCode = validateCode;
    }

    public String getTimeFormat() {
        return this.timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public boolean isOpenAopLog() {
        return this.openAopLog;
    }

    public void setOpenAopLog(boolean openAopLog) {
        this.openAopLog = openAopLog;
    }
}
