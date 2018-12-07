package com.aisy.b2c.model;

import java.io.Serializable;

/**
 * 进度条model
 * 
 * @author cailongyang
 *
 */
public class Progress implements Serializable {

	private static final long serialVersionUID = 4581239744956353614L;
	private long bytesRead;
    private long contentLength;
    private long items;
    
    public long getBytesRead() {
        return bytesRead;
    }
    
    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }
    
    public long getContentLength() {
        return contentLength;
    }
    
    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }
    
    public long getItems() {
        return items;
    }
    
    public void setItems(long items) {
        this.items = items;
    }
    
    @Override
    public String toString() {
        return "Progress [bytesRead=" + bytesRead + ", contentLength=" + contentLength + ", items=" + items + "]";
    }

}