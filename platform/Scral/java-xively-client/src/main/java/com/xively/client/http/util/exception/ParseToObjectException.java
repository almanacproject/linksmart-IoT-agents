// Copyright (c) 2003-2013, LogMeIn, Inc. All rights reserved.
// This is part of Xively4J library, it is under the BSD 3-Clause license.
package com.xively.client.http.util.exception;

import com.xively.client.XivelyClientException;
import com.xively.client.model.DomainObject;

/**
 * Exception condition when parsing to {@link DomainObject}
 * 
 * @author s0pau
 * 
 */
public class ParseToObjectException extends XivelyClientException
{
	private static final long serialVersionUID = 7105379283948908356L;

	public ParseToObjectException(String msg, Throwable e)
	{
		super(msg, e);
	}

	public ParseToObjectException(String msg)
	{
		super(msg);
	}
}
