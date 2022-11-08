package net.dahliasolutions.varcomp.models;

import net.dahliasolutions.varcomp.connectors.MetricDetailConnector;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MetricDetail {

    private Integer metric_detail_id;
    private Integer metric_id;
    private Integer detail_period;
    private BigDecimal detail_budget;
    private BigDecimal detail_actual;
    private BigDecimal detail_earnings;

    public MetricDetail() {
        setMetric_detail_id(0);
        setMetric_id(0);
        setDetail_period(0);
        setDetail_budget(new BigDecimal(0.00));
        setDetail_actual(new BigDecimal(0.00));
        setDetail_earnings(new BigDecimal(0.00));
    }

    public MetricDetail(Integer detailID, Integer metricID, Integer detailPeriod,
                        BigDecimal budget, BigDecimal actual, BigDecimal earnings) {
        setMetric_detail_id(detailID);
        setMetric_id(metricID);
        setDetail_period(detailPeriod);
        setDetail_budget(budget);
        setDetail_actual(actual);
        setDetail_earnings(earnings);
    }

    public void setMetricDetail(MetricDetail metricDetail) {
        this.metric_detail_id = metricDetail.getMetric_detail_id();
        this.metric_id = metricDetail.getMetric_id();
        this.detail_period = metricDetail.getDetail_period();
        this.detail_budget = metricDetail.getDetail_budget();
        this.detail_actual = metricDetail.getDetail_actual();
        this.detail_earnings = metricDetail.getDetail_earnings();
    }

    public MetricDetail getMetricDetail() { return this; }

    public Integer insertMetricDetail() { return MetricDetailConnector.insertMetric(this).getMetric_detail_id();}

    public void updateMetricDetail() { MetricDetailConnector.updateMetricDetail(this); }

    public void deleteMetricDetail() { MetricDetailConnector.deleteMetricDetail(this.metric_detail_id); }

    public Integer getMetric_detail_id() { return metric_detail_id; }

    public void setMetric_detail_id(Integer metric_detail_id) { this.metric_detail_id = metric_detail_id; }

    public Integer getMetric_id() { return metric_id; }

    public void setMetric_id(Integer metric_id) { this.metric_id = metric_id; }

    public Integer getDetail_period() { return detail_period; }

    public void setDetail_period(Integer detail_period) { this.detail_period = detail_period; }

    public BigDecimal getDetail_budget() { return detail_budget; }

    public void setDetail_budget(BigDecimal detail_budget) {
        this.detail_budget = detail_budget;
        this.detail_budget = this.detail_budget.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getDetail_actual() { return detail_actual; }

    public void setDetail_actual(BigDecimal detail_actual) {
        this.detail_actual = detail_actual;
        this.detail_actual = this.detail_actual.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getDetail_earnings() { return detail_earnings; }

    public void setDetail_earnings(BigDecimal detail_earnings) {
        this.detail_earnings = detail_earnings;
        this.detail_earnings = this.detail_earnings.setScale(2, RoundingMode.HALF_UP);
    }
}
