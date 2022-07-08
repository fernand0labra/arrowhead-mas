package eu.arrowhead.application.skeleton.provider.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;

import ai.aitia.arrowhead.application.library.config.DefaultSecurityConfig;
import ai.aitia.arrowhead.application.library.util.ApplicationCommonConstants;

@Configuration
@EnableWebSecurity
public class ProviderSecurityConfig extends DefaultSecurityConfig {
	
	//=================================================================================================
	// members
	
	@Value(ApplicationCommonConstants.$TOKEN_SECURITY_FILTER_ENABLED_WD)
	private boolean tokenSecurityFilterEnabled;
	
	private ProviderTokenSecurityFilter tokenSecurityFilter;
	
	//=================================================================================================
	// methods

    //-------------------------------------------------------------------------------------------------
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		if (tokenSecurityFilterEnabled) {
			tokenSecurityFilter = new ProviderTokenSecurityFilter();
			http.addFilterAfter(tokenSecurityFilter, SecurityContextHolderAwareRequestFilter.class);			
		}
	}

	//-------------------------------------------------------------------------------------------------
	public ProviderTokenSecurityFilter getTokenSecurityFilter() {
		return tokenSecurityFilter;
	}	
}
