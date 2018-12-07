package com.aisy.b2c.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "order_header")
public class OrderHeader {
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "order_id")
    private Short orderId;

    private String tel;

    @Column(name = "client_Name")
    private String clientName;
    

	@Column(name = "order_status")
    private String orderStatus;

    private BigDecimal amount;

    @Column(name = "client_id")
    private Short clientId;
    
    @Column(name = "flag")
    private Short flag;

    public Short getFlag() {
		return flag;
	}

	public void setFlag(Short flag) {
		this.flag = flag;
	}

	@Column(name = "client_log")
    private String clientLog;

    @Column(name = "pay_channel")
    private String payChannel;

    @Column(name = "pay_time")
    private Date payTime;

    @Column(name = "pay_order_id")
    private String payOrderId;

    @Column(name = "order_time")
    private Date orderTime;

    private String address;

    @Column(name = "logistics_type")
    private String logisticsType;

    @Column(name = "logistics_amount")
    private BigDecimal logisticsAmount;

    @Column(name = "product_num")
    private Short productNum;

    @Column(name = "product_amount")
    private BigDecimal productAmount;

    @Column(name = "send_time")
    private Date sendTime;

    @Column(name = "settlement_time")
    private Date settlementTime;

    @Column(name = "invoice_type")
    private String invoiceType;

    @Column(name = "invoice_id")
    private Short invoiceId;

    @Column(name = "invoice_amount")
    private BigDecimal invoiceAmount;

    @Column(name = "promotion_amount")
    private BigDecimal promotionAmount;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;
    
    

    /**
     * @return order_id
     */
    public Short getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Short orderId) {
        this.orderId = orderId;
    }

    /**
     * @return order_status
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    /**
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return client_id
     */
    public Short getClientId() {
        return clientId;
    }

    /**
     * @param clientId
     */
    public void setClientId(Short clientId) {
        this.clientId = clientId;
    }

    /**
     * @return client_log
     */
    public String getClientLog() {
        return clientLog;
    }

    /**
     * @param clientLog
     */
    public void setClientLog(String clientLog) {
        this.clientLog = clientLog == null ? null : clientLog.trim();
    }

    /**
     * @return pay_channel
     */
    public String getPayChannel() {
        return payChannel;
    }

    /**
     * @param payChannel
     */
    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel == null ? null : payChannel.trim();
    }

    /**
     * @return pay_time
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * @param payTime
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * @return pay_order_id
     */
    public String getPayOrderId() {
        return payOrderId;
    }

    /**
     * @param payOrderId
     */
    public void setPayOrderId(String payOrderId) {
        this.payOrderId = payOrderId == null ? null : payOrderId.trim();
    }

    /**
     * @return order_time
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * @return logistics_type
     */
    public String getLogisticsType() {
        return logisticsType;
    }

    /**
     * @param logisticsType
     */
    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType == null ? null : logisticsType.trim();
    }

    /**
     * @return logistics_amount
     */
    public BigDecimal getLogisticsAmount() {
        return logisticsAmount;
    }

    /**
     * @param logisticsAmount
     */
    public void setLogisticsAmount(BigDecimal logisticsAmount) {
        this.logisticsAmount = logisticsAmount;
    }

    /**
     * @return product_num
     */
    public Short getProductNum() {
        return productNum;
    }

    /**
     * @param productNum
     */
    public void setProductNum(Short productNum) {
        this.productNum = productNum;
    }

    /**
     * @return product_amount
     */
    public BigDecimal getProductAmount() {
        return productAmount;
    }

    /**
     * @param productAmount
     */
    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    /**
     * @return send_time
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * @param sendTime
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * @return settlement_time
     */
    public Date getSettlementTime() {
        return settlementTime;
    }

    /**
     * @param settlementTime
     */
    public void setSettlementTime(Date settlementTime) {
        this.settlementTime = settlementTime;
    }

    /**
     * @return invoice_type
     */
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * @param invoiceType
     */
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    /**
     * @return invoice_id
     */
    public Short getInvoiceId() {
        return invoiceId;
    }

    /**
     * @param invoiceId
     */
    public void setInvoiceId(Short invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * @return invoice_amount
     */
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    /**
     * @param invoiceAmount
     */
    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    /**
     * @return promotion_amount
     */
    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    /**
     * @param promotionAmount
     */
    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
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
    public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}