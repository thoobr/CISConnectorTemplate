package com.frequentis.cis.connector.core;

import java.io.InvalidObjectException;

public interface ConnectorCore {
	
	public void notify(Object msg) throws InvalidObjectException;
	
	public void msgReceived(String msg) throws Exception;

}
