package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "client_id")
    private Short clientId;

	@Column(name = "client_name")
    private String clientName;

    @Column(name = "nick_name")
    private String nickName;

    private Short age;

    private String telphone;

    private String password;
    
    private String status;
    
    @Column(name = "header_img")
    private String headerImg;

    public String getHeaderImg() {
		return headerImg;
	}

	public void setHeaderImg(String headerImg) {
		this.headerImg = headerImg;
	}

	private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

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
     * @return client_name
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    /**
     * @return nick_name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * @return age
     */
    public Short getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Short age) {
        this.age = age;
    }

    /**
     * @return telphone
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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
    
    @Override
	public String toString() {
		return "Client [clientId=" + clientId + ", clientName=" + clientName + ", nickName=" + nickName + ", age=" + age
				+ ", telphone=" + telphone + ", password=" + password + ", status=" + status + ", headerImg="
				+ headerImg + ", cu=" + cu + ", ct=" + ct + ", uu=" + uu + ", ut=" + ut + ", getHeaderImg()="
				+ getHeaderImg() + ", getClientId()=" + getClientId() + ", getClientName()=" + getClientName()
				+ ", getNickName()=" + getNickName() + ", getAge()=" + getAge() + ", getTelphone()=" + getTelphone()
				+ ", getPassword()=" + getPassword() + ", getCu()=" + getCu() + ", getCt()=" + getCt() + ", getUu()="
				+ getUu() + ", getUt()=" + getUt() + ", getStatus()=" + getStatus() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}