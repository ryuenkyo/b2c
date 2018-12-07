package com.aisy.b2c.mvc.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import com.aisy.b2c.model.Progress;

/**
 * 文件上传监听
 * 
 * 
 * @author cailongyang
 *
 */
@Component
public class FileUploadProgressListener implements ProgressListener {

	private HttpSession session;
	
	public void setSession(HttpSession session) {
		this.session = session;
		Progress status = new Progress();
		session.setAttribute("uploadStatus", status);
	}
	
	
	@Override
	public void update(long bytesRead, long contentLength, int items) {
		Progress status = (Progress) session.getAttribute("uploadStatus");
		status.setBytesRead(bytesRead);
		status.setContentLength(contentLength);
		status.setItems(items);
	}

}
