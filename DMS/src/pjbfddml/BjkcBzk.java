package com.business.pjbfddml;

import java.io.Serializable;

// 新增功能中，查询后列表显示的数据
public class BjkcBzk implements Serializable {
    private String clh;
    private String clmc;
    private String scwlh;
    private String dw;
    private Integer kcl;
    private Integer ydl;
    private Integer yll;
    private Integer cgs;
    private String sycx;
    private String zt;
    private Integer mcyl;
    private String memo;
    private Integer cls;
    private Integer cldj;

    public BjkcBzk() {

    }

    public BjkcBzk(String clh, String clmc, String scwlh, String dw, Integer kcl, Integer ydl, Integer yll, Integer cgs, String sycx, String zt, Integer mcyl, String memo, Integer cldj) {
        this.cgs = cgs;
        this.clh = clh;
        this.clmc = clmc;
        this.dw = dw;
        this.kcl = kcl;
        this.mcyl = mcyl;
        this.memo = memo;
        this.scwlh = scwlh;
        this.sycx = sycx;
        this.ydl = ydl;
        this.yll = yll;
        this.zt = zt;
        this.cldj = cldj;
    }

    public Integer getCgs() {
        return cgs;
    }

    public void setCgs(Integer cgs) {
        this.cgs = cgs;
    }

    public Integer getCldj() {
        return cldj;
    }

    public void setCldj(Integer cldj) {
        this.cldj = cldj;
    }

    public String getClh() {
        return clh;
    }

    public void setClh(String clh) {
        this.clh = clh;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public Integer getCls() {
        return cls;
    }

    public void setCls(Integer cls) {
        this.cls = cls;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public Integer getKcl() {
        return kcl;
    }

    public void setKcl(Integer kcl) {
        this.kcl = kcl;
    }

    public Integer getMcyl() {
        return mcyl;
    }

    public void setMcyl(Integer mcyl) {
        this.mcyl = mcyl;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getScwlh() {
        return scwlh;
    }

    public void setScwlh(String scwlh) {
        this.scwlh = scwlh;
    }

    public String getSycx() {
        return sycx;
    }

    public void setSycx(String sycx) {
        this.sycx = sycx;
    }

    public Integer getYdl() {
        return ydl;
    }

    public void setYdl(Integer ydl) {
        this.ydl = ydl;
    }

    public Integer getYll() {
        return yll;
    }

    public void setYll(Integer yll) {
        this.yll = yll;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }
}
