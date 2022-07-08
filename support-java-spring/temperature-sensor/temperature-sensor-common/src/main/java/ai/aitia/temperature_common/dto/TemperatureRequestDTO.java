package ai.aitia.temperature_common.dto;

import java.io.Serializable;

public class TemperatureRequestDTO implements Serializable {

	//=================================================================================================
	// members

	private static final long serialVersionUID = -5363562707054976998L;

	private String unit;
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public TemperatureRequestDTO(final String unit) {
		this.unit = unit;
	}

	//-------------------------------------------------------------------------------------------------
	public String getUnit() { return unit; }	
}
