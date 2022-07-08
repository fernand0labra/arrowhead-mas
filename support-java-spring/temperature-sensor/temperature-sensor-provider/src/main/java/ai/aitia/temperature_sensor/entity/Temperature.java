package ai.aitia.temperature_sensor.entity;

public class Temperature {

	//=================================================================================================
	// members

	private final int temperature;
	private final String unit;
	private final double time;

	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	public Temperature(final int temperature, final String unit, final double time) {
		this.temperature = temperature;
		this.unit = unit;
		this.time = time;
	}

	//-------------------------------------------------------------------------------------------------
	public int getTemperature() { return temperature; }
	public String getUnit() { return unit; }
	public double getTime() { return time; }	
}
