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

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;

import ai.aitia.arrowhead.application.library.ArrowheadService;
import eu.arrowhead.common.CommonConstants;
import eu.arrowhead.common.SSLProperties;
import eu.arrowhead.common.Utilities;
import eu.arrowhead.common.core.CoreSystem;
import eu.arrowhead.common.exception.AuthException;

public abstract class ApplicationInitListener {

	//=================================================================================================
	// members
	
	@Autowired
	private ArrowheadService arrowheadService;
	
	@Autowired
	protected SSLProperties sslProperties;
	
	protected final Logger logger = LogManager.getLogger(ApplicationInitListener.class);
	
	//=================================================================================================
	// methods

	//-------------------------------------------------------------------------------------------------
	@EventListener
	@Order(10)
	public void onApplicationEvent(final ContextRefreshedEvent event) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, InterruptedException {
		logger.info("Security mode: {}", getModeString());
		
		if (sslProperties.isSslEnabled()) {
			final KeyStore keyStore = initializeKeyStore();
			checkServerCertificate(keyStore, event.getApplicationContext());
			obtainKeys(keyStore, event.getApplicationContext());
		}
		
		customInit(event);
	}
	
	//-------------------------------------------------------------------------------------------------
	@PreDestroy
	public void destroy() throws InterruptedException {
		customDestroy();
	}	

	//=================================================================================================
	// assistant methods
	
	//-------------------------------------------------------------------------------------------------
	protected void customInit(final ContextRefreshedEvent event) {}
	
	//-------------------------------------------------------------------------------------------------
	protected void customDestroy() {}
	
	//-------------------------------------------------------------------------------------------------
	protected String getModeString() {
		return sslProperties.isSslEnabled() ? "SECURED" : "NOT SECURED";
	}
	
	//-------------------------------------------------------------------------------------------------
	protected void checkCoreSystemReachability(final CoreSystem coreSystem) {
		if (arrowheadService.echoCoreSystem(coreSystem)) {
			logger.info("'{}' core system is reachable.", coreSystem.name());
		} else {
			logger.info("'{}' core system is NOT reachable.", coreSystem.name());
		}
	}
	
	//-------------------------------------------------------------------------------------------------
	private KeyStore initializeKeyStore() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		logger.debug("initializeKeyStore started...");
		Assert.isTrue(sslProperties.isSslEnabled(), "SSL is not enabled.");
		final String messageNotDefined = " is not defined.";
		Assert.isTrue(!Utilities.isEmpty(sslProperties.getKeyStoreType()), CommonConstants.KEYSTORE_TYPE + messageNotDefined);
		Assert.notNull(sslProperties.getKeyStore(), CommonConstants.KEYSTORE_PATH + messageNotDefined);
		Assert.isTrue(sslProperties.getKeyStore().exists(), CommonConstants.KEYSTORE_PATH + " file is not found.");
		Assert.notNull(sslProperties.getKeyStorePassword(), CommonConstants.KEYSTORE_PASSWORD + messageNotDefined);
		
		final KeyStore keystore = KeyStore.getInstance(sslProperties.getKeyStoreType());
		keystore.load(sslProperties.getKeyStore().getInputStream(), sslProperties.getKeyStorePassword().toCharArray());

		return keystore;
	}
	
	//-------------------------------------------------------------------------------------------------
	private void checkServerCertificate(final KeyStore keyStore, final ApplicationContext appContext) {
		logger.debug("checkServerCertificate started...");
		final X509Certificate serverCertificate = Utilities.getSystemCertFromKeyStore(keyStore);
		final String serverCN = Utilities.getCertCNFromSubject(serverCertificate.getSubjectDN().getName());
		if (!Utilities.isKeyStoreCNArrowheadValid(serverCN)) {
			logger.info("Application system CN ({}) is not compliant with the Arrowhead certificate structure, since it does not have 5 parts, or does not end with \"arrowhead.eu\".", serverCN);
			throw new AuthException("Server CN (" + serverCN + ") is not compliant with the Arrowhead certificate structure, since it does not have 5 parts, or does not end with \"arrowhead.eu\".");
		}
		logger.info("Application system CN: {}", serverCN);
		
		@SuppressWarnings("unchecked")
		final Map<String,Object> context = appContext.getBean(CommonConstants.ARROWHEAD_CONTEXT, Map.class);
		context.put(CommonConstants.SERVER_COMMON_NAME, serverCN);
	}
	
	//-------------------------------------------------------------------------------------------------
	private void obtainKeys(final KeyStore keyStore, final ApplicationContext appContext) {
		logger.debug("obtainKeys started...");
		@SuppressWarnings("unchecked")
		final Map<String,Object> context = appContext.getBean(CommonConstants.ARROWHEAD_CONTEXT, Map.class);
		
		context.put(CommonConstants.SERVER_PUBLIC_KEY, Utilities.getSystemCertFromKeyStore(keyStore).getPublicKey());
		
		final PrivateKey privateKey = Utilities.getPrivateKey(keyStore, sslProperties.getKeyPassword());
		context.put(CommonConstants.SERVER_PRIVATE_KEY, privateKey);
	}
}
