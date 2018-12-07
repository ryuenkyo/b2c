package com.aisy.b2c.pojo;

/**
 * @author Alan_Liu
 */
public class VClientPoint extends ClientPoint {

    private String clientName;
    
    private String nickName;
    
    private String levelName;
    
    private String pointReason;
    
    private Integer point;

    private Short recordId;

	public Short getRecordId() {
		return recordId;
	}

	public void setRecordId(Short recordId) {
		this.recordId = recordId;
	}

	public String getPointReason() {
		return pointReason;
	}

	public void setPointReason(String pointReason) {
		this.pointReason = pointReason;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
    
}