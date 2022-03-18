package ai.aitia.temperature_common.dto;

import java.io.Serializable;

public class TemperatureResponseDTO implements Serializable {

	//=================================================================================================
	// members

	private static final long serialVersionUID = -5363562707054976998L;

	private int temperature;
	private String unit;
	private double time;
	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public TemperatureResponseDTO() {}
	
	//-------------------------------------------------------------------------------------------------
	public TemperatureResponseDTO(final int temperature, final String unit, final double time) {
		this.temperature = temperature;
		this.unit = unit;
		this.time = time;
	}

	//-------------------------------------------------------------------------------------------------
	public int getTemperature() { return temperature; }
	public String getUnit() { return unit; }
	public double getTime() { return time; }	
}
