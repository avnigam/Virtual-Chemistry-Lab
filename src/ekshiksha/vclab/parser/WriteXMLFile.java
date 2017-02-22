package ekshiksha.vclab.parser;
/**
 * @author aviral
 */
import ekshiksha.vclab.activity.Activities;
import ekshiksha.vclab.activity.Activity;
import ekshiksha.vclab.equipment.Equipment;
import ekshiksha.vclab.equipment.Equipments;
import ekshiksha.vclab.evaluation.Evaluation;
import ekshiksha.vclab.header.Header;
import ekshiksha.vclab.observation.Observation;
import java.io.File;
import java.util.ArrayList;
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

public class WriteXMLFile {
    int type =1 ;
    Header header = new Header();
    ArrayList<Equipment> equipments = new ArrayList<Equipment>();
    ArrayList<Activity> activities = new ArrayList<Activity>();
    Observation observation = new Observation();
    Evaluation evaluation = new Evaluation();
    File file;

    /**
     * Constructor for Store to write XML.
     * @param header : object of Header class.
     * @param setup : Array list of equipment class.
     * @param type : Array list of activity class.
     * @param file : XML file to be written.
     */
    public WriteXMLFile(Header header, ArrayList<Equipment> setup,int type,File file){
        this.header=header;
        this.equipments = setup;
        this.type = type;
        this.file=file;
        this.writer();
    }

    /**
     * Constructor for Store to write XML.
     * @param header : object of Header class.
     * @param setup : Array list of equipment class.
     * @param perform : Array list of activity class.
     * @param observation : Object of Observation class.
     * @param evaluation : Object of Evaluation class.
     * @param file : XML file to be written.
     */
    public WriteXMLFile(Header header, ArrayList<Equipment> setup, ArrayList<Activity> perform, Observation observation,Evaluation evaluation,File file){
        this.header = header;
        this.equipments=setup;
        this.activities=perform;
        this.observation=observation;
        this.evaluation=evaluation;
        this.file=file;
        this.writer();
        type=1;
    }

    /**
     * Extracting data from the objects and writing them into XML file in appropriate tags. 
     */
    private void writer() {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("Specification");
            rootElement.setAttribute("SpecificationAuthor","Aviral Nigam");
            rootElement.setAttribute("Title","Virtual Chemistry Lab");
            doc.appendChild(rootElement);

            Element staff = doc.createElement("Header");
            rootElement.appendChild(staff);

            Element title = doc.createElement("Title");
            title.appendChild(doc.createTextNode(header.getTitle()));
            staff.appendChild(title);

            Element author = doc.createElement("Author");
            author.appendChild(doc.createTextNode(header.getAuthor_Name()));
            staff.appendChild(author);

            Element level = doc.createElement("Level");
            level.appendChild(doc.createTextNode(header.getLevel()));
            staff.appendChild(level);

            Element marks = doc.createElement("Marks");
            marks.appendChild(doc.createTextNode(Integer.toString(header.getMarks())));
            staff.appendChild(marks);

            Element desc = doc.createElement("Description");
            desc.appendChild(doc.createTextNode(header.getDescription()));
            staff.appendChild(desc);

            Element stud = doc.createElement("Student");
            stud.appendChild(doc.createTextNode(header.getStudent_Name()));
            staff.appendChild(stud);

            Element inst = doc.createElement("Instruction");
            inst.appendChild(doc.createTextNode(header.getInstruction()));
            staff.appendChild(inst);


            Element set = doc.createElement("Setup");
            rootElement.appendChild(set);
            if(type!=0)
                equipments = Equipments.getEquipments();

            for (int i = 0; i < equipments.size(); i++) {
                Element equip = doc.createElement("Equipment");
                set.appendChild(equip);

                if (!"".equals(Integer.toString(equipments.get(i).getId()))) {
                    equip.setAttribute("Id", Integer.toString(equipments.get(i).id));
                }
                if (!"".equals(equipments.get(i).getType())) {
                    equip.setAttribute("Type", equipments.get(i).getType());
                }
                if (!"".equals(Integer.toString(equipments.get(i).getSize()))) {
                    equip.setAttribute("Size", Integer.toString(equipments.get(i).getSize()));
                }
                if (!"".equals(equipments.get(i).getContents())) {
                    equip.setAttribute("Contents", equipments.get(i).getContents());
                }
                if (!"".equals(Integer.toString(equipments.get(i).getContentColor().getRed()))) {
                    equip.setAttribute("ColorR", Integer.toString(equipments.get(i).getContentColor().getRed()));
                }
                if (!"".equals(Integer.toString(equipments.get(i).getContentColor().getGreen()))) {
                    equip.setAttribute("ColorG", Integer.toString(equipments.get(i).getContentColor().getGreen()));
                }
                if (!"".equals(Integer.toString(equipments.get(i).getContentColor().getBlue()))) {
                    equip.setAttribute("ColorB", Integer.toString(equipments.get(i).getContentColor().getBlue()));
                }
                if (!"".equals(Float.toString(equipments.get(i).getQuantity()))) {
                    equip.setAttribute("Quantity", Float.toString(equipments.get(i).getQuantity()));
                }
                if (!"".equals(Float.toString(equipments.get(i).getStrength()))) {
                    equip.setAttribute("Strength", Float.toString(equipments.get(i).getStrength()));
                }
                if (!"".equals(Float.toString(equipments.get(i).getX()))) {
                    equip.setAttribute("InitialX", Float.toString(equipments.get(i).getX()));
                }
                if (!"".equals(Float.toString(equipments.get(i).getY()))) {
                    equip.setAttribute("InitialY", Float.toString(equipments.get(i).getY()));
                }
            }

            if (type == 0) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);

                transformer.transform(source, result);

               
            }

            else if(type==1){

                Element per = doc.createElement("Perform");
                rootElement.appendChild(per);
                activities = Activities.getActivities();

                for (int i = 0; i < activities.size(); i++) {
                   
                    Element act = doc.createElement("Activity");
                    per.appendChild(act);

                    if (!activities.get(i).getType().equals("")) {
                        act.setAttribute("Type", activities.get(i).getType());
                    }
                    if(activities.get(i).getType().equals("Move")){
                        if (activities.get(i).getId()!=0) {
                            act.setAttribute("Id", Integer.toString(activities.get(i).getId()));
                        }
                        if (activities.get(i).getFromDest().x!=0.0f){
                            act.setAttribute("InitialX", Float.toString(activities.get(i).getFromDest().x));
                        }
                        if (activities.get(i).getFromDest().y!=0.0f){
                            act.setAttribute("InitialY", Float.toString(activities.get(i).getFromDest().y));
                        }
                        if (activities.get(i).getToDest().x!=0.0f){
                            act.setAttribute("FinalX", Float.toString(activities.get(i).getToDest().x));
                        }
                        if (activities.get(i).getToDest().y!=0.0f){
                            act.setAttribute("FinalY", Float.toString(activities.get(i).getToDest().y));
                        }
                    }
                    if(activities.get(i).getType().equals("Pour")){
                        if(activities.get(i).getSourceId()!=0){
                            act.setAttribute("SourceId", Integer.toString(activities.get(i).getSourceId()));
                        }
                        if (activities.get(i).getDestinationId() != 0) {
                            act.setAttribute("DestinationId", Integer.toString(activities.get(i).getDestinationId()));
                        }
                        if (activities.get(i).getQuantity() != 0.0f) {
                            act.setAttribute("Quantity", Float.toString(activities.get(i).getQuantity()));
                        }
                        if (activities.get(i).getFromDest().x!=0.0f){
                            act.setAttribute("InitialX", Float.toString(activities.get(i).getFromDest().x));
                        }
                        if (activities.get(i).getFromDest().y!=0.0f){
                            act.setAttribute("InitialY", Float.toString(activities.get(i).getFromDest().y));
                        }
                        if (!activities.get(i).getContents().equals("")) {
                            act.setAttribute("Content", activities.get(i).getContents());
                        }
                    }
                    if(activities.get(i).getType().equals("Wash")){
                        if (activities.get(i).getId()!=0) {
                            act.setAttribute("Id", Integer.toString(activities.get(i).getId()));
                        }
                        if (activities.get(i).getFromDest().x!=0.0f){
                            act.setAttribute("InitialX", Float.toString(activities.get(i).getFromDest().x));
                        }
                        if (activities.get(i).getFromDest().y!=0.0f){
                            act.setAttribute("InitialY", Float.toString(activities.get(i).getFromDest().y));
                        }
                        if (activities.get(i).getQuantity() != 0.0f) {
                            act.setAttribute("Quantity", Float.toString(activities.get(i).getQuantity()));
                        }
                        
                    }
                    if(activities.get(i).getType().equals("Burn")){
                        if (activities.get(i).getId()!=0) {
                            act.setAttribute("Id", Integer.toString(activities.get(i).getId()));
                        }
                        if (activities.get(i).getDestinationId() != 0) {
                            act.setAttribute("DestinationId", Integer.toString(activities.get(i).getDestinationId()));
                        }
                        if (activities.get(i).getFromDest().x!=0.0f){
                            act.setAttribute("InitialX", Float.toString(activities.get(i).getFromDest().x));
                        }
                        if (activities.get(i).getFromDest().y!=0.0f){
                            act.setAttribute("InitialY", Float.toString(activities.get(i).getFromDest().y));
                        }
                        if (activities.get(i).getCur_temp()!=0.0f){
                        act.setAttribute("InitialTemp", Float.toString(activities.get(i).getCur_temp()));
                        }
                        if (activities.get(i).getFinal_temp()!=0.0f){
                            act.setAttribute("Finaltemp", Float.toString(activities.get(i).getFinal_temp()));
                        }
                    }

                    }



 
                /*Element obs = doc.createElement("Observation");
                rootElement.appendChild(obs);

                String remark[] = new String[10];
                remark=observation.getRemarks();
                int i=0;
                for (i = 1; i < observation.getRemarks().length; i++) {
                    Element rem = doc.createElement("Remarks");
                    rem.appendChild(doc.createTextNode(remark[i]));
                    obs.appendChild(rem);
                }


                Element eval = doc.createElement("Evaluation");
                rootElement.appendChild(eval);

                i = 0;
                Element mark = doc.createElement("Marks");
                mark.appendChild(doc.createTextNode(Integer.toString(evaluation.getMarks())));
                eval.appendChild(mark);

                String remarks[] = new String[10];
                remarks=evaluation.getRemarks();

                for (i = 1; i < evaluation.getRemarks().length; i++) {
                    Element rem = doc.createElement("Remarks");
                    rem.appendChild(doc.createTextNode(remarks[i]));
                    eval.appendChild(rem);
                }
                */
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(file);

                transformer.transform(source, result);

                
            }

        } catch (ParserConfigurationException pce) {
        } catch (TransformerException tfe) {
        }
    }
}