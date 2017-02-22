package ekshiksha.vclab.parser;

/**
 *
 * @author aviral
 */
import ekshiksha.vclab.lab.BottleChemical;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StoreXml 
{
    
    public static ArrayList Warnings = new ArrayList(); //array list to store warnings
    public static ArrayList ErrorList = new ArrayList();
    public LinkedList<BottleChemical> chemicals ;
    
    File file = new File("Chemicals.xml");
    String validator  = "XMLModSchema.xsd";
        
    public StoreXml()
    {
        chemicals = new LinkedList<BottleChemical>();
        XMLSchemaValidator.validation(file,validator,0);
        this.showError();
        this.read();
    }

    /**
     * Parsing the XML file, Creating the objects of equipments and adding them to array list.
     */
    private void read() 
    {

	try {
	
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
	Document doc = db.parse(file);
	doc.getDocumentElement().normalize();


	NodeList setup = doc.getElementsByTagName("Store");
	for (int s = 0; s < setup.getLength(); s++) {
        	Node fstNode = setup.item(s);

		Element zroElmnt = (Element) fstNode;
		NodeList nList = zroElmnt.getElementsByTagName("Cheminfo");
		
		for (int temp = 0; temp < nList.getLength(); temp++) {

	   		Node nNode = nList.item(temp);
	   		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	        		Element fstNmElmnt = (Element) nNode;
 
				String name = fstNmElmnt.getAttribute("Name");
				String formula = fstNmElmnt.getAttribute("Formula");
				int colorR = Integer.parseInt(fstNmElmnt.getAttribute("ColorR"));
				int colorG = Integer.parseInt(fstNmElmnt.getAttribute("ColorG"));
				int colorB = Integer.parseInt(fstNmElmnt.getAttribute("ColorB"));
				chemicals.add(new BottleChemical(name, formula, colorR, colorG, colorB));
				
			}
		}
	}
}
 
catch (Exception e) {
	e.printStackTrace();
}
}
    /**
     * For showing the error.
     */
    private void showError() {

    }

    /**
     * Function to return the array list for store.
     * @return chemicals : array list for store.
     */
    public LinkedList<BottleChemical> getChemInfo() 
    {
	return chemicals;
    }

}
