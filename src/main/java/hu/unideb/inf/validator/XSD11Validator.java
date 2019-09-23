package hu.unideb.inf.validator;

import javax.xml.transform.stream.StreamSource;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXParseException;

public class XSD11Validator {

    public static void main(String[] args) {
        if (args.length != 1 && args.length != 2) {
            System.err.printf("java %s [schema] instance%n", XSD11Validator.class.getName());
            System.exit(1);
        }
        System.setProperty("javax.xml.validation.SchemaFactory:http://www.w3.org/XML/XMLSchema/v1.1",
                "org.apache.xerces.jaxp.validation.XMLSchema11Factory");
        try {
            new org.apache.xerces.jaxp.validation.XMLSchema11Factory();
            SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
            sf.setErrorHandler(new SimpleErrorHandler(true));

            Schema schema = args.length == 1 ? sf.newSchema() : sf.newSchema(new StreamSource(args[0]));
            StreamSource instance = new StreamSource(args.length == 1 ? args[0] : args[1]);

            Validator validator = schema.newValidator();
            validator.setErrorHandler(new SimpleErrorHandler(false));
            validator.validate(instance);
        } catch (SAXParseException e) {
            // Already handled by the error handler
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
