package com.pub.bxgsk;

import java.io.Serializable;

public class BxgskParam implements Serializable {
    private String bxgsdm;
    private String bxgsmc;

    public BxgskParam() {
    }

    public String getBxgsdm() {
        return bxgsdm;
    }

    public void setBxgsdm(String bxgsdm) {
        this.bxgsdm = bxgsdm.toUpperCase();
    }

    public String getBxgsmc() {
        return bxgsmc;
    }

    public void setBxgsmc(String bxgsmc) {
        this.bxgsmc = bxgsmc;
    }
}
