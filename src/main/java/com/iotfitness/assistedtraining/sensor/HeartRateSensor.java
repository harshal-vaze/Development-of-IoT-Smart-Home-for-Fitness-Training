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
public class HeartRateSensor {

	public HeartRateSensor() {

		CoapServer server = new CoapServer(5687);

		server.add(new GetHeartRate());

		server.start();

	}

	public static class GetHeartRate extends CoapResource {
		public GetHeartRate() {

			super("getHeartRate");

			getAttributes().setTitle("Get Heart Rate");
		}

		@Override
		public void handleGET(CoapExchange exchange) {

			StringBuilder HeartRate = new StringBuilder();
			BufferedWriter bw = null;
			BufferedReader br = null;

			if (!(new File("HeartRate.txt")).exists()) {
				try {
					bw = new BufferedWriter(new FileWriter(new File("HeartRate.txt")));
					bw.write("100");
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
				br = Files.newBufferedReader(Paths.get("HeartRate.txt").toAbsolutePath());

				String line;
				while ((line = br.readLine()) != null) {
					HeartRate.append(line);
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

			exchange.respond(ResponseCode.CONTENT, "{\"HeartRate\":" + HeartRate + "}",
					MediaTypeRegistry.APPLICATION_JSON);

		}
	}
}
