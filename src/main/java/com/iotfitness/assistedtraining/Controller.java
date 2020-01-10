package com.iotfitness.assistedtraining;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@RequestMapping(value = "/getCounts", produces = "application/xml")
	public String getRepCount() {
		
		String xml = "";
		JSONObject json = new JSONObject();
		
		CoapClient client1 = new CoapClient("coap://localhost:5683/getRepCount");
		CoapResponse response1 = client1.get();
		
		if (response1!=null) {        
        	System.out.println( response1.getCode() );
        	System.out.println( response1.getOptions() );
        	System.out.println( response1.getResponseText() );
        	json.put("RepCount", new JSONObject(response1.getResponseText()).get("RepCount"));        	
        }
        
        else {        	
        	System.out.println("Request failed");        	
        }
		
		CoapClient client2 = new CoapClient("coap://localhost:5684/getCaloriesCount");    
        CoapResponse response2 = client2.get();
        
        if (response2!=null) {        
        	System.out.println( response2.getCode() );
        	System.out.println( response2.getOptions() );
        	System.out.println( response2.getResponseText() );
        	json.put("CaloriesCount", new JSONObject(response2.getResponseText()).get("CaloriesCount"));
        	       	
        }
        
        else {        	
        	System.out.println("Request failed");        	
        }
        
        CoapClient client3 = new CoapClient("coap://localhost:5689/getHeartRate");    
        CoapResponse response3 = client3.get();
        
        if (response3!=null) {        
        	System.out.println( response3.getCode() );
        	System.out.println( response3.getOptions() );
        	System.out.println( response3.getResponseText() );
        	json.put("HeartRate", new JSONObject(response3.getResponseText()).get("HeartRate"));
        	       	
        }
        
        else {        	
        	System.out.println("Request failed");        	
        }
        
        int newHeartRate = json.getInt("HeartRate");
        
        if (newHeartRate <= 110) {
        	
        	 if (newHeartRate <= 110) {
             	newHeartRate = newHeartRate + 1;
             }
        	
        	newHeartRate = newHeartRate + 2;
        }
        
        xml = "<HeartRate>" + XML.toString( json ) + "</HeartRate>";
    	System.out.println( xml ); 
    	
    	JSONObject postHeartData = new JSONObject();
    	postHeartData.put("HeartRate",newHeartRate);
    	
    	CoapClient client5 = new CoapClient("coap://localhost:5690/setHeartRate");
    	CoapResponse response5 = client5.post(postHeartData.toString(), MediaTypeRegistry.APPLICATION_JSON);
    	if (response5!=null) {        
        	System.out.println( response5.getCode() );
        	System.out.println( response5.getOptions() );
        	System.out.println( response5.getResponseText() );        	
        }
        
        else {        	
        	System.out.println("Request failed");        	
        }
        
        int newCaloriesCount = json.getInt("CaloriesCount") + 5;
        
        xml = "<Counts>" + XML.toString( json ) + "</Counts>";
    	System.out.println( xml );     	    	
    	
    	JSONObject postCaloriesData = new JSONObject();
    	postCaloriesData.put("CaloriesCount",newCaloriesCount);
    	
    	CoapClient client4 = new CoapClient("coap://localhost:5685/setCaloriesCount");
    	CoapResponse response4 = client4.post(postCaloriesData.toString(), MediaTypeRegistry.APPLICATION_JSON);
    	if (response4!=null) {        
        	System.out.println( response4.getCode() );
        	System.out.println( response4.getOptions() );
        	System.out.println( response4.getResponseText() );        	
        }
        
        else {        	
        	System.out.println("Request failed");        	
        }
    	    	
        return xml;
		
	}

	@RequestMapping(value = "/getCounts2", produces = "application/xml")
	public String getSpeedCount() {
		
		String xml = "";
		JSONObject json = new JSONObject();
		
		CoapClient client6 = new CoapClient("coap://localhost:5686/getSpeedCount");
		CoapResponse response6 = client6.get();
		
		if (response6!=null) {        
        	System.out.println( response6.getCode() );
        	System.out.println( response6.getOptions() );
        	System.out.println( response6.getResponseText() );
        	json.put("SpeedCount", new JSONObject(response6.getResponseText()).get("SpeedCount"));        	
        }
        
        else {        	
        	System.out.println("Request failed");        	
        }
		
		CoapClient client7 = new CoapClient("coap://localhost:5687/getCaloriesCount2");    
        CoapResponse response7 = client7.get();
        
        if (response7!=null) {        
        	System.out.println( response7.getCode() );
        	System.out.println( response7.getOptions() );
        	System.out.println( response7.getResponseText() );
        	json.put("CaloriesCount2", new JSONObject(response7.getResponseText()).get("CaloriesCount2"));
        	       	
        }
        
        else {        	
        	System.out.println("Request failed");        	
        }
    	
        CoapClient client8 = new CoapClient("coap://localhost:5691/getHeartRate2");
		CoapResponse response8 = client8.get();
		
		if (response8!=null) {        
        	System.out.println( response8.getCode() );
        	System.out.println( response8.getOptions() );
        	System.out.println( response8.getResponseText() );
        	json.put("HeartRate2", new JSONObject(response8.getResponseText()).get("HeartRate2"));        	
        }
        
        else {        	
        	System.out.println("Request failed");        	
        }
        
		int newHeartRate = json.getInt("HeartRate2");
		
		if (newHeartRate >= 100) {
	        	
        	if (newHeartRate <= 105) {
            	newHeartRate = newHeartRate + 2;
            }
        	
          newHeartRate = newHeartRate + 4;
        }
        
        xml = "<CyclingHeartRate>" + XML.toString( json ) + "</CyclingHeartRate>";
    	System.out.println( xml ); 
    	
    	JSONObject postHeartData = new JSONObject();
    	postHeartData.put("HeartRate2",newHeartRate);
    	
    	CoapClient client10 = new CoapClient("coap://localhost:5692/setHeartRate2");
    	CoapResponse response10 = client10.post(postHeartData.toString(), MediaTypeRegistry.APPLICATION_JSON);
    	if (response10!=null) {        
        	System.out.println( response10.getCode() );
        	System.out.println( response10.getOptions() );
        	System.out.println( response10.getResponseText() );        	
        }
        
        else {        	
        	System.out.println("Request failed");        	
        }
		
    	int newCaloriesCount = json.getInt("CaloriesCount2") + json.getInt("SpeedCount") * 2;
        
    	xml = "<CyclingCounts>" + XML.toString( json ) + "</CyclingCounts>";
    	System.out.println( xml );
    	
    	JSONObject postCaloriesData = new JSONObject();
    	postCaloriesData.put("CaloriesCount2",newCaloriesCount);
    	
    	CoapClient client9 = new CoapClient("coap://localhost:5688/setCaloriesCount2");
    	CoapResponse response9 = client9.post(postCaloriesData.toString(), MediaTypeRegistry.APPLICATION_JSON);
    	if (response9!=null) {        
        	System.out.println( response9.getCode() );
        	System.out.println( response9.getOptions() );
        	System.out.println( response9.getResponseText() );        	
        }
        
        else {        	
        	System.out.println("Request failed");        	
        }
    	    	   	
        return xml;
		
	}	
	
}
