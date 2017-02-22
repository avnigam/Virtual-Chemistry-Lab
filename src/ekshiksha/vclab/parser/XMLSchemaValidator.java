package ekshiksha.vclab.parser;

/**
 * @author aviral
 */
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLSchemaValidator {
 
    static final String JAXP_SCHEMA_LANGUAGE
            = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    static final String W3C_XML_SCHEMA 
            = "http://www.w3.org/2001/XMLSchema";
    static final String JAXP_SCHEMA_SOURCE
            = "http://java.sun.com/xml/jaxp/properties/schemaSource";
    
    /**
     * Function for validating the Xml file and storing the errors.
     * @param XMLFile : XML File to be validated.
     * @param XMLValid : Name of the XML Schema Validator File.
     * @param type : To choose the array list for storing the errors.
     */
    public static void validation(File XMLFile,String XMLValid,int type){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);   	
        
        factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
        // Set the schema file
        factory.setAttribute(JAXP_SCHEMA_SOURCE, XMLValid);  
        try {
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(new SimpleErrorHandler(type));

		// Parse the file. If errors found, they will be printed.
                builder.parse(XMLFile);
                	            
        } 
	catch (Exception e) {
            e.printStackTrace();
        }
    }
} 

