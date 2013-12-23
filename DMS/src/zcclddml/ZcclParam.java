package com.business.zcclddml;

import java.io.Serializable;

/**
 * User: ycx
 * Date: 13-8-29
 * Time: 下午2:56
 */
public class ZcclParam implements Serializable {
    private String mclh;

    public ZcclParam() {
    }

    public ZcclParam(String mclh) {
        this.mclh = mclh;
    }

    public String getMclh() {
        return mclh;
    }

    public void setMclh(String mclh) {
        this.mclh = mclh.trim();
    }
}
