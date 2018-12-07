package com.aisy.b2c.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "evaluation")
public class Evaluation {
	@Id
    @Column(name = "evaluation_id")
	private Short evaluationId;
	
	@Column(name = "client_id")
	private Short clientId;
	
	@Column(name = "sku_id")
	private Short skuId;
	
	@Column(name = "product_id")
	private Short productId;
	
	public Short getProductId() {
		return productId;
	}

	public void setProductId(Short productId) {
		this.productId = productId;
	}

	@Column(name = "evaluation_content")
	private String evaluationContent;
	
	@Column(name = "assess_type")
	private String assessType;
	
	private Date ct;

	public Short getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(Short evaluationId) {
		this.evaluationId = evaluationId;
	}

	public Short getClientId() {
		return clientId;
	}

	public void setClientId(Short clientId) {
		this.clientId = clientId;
	}

	public Short getSkuId() {
		return skuId;
	}

	public void setSkuId(Short skuId) {
		this.skuId = skuId;
	}

	public String getEvaluationContent() {
		return evaluationContent;
	}

	public void setEvaluationContent(String evaluationContent) {
		this.evaluationContent = evaluationContent;
	}

	public String getAssessType() {
		return assessType;
	}

	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}

	public Date getCt() {
		return ct;
	}

	public void setCt(Date ct) {
		this.ct = ct;
	}
}
