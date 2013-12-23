package com.business.pjbfddml;

/**
 * Pjbfddnr entity. @author MyEclipse Persistence Tools
 */

public class Pjbfddnr implements java.io.Serializable {

	// Fields

	private PjbfddnrId id;
	private String clmc;
	private String dw;
	private Double cldj;
	private Integer cls;

	// Constructors

	/** default constructor */
	public Pjbfddnr() {
	}

	/** minimal constructor */
	public Pjbfddnr(PjbfddnrId id) {
		this.id = id;
	}

	/** full constructor */
	public Pjbfddnr(PjbfddnrId id, String clmc, String dw, Double cldj,
			Integer cls) {
		this.id = id;
		this.clmc = clmc;
		this.dw = dw;
		this.cldj = cldj;
		this.cls = cls;
	}

	// Property accessors

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	public PjbfddnrId getId() {
		return this.id;
	}

	public void setId(PjbfddnrId id) {
		this.id = id;
	}

	public String getClmc() {
		return this.clmc;
	}

	public void setClmc(String clmc) {
		this.clmc = clmc;
	}

	public String getDw() {
		return this.dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

	public Double getCldj() {
		return this.cldj;
	}

	public void setCldj(Double cldj) {
		this.cldj = cldj;
	}

	public Integer getCls() {
		return this.cls;
	}

	public void setCls(Integer cls) {
		this.cls = cls;
	}

}