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
public class RepCountSensor {

	public RepCountSensor() {

		CoapServer server = new CoapServer(5685);

		server.add(new GetRepCount());

		server.start();

	}

	public static class GetRepCount extends CoapResource {
		public GetRepCount() {

			super("getRepCount");

			getAttributes().setTitle("Get Rep Count");
		}

		@Override
		public void handleGET(CoapExchange exchange) {

			StringBuilder repCount = new StringBuilder();
			BufferedWriter bw = null;
			BufferedReader br = null;

			if (!(new File("RepCount.txt")).exists()) {
				try {
					bw = new BufferedWriter(new FileWriter(new File("RepCount.txt")));
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
				br = Files.newBufferedReader(Paths.get("RepCount.txt").toAbsolutePath());

				String line;
				while ((line = br.readLine()) != null) {
					repCount.append(line);
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

			exchange.respond(ResponseCode.CONTENT, "{\"RepCount\":" + repCount + "}",
					MediaTypeRegistry.APPLICATION_JSON);
		}
	}

}
