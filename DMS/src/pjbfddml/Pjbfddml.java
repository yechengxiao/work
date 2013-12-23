package com.business.pjbfddml;

import java.util.HashSet;
import java.util.Set;

/**
 * Pjbfddml entity. @author MyEclipse Persistence Tools
 */

public class Pjbfddml implements java.io.Serializable {

    // Fields

    private String zyh;
    private String rq;
    private String sj;
    private String czr;
    private Integer bfpz;
    private Double bfje;
    private String czlx;
    private String czbz;
    private String ckh;
    private String ckjc;
    private String memo;
    private String zt;
    private Set pjbfddnrs = new HashSet(0);

    // Constructors

    /**
     * default constructor
     */
    public Pjbfddml() {
    }

    /**
     * minimal constructor
     */
    public Pjbfddml(String zyh) {
        this.zyh = zyh;
    }

    // BjkcDao中调用
    public Pjbfddml(String ckh, String ckjc) {
        this.ckh = ckh;
        this.ckjc = ckjc;
    }

    /**
     * full constructor
     */
    public Pjbfddml(String zyh, String rq, String sj, String czr, Integer bfpz,
                    Double bfje, String czlx, String czbz, String ckh, String memo,
                    String ckjc, String zt, Set pjbfddnrs) {
        this.zyh = zyh;
        this.rq = rq;
        this.sj = sj;
        this.czr = czr;
        this.bfpz = bfpz;
        this.bfje = bfje;
        this.czlx = czlx;
        this.czbz = czbz;
        this.ckh = ckh;
        this.memo = memo;
        this.ckjc = ckjc;
        this.zt = zt;
        this.pjbfddnrs = pjbfddnrs;
    }

    // Property accessors

    public String getZyh() {
        return this.zyh;
    }

    public void setZyh(String zyh) {
        this.zyh = zyh;
    }

    public String getRq() {
        return this.rq;
    }

    public void setRq(String rq) {
        this.rq = rq;
    }

    public String getSj() {
        return this.sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    public String getCzr() {
        return this.czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public Integer getBfpz() {
        return this.bfpz;
    }

    public void setBfpz(Integer bfpz) {
        this.bfpz = bfpz;
    }

    public Double getBfje() {
        return this.bfje;
    }

    public void setBfje(Double bfje) {
        this.bfje = bfje;
    }

    public String getCzlx() {
        return this.czlx;
    }

    public void setCzlx(String czlx) {
        this.czlx = czlx;
    }

    public String getCzbz() {
        return this.czbz;
    }

    public void setCzbz(String czbz) {
        this.czbz = czbz;
    }

    public String getCkh() {
        return this.ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh.trim();
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCkjc() {
        return this.ckjc;
    }

    public void setCkjc(String ckjc) {
        this.ckjc = ckjc;
    }

    public String getZt() {
        return this.zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public Set getPjbfddnrs() {
        return this.pjbfddnrs;
    }

    public void setPjbfddnrs(Set pjbfddnrs) {
        this.pjbfddnrs = pjbfddnrs;
    }

}