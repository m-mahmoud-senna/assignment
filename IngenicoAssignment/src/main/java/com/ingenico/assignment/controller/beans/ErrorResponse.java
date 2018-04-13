package com.ingenico.assignment.controller.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Immutable POJO for holding the timestamp and the description of the errors
 * that will be returned to APIs consumers
 * 
 * @author Mohamed Mahmoud (m.mahmoud.senna@gmail.com)
 */
public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 4253167587192744736L;
	private Date timestamp;
	private String message;

	public ErrorResponse(Date timestamp, String message) {
		this.timestamp = timestamp;
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}
}
