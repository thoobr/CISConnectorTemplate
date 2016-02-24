package com.frequentis.cis.connector.core.cap.frqcop;

import java.io.InvalidObjectException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.xml.sax.SAXParseException;

import com.frequentis.cis.connector.callback.AppCallbackHandlerImpl;
import com.frequentis.cis.core.CISCore;
import com.frequentis.cis.core.CISCoreImpl;
import com.google.publicalerts.cap.Alert;
import com.google.publicalerts.cap.CapException;
import com.google.publicalerts.cap.CapXmlBuilder;
import com.google.publicalerts.cap.CapXmlParser;
import com.google.publicalerts.cap.NotCapException;

public class FrqCapConnectorCoreExt {
	
	private CapXmlParser capParser = new CapXmlParser(true);
	private CapXmlBuilder capPBuilder = new CapXmlBuilder();
	
	private Logger log = Logger.getLogger(this.getClass());
	
	private CISCore core = CISCoreImpl.getInstance();
	
	public FrqCapConnectorCoreExt() {
		
	}
	/**
	 * This method receives an Object (CAP XML as String), validates the XML against the defined schema and
	 * forwards it to the CIS CIS Core
	 * 
	 * @param msg the CAP XML String
	 * @throws InvalidObjectException when the XML is no valid CAP message
	 */
	public void notify(Object msg) throws InvalidObjectException {
		log.info("notify");
		
		String capMsgStr = null;
		if (msg instanceof String) {
			capMsgStr = (String)msg;
		} else {
			throw new InvalidObjectException("Given Object is no valid CAP Message!");
		}
		
		Alert capMsg = null;
		try {
			capMsg = capParser.parseFrom(capMsgStr);
		} catch (NotCapException | SAXParseException | CapException e) {
			log.error("notify: " + e);
			throw new InvalidObjectException("Message is no valid CAP message!" + e.getMessage());
		}
		
		try {
			core.notify("CAP", capMsg, null, false);
		} catch (Exception e) {
			log.error("notify: " + e);
		}
		
	}
	
	/**
	 * This message is called by the ConnectorCore when a message was receive. It will be translatet to the Application
	 * representation and the translatet message if send via the registered callback to the application.
	 * @param msg
	 * @throws InvalidObjectException
	 */
	public void msgReceived(String msg) throws InvalidObjectException {
		log.info("--> messageReceived");
		
		Alert alert = null;
	    try {
			 alert = capParser.parseFrom(msg);
		} catch (NotCapException | SAXParseException | CapException e) {
			log.error("Error parsing the CAP message!", e);
		}
	    
	    // translate the Object

		
		// create and forward the message to the connector
		AppCallbackHandlerImpl.getInstance().getCallback("CAP").msgReceived(alert);
	}

}
