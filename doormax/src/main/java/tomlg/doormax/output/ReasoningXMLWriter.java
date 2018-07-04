package tomlg.doormax.output;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ReasoningXMLWriter {
	private final String file;
	private final XMLEventWriter eventWriter;
	private final XMLEventFactory eventFactory;
	
	public ReasoningXMLWriter(String file) throws FileNotFoundException, XMLStreamException {
		this.file = file;
	
		 // create an XMLOutputFactory
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        // create XMLEventWriter
        this.eventWriter = outputFactory
                .createXMLEventWriter(new FileOutputStream(this.file));
        // create an EventFactory
        this.eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        // create and write Start Tag
        StartDocument startDocument = eventFactory.createStartDocument();
        eventWriter.add(startDocument);

        
        // create MindHistory open tag
        StartElement mindHistoryStart = eventFactory.createStartElement("",
                "", "MindHistory");
        eventWriter.add(mindHistoryStart);
        eventWriter.add(end);
	}
	
	public void writeReasoningStep(ReasoningMind mind) {
	    // Write the different nodes
//		 StartElement mindHistoryStart = eventFactory.createStartElement("",
//	                "", "MindHistory");
	}
	
	public void finish() {
		try {
	        XMLEvent end = eventFactory.createDTD("\n");
	        this.eventWriter.add(eventFactory.createEndElement("", "", "MindHistory"));
	        this.eventWriter.add(end);
	        this.eventWriter.add(eventFactory.createEndDocument());
	        this.eventWriter.close();
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
