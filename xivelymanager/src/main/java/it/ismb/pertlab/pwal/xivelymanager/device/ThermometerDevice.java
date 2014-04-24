package it.ismb.pertlab.pwal.xivelymanager.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xively.client.XivelyService;
import com.xively.client.http.api.DatastreamRequester;
import com.xively.client.model.Datastream;

import it.ismb.pertlab.pwal.api.devices.model.Location;
import it.ismb.pertlab.pwal.api.devices.model.Thermometer;
import it.ismb.pertlab.pwal.api.devices.model.types.DeviceType;

/**
 * Class used to drive a thermometer via xively
 *
 */
public class ThermometerDevice implements Thermometer {

	private static final Logger LOG = LoggerFactory.getLogger(ThermometerDevice.class);
	
	private String id;
	private String updatedAt;
	private Location location;
	private final String type = DeviceType.THERMOMETER;
	private DatastreamRequester req;
	private String streamId;
	
	/**
	 * Constructor of the temperature sensor controlled using xively
	 * 
	 * @param feedID
	 *           id of the feed containing the datastream 
	 * 
	 * @param streamID
	 *           id of the datastream of the sensor
	 */
	public ThermometerDevice(Integer feedID, String streamID) {
		req = XivelyService.instance().datastream(feedID);
		this.streamId = streamID;
		id = feedID+streamID;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getType() {
		return this.type;
	}
	

	@Override
	public String getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public void setLocation(Location location) {
		this.location = location;
	}

	
	@Override
	public Double getTemperature() {
		Datastream stream = req.get(streamId);
		if(stream.getValue()!=null) {
			this.setUpdatedAt(stream.getUpdatedAt());
			LOG.info("Temperature value for "+stream.getId()+
					" "+stream.getValue()+
					((stream.getUnit()!=null) ? " "+stream.getUnit().getSymbol() : "") +
					" updated at: "+stream.getUpdatedAt());
			return Double.valueOf(stream.getValue());
		} else {
			LOG.error("Current value for "+stream.getId()+" not available");
			return 0.0;
		}
	}	
}
