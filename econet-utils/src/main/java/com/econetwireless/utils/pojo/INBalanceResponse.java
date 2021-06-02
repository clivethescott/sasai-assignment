package com.econetwireless.utils.pojo;

import java.util.Objects;

/**
 * Created by tnyamakura on 17/3/2017.
 */
public class INBalanceResponse {
    private String responseCode;
    private String narrative;
    private String msisdn;
    private double amount;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("INBalanceResponse{");
        sb.append("responseCode='").append(responseCode).append('\'');
        sb.append(", narrative='").append(narrative).append('\'');
        sb.append(", msisdn='").append(msisdn).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final INBalanceResponse that = (INBalanceResponse) o;
        return Double.compare(that.amount, amount) == 0 && Objects.equals(responseCode, that.responseCode) && Objects.equals(narrative, that.narrative) && Objects.equals(msisdn, that.msisdn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, narrative, msisdn, amount);
    }
}
