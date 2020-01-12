package com.iotfitness.assistedtraining.sensor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.springframework.stereotype.Component;

@Component
public class CaloriesCountSensor2 {

	public CaloriesCountSensor2() {

		CoapServer server = new CoapServer(5687);

		server.add(new GetCaloriesCount2());

		server.start();

	}

	public static class GetCaloriesCount2 extends CoapResource {
		public GetCaloriesCount2() {

			super("getCaloriesCount2");

			getAttributes().setTitle("Get Cycling Calories Count");
		}

		@Override
		public void handleGET(CoapExchange exchange) {

			StringBuilder Calories = new StringBuilder();
			BufferedWriter bw = null;
			BufferedReader br = null;

			if (!(new File("Calories.txt")).exists()) {
				try {
					bw = new BufferedWriter(new FileWriter(new File("Calories.txt")));
					bw.write("0");
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

			try {
				br = Files.newBufferedReader(Paths.get("Calories.txt").toAbsolutePath());

				String line;
				while ((line = br.readLine()) != null) {
					Calories.append(line);
				}
			}

			catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}

			finally {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			exchange.respond(ResponseCode.CONTENT, "{\"CaloriesCount2\":" + Calories + "}",
					MediaTypeRegistry.APPLICATION_JSON);

		}
	}
}
