package com.iotfitness.assistedtraining.sensor;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.springframework.stereotype.Component;

@Component
public class RepCountSensor {

	public RepCountSensor() {
		
		CoapServer server = new CoapServer();
		
		server.add(new GetRepCount());       

        server.start();  
        
	}
	
	public static class GetRepCount extends CoapResource {
        public GetRepCount() {
        	
            super("getRepCount");
            
            getAttributes().setTitle("Get Rep Count");
        }
        
        int timer=0;
        
        @Override
        public void handleGET(CoapExchange exchange) {
        	
        	if (timer>15)
        	{
        		timer=0;
        		
        	}
            exchange.respond(ResponseCode.CONTENT, "{\"RepCount\":" + timer + "}", MediaTypeRegistry.APPLICATION_JSON);
            timer++;

        }
    }
	
}
