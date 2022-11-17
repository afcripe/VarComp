package net.dahliasolutions.varcomp.models;

import net.dahliasolutions.varcomp.connectors.MetricConnector;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Metric {

    private Integer metric_id;
    private Integer company_id;
    private String metric_label;
    private BigDecimal metric_earnings;
    private BigDecimal metric_funding;
    private BigDecimal metric_eps;
    private Integer metric_shares;
    private BigDecimal metric_payout;
    private Integer metric_year;
    private Integer metric_period;
    private Boolean locked;
    private LocalDate lock_date;

    public Metric() {
        setMetric_id(0);
        setCompany_id(0);
        setMetric_label("");
        setMetric_earnings(new BigDecimal("0.00"));
        setMetric_funding(new BigDecimal("0.00"));
        setMetric_eps(new BigDecimal("0.00"));
        setMetric_shares(0);
        setMetric_payout(new BigDecimal("0.00"));
        setMetric_year(0);
        setMetric_period(0);
        setLocked(false);
        setLock_date(LocalDate.now());

    }

    public Metric(Integer metricID, Integer companyID, String metricLabel, BigDecimal earnings,
                  BigDecimal funding, BigDecimal eps, Integer shares, BigDecimal payout,
                  Integer year, Integer period, Boolean locked, LocalDate lockDate) {
        setMetric_id(metricID);
        setCompany_id(companyID);
        setMetric_label(metricLabel);
        setMetric_earnings(earnings);
        setMetric_funding(funding);
        setMetric_eps(eps);
        setMetric_shares(shares);
        setMetric_payout(payout);
        setMetric_year(year);
        setMetric_period(period);
        setLocked(locked);
        setLock_date(lockDate);
    }

    public void setMetric(Metric metric) {
        this.metric_id = metric.getMetric_id();
        this.company_id = metric.getCompany_id();
        this.metric_label = metric.getMetric_label();
        this.metric_earnings = metric.getMetric_earnings();
        this.metric_funding = metric.getMetric_funding();
        this.metric_eps = metric.getMetric_eps();
        this.metric_shares = metric.getMetric_shares();
        this.metric_payout = metric.getMetric_payout();
        this.metric_year = metric.getMetric_year();
        this.metric_period = metric.getMetric_period();
        this.locked = metric.getLocked();
        this.lock_date = metric.getLock_date();
    }

    public Metric getMetric() { return this; }

    public Integer insertMetric () { return MetricConnector.insertMetric(this).getMetric_id(); }

    public void updateMetric() { MetricConnector.updateMetric(this); }

    public Integer getMetric_id() { return metric_id; }

    public void setMetric_id(Integer metric_id) { this.metric_id = metric_id; }

    public Integer getCompany_id() { return company_id; }

    public void setCompany_id(Integer company_id) { this.company_id = company_id; }

    public String getMetric_label() { return metric_label; }

    public void setMetric_label( String metric_label) { this.metric_label = metric_label; }

    public void updateMetric_label() { this.metric_label = this.getMetric_year().toString()+" - "+this.getMetric_period().toString(); }

    public BigDecimal getMetric_earnings() { return metric_earnings; }

    public void setMetric_earnings(BigDecimal metric_earnings) {
        this.metric_earnings = metric_earnings;
        this.metric_earnings = this. metric_earnings.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getMetric_funding() { return metric_funding; }

    public void setMetric_funding(BigDecimal metric_funding) {
        this.metric_funding = metric_funding;
        this.metric_funding = this. metric_funding.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getMetric_eps() { return metric_eps; }

    public void setMetric_eps(BigDecimal metric_eps) {
        this.metric_eps = metric_eps;
        this.metric_eps = this. metric_eps.setScale(2, RoundingMode.HALF_UP);
    }

    public Integer getMetric_shares() { return metric_shares; }

    public void setMetric_shares(Integer metric_shares) { this.metric_shares = metric_shares; }

    public BigDecimal getMetric_payout() { return metric_payout; }

    public void setMetric_payout(BigDecimal metric_payout) {
        this.metric_payout = metric_payout;
        this.metric_payout = this. metric_payout.setScale(2, RoundingMode.HALF_UP);
    }

    public Integer getMetric_year() { return metric_year; }

    public void setMetric_year(Integer metric_year) { this.metric_year = metric_year; }

    public Integer getMetric_period() { return metric_period; }

    public void setMetric_period(Integer metric_period) { this.metric_period = metric_period; }

    public Boolean getLocked() { return locked; }

    public void setLocked(Boolean locked) { this.locked = locked; }

    public LocalDate getLock_date() { return lock_date; }

    public void setLock_date(LocalDate lock_date) { this.lock_date = lock_date; }
}
