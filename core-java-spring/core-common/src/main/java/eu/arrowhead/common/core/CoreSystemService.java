/********************************************************************************
 * Copyright (c) 2019 AITIA
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   AITIA - implementation
 *   Arrowhead Consortia - conceptualization
 ********************************************************************************/

package eu.arrowhead.common.core;

import java.util.List;

import org.springframework.util.Assert;

import eu.arrowhead.common.CommonConstants;
import eu.arrowhead.common.Utilities;

public enum CoreSystemService {

	//=================================================================================================
	// elements
	
	// Authorization services
	AUTH_CONTROL_INTRA_SERVICE(CommonConstants.CORE_SERVICE_AUTH_CONTROL_INTRA, CommonConstants.AUTHORIZATION_URI + CommonConstants.OP_AUTH_INTRA_CHECK_URI),
	AUTH_CONTROL_INTER_SERVICE(CommonConstants.CORE_SERVICE_AUTH_CONTROL_INTER, CommonConstants.AUTHORIZATION_URI + CommonConstants.OP_AUTH_INTER_CHECK_URI),
	AUTH_TOKEN_GENERATION_SERVICE(CommonConstants.CORE_SERVICE_AUTH_TOKEN_GENERATION, CommonConstants.AUTHORIZATION_URI + CommonConstants.OP_AUTH_TOKEN_URI),
	AUTH_PUBLIC_KEY_SERVICE(CommonConstants.CORE_SERVICE_AUTH_PUBLIC_KEY, CommonConstants.AUTHORIZATION_URI + CommonConstants.OP_AUTH_KEY_URI),
	AUTH_CONTROL_SUBSCRIPTION_SERVICE(CommonConstants.CORE_SERVICE_AUTH_CONTROL_SUBSCRIPTION, CommonConstants.AUTHORIZATION_URI + CommonConstants.OP_AUTH_SUBSCRIPTION_CHECK_URI),
	
	// Orchestrator services
	ORCHESTRATION_SERVICE(CommonConstants.CORE_SERVICE_ORCH_PROCESS, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_PROCESS_URI),
	ORCHESTRATION_CREATE_FLEXIBLE_STORE_RULES_SERVICE(CommonConstants.CORE_SERVICE_ORCH_CREATE_FLEXIBLE_STORE_RULES, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_CREATE_FLEXIBLE_STORE_RULES_URI),
	ORCHESTRATION_REMOVE_FLEXIBLE_STORE_RULE_SERVICE(CommonConstants.CORE_SERVICE_ORCH_REMOVE_FLEXIBLE_STORE_RULE, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_REMOVE_FLEXIBLE_STORE_RULE_URI),
	ORCHESTRATION_CLEAN_FLEXIBLE_STORE_SERVICE(CommonConstants.CORE_SERVICE_ORCH_CLEAN_FLEXIBLE_STORE, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_CLEAN_FLEXIBLE_STORE_URI),
	ORCHESTRATION_QOS_ENABLED_SERVICE(CommonConstants.CORE_SERVICE_ORCH_QOS_ENABLED, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_QOS_ENABLED_URI),
	ORCHESTRATION_QOS_RESERVATIONS_SERVICE(CommonConstants.CORE_SERVICE_ORCH_QOS_RESERVATIONS, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_QOS_RESERVATIONS_URI),
	ORCHESTRATION_QOS_TEMPORARY_LOCK_SERVICE(CommonConstants.CORE_SERVICE_ORCH_QOS_TEMPORARY_LOCK, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_QOS_TEMPORARY_LOCK_URI),
	ORCHESTRATION_QOS_CONFIRM_RESERVATION_SERVICE(CommonConstants.CORE_SERVICE_ORCH_QOS_CONFIRM_RESERVATION, CommonConstants.ORCHESTRATOR_URI + CommonConstants.OP_ORCH_QOS_RESERVATIONS_URI),
	
	// Gatekeeper services
	GATEKEEPER_GLOBAL_SERVICE_DISCOVERY(CommonConstants.CORE_SERVICE_GATEKEEPER_GSD, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_GSD_SERVICE),
	GATEKEEPER_INTER_CLOUD_NEGOTIATION(CommonConstants.CORE_SERVICE_GATEKEEPER_ICN, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_ICN_SERVICE),
	GATEKEEPER_PULL_CLOUDS(CommonConstants.CORE_SERVICE_GATEKEEPER_PULL_CLOUDS, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_PULL_CLOUDS_SERVICE),
	GATEKEEPER_COLLECT_SYSTEM_ADDRESSES(CommonConstants.CORE_SERVICE_GATEKEEPER_COLLECT_SYSTEM_ADDRESSES, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_COLLECT_SYSTEM_ADDRESSES_SERVICE),
	GATEKEEPER_COLLECT_ACCESS_TYPES(CommonConstants.CORE_SERVICE_GATEKEEPER_COLLECT_ACCESS_TYPES, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_COLLECT_ACCESS_TYPES_SERVICE),
	GATEKEEPER_RELAY_TEST_SERVICE(CommonConstants.CORE_SERVICE_GATEKEEPER_RELAY_TEST, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_RELAY_TEST_SERVICE),
	GATEKEEPER_GET_CLOUD_SERVICE(CommonConstants.CORE_SERVICE_GATEKEEPER_GET_CLOUD, CommonConstants.GATEKEEPER_URI + CommonConstants.OP_GATEKEEPER_GET_CLOUD_SERVICE +
								 CommonConstants.OP_GATEKEEPER_GET_CLOUD_SERVICE_SUFFIX),
	
	// Gateway services
	GATEWAY_PUBLIC_KEY_SERVICE(CommonConstants.CORE_SERVICE_GATEWAY_PUBLIC_KEY, CommonConstants.GATEWAY_URI + CommonConstants.OP_GATEWAY_KEY_URI),
	GATEWAY_PROVIDER_SERVICE(CommonConstants.CORE_SERVICE_GATEWAY_CONNECT_PROVIDER, CommonConstants.GATEWAY_URI + CommonConstants.OP_GATEWAY_CONNECT_PROVIDER_URI),
	GATEWAY_CONSUMER_SERVICE(CommonConstants.CORE_SERVICE_GATEWAY_CONNECT_CONSUMER, CommonConstants.GATEWAY_URI + CommonConstants.OP_GATEWAY_CONNECT_CONSUMER_URI),
	
	// Eventhandler services
	EVENT_PUBLISH_SERVICE(CommonConstants.CORE_SERVICE_EVENTHANDLER_PUBLISH, CommonConstants.EVENTHANDLER_URI + CommonConstants.OP_EVENTHANDLER_PUBLISH),
	EVENT_SUBSCRIBE_SERVICE(CommonConstants.CORE_SERVICE_EVENTHANDLER_SUBSCRIBE, CommonConstants.EVENTHANDLER_URI + CommonConstants.OP_EVENTHANDLER_SUBSCRIBE),
	EVENT_UNSUBSCRIBE_SERVICE(CommonConstants.CORE_SERVICE_EVENTHANDLER_UNSUBSCRIBE, CommonConstants.EVENTHANDLER_URI + CommonConstants.OP_EVENTHANDLER_UNSUBSCRIBE),
	EVENT_PUBLISH_AUTH_UPDATE_SERVICE(CommonConstants.CORE_SERVICE_EVENTHANDLER_PUBLISH_AUTH_UPDATE, CommonConstants.EVENTHANDLER_URI + CommonConstants.OP_EVENTHANDLER_PUBLISH_AUTH_UPDATE),

	// DataManager services
	PROXY_SERVICE(CommonConstants.CORE_SERVICE_DATAMANAGER_PROXY, CommonConstants.DATAMANAGER_URI + CommonConstants.OP_DATAMANAGER_PROXY),
	HISTORIAN_SERVICE(CommonConstants.CORE_SERVICE_DATAMANAGER_HISTORIAN, CommonConstants.DATAMANAGER_URI + CommonConstants.OP_DATAMANAGER_HISTORIAN),

	// TimeManager services
	TIME_SERVICE(CommonConstants.CORE_SERVICE_TIMEMANAGER_TIME, CommonConstants.TIMEMANAGER_URI + CommonConstants.OP_TIMEMANAGER_TIME),

	// Translator services
	TRANSLATOR_SERVICE(CommonConstants.CORE_SERVICE_TRANSLATOR, CommonConstants.TRANSLATOR_URI),
	TRANSLATOR_FIWARE_SERVICE(CommonConstants.CORE_SERVICE_TRANSLATOR_FIWARE, CommonConstants.TRANSLATOR_URI + CommonConstants.OP_TRANSLATOR_FIWARE_URI),
	TRANSLATOR_PLUGIN_SERVICE(CommonConstants.CORE_SERVICE_TRANSLATOR_PLUGIN, CommonConstants.TRANSLATOR_URI + CommonConstants.OP_TRANSLATOR_PLUGIN_URI),
	
	// CA services
	CERTIFICATEAUTHORITY_SIGN_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATEAUTHORITY_SIGN, CommonConstants.CERTIFICATEAUTHRORITY_URI + CommonConstants.OP_CA_SIGN_CERTIFICATE_URI),
	CERTIFICATEAUTHORITY_LIST_CERTIFICATES_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATEAUTHORITY_LIST_CERTIFICATES, CommonConstants.CERTIFICATEAUTHRORITY_URI + CommonConstants.OP_CA_MGMT_CERTIFICATES_URI),
	CERTIFICATEAUTHORITY_CHECK_CERTIFICATE_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATEAUTHORITY_CHECK_CERTIFICATE, CommonConstants.CERTIFICATEAUTHRORITY_URI + CommonConstants.OP_CA_CHECK_CERTIFICATE_URI),
	CERTIFICATEAUTHORITY_REVOKE_CERTIFICATE_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATEAUTHORITY_REVOKE_CERTIFICATE, CommonConstants.CERTIFICATEAUTHRORITY_URI + CommonConstants.OP_CA_MGMT_CERTIFICATES_URI +
													CommonConstants.OP_CA_MGMT_CERTIFICATES_URI_SUFFIX),
	CERTIFICATEAUTHORITY_LIST_TRUSTED_KEYS_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATEAUTHORITY_LIST_TRUSTED_KEYS, CommonConstants.CERTIFICATEAUTHRORITY_URI + CommonConstants.OP_CA_MGMT_TRUSTED_KEYS_URI),
	CERTIFICATEAUTHORITY_CHECK_TRUSTED_KEY_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATEAUTHORITY_CHECK_TRUSTED_KEY, CommonConstants.CERTIFICATEAUTHRORITY_URI + CommonConstants.OP_CA_CHECK_TRUSTED_KEY_URI),
	CERTIFICATEAUTHORITY_ADD_TRUSTED_KEY_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATEAUTHORITY_ADD_TRUSTED_KEY, CommonConstants.CERTIFICATEAUTHRORITY_URI + CommonConstants.OP_CA_MGMT_TRUSTED_KEYS_URI),
	CERTIFICATEAUTHORITY_DELETE_TRUSTED_KEY_SERVICE(CommonConstants.CORE_SERVICE_CERTIFICATEAUTHORITY_DELETE_TRUSTED_KEY, CommonConstants.CERTIFICATEAUTHRORITY_URI + CommonConstants.OP_CA_MGMT_TRUSTED_KEYS_URI +
													CommonConstants.OP_CA_MGMT_TRUSTED_KEYS_URI_SUFFIX),

    // Configuration services
	CONFIGURATION_SERVICE(CommonConstants.CORE_SERVICE_CONFIGURATION_CONF, CommonConstants.CONFIGURATION_URI +  CommonConstants.OP_CONFIGURATION_CONF),
	CONFIGURATION_RAW_SERVICE(CommonConstants.CORE_SERVICE_CONFIGURATION_RAWCONF, CommonConstants.CONFIGURATION_URI +  CommonConstants.OP_CONFIGURATION_RAWCONF, List.of(new InterfaceData(CommonConstants.HTTP, CommonConstants.BINARY))),
	CONFIGURATION_MANAGE_SERVICE(CommonConstants.CORE_SERVICE_CONFIGURATION_CONF, CommonConstants.CONFIGURATION_URI +  CommonConstants.OP_CONFIGURATION_MGMT_MANAGE),
	
    // Choreographer services
	CHOREOGRAPHER_SERVICE(CommonConstants.CORE_SERVICE_CHOREOGRAPHER_PROCESS, CommonConstants.CHOREOGRAPHER_URI +  CommonConstants.OP_CHOREOGRAPHER_NOTIFY_STEP_DONE),
	
	// QoS Monitor services

	QOSMONITOR_INTRA_PING_MEASUREMENT_SERVICE(CommonConstants.CORE_SERVICE_QOSMONITOR_INTRA_PING_MEASUREMENT, CommonConstants.QOSMONITOR_URI + CommonConstants.OP_QOSMONITOR_INTRA_PING_MEASUREMENT + 
										 	   CommonConstants.OP_QOSMONITOR_INTRA_PING_MEASUREMENT_SUFFIX),
	QOSMONITOR_INTRA_PING_MEDIAN_MEASUREMENT_SERVICE(CommonConstants.CORE_SERVICE_QOSMONITOR_INTRA_PING_MEDIAN_MEASUREMENT, CommonConstants.QOSMONITOR_URI + CommonConstants.OP_QOSMONITOR_INTRA_PING_MEDIAN_MEASUREMENT),
	QOSMONITOR_INTER_DIRECT_PING_MEASUREMENT_SERVICE(CommonConstants.CORE_SERVICE_QOSMONITOR_INTER_DIRECT_PING_MEASUREMENT, CommonConstants.QOSMONITOR_URI + CommonConstants.OP_QOSMONITOR_INTER_DIRECT_PING_MEASUREMENT),
	QOSMONITOR_INTER_RELAY_ECHO_MEASUREMENT_SERVICE(CommonConstants.CORE_SERVICE_QOSMONITOR_INTER_RELAY_ECHO_MEASUREMENT, CommonConstants.QOSMONITOR_URI + CommonConstants.OP_QOSMONITOR_INTER_RELAY_ECHO_MEASUREMENT),
	QOSMONITOR_PUBLIC_KEY_SERVICE(CommonConstants.CORE_SERVICE_QOSMONITOR_PUBLIC_KEY, CommonConstants.QOSMONITOR_URI + CommonConstants.OP_QOSMONITOR_KEY_URI),
	QOSMONITOR_JOIN_RELAY_TEST_SERVICE(CommonConstants.CORE_SERVICE_QOSMONITOR_JOIN_RELAY_TEST, CommonConstants.QOSMONITOR_URI + CommonConstants.OP_QOSMONITOR_JOIN_RELAY_TEST_URI),
	QOSMONITOR_INIT_RELAY_TEST_SERVICE(CommonConstants.CORE_SERVICE_QOSMONITOR_INIT_RELAY_TEST, CommonConstants.QOSMONITOR_URI + CommonConstants.OP_QOSMONITOR_INIT_RELAY_TEST_URI),

	// Onboarding services
    ONBOARDING_WITH_CERTIFICATE_AND_NAME_SERVICE(CommonConstants.CORE_SERVICE_ONBOARDING_WITH_CERTIFICATE_AND_NAME, CommonConstants.ONBOARDING_URI + CommonConstants.OP_ONBOARDING_WITH_CERTIFICATE_AND_NAME),
    ONBOARDING_WITH_CERTIFICATE_AND_CSR_SERVICE(CommonConstants.CORE_SERVICE_ONBOARDING_WITH_CERTIFICATE_AND_CSR, CommonConstants.ONBOARDING_URI + CommonConstants.OP_ONBOARDING_WITH_CERTIFICATE_AND_CSR),
    ONBOARDING_WITH_SHARED_SECRET_AND_NAME_SERVICE(CommonConstants.CORE_SERVICE_ONBOARDING_WITH_SHARED_SECRET_AND_NAME, CommonConstants.ONBOARDING_URI + CommonConstants.OP_ONBOARDING_WITH_SHARED_SECRET_AND_NAME),
    ONBOARDING_WITH_SHARED_SECRET_AND_CSR_SERVICE(CommonConstants.CORE_SERVICE_ONBOARDING_WITH_SHARED_SECRET_AND_CSR, CommonConstants.ONBOARDING_URI + CommonConstants.OP_ONBOARDING_WITH_SHARED_SECRET_AND_CSR),

    // Device Registry services
    DEVICEREGISTRY_REGISTER_SERVICE(CommonConstants.CORE_SERVICE_DEVICEREGISTRY_REGISTER, CommonConstants.DEVICEREGISTRY_URI + CommonConstants.OP_DEVICEREGISTRY_REGISTER_URI),
    DEVICEREGISTRY_UNREGISTER_SERVICE(CommonConstants.CORE_SERVICE_DEVICEREGISTRY_UNREGISTER, CommonConstants.DEVICEREGISTRY_URI + CommonConstants.OP_DEVICEREGISTRY_UNREGISTER_URI),
    DEVICEREGISTRY_ONBOARDING_WITH_NAME_SERVICE(CommonConstants.CORE_SERVICE_DEVICEREGISTRY_ONBOARDING_WITH_NAME,
    											 CommonConstants.DEVICEREGISTRY_URI + CommonConstants.ONBOARDING_URI + CommonConstants.OP_DEVICEREGISTRY_ONBOARDING_WITH_NAME_URI),
    DEVICEREGISTRY_ONBOARDING_WITH_CSR_SERVICE(CommonConstants.CORE_SERVICE_DEVICEREGISTRY_ONBOARDING_WITH_CSR,
    											CommonConstants.DEVICEREGISTRY_URI + CommonConstants.ONBOARDING_URI + CommonConstants.OP_DEVICEREGISTRY_ONBOARDING_WITH_CSR_URI),

    // System Registry services
    SYSTEMREGISTRY_REGISTER_SERVICE(CommonConstants.CORE_SERVICE_SYSTEMREGISTRY_REGISTER, CommonConstants.SYSTEMREGISTRY_URI + CommonConstants.OP_SYSTEMREGISTRY_REGISTER_URI),
    SYSTEMREGISTRY_UNREGISTER_SERVICE(CommonConstants.CORE_SERVICE_SYSTEMREGISTRY_UNREGISTER, CommonConstants.SYSTEMREGISTRY_URI + CommonConstants.OP_SYSTEMREGISTRY_UNREGISTER_URI),
    SYSTEMREGISTRY_ONBOARDING_WITH_NAME_SERVICE(CommonConstants.CORE_SERVICE_SYSTEMREGISTRY_ONBOARDING_WITH_NAME,
    											 CommonConstants.SYSTEMREGISTRY_URI + CommonConstants.ONBOARDING_URI + CommonConstants.OP_SYSTEMREGISTRY_ONBOARDING_WITH_NAME_URI),
    SYSTEMREGISTRY_ONBOARDING_WITH_CSR_SERVICE(CommonConstants.CORE_SERVICE_SYSTEMREGISTRY_ONBOARDING_WITH_CSR,
    											CommonConstants.SYSTEMREGISTRY_URI + CommonConstants.ONBOARDING_URI + CommonConstants.OP_SYSTEMREGISTRY_ONBOARDING_WITH_CSR_URI),

    // Service Registry services
    SERVICEREGISTRY_REGISTER_SERVICE(CommonConstants.CORE_SERVICE_SERVICEREGISTRY_REGISTER, CommonConstants.SERVICEREGISTRY_URI + CommonConstants.OP_SERVICEREGISTRY_REGISTER_URI),
    SERVICEREGISTRY_UNREGISTER_SERVICE(CommonConstants.CORE_SERVICE_SERVICEREGISTRY_UNREGISTER, CommonConstants.SERVICEREGISTRY_URI + CommonConstants.OP_SERVICEREGISTRY_UNREGISTER_URI),
    SERVICEREGISTRY_REGISTER_SYSTEM(CommonConstants.CORE_SERVICE_SERVICEREGISTRY_REGISTER_SYSTEM, CommonConstants.SERVICEREGISTRY_URI + CommonConstants.OP_SERVICEREGISTRY_REGISTER_SYSTEM_URI),
    SERVICEREGISTRY_UNREGISTER_SYSTEM(CommonConstants.CORE_SERVICE_SERVICEREGISTRY_UNREGISTER_SYSTEM, CommonConstants.SERVICEREGISTRY_URI + CommonConstants.OP_SERVICEREGISTRY_UNREGISTER_SYSTEM_URI),
    SERVICEREGISTRY_PULL_SYSTEMS(CommonConstants.CORE_SERVICE_SERVICEREGISTRY_PULL_SYSTEMS, CommonConstants.SERVICEREGISTRY_URI + CommonConstants.OP_SERVICEREGISTRY_PULL_SYSTEMS_URI);
	
	//TODO: additional services 
	
	//=================================================================================================
	// members
	
	private final String serviceDefinition;
	private final String serviceUri;
	private final List<InterfaceData> interfaces;
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public String getServiceDefinition() { return serviceDefinition; }
	public String getServiceUri() { return serviceUri; }
	public List<InterfaceData> getInterfaces() { return interfaces; }
	
	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	private CoreSystemService(final String serviceDefinition, final String serviceUri) {
		this(serviceDefinition, serviceUri, null);
	}
	
	//-------------------------------------------------------------------------------------------------
	private CoreSystemService(final String serviceDefinition, final String serviceUri, final List<InterfaceData> interfaces) {
		Assert.isTrue(!Utilities.isEmpty(serviceDefinition), "Service definition is null or blank");
		Assert.isTrue(!Utilities.isEmpty(serviceUri), "Service URI is null or blank");
		
		this.serviceDefinition = serviceDefinition;
		this.serviceUri = serviceUri;
		this.interfaces = interfaces == null || interfaces.isEmpty() ? null : interfaces;
	}
	
	//=================================================================================================
	// nested classes
	
	//-------------------------------------------------------------------------------------------------
	public static class InterfaceData {
		
		//=================================================================================================
		// members
		
		private final String protocol;
		private final String format;
		
		//=================================================================================================
		// methods
		
		//-------------------------------------------------------------------------------------------------
		public InterfaceData(final String protocol, final String format) {
			Assert.isTrue(!Utilities.isEmpty(protocol), "protocol is invalid");
			Assert.isTrue(!Utilities.isEmpty(format), "format is invalid");
			
			this.protocol = protocol.toUpperCase().trim();
			this.format = format.toUpperCase().trim();
		}

		//-------------------------------------------------------------------------------------------------
		public String getProtocol() { return protocol; }
		public String getFormat() {	return format; }
	}
}