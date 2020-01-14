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
public class HeartRateSensor2 {

	public HeartRateSensor2() {

		CoapServer server = new CoapServer(5693);

		server.add(new GetHeartRate2());

		server.start();

	}

	public static class GetHeartRate2 extends CoapResource {
		public GetHeartRate2() {

			super("getHeartRate2");

			getAttributes().setTitle("Get Cycling Heart Rate");
		}

		@Override
		public void handleGET(CoapExchange exchange) {

			StringBuilder HeartRate = new StringBuilder();
			BufferedWriter bw = null;
			BufferedReader br = null;

			if (!(new File("HeartRate2.txt")).exists()) {
				try {
					bw = new BufferedWriter(new FileWriter(new File("HeartRate2.txt")));
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
				br = Files.newBufferedReader(Paths.get("HeartRate2.txt").toAbsolutePath());

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

			exchange.respond(ResponseCode.CONTENT, "{\"HeartRate2\":" + HeartRate + "}",
					MediaTypeRegistry.APPLICATION_JSON);

		}
	}
}
