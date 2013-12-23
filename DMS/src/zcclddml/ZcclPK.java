package com.business.zcclddml;

public class ZcclPK implements java.io.Serializable {

    // Fields

    private String mclh;
    private String zclh;

    // Constructors

    /**
     * default constructor
     */
    public ZcclPK() {
    }

    /**
     * full constructor
     */
    public ZcclPK(String mclh, String zclh) {
        this.mclh = mclh.trim();
        this.zclh = zclh.trim();
    }

    // Property accessors

    public String getMclh() {
        return this.mclh;
    }

    public void setMclh(String mclh) {
        this.mclh = mclh.trim();
    }

    public String getZclh() {
        return this.zclh;
    }

    public void setZclh(String zclh) {
        this.zclh = zclh.trim();
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof ZcclPK))
            return false;
        ZcclPK castOther = (ZcclPK) other;

        return ((this.getMclh() == castOther.getMclh()) || (this.getMclh() != null
                && castOther.getMclh() != null && this.getMclh().equals(
                castOther.getMclh())))
                && ((this.getZclh() == castOther.getZclh()) || (this.getZclh() != null
                && castOther.getZclh() != null && this.getZclh()
                .equals(castOther.getZclh())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result
                + (getMclh() == null ? 0 : this.getMclh().hashCode());
        result = 37 * result
                + (getZclh() == null ? 0 : this.getZclh().hashCode());
        return result;
    }

}