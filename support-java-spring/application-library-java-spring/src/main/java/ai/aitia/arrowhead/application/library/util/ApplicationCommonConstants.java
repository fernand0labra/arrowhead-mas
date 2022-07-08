/********************************************************************************
 * Copyright (c) 2020 AITIA
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   AITIA - implementation
 ********************************************************************************/

package ai.aitia.arrowhead.application.library.util;

public class ApplicationCommonConstants {
	
	//=================================================================================================
	// members

	public static final String APPLICATION_SYSTEM_NAME = "application_system_name";
	public static final String $APPLICATION_SYSTEM_NAME = "${" + APPLICATION_SYSTEM_NAME + "}";
	public static final String APPLICATION_SERVER_ADDRESS = "server.address";
	public static final String $APPLICATION_SERVER_ADDRESS_WD = "${" + APPLICATION_SERVER_ADDRESS + ": localhost" + "}";
	public static final String APPLICATION_SERVER_PORT = "server.port";
	public static final String $APPLICATION_SERVER_PORT_WD = "${" + APPLICATION_SERVER_PORT + ": 8080" + "}";
	public static final String TOKEN_SECURITY_FILTER_ENABLED = "token.security.filter.enabled";
	public static final String $TOKEN_SECURITY_FILTER_ENABLED_WD = "${" + TOKEN_SECURITY_FILTER_ENABLED + ":true" + "}";
	public static final String CORE_SERVICE_DEFINITION_SUFFIX = "-ah.core";

	public static final String TOMCAT_WS_SSL_CONTEXT = "org.apache.tomcat.websocket.SSL_CONTEXT";
	public static final String WS_MANAGER_ID_PREFIX = "ws.manager.";
	
	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	private ApplicationCommonConstants() {
		throw new UnsupportedOperationException();
	}
}
