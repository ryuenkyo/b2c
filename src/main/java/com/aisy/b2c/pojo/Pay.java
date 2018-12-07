package com.aisy.b2c.pojo;

import java.util.Date;

import javax.persistence.Column;

public class Pay {
    @Column(name = "pay_id")
	private Short payId;
    
    @Column(name = "pay_name")
    private String payName;

    @Column(name = "image_url")
    private String imageUrl;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

	public Short getPayId() {
		return payId;
	}

	public void setPayId(Short payId) {
		this.payId = payId;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Short getCu() {
		return cu;
	}

	public void setCu(Short cu) {
		this.cu = cu;
	}

	public Date getCt() {
		return ct;
	}

	public void setCt(Date ct) {
		this.ct = ct;
	}

	public Short getUu() {
		return uu;
	}

	public void setUu(Short uu) {
		this.uu = uu;
	}

	public Date getUt() {
		return ut;
	}

	public void setUt(Date ut) {
		this.ut = ut;
	}
    
}
