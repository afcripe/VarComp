package net.dahliasolutions.varcomp.models;

import net.dahliasolutions.varcomp.connectors.AppCompanyConnector;
import net.dahliasolutions.varcomp.connectors.CompanyConnector;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Company {
    private Integer company_id;
    private String company_name;
    private Integer shares_total;
    private Integer shares_outstanding;
    private BigDecimal funding_percentage;
    private Integer shares_issued_years;
    private Integer shares_issued_amount;
    private Boolean company_logo_show;

    public Company() {
        setCompany_id(0);
        setCompany_name("NA");
        setShares_total(0);
        setShares_outstanding(0);
        setFunding_percentage(new BigDecimal("0.0000"));
        setShares_issued_years(0);
        setShares_issued_amount(0);
        setCompany_logo_show(false);

    }
    public Company(Integer companyID, String companyName,
                   Integer sharesTotal, Integer sharesOutstanding, BigDecimal fundingPercentage,
                   Integer sharesIssuedYears, Integer sharesIssuedAmount, Boolean companyLogoShow) {
        setCompany_id(companyID);
        setCompany_name(companyName);
        setShares_total(sharesTotal);
        setShares_outstanding(sharesOutstanding);
        setFunding_percentage(fundingPercentage);
        setShares_issued_years(sharesIssuedYears);
        setShares_issued_amount(sharesIssuedAmount);
        setCompany_logo_show(companyLogoShow);

    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Integer getShares_total() {
        return shares_total;
    }

    public void setShares_total(Integer shares_total) {
        this.shares_total = shares_total;
    }

    public Integer getShares_outstanding() {
        return shares_outstanding;
    }

    public void setShares_outstanding(Integer shares_outstanding) {
        this.shares_outstanding = shares_outstanding;
    }

    public BigDecimal getFunding_percentage() {
        return funding_percentage;
    }

    public void setFunding_percentage(BigDecimal funding_percentage) {
        this.funding_percentage = funding_percentage;
        this.funding_percentage = this.funding_percentage.setScale(4, RoundingMode.HALF_UP);
    }

    public Integer getShares_issued_years() {
        return shares_issued_years;
    }

    public void setShares_issued_years(Integer shares_issued_years) {
        this.shares_issued_years = shares_issued_years;
    }

    public Integer getShares_issued_amount() {
        return shares_issued_amount;
    }

    public void setShares_issued_amount(Integer shares_issued_amount) {
        this.shares_issued_amount = shares_issued_amount;
    }

    public Boolean getCompany_logo_show() {
        return company_logo_show;
    }

    public void setCompany_logo_show(Boolean company_logo_show) {
        this.company_logo_show = company_logo_show;
    }

    public void setCompany(Company company) {
        this.company_id = company.getCompany_id();
        this.company_name = company.getCompany_name();
        this.shares_total = company.getShares_total();
        this.shares_outstanding = company.getShares_outstanding();
        this.funding_percentage = company.getFunding_percentage();
        this.shares_issued_years = company.getShares_issued_years();
        this.shares_issued_amount = company.getShares_issued_amount();
        this.company_logo_show = company.getCompany_logo_show();
    }

    public Company getCompany() { return this; }

    public void updateCompany() {
        CompanyConnector.updateCompany(this);
        AppCompanyConnector.updateCompany(getCompany_id(), getCompany_name());
    }
    public void updateNewCompany() { CompanyConnector.updateNewCompany(this); }
}
