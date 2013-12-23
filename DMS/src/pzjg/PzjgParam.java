package com.pub.pzjg;

import java.io.Serializable;

//查询条件
public class PzjgParam implements Serializable {

    private String vsn;
    private String cx;
    private String cxfl;
    private String xhms;
    private String clys;
    private String pzlx;
    private String zt;

    public PzjgParam() {

    }

    public PzjgParam(String vsn, String cx, String cxfl, String xhms,
                     String clys, String pzlx, String zt) {
        this.vsn = vsn;
        this.cx = cx;
        this.cxfl = cxfl;
        this.xhms = xhms;
        this.clys = clys;
        this.pzlx = pzlx;
        this.zt = zt;
    }

    public String getVsn() {
        return vsn;
    }

    public void setVsn(String vsn) {
        this.vsn = (vsn != null ? vsn.toUpperCase() : "");
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = (cx != null ? cx.toUpperCase() : "");
    }

    public String getCxfl() {
        return cxfl;
    }

    public void setCxfl(String cxfl) {
        this.cxfl = (cxfl != null ? cxfl.toUpperCase() : "");
    }

    public String getXhms() {
        return xhms;
    }

    public void setXhms(String xhms) {
        this.xhms = xhms;
    }

    public String getClys() {
        return clys;
    }

    public void setClys(String clys) {
        this.clys = clys;
    }

    public String getPzlx() {
        return pzlx;
    }

    public void setPzlx(String pzlx) {
        this.pzlx = (pzlx != null ? pzlx.toUpperCase() : "");
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

}
