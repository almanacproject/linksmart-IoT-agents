// Copyright (c) 2003-2013, LogMeIn, Inc. All rights reserved.
// This is part of Xively4J library, it is under the BSD 3-Clause license.
package com.xively.client.http.exception;

import com.xively.client.XivelyClientException;
import com.xively.client.utils.StringUtil;

/**
 * Exception condition when executing HTTP requests to API
 * 
 * @author s0pau
 * 
 */
public class HttpException extends XivelyClientException
{
	private Integer statusCode;
	private String errorDetail;

	public HttpException(String msg, int statusCode, String errorDetail)
	{
		super(msg);
		this.statusCode = statusCode;
		this.errorDetail = errorDetail;
	}

	public HttpException(String msg, Exception e)
	{
		super(msg, e);
	}

	public String getLocalizedMessage()
	{
		if (statusCode == null)
		{
			return super.getLocalizedMessage();
		}

		StringBuilder sb = new StringBuilder(getMessage());
		sb.append("[Status code: ").append(statusCode).append(".");
		if (!StringUtil.isNullOrEmpty(errorDetail))
		{
			sb.append("; Reason: ").append(errorDetail);
		}
		sb.append("]");
		return sb.toString();
	}

	public int getStatusCode()
	{
		return statusCode == null ? 0 : statusCode;
	}
}
