package com.business.pjbfddml;

import java.io.Serializable;

//供查询使用
public class PjbfddmlParam implements Serializable {

	private String rq;// 年月日
	private String rqStart;// 查询起始日期
	private String rqEnd;// 查询结束日期
	private String zyh;// 流水号
	private String ckh;// 仓库号
	private String czlx;// 类型 BF/HY 报废/还原
	private String zt;// 暂存，已确认

	public PjbfddmlParam() {
	}

	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	public String getZyh() {
		return zyh;
	}

	public void setZyh(String zyh) {
		this.zyh = (zyh != null ? zyh.toUpperCase() : "");
	}

	public String getCkh() {
		return ckh;
	}

	public void setCkh(String ckh) {
		this.ckh = (ckh != null ? ckh.toUpperCase() : "");
		;
	}

	public String getRqStart() {
		return rqStart;
	}

	public void setRqStart(String rqStart) {
		this.rqStart = rqStart;
	}

	public String getRqEnd() {
		return rqEnd;
	}

	public void setRqEnd(String rqEnd) {
		this.rqEnd = rqEnd;
	}

	public String getCzlx() {
		return czlx;
	}

	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

}
