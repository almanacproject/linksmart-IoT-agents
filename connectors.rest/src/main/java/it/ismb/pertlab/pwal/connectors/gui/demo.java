package it.ismb.pertlab.pwal.connectors.gui;

import java.util.ArrayList;
import java.util.Collection;

import it.ismb.pertlab.pwal.api.devices.events.DeviceLogger;
import it.ismb.pertlab.pwal.api.devices.interfaces.Device;
import it.ismb.pertlab.pwal.api.devices.model.Accelerometer;
import it.ismb.pertlab.pwal.api.devices.model.Thermometer;
import it.ismb.pertlab.pwal.api.devices.model.types.DeviceType;
import it.ismb.pertlab.pwal.api.internal.Pwal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class demo {
	
	@Autowired
	private Pwal pwal;
	
	@RequestMapping(value="demo", method=RequestMethod.GET)
	public String renderTheDemo(Model model)
	{
		Collection<Device> devlist = pwal.getDevicesList();
		ArrayList<DeviceLogger> loglist = pwal.getDeviceLogList();
		
		for (Device d: devlist)
		{
			if(DeviceType.THERMOMETER.equals(d.getType() )){
				Thermometer t=(Thermometer) d;
				t.getTemperature();
				 //System.out.println(itr.next());
			    System.err.println("\n"+t.getId()+" "+t.getType()+ " "+t.getNetworkType()+"\n" +t.getTemperature());
			}
			
			if(DeviceType.ACCELEROMETER.equals(d.getType() )){
				Accelerometer t=(Accelerometer) d;
				 //System.out.println(itr.next());
			    System.err.println("\n"+t.getId()+" "+t.getType()+ " "+t.getNetworkType()+"\n x:" +t.getXAcceleration()+" y:"+t.getYAcceleration()+" z:"+t.getZAcceleration());
			}
			
		}
		
		for (DeviceLogger listlog : loglist){
			System.err.println("\n Log "+listlog.Date+"Msg:"+listlog.LogMsg+"\n");
		}
		
		model.addAttribute("devlist", devlist);
		model.addAttribute("loglist", loglist);
		
		return "demo";
	}
	
	@RequestMapping(value="demosensor", method=RequestMethod.GET)
	@ResponseBody
	public String loadsensors(Model model)
	{
		Collection<Device> devlist = pwal.getDevicesList();
		
		model.addAttribute("devlist", devlist);
		
		return "demo#sensors";
	}
	
	@RequestMapping(value="demolog", method=RequestMethod.GET)
	@ResponseBody
	public String loadlog(Model model)
	{
		ArrayList<DeviceLogger> loglist = pwal.getDeviceLogList();
		
		model.addAttribute("loglist", loglist);
		
		return "demo#log";
	}

	private Collection<Device> removePwalReference(Collection<Device> source)
	{
		Collection<Device> destination = new ArrayList<>();
		for (Device d : source) {

		}
		return destination;
	}
}
