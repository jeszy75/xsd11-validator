package hu.unideb.inf.validator;

import java.io.PrintWriter;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import org.xml.sax.ErrorHandler;

public class SimpleErrorHandler implements ErrorHandler {

	private PrintWriter out;
	private boolean failOnError = false;

	public SimpleErrorHandler() {
		out = new PrintWriter(System.err, true);
	}

	public SimpleErrorHandler(boolean failOnError) {
		this();
		this.failOnError = failOnError;
	}

	public SimpleErrorHandler(PrintWriter out) {
		this.out = out;
	}

	public void warning(SAXParseException e) throws SAXException {
		printException("Warning", e);
	}

	public void error(SAXParseException e) throws SAXException {
		printException("Error", e);
		if (failOnError) throw e;
	}

	public void fatalError(SAXParseException e) throws SAXException {
		printException("Fatal error", e);
		throw e;
	}

	private void printException(String prefix, SAXParseException e) throws SAXException {
		out.format("[%s] %s:%d:%d: %s%n", prefix, e.getSystemId(), e.getLineNumber(), e.getColumnNumber(), e.getMessage());
	}

}
