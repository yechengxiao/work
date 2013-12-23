package com.business.zcclddml;

import java.io.Serializable;

/**
 * User: ycx
 * Date: 13-8-29
 * Time: 下午12:03
 */
public class ZcclddmlParam implements Serializable {
    private String zyh;
    private String zt;//状态 0已确认 1未确认
    private String rqStart;
    private String rqEnd;
    private String ckh;

    public ZcclddmlParam() {
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh;
    }

    public String getRqEnd() {
        return rqEnd;
    }

    public void setRqEnd(String rqEnd) {
        this.rqEnd = rqEnd;
    }

    public String getRqStart() {
        return rqStart;
    }

    public void setRqStart(String rqStart) {
        this.rqStart = rqStart;
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
