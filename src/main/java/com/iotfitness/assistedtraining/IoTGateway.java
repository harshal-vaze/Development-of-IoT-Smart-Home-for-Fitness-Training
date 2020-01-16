package com.iotfitness.assistedtraining;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IoTGateway {

	@RequestMapping(value = "/getRepCounts", produces = "application/xml")
	public String getRepCounts() {

		String xml = "";
		JSONObject json = new JSONObject();

		CoapClient client1 = new CoapClient("coap://localhost:5685/getRepCount");
		CoapResponse response1 = client1.get();

		if (response1 != null) {
			System.out.println(response1.getCode());
			System.out.println(response1.getOptions());
			System.out.println(response1.getResponseText());
			json.put("RepCount", new JSONObject(response1.getResponseText()).get("RepCount"));
		}

		else {
			System.out.println("Request failed");
		}

		CoapClient client2 = new CoapClient("coap://localhost:5686/getCaloriesCount");
		CoapResponse response2 = client2.get();

		if (response2 != null) {
			System.out.println(response2.getCode());
			System.out.println(response2.getOptions());
			System.out.println(response2.getResponseText());
			json.put("CaloriesCount", new JSONObject(response2.getResponseText()).get("CaloriesCount"));

		}

		else {
			System.out.println("Request failed");
		}

		CoapClient client3 = new CoapClient("coap://localhost:5687/getHeartRate");
		CoapResponse response3 = client3.get();

		if (response3 != null) {
			System.out.println(response3.getCode());
			System.out.println(response3.getOptions());
			System.out.println(response3.getResponseText());
			json.put("HeartRate", new JSONObject(response3.getResponseText()).get("HeartRate"));

		}

		else {
			System.out.println("Request failed");
		}

		xml = "<WeightTrainingCounts>" + XML.toString(json) + "</WeightTrainingCounts>";
		System.out.println(xml);

		return xml;

	}

	@RequestMapping(value = "/getCyclingCounts", produces = "application/xml")
	public String getCyclingCounts() {

		String xml = "";
		JSONObject json = new JSONObject();

		CoapClient client1 = new CoapClient("coap://localhost:5691/getSpeedCount");
		CoapResponse response1 = client1.get();

		if (response1 != null) {
			System.out.println(response1.getCode());
			System.out.println(response1.getOptions());
			System.out.println(response1.getResponseText());
			json.put("SpeedCount", new JSONObject(response1.getResponseText()).get("SpeedCount"));
		}

		else {
			System.out.println("Request failed");
		}

		CoapClient client2 = new CoapClient("coap://localhost:5692/getCaloriesCount2");
		CoapResponse response2 = client2.get();

		if (response2 != null) {
			System.out.println(response2.getCode());
			System.out.println(response2.getOptions());
			System.out.println(response2.getResponseText());
			json.put("CaloriesCount2", new JSONObject(response2.getResponseText()).get("CaloriesCount2"));

		}

		else {
			System.out.println("Request failed");
		}

		CoapClient client3 = new CoapClient("coap://localhost:5693/getHeartRate2");
		CoapResponse response3 = client3.get();

		if (response3 != null) {
			System.out.println(response3.getCode());
			System.out.println(response3.getOptions());
			System.out.println(response3.getResponseText());
			json.put("HeartRate2", new JSONObject(response3.getResponseText()).get("HeartRate2"));
		}

		else {
			System.out.println("Request failed");
		}

		xml = "<CyclingCounts>" + XML.toString(json) + "</CyclingCounts>";
		System.out.println(xml);

		return xml;

	}

	@RequestMapping(method = RequestMethod.POST, value = "/setRepCounts", consumes = "application/xml")
	public void setRepCounts(@RequestBody String xml) {

		JSONObject json = new JSONObject();

		CoapClient client1 = new CoapClient("coap://localhost:5685/getRepCount");
		CoapResponse response1 = client1.get();

		if (response1 != null) {
			System.out.println(response1.getCode());
			System.out.println(response1.getOptions());
			System.out.println(response1.getResponseText());
			json.put("RepCount", new JSONObject(response1.getResponseText()).get("RepCount"));
		}

		else {
			System.out.println("Request failed");
		}

		CoapClient client2 = new CoapClient("coap://localhost:5687/getHeartRate");
		CoapResponse response2 = client2.get();

		if (response2 != null) {
			System.out.println(response2.getCode());
			System.out.println(response2.getOptions());
			System.out.println(response2.getResponseText());
			json.put("HeartRate", new JSONObject(response2.getResponseText()).get("HeartRate"));

		}

		else {
			System.out.println("Request failed");
		}

		CoapClient client3 = new CoapClient("coap://localhost:5686/getCaloriesCount");
		CoapResponse response3 = client3.get();

		if (response3 != null) {
			System.out.println(response3.getCode());
			System.out.println(response3.getOptions());
			System.out.println(response3.getResponseText());
			json.put("CaloriesCount", new JSONObject(response3.getResponseText()).get("CaloriesCount"));

		}

		else {
			System.out.println("Request failed");
		}

		int newHeartRate = json.getInt("HeartRate");

		if (newHeartRate < 130) {
			if (json.getInt("RepCount") < 5) {
				newHeartRate = newHeartRate + 1;
			} else {
				newHeartRate = newHeartRate + 2;
			}
		}

		JSONObject postHeartData = new JSONObject();
		postHeartData.put("HeartRate", newHeartRate);

		CoapClient client4 = new CoapClient("coap://localhost:5690/setHeartRate");
		CoapResponse response4 = client4.post(postHeartData.toString(), MediaTypeRegistry.APPLICATION_JSON);
		if (response4 != null) {
			System.out.println(response4.getCode());
			System.out.println(response4.getOptions());
			System.out.println(response4.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

		double newCaloriesCount = json.getDouble("CaloriesCount");

		if (json.getInt("RepCount") < 5) {
			newCaloriesCount = newCaloriesCount + 0.5;
		} else {
			newCaloriesCount = newCaloriesCount + 0.75;
		}

		JSONObject postCaloriesData = new JSONObject();
		postCaloriesData.put("CaloriesCount", newCaloriesCount);

		CoapClient client5 = new CoapClient("coap://localhost:5689/setCaloriesCount");
		CoapResponse response5 = client5.post(postCaloriesData.toString(), MediaTypeRegistry.APPLICATION_JSON);
		if (response5 != null) {
			System.out.println(response5.getCode());
			System.out.println(response5.getOptions());
			System.out.println(response5.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

		CoapClient client6 = new CoapClient("coap://localhost:5688/setRepCount");

		JSONObject jsonPost = new JSONObject();
		JSONObject jsonXML = XML.toJSONObject(xml);

		if (jsonXML.getInt("RepCount") == 1) {
			jsonPost.put("RepCount", json.getInt("RepCount") + 1);
		} else if (jsonXML.getInt("RepCount") == 0) {
			jsonPost.put("RepCount", 0);
		}

		CoapResponse response6 = client6.post(jsonPost.toString(), MediaTypeRegistry.APPLICATION_JSON);

		if (response6 != null) {
			System.out.println(response6.getCode());
			System.out.println(response6.getOptions());
			System.out.println(response6.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/setCyclingCounts", consumes = "application/xml")
	public void setCyclingCounts(@RequestBody String xml) {

		JSONObject json = new JSONObject();

		CoapClient client1 = new CoapClient("coap://localhost:5691/getSpeedCount");
		CoapResponse response1 = client1.get();

		if (response1 != null) {
			System.out.println(response1.getCode());
			System.out.println(response1.getOptions());
			System.out.println(response1.getResponseText());
			json.put("SpeedCount", new JSONObject(response1.getResponseText()).get("SpeedCount"));
		}

		else {
			System.out.println("Request failed");
		}

		CoapClient client2 = new CoapClient("coap://localhost:5692/getCaloriesCount2");
		CoapResponse response2 = client2.get();

		if (response2 != null) {
			System.out.println(response2.getCode());
			System.out.println(response2.getOptions());
			System.out.println(response2.getResponseText());
			json.put("CaloriesCount2", new JSONObject(response2.getResponseText()).get("CaloriesCount2"));

		}

		else {
			System.out.println("Request failed");
		}

		CoapClient client3 = new CoapClient("coap://localhost:5693/getHeartRate2");
		CoapResponse response3 = client3.get();

		if (response3 != null) {
			System.out.println(response3.getCode());
			System.out.println(response3.getOptions());
			System.out.println(response3.getResponseText());
			json.put("HeartRate2", new JSONObject(response3.getResponseText()).get("HeartRate2"));
		}

		else {
			System.out.println("Request failed");
		}

		int newHeartRate = json.getInt("HeartRate2");

		if (newHeartRate < 160) {
			if (json.getInt("SpeedCount") < 5) {
				newHeartRate = newHeartRate + 1;
			} else if (json.getInt("SpeedCount") < 10) {
				newHeartRate = newHeartRate + 2;
			} else {
				newHeartRate = newHeartRate + 3;
			}
		}

		JSONObject postHeartData = new JSONObject();
		postHeartData.put("HeartRate2", newHeartRate);

		CoapClient client4 = new CoapClient("coap://localhost:5696/setHeartRate2");
		CoapResponse response4 = client4.post(postHeartData.toString(), MediaTypeRegistry.APPLICATION_JSON);
		if (response4 != null) {
			System.out.println(response4.getCode());
			System.out.println(response4.getOptions());
			System.out.println(response4.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

		double newCaloriesCount = json.getDouble("CaloriesCount2");

		if (json.getInt("SpeedCount") < 5) {
			newCaloriesCount = newCaloriesCount + 0.5;
		} else if (json.getInt("SpeedCount") < 10) {
			newCaloriesCount = newCaloriesCount + 1;
		} else {
			newCaloriesCount = newCaloriesCount + 1.5;
		}

		JSONObject postCaloriesData = new JSONObject();
		postCaloriesData.put("CaloriesCount2", newCaloriesCount);

		CoapClient client5 = new CoapClient("coap://localhost:5695/setCaloriesCount2");
		CoapResponse response5 = client5.post(postCaloriesData.toString(), MediaTypeRegistry.APPLICATION_JSON);
		if (response5 != null) {
			System.out.println(response5.getCode());
			System.out.println(response5.getOptions());
			System.out.println(response5.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

		CoapClient client6 = new CoapClient("coap://localhost:5694/setSpeedCount");

		JSONObject jsonPost = new JSONObject();
		JSONObject jsonXML = XML.toJSONObject(xml);

		if (jsonXML.getInt("SpeedCount") == 1) {
			jsonPost.put("SpeedCount", json.getInt("SpeedCount") + 1);
		} else if (jsonXML.getInt("SpeedCount") == 0) {
			jsonPost.put("SpeedCount", 0);
		}

		CoapResponse response6 = client6.post(jsonPost.toString(), MediaTypeRegistry.APPLICATION_JSON);

		if (response6 != null) {
			System.out.println(response6.getCode());
			System.out.println(response6.getOptions());
			System.out.println(response6.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

	}

	@RequestMapping(method = RequestMethod.POST, value = "/setNextExercise", consumes = "application/xml")
	public void setNextExercise(@RequestBody String xml) {

		JSONObject jsonPostRepCount = new JSONObject();
		jsonPostRepCount.put("RepCount", 0);

		CoapClient client1 = new CoapClient("coap://localhost:5688/setRepCount");
		CoapResponse response1 = client1.post(jsonPostRepCount.toString(), MediaTypeRegistry.APPLICATION_JSON);
		if (response1 != null) {
			System.out.println(response1.getCode());
			System.out.println(response1.getOptions());
			System.out.println(response1.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

		JSONObject jsonPostHeartRate = new JSONObject();
		jsonPostHeartRate.put("HeartRate", 100);

		CoapClient client2 = new CoapClient("coap://localhost:5690/setHeartRate");
		CoapResponse response2 = client2.post(jsonPostHeartRate.toString(), MediaTypeRegistry.APPLICATION_JSON);
		if (response2 != null) {
			System.out.println(response2.getCode());
			System.out.println(response2.getOptions());
			System.out.println(response2.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

		JSONObject jsonPostHeartRate2 = new JSONObject();
		jsonPostHeartRate2.put("HeartRate2", 100);

		CoapClient client3 = new CoapClient("coap://localhost:5696/setHeartRate2");
		CoapResponse response3 = client3.post(jsonPostHeartRate2.toString(), MediaTypeRegistry.APPLICATION_JSON);
		if (response3 != null) {
			System.out.println(response3.getCode());
			System.out.println(response3.getOptions());
			System.out.println(response3.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

		JSONObject jsonPostCalories = new JSONObject();
		jsonPostCalories.put("CaloriesCount", 0);

		CoapClient client4 = new CoapClient("coap://localhost:5689/setCaloriesCount");
		CoapResponse response4 = client4.post(jsonPostCalories.toString(), MediaTypeRegistry.APPLICATION_JSON);
		if (response4 != null) {
			System.out.println(response4.getCode());
			System.out.println(response4.getOptions());
			System.out.println(response4.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

		JSONObject jsonPostCalories2 = new JSONObject();
		jsonPostCalories2.put("CaloriesCount2", 0);

		CoapClient client5 = new CoapClient("coap://localhost:5695/setCaloriesCount2");
		CoapResponse response5 = client5.post(jsonPostCalories2.toString(), MediaTypeRegistry.APPLICATION_JSON);
		if (response5 != null) {
			System.out.println(response5.getCode());
			System.out.println(response5.getOptions());
			System.out.println(response5.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

		JSONObject jsonPostSpeedCount = new JSONObject();
		jsonPostSpeedCount.put("SpeedCount", 0);

		CoapClient client6 = new CoapClient("coap://localhost:5694/setSpeedCount");
		CoapResponse response6 = client6.post(jsonPostSpeedCount.toString(), MediaTypeRegistry.APPLICATION_JSON);
		if (response6 != null) {
			System.out.println(response6.getCode());
			System.out.println(response6.getOptions());
			System.out.println(response6.getResponseText());
		}

		else {
			System.out.println("Request failed");
		}

	}

}
