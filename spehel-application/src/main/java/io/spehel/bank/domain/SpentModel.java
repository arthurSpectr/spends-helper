
package io.spehel.bank.domain;


import io.blend.api.model.Spend;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class SpentModel {

    private Long amount;
    private Long balance;
    private Long cashbackAmount;
    private String comment;
    private Long commissionRate;
    private String counterEdrpou;
    private String counterIban;
    private Long currencyCode;
    private String description;
    private Boolean hold;
    private String id;
    private Long mcc;
    private Long operationAmount;
    private String receiptId;
    private Long time;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getCashbackAmount() {
        return cashbackAmount;
    }

    public void setCashbackAmount(Long cashbackAmount) {
        this.cashbackAmount = cashbackAmount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Long commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getCounterEdrpou() {
        return counterEdrpou;
    }

    public void setCounterEdrpou(String counterEdrpou) {
        this.counterEdrpou = counterEdrpou;
    }

    public String getCounterIban() {
        return counterIban;
    }

    public void setCounterIban(String counterIban) {
        this.counterIban = counterIban;
    }

    public Long getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Long currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHold() {
        return hold;
    }

    public void setHold(Boolean hold) {
        this.hold = hold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMcc() {
        return mcc;
    }

    public void setMcc(Long mcc) {
        this.mcc = mcc;
    }

    public Long getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(Long operationAmount) {
        this.operationAmount = operationAmount;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public Date getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time * 1000L);
        return calendar.getTime();
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Spend toDTO() {
        return new Spend(amount.intValue(), Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate(), description);
    }

    @Override
    public String toString() {
        return "\n{ " +
                "amount=" + amount +
                ", balance=" + balance +
//                ", cashbackAmount=" + cashbackAmount +
//                ", comment='" + comment + '\'' +
//                ", commissionRate=" + commissionRate +
//                ", counterEdrpou='" + counterEdrpou + '\'' +
//                ", counterIban='" + counterIban + '\'' +
                ", currencyCode=" + currencyCode +
                ", description='" + description + '\'' +
//                ", hold=" + hold +
//                ", id='" + id + '\'' +
//                ", mcc=" + mcc +
                ", operationAmount=" + operationAmount +
//                ", receiptId='" + receiptId + '\'' +
                ", time=" + getTime() +
                " }\n";
    }
}
