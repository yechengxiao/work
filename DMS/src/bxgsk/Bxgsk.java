package com.pub.bxgsk;

public class Bxgsk implements java.io.Serializable {

    // Fields

    private String bxgsdm;
    private String bxgsmc;
    private String memo;
    private String czymc;
    private String czrq;

    // Constructors

    /**
     * default constructor
     */
    public Bxgsk() {
    }

    /**
     * minimal constructor
     */
    public Bxgsk(String bxgsdm) {
        this.bxgsdm = bxgsdm;
    }

    /**
     * full constructor
     */
    public Bxgsk(String bxgsdm, String bxgsmc, String memo, String czymc,
                 String czrq) {
        this.bxgsdm = bxgsdm;
        this.bxgsmc = bxgsmc;
        this.memo = memo;
        this.czymc = czymc;
        this.czrq = czrq;
    }

    // Property accessors

    public String getBxgsdm() {
        return this.bxgsdm;
    }

    public void setBxgsdm(String bxgsdm) {
        this.bxgsdm = bxgsdm.toUpperCase();
    }

    public String getBxgsmc() {
        return this.bxgsmc;
    }

    public void setBxgsmc(String bxgsmc) {
        this.bxgsmc = bxgsmc;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCzymc() {
        return this.czymc;
    }

    public void setCzymc(String czymc) {
        this.czymc = czymc;
    }

    public String getCzrq() {
        return this.czrq;
    }

    public void setCzrq(String czrq) {
        this.czrq = czrq;
    }

}