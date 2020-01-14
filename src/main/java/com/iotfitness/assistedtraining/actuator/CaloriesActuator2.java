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

@Component
public class CaloriesActuator2 {

	public CaloriesActuator2() {

		CoapServer server = new CoapServer(5695);

		server.add(new SetCaloriesCount2());

		server.start();

	}

	public static class SetCaloriesCount2 extends CoapResource {
		public SetCaloriesCount2() {

			super("setCaloriesCount2");

			getAttributes().setTitle("Set Cycling Calories Count");
		}

		@Override
		public void handlePOST(CoapExchange exchange) {

			exchange.respond(ResponseCode.CONTENT, "{\"message\":\"POST_REQUEST_SUCCESS\"}",
					MediaTypeRegistry.APPLICATION_JSON);

			JSONObject json = new JSONObject(exchange.getRequestText());
			String data = json.get("CaloriesCount2").toString();

			BufferedWriter bw = null;

			try {
				bw = new BufferedWriter(new FileWriter(new File("Calories.txt")));
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
