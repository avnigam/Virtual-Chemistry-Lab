package ekshiksha.vclab.parser;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import ekshiksha.vclab.lab.BottleChemical;

public class ModifyStoreXML {

    BottleChemical info = new BottleChemical();

    /**
     *
     * @param info : Object of BottleChemical.java
     */
    public ModifyStoreXML(BottleChemical info) {
        this.info = info;
        this.modify();
    }

    /**
     *Appending new chemicals into Chemicals.xml  .
     */
    private void modify() {

        try {
            String filepath = "Chemicals.xml";
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);

            // Get the root element
            Node storesetup = doc.getFirstChild();

            // Get the Store element by tag name directly
            Node store = doc.getElementsByTagName("Store").item(0);

            // append a new node to Store
            Element cheminfo = doc.createElement("Cheminfo");
            cheminfo.setAttribute("Name", info.getName());
            cheminfo.setAttribute("Formula", info.getFormula());
            cheminfo.setAttribute("ColorR", Integer.toString(info.getColorR()));
            cheminfo.setAttribute("ColorG", Integer.toString(info.getColorG()));
            cheminfo.setAttribute("ColorB", Integer.toString(info.getColorB()));
            store.appendChild(cheminfo);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);


        } catch (ParserConfigurationException pce) {
        } catch (TransformerException tfe) {
        } catch (IOException ioe) {
        } catch (SAXException sae) {
        }
    }
}
