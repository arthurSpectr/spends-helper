package io.spehel.bank.domain.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RangeModel {

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dateFrom;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dateTo;

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
