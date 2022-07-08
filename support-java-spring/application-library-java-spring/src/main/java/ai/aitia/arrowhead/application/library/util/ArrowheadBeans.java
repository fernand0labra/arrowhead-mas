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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import ai.aitia.arrowhead.application.library.ArrowheadService;
import eu.arrowhead.common.SSLProperties;
import eu.arrowhead.common.http.HttpService;

@Component
public class ArrowheadBeans implements ApplicationContextAware {

	//=================================================================================================
	// members
	
	private static ApplicationContext appContext;
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	/** 
	 * @return the ArrowheadService spring managed bean
	 */
	public static ArrowheadService getArrowheadService() {
		return appContext.getBean(ArrowheadService.class);
	}
	
	//-------------------------------------------------------------------------------------------------
	/** 
	 * @return the SSLProperties spring managed bean
	 */
	public static SSLProperties getSSLProperties() {
		return appContext.getBean(SSLProperties.class);
	}
	
	//-------------------------------------------------------------------------------------------------
	/** 
	 * @return the HttpService spring managed bean
	 */
	public static HttpService getHttpService() {
		return appContext.getBean(HttpService.class);
	}

	//-------------------------------------------------------------------------------------------------
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(final ApplicationContext ac) throws BeansException {
		this.appContext = ac;	
	}
}
