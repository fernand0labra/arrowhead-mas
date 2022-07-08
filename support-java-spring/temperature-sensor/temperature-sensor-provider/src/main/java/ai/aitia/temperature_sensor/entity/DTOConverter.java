package ai.aitia.temperature_sensor.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import ai.aitia.temperature_common.dto.TemperatureResponseDTO;

public class DTOConverter {

	
	//=================================================================================================
	// methods
	
	//-------------------------------------------------------------------------------------------------
	public static TemperatureResponseDTO convertTemperatureToTemperatureResponseDTO(final Temperature temperature) {
		Assert.notNull(temperature, "temperature is null");
		return new TemperatureResponseDTO(temperature.getTemperature(), temperature.getUnit(), temperature.getTime());
	}
	
	//-------------------------------------------------------------------------------------------------
	public static List<TemperatureResponseDTO> convertTemperatureListToTemperatureResponseDTOList(final List<Temperature> temperatures) {
		Assert.notNull(temperatures, "temperature list is null");
		final List<TemperatureResponseDTO> temperatureResponse = new ArrayList<>(temperatures.size());
		for (final Temperature temperature : temperatures) {
			temperatureResponse.add(convertTemperatureToTemperatureResponseDTO(temperature));
		}
		return temperatureResponse;
	}

	//=================================================================================================
	// assistant methods

	//-------------------------------------------------------------------------------------------------
	public DTOConverter() {
		throw new UnsupportedOperationException(); 
	}
}
