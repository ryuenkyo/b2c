package com.aisy.b2c.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ProductModel implements Serializable {

	private static final long serialVersionUID = 7189606618520656370L;

	private Short productId;

    private String productName;

    private BigDecimal showPrice;

    private Short brandId;

    private String status;

    private Integer salesVolume;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    private String des;

    private Short categoryId;
	private String catgoryName;
    private String brandName;
    private String statusName;

	private String coverImageUrl;
    private List<String> detailImageUrls;

    /**
     * @return product_id
     */
    public Short getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(Short productId) {
        this.productId = productId;
    }

    /**
     * @return product_name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * @return show_price
     */
    public BigDecimal getShowPrice() {
        return showPrice;
    }

    /**
     * @param showPrice
     */
    public void setShowPrice(BigDecimal showPrice) {
        this.showPrice = showPrice;
    }

    /**
     * @return brand_id
     */
    public Short getBrandId() {
        return brandId;
    }

    /**
     * @param brandId
     */
    public void setBrandId(Short brandId) {
        this.brandId = brandId;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return sales_volume
     */
    public Integer getSalesVolume() {
        return salesVolume;
    }

    /**
     * @param salesVolume
     */
    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    /**
     * @return cu
     */
    public Short getCu() {
        return cu;
    }

    /**
     * @param cu
     */
    public void setCu(Short cu) {
        this.cu = cu;
    }

    /**
     * @return ct
     */
    public Date getCt() {
        return ct;
    }

    /**
     * @param ct
     */
    public void setCt(Date ct) {
        this.ct = ct;
    }

    /**
     * @return uu
     */
    public Short getUu() {
        return uu;
    }

    /**
     * @param uu
     */
    public void setUu(Short uu) {
        this.uu = uu;
    }

    /**
     * @return ut
     */
    public Date getUt() {
        return ut;
    }

    /**
     * @param ut
     */
    public void setUt(Date ut) {
        this.ut = ut;
    }

    /**
     * @return des
     */
    public String getDes() {
        return des;
    }

    /**
     * @param des
     */
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }
    
    public Short getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Short categoryId) {
		this.categoryId = categoryId;
	}

	public String getCatgoryName() {
		return catgoryName;
	}

	public void setCatgoryName(String catgoryName) {
		this.catgoryName = catgoryName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
    public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public List<String> getDetailImageUrls() {
		return detailImageUrls;
	}

	public void setDetailImageUrls(List<String> detailImageUrls) {
		this.detailImageUrls = detailImageUrls;
	}
}
