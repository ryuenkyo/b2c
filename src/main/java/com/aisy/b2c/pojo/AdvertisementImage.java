package com.aisy.b2c.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 广告轮播pojo
 * @author 刘笑楠
 *
 */
@Table(name = "advertisement_image")
public class AdvertisementImage {
	@Id
    @Column(name = "advertisement_id")
	private Short advertisementId;
	
	@Column(name = "advertisement_url")
	private String advertisementUrl;
	
	@Column(name = "ad_img_url")
	private String adImgUrl;
	
	private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;
    
    private String status;

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

	public Short getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(Short advertisementId) {
		this.advertisementId = advertisementId;
	}

	public String getAdvertisementUrl() {
		return advertisementUrl;
	}

	public void setAdvertisementUrl(String advertisementUrl) {
		this.advertisementUrl = advertisementUrl;
	}

	public String getAdImgUrl() {
		return adImgUrl;
	}

	public void setAdImgUrl(String adImgUrl) {
		this.adImgUrl = adImgUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
