package cn.storm.bean;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class RubbishUsers implements Serializable {

    private Integer homeCity;
    private Integer userId;
    private Integer msisdn;
    String smsContent;

    public static final String HOMECITY_COLUMNNAME = "home_city";
    public static final String USERID_COLUMNNAME = "user_id";
    public static final String MSISDN_COLUMNNAME = "msisdn";
    public static final String SMSCONTENT_COLUMNNAME = "sms_content";

    public static final String SENSITIVE_KEYWORD1 = "bad";
    public static final String SENSITIVE_KEYWORD2 = "racketeer";
    public static final String[] SENSITIVE_KEYWORDS = new String[]{
      SENSITIVE_KEYWORD1,SENSITIVE_KEYWORD2
    };

    public Integer getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(Integer homeCity) {
        this.homeCity = homeCity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(Integer msisdn) {
        this.msisdn = msisdn;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("homeCity",homeCity)
                .append("userId",userId)
                .append("msisdn",msisdn)
                .append("smsContent",smsContent)
                .toString();
    }
}
