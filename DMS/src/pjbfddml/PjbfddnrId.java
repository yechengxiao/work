package com.business.pjbfddml;

/**
 * PjbfddnrId entity. @author MyEclipse Persistence Tools
 */

public class PjbfddnrId implements java.io.Serializable {

	// Fields

	private Pjbfddml pjbfddml;
	private String clh;

	// Constructors

	/** default constructor */
	public PjbfddnrId() {
	}

	/** full constructor */
	public PjbfddnrId(Pjbfddml pjbfddml, String clh) {
		this.pjbfddml = pjbfddml;
		this.clh = clh;
	}

	// Property accessors

	public Pjbfddml getPjbfddml() {
		return this.pjbfddml;
	}

	public void setPjbfddml(Pjbfddml pjbfddml) {
		this.pjbfddml = pjbfddml;
	}

	public String getClh() {
		return this.clh;
	}

	public void setClh(String clh) {
		this.clh = clh.trim();
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PjbfddnrId))
			return false;
		PjbfddnrId castOther = (PjbfddnrId) other;

		return ((this.getPjbfddml() == castOther.getPjbfddml()) || (this
				.getPjbfddml() != null && castOther.getPjbfddml() != null && this
				.getPjbfddml().equals(castOther.getPjbfddml())))
				&& ((this.getClh() == castOther.getClh()) || (this.getClh() != null
						&& castOther.getClh() != null && this.getClh().equals(
						castOther.getClh())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPjbfddml() == null ? 0 : this.getPjbfddml().hashCode());
		result = 37 * result
				+ (getClh() == null ? 0 : this.getClh().hashCode());
		return result;
	}

}