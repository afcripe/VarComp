package net.dahliasolutions.varcomp.models;

public class AppCompany {
    private Integer company_id;
    private String company_name;
    private String dir_name;

    public AppCompany() {
        setCompany_id(0);
        setCompany_name("New Company");
        setDir_Name("new_company");

    }

    public AppCompany(String companyName, String dirName) {
        setCompany_id(0);
        setCompany_name(companyName);
        setDir_Name(dirName);

    }

    public AppCompany(Integer companyID, String companyName, String dirName) {
        setCompany_id(companyID);
        setCompany_name(companyName);
        setDir_Name(dirName);
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

    public String getDir_Name() {
        return dir_name;
    }

    public void setDir_Name(String dir_name) {
        this.dir_name = dir_name;
    }

    public void setAppCompany(AppCompany appCompany) {
        this.company_id = appCompany.getCompany_id();
        this.company_name = appCompany.getCompany_name();
        this.dir_name = appCompany.getDir_Name();
    }

    public AppCompany getAppCompany() { return this; }
}
