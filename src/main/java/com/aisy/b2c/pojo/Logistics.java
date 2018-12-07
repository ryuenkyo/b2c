package com.aisy.b2c.pojo;

import java.util.Date;

import javax.persistence.Column;

public class Logistics {
    @Column(name = "logistics_id")
	private Short logisticsId;
    
    @Column(name = "logistics_name")
    private String logisticsName;

    @Column(name = "image_url")
    private String imageUrl;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

	public Short getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(Short logisticsId) {
		this.logisticsId = logisticsId;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
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
