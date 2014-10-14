package eu.linksmart.event.datafusion.esper.impl;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import eu.linksmart.api.event.datafusion.ComplexEventHandler;
import eu.linksmart.api.event.datafusion.ResponseSet;
import eu.linksmart.api.event.datafusion.ResponseSet.Response;

import java.util.ArrayList;

public class QueryListener implements UpdateListener {
	private ComplexEventHandler CEPHandler;
	private final EsperQuery query;
	public QueryListener(EsperQuery query, ComplexEventHandler CEPHandler){
		this.CEPHandler= CEPHandler;
		this.query=query;
		
	}
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
    	ResponseSet ret = new EsperResponseSet(query.getName());
    	
    	
    	 for(EventBean i : newEvents){
    		 
    		 ((ArrayList<Response>)ret).add(((EsperResponse)i.getUnderlying()));
    		 
    	 }
    	 
    	CEPHandler.callerback(ret);
       // System.out.println("avg=" + event.get("instanceId"));
    }
}