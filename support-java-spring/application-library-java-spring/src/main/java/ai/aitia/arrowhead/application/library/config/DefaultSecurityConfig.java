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

package ai.aitia.arrowhead.application.library.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import eu.arrowhead.common.CommonConstants;

public class DefaultSecurityConfig extends WebSecurityConfigurerAdapter {
	
	//=================================================================================================
	// members

	@Value(CommonConstants.$SERVER_SSL_ENABLED_WD)
	private boolean sslEnabled;
	
	//=================================================================================================
	// assistant methods

    //-------------------------------------------------------------------------------------------------
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
    	http.httpBasic().disable()
    	    .csrf().disable()
    	    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER);
    	if (sslEnabled) {
    		http.requiresChannel().anyRequest().requiresSecure();
    	}
    }
}
