package ekshiksha.vclab.parser;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author aviral
 */
public class SimpleErrorHandler implements ErrorHandler {
    int type;
    
    /**
     * 
     * @param type : type of Error List.
     */
    public SimpleErrorHandler(int type){
        this.type = type;
    }
    
    /**
     * Adding errors to list depending on type.
     * @param e : Object of SAXParseException
     * @throws SAXException 
     */
    public void warning(SAXParseException e) throws SAXException {
        if(type == 0)
            StoreXml.Warnings.add(e.getMessage());
        else
            XMLReader.Warnings.add(e.getMessage());
    }

    /**
     * Adding errors to list depending on type.
     * @param e : Object of SAXParseException
     * @throws SAXException 
     */
    public void error(SAXParseException e) throws SAXException {
        if(type == 0)
            StoreXml.ErrorList.add(e.getMessage());
        else
            XMLReader.ErrorList.add(e.getMessage());
    }

    /**
     * Adding errors to list depending on type.
     * @param e : Object of SAXParseException
     * @throws SAXException 
     */    
    public void fatalError(SAXParseException e) throws SAXException {
        if(type == 0)
            StoreXml.ErrorList.add(e.getMessage());
        else
            XMLReader.ErrorList.add(e.getMessage());     
    } 
}