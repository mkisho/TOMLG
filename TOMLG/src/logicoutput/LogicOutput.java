package logicoutput;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import doormax.DOORMax;
import tomlg.Agent;
import tomlg.Mind;

public class LogicOutput {
	private final GsonBuilder gsonBuilder;
	private final Gson gson;
	private int step;
	private String fileName;
	private Writer writer;
	private JsonWriter jsonWriter;

	public LogicOutput(String fileName) {
		this.gsonBuilder = new GsonBuilder();
		
		this.fileName = fileName;
		this.gsonBuilder.registerTypeAdapter(Agent.class, new AgentAdapter());
		this.gsonBuilder.registerTypeAdapter(DOORMax.class, new DoormaxAdapter());
		this.gsonBuilder.registerTypeAdapter(Mind.class, new MindAdapter());

		this.gsonBuilder.setPrettyPrinting();
		this.gson = gsonBuilder.create();
		this.step = 0;
		initialize();
	}

	private void initialize() {
		try {
			this.writer = new FileWriter(fileName);
			this.jsonWriter = gson.newJsonWriter(this.writer);
			jsonWriter.beginObject();
			this.jsonWriter.name("Minds");
			this.jsonWriter.beginArray();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void output(Agent agent) throws IOException {
		step++;
//		System.out.println("\n\n\n");
//		System.out.println(gson.toJson(agent));
//		this.jsonWriter.name("Mind");
		new AgentAdapter().write(this.jsonWriter, agent);
//		System.out.println("\n\n\n");
	}

	public void finalize() {
		try {
			this.jsonWriter.endArray();
			this.jsonWriter.endObject();
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
