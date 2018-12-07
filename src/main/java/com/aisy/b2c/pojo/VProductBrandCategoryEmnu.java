package com.aisy.b2c.pojo;
/**
 * 新建多表需要的值的pojo
 */
public class VProductBrandCategoryEmnu extends Product {
	/* add start by liuzhaoyu add new pojo 2018-05-11 18:03:30 */
	/**
	 *  categoryName 分类名
	 *  brandName 品牌名
	 *  statusName 枚举表的值
	 *  emnuName 枚举表的名字
	 */
	private String categoryName;
    private String brandName;
    private String statusName;
    private String emnuName;
    private String imgCover;
    
    public String getImgCover() {
		return imgCover;
	}

	public void setImgCover(String imgCover) {
		this.imgCover = imgCover;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public String getEmnuName() {
		return emnuName;
	}

	public void setEmnuName(String emnuName) {
		this.emnuName = emnuName;
	}
	/* add end by liuzhaoyu add new pojo 2018-05-11 18:03:30 */
}
