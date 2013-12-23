package com.business.zcclddml;

public class ZcclddnrPK implements java.io.Serializable {

    // Fields

    private String zyh;
    private String clh;

    // Constructors

    /**
     * default constructor
     */
    public ZcclddnrPK() {
    }

    /**
     * full constructor
     */
    public ZcclddnrPK(String zyh, String clh) {
        this.zyh = zyh;
        this.clh = clh.trim();
    }

    // Property accessors

    public String getZyh() {
        return this.zyh;
    }

    public void setZyh(String zyh) {
        this.zyh = zyh;
    }

    public String getClh() {
        return this.clh;
    }

    public void setClh(String clh) {
        this.clh = clh.trim();
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof ZcclddnrPK))
            return false;
        ZcclddnrPK castOther = (ZcclddnrPK) other;

        return ((this.getZyh() == castOther.getZyh()) || (this.getZyh() != null
                && castOther.getZyh() != null && this.getZyh().equals(
                castOther.getZyh())))
                && ((this.getClh() == castOther.getClh()) || (this.getClh() != null
                && castOther.getClh() != null && this.getClh().equals(
                castOther.getClh())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result
                + (getZyh() == null ? 0 : this.getZyh().hashCode());
        result = 37 * result
                + (getClh() == null ? 0 : this.getClh().hashCode());
        return result;
    }

}