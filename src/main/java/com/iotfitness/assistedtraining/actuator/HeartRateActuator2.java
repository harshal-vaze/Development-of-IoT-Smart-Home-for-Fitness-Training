package com.iotfitness.assistedtraining.actuator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/* This class generates the Heart Rate Actuator 
	and implements CoAP Server. */

@Component
public class HeartRateActuator2 {

	/*
	 * This constructor initiates CoAP Server with assigned Port and adds CoAP
	 * Resource to it.
	 */

	public HeartRateActuator2() {

		CoapServer server = new CoapServer(5696);

		server.add(new SetHeartRate2());

		server.start();

	}

	/*
	 * This class extends the CoAP Resource and handles the CoAP Resource methods.
	 */

	public static class SetHeartRate2 extends CoapResource {

		// This constructor adds new CoAP Resource.

		public SetHeartRate2() {

			super("setHeartRate2");

			getAttributes().setTitle("Set Cycling Heart Rate");
		}

		@Override
		public void handlePOST(CoapExchange exchange) {

			exchange.respond(ResponseCode.CONTENT, "{\"message\":\"HEART RATE POST REQUEST SUCCESS\"}",
					MediaTypeRegistry.APPLICATION_JSON);

			JSONObject json = new JSONObject(exchange.getRequestText());
			String data = json.get("HeartRate2").toString();

			BufferedWriter bw = null;

			try {
				bw = new BufferedWriter(new FileWriter(new File("HeartRate2.txt")));
				bw.write(data);
			}

			catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}

			finally {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
