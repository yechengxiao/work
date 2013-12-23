package com.business.zcclddml;

public class Zcclddnr implements java.io.Serializable {

    // Fields

    private ZcclddnrPK zcclddnrPK;
    private String clmc;
    private Double cls;
    private String dw;
    private Double cldj;
    private String ywm;
    private String memo;
    private String th;

    // Constructors

    /**
     * default constructor
     */
    public Zcclddnr() {
    }

    /**
     * minimal constructor
     */
    public Zcclddnr(ZcclddnrPK zcclddnrPK) {
        this.zcclddnrPK = zcclddnrPK;
    }

    /**
     * full constructor
     */
    public Zcclddnr(ZcclddnrPK zcclddnrPK, String clmc, Double cls, String dw,
                    Double cldj, String ywm, String memo, String th) {
        this.zcclddnrPK = zcclddnrPK;
        this.clmc = clmc;
        this.cls = cls;
        this.dw = dw;
        this.cldj = cldj;
        this.ywm = ywm;
        this.memo = memo;
        this.th = th;
    }

    // Property accessors

    public String getClmc() {
        return this.clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public ZcclddnrPK getZcclddnrPK() {
        return zcclddnrPK;
    }

    public void setZcclddnrPK(ZcclddnrPK zcclddnrPK) {
        this.zcclddnrPK = zcclddnrPK;
    }

    public Double getCls() {
        return this.cls;
    }

    public void setCls(Double cls) {
        this.cls = cls;
    }

    public String getDw() {
        return this.dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public Double getCldj() {
        return this.cldj;
    }

    public void setCldj(Double cldj) {
        this.cldj = cldj;
    }

    public String getYwm() {
        return this.ywm;
    }

    public void setYwm(String ywm) {
        this.ywm = ywm;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTh() {
        return this.th;
    }

    public void setTh(String th) {
        this.th = th;
    }

}