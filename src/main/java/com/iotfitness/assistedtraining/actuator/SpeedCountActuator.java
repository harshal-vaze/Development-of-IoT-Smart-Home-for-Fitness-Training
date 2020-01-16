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

/* This class generates the Speed Count Actuator 
	and implements CoAP Server. */

@Component
public class SpeedCountActuator {

	/*
	 * This constructor initiates CoAP Server with assigned Port and adds CoAP
	 * Resource to it.
	 */

	public SpeedCountActuator() {

		CoapServer server = new CoapServer(5694);

		server.add(new SetSpeedCount());

		server.start();

	}

	/*
	 * This class extends the CoAP Resource and handles the CoAP Resource methods.
	 */

	public static class SetSpeedCount extends CoapResource {

		// This constructor adds new CoAP Resource.

		public SetSpeedCount() {

			super("setSpeedCount");

			getAttributes().setTitle("Set Speed Count");
		}

		@Override
		public void handlePOST(CoapExchange exchange) {

			exchange.respond(ResponseCode.CONTENT, "{\"message\":\"SPEED COUNT POST REQUEST SUCCESS\"}",
					MediaTypeRegistry.APPLICATION_JSON);

			JSONObject json = new JSONObject(exchange.getRequestText());
			String data = json.get("SpeedCount").toString();

			BufferedWriter bw = null;

			try {
				bw = new BufferedWriter(new FileWriter(new File("SpeedCount.txt")));
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
