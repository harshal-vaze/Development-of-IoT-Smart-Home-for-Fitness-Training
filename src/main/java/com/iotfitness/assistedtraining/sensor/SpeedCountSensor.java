package com.iotfitness.assistedtraining.sensor;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.springframework.stereotype.Component;

@Component
public class SpeedCountSensor {

	public SpeedCountSensor() {
		
		CoapServer server = new CoapServer(5686);
		
		server.add(new GetSpeedCount());       

        server.start();  
        
	}

	public static class GetSpeedCount extends CoapResource {
		public GetSpeedCount() {
    	
			super("getSpeedCount");
        
			getAttributes().setTitle("Get Speed Count");
    }
 
		int speed=-1;
        
        @Override
        public void handleGET(CoapExchange exchange) {
        	
        	if (speed<20)
        	{        		
                speed++;       		
        	}   
        	
        	exchange.respond(ResponseCode.CONTENT, "{\"SpeedCount\":" + speed + "}", MediaTypeRegistry.APPLICATION_JSON);

        }
    }			
}
