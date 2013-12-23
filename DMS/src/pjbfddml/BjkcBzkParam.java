package com.business.pjbfddml;

import java.io.Serializable;

//新增页面中，查询时的参数
public class BjkcBzkParam implements Serializable {
    private String ckh;//仓库号
    private String clh;//材料号
    private String scwlh;//物料号
    private String clmc;//材料名称

    public BjkcBzkParam() {

    }

    public BjkcBzkParam(String ckh, String clh) {
        this.ckh = ckh;
        this.clh = clh;
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh;
    }

    public String getClh() {
        return clh;
    }

    public void setClh(String clh) {
        this.clh = clh;
    }

    public String getScwlh() {
        return scwlh;
    }

    public void setScwlh(String scwlh) {
        this.scwlh = scwlh;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

}
