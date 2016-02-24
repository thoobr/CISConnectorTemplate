package com.frequentis.cis.connector.core;

import java.io.InvalidObjectException;

import org.apache.log4j.Logger;

import com.frequentis.cis.connector.core.cap.frqcop.FrqCapConnectorCoreExt;

public class ConnectorCoreImpl implements ConnectorCore {
	
	private Logger log = Logger.getLogger(this.getClass());
	FrqCapConnectorCoreExt capConnector = new FrqCapConnectorCoreExt();
	
	public void notify(Object msg) throws InvalidObjectException {
		log.info("--> sendMessage");
	}
	
	public void msgReceived(String msg) throws Exception {
		log.info("--> receviedMessage");
		log.debug(msg);
		
		if (msg.indexOf(CoreConstants.CAPMSG) != -1) {
			log.info("CAP Message received, forwarding to CAP Connector!");
			// CAP Message
			capConnector.msgReceived(msg);
		}
	}

}
