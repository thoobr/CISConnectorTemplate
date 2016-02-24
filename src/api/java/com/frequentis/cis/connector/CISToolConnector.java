package com.frequentis.cis.connector;

import java.util.Map;

import com.frequentis.cis.callback.AppCallback;
import com.frequentis.cis.connector.criteria.Criteria;
import com.frequentis.cis.connector.edxlde.EDXLDEParameters;
import com.frequentis.cis.core.edxlde.parameters.DEParameters;

public interface CISToolConnector {

	public void registerForMsg(String msgType, Criteria criteria, AppCallback callback);
	
	public void notify(String msgType, String msg, Map<DEParameters, String> deParameters);
}
