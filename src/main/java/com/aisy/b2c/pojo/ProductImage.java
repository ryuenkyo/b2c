package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "product_image")
public class ProductImage {
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "image_id")
    private Short imageId;

    @Column(name = "product_id")
    private Short productId;

    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_url")
    private String imageUrl;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    @Column(name = "image_base")
    private byte[] imageBase;

    /**
     * @return image_id
     */
    public Short getImageId() {
        return imageId;
    }

    /**
     * @param imageId
     */
    public void setImageId(Short imageId) {
        this.imageId = imageId;
    }

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
     * @return image_type
     */
    public String getImageType() {
        return imageType;
    }

    /**
     * @param imageType
     */
    public void setImageType(String imageType) {
        this.imageType = imageType == null ? null : imageType.trim();
    }

    /**
     * @return image_url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
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
     * @return image_base
     */
    public byte[] getImageBase() {
        return imageBase;
    }

    /**
     * @param imageBase
     */
    public void setImageBase(byte[] imageBase) {
        this.imageBase = imageBase;
    }
}