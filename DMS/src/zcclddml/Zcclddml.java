package com.business.zcclddml;

public class Zcclddml implements java.io.Serializable {

    // Fields

    private String zyh;
    private String ckh;
    private String ckjc;
    private String ddh;
    private String czymc;
    private String czsj;
    private String clh;
    private String clmc;
    private Double cls;
    private String dw;
    private Double cldj;
    private String ywm;
    private String th;
    private Double clzjz;
    private Double cfhjz;
    private String zt;
    private String shr;
    private String shsj;
    private String memo;
    //类型：拆零、组装。与DB无关。
    private String lx;


    public Zcclddml() {
    }

    public Zcclddml(String zyh, String ckh, String clh) {
        this.zyh = zyh;
        this.ckh = ckh.trim();
        this.clh = clh.trim();
    }

    public Zcclddml(Double cfhjz, String ckh, String ckjc, Double cldj, String clh, String clmc, Double cls, Double clzjz, String czsj, String czymc, String ddh, String dw, String lx, String memo, String shr, String shsj, String th, String ywm, String zt, String zyh) {
        this.cfhjz = cfhjz;
        this.ckh = ckh.trim();
        this.ckjc = ckjc;
        this.cldj = cldj;
        this.clh = clh.trim();
        this.clmc = clmc;
        this.cls = cls;
        this.clzjz = clzjz;
        this.czsj = czsj;
        this.czymc = czymc;
        this.ddh = ddh;
        this.dw = dw;
        this.lx = lx;
        this.memo = memo;
        this.shr = shr;
        this.shsj = shsj;
        this.th = th;
        this.ywm = ywm;
        this.zt = zt;
        this.zyh = zyh;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    // Property accessors
    public Double getCfhjz() {
        return cfhjz;
    }

    public void setCfhjz(Double cfhjz) {
        this.cfhjz = cfhjz;
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh.trim();
    }

    public String getCkjc() {
        return ckjc;
    }

    public void setCkjc(String ckjc) {
        this.ckjc = ckjc;
    }

    public Double getCldj() {
        return cldj;
    }

    public void setCldj(Double cldj) {
        this.cldj = cldj;
    }

    public String getClh() {
        return clh;
    }

    public void setClh(String clh) {
        this.clh = clh.trim();
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public Double getCls() {
        return cls;
    }

    public void setCls(Double cls) {
        this.cls = cls;
    }

    public Double getClzjz() {
        return clzjz;
    }

    public void setClzjz(Double clzjz) {
        this.clzjz = clzjz;
    }

    public String getCzsj() {
        return czsj;
    }

    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    public String getCzymc() {
        return czymc;
    }

    public void setCzymc(String czymc) {
        this.czymc = czymc;
    }

    public String getDdh() {
        return ddh;
    }

    public void setDdh(String ddh) {
        this.ddh = ddh;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getShr() {
        return shr;
    }

    public void setShr(String shr) {
        this.shr = shr;
    }

    public String getShsj() {
        return shsj;
    }

    public void setShsj(String shsj) {
        this.shsj = shsj;
    }

    public String getTh() {
        return th;
    }

    public void setTh(String th) {
        this.th = th;
    }

    public String getYwm() {
        return ywm;
    }

    public void setYwm(String ywm) {
        this.ywm = ywm;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getZyh() {
        return zyh;
    }

    public void setZyh(String zyh) {
        this.zyh = zyh;
    }
}