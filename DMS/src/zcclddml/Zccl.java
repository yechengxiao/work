package com.business.zcclddml;

public class Zccl implements java.io.Serializable {

    // Fields

    private ZcclPK zcclPK;
    private int cls;
    private String mclmc;
    private String zclmc;
    private String dw;
    private Double cldj;

    // Constructors

    /**
     * default constructor
     */
    public Zccl() {
    }

    /**
     * minimal constructor
     */
    public Zccl(ZcclPK zcclPK) {
        this.zcclPK = zcclPK;
    }


    // Property accessors

    public ZcclPK getZcclPK() {
        return zcclPK;
    }

    public void setZcclPK(ZcclPK zcclPK) {
        this.zcclPK = zcclPK;
    }

    public int getCls() {
        return this.cls;
    }

    public void setCls(int cls) {
        this.cls = cls;
    }

    public Double getCldj() {
        return cldj;
    }

    public void setCldj(Double cldj) {
        this.cldj = cldj;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw.trim();
    }

    public String getMclmc() {
        return mclmc;
    }

    public void setMclmc(String mclmc) {
        this.mclmc = mclmc.trim();
    }

    public String getZclmc() {
        return zclmc;
    }

    public void setZclmc(String zclmc) {
        this.zclmc = zclmc.trim();
    }
}