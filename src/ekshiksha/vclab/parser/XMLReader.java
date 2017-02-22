package ekshiksha.vclab.parser;

/**
 * @author aviral
 */
import ekshiksha.vclab.activity.*;
import ekshiksha.vclab.equipment.*;
import ekshiksha.vclab.evaluation.Evaluation;
import ekshiksha.vclab.header.Header;
import ekshiksha.vclab.lab.Cupboard;
import ekshiksha.vclab.lab.WorkBench;
import ekshiksha.vclab.observation.Observation;
import ekshiksha.vclab.util.ErrorCode;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLReader {

    WorkBench environ;
    Cupboard cup;
    File file;
    String validator = "XMLSchemaDefinition.xsd"; //name of XML schema used for validator
    int type; //For differentiating between cupboard and workbench
    ArrayList<Equipment> storeequip; // Temporary array list for store
    public static ArrayList Warnings = new ArrayList(); //array list to store warnings
    public static ArrayList ErrorList = new ArrayList(); //array list to store errors
    int Errorcode = ErrorCode.SUCCESS;  //initial error code is success
    ArrayList idlist = new ArrayList(); //arraylist to find duplicate id of equipment
    public Header head = null; //header object for storing header details
    Observation obs = null;
    Evaluation evaluate=null;    
    
    /**
     * Constructor for XML file called from workbench.
     * @param e //object of workbench
     * @param file //file object of file to be opened
     */
    public XMLReader(WorkBench e,File file) {
        removeAll();
        this.file = file;
        environ = e;
        type=1;
        XMLSchemaValidator.validation(file,validator,1); //Calling Schema validator for verifying the correctness of Xml opened 
        this.read();
    }
    
    /**
     * Constructor for XML file called from cupboard.
     * @param storeequip //array list for storing objects of 
     * @param file 
     */
    public XMLReader(Cupboard c,ArrayList<Equipment> storeequip,File file){
        removeAll();
        this.storeequip=storeequip;
        this.file = file;
        this.cup = c;
        XMLSchemaValidator.validation(file,validator,1); //Calling Schema validator for verifying the correctness of Xml opened
        type=0;
        this.read();
    }
    
    /**
     * Parsing the XML file, Creating the objects of equipments and adding them to array list.
     */
    private void read() {

        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList header = doc.getElementsByTagName("Header");            
            
            for (int s = 0; s < header.getLength(); s++) {

                Node fstNode = header.item(s);

                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element zroElmnt = (Element) fstNode;

                    NodeList fstNmElmntLst = zroElmnt.getElementsByTagName("Title");
                    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                    NodeList fstNm = fstNmElmnt.getChildNodes();
                    String title = ((Node) fstNm.item(0)).getNodeValue();
                    
                    NodeList sndNmElmntLst = zroElmnt.getElementsByTagName("Author");
                    Element sndNmElmnt = (Element) sndNmElmntLst.item(0);
                    NodeList sndNm = sndNmElmnt.getChildNodes();
                    String author = ((Node) sndNm.item(0)).getNodeValue();
                    
                    NodeList thdNmElmntLst = zroElmnt.getElementsByTagName("Level");
                    Element thdNmElmnt = (Element) thdNmElmntLst.item(0);
                    NodeList thdNm = thdNmElmnt.getChildNodes();
                    String level = ((Node) thdNm.item(0)).getNodeValue();
                    
                    NodeList frtNmElmntLst = zroElmnt.getElementsByTagName("Marks");
                    Element frtNmElmnt = (Element) frtNmElmntLst.item(0);
                    NodeList frtNm = frtNmElmnt.getChildNodes();
                    int marks = Integer.parseInt(((Node) frtNm.item(0)).getNodeValue());
                    
                    NodeList fftNmElmntLst = zroElmnt.getElementsByTagName("Description");
                    Element fftNmElmnt = (Element) fftNmElmntLst.item(0);
                    NodeList fftNm = fftNmElmnt.getChildNodes();
                    String description = ((Node) fftNm.item(0)).getNodeValue();
                    
                    NodeList sixNmElmntLst = zroElmnt.getElementsByTagName("Student");
                    Element sixNmElmnt = (Element) sixNmElmntLst.item(0);
                    NodeList sixNm = sixNmElmnt.getChildNodes();
                    String student = ((Node) sixNm.item(0)).getNodeValue();
                    
                    NodeList svnNmElmntLst = zroElmnt.getElementsByTagName("Instruction");
                    Element svnNmElmnt = (Element) svnNmElmntLst.item(0);
                    NodeList svnNm = svnNmElmnt.getChildNodes();
                    String instruction = ((Node) svnNm.item(0)).getNodeValue();
             
                    head = new Header(title, author, level, marks, description, student, instruction);
                   
                    }               
            }

            
            NodeList setup = doc.getElementsByTagName("Setup");
            
            for (int s = 0; s < setup.getLength(); s++) {
                Node fstNode = setup.item(s);
                Equipment equip = null;
                Element zroElmnt = (Element) fstNode;
                NodeList nList = zroElmnt.getElementsByTagName("Equipment");
                int id;
                Float strength;
                Float quantity;
                int colorR = 0;
                int colorG = 0;
                int colorB = 0;

               
                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element fstNmElmnt = (Element) nNode;

                            String tempid = fstNmElmnt.getAttribute("Id");
                            String type = fstNmElmnt.getAttribute("Type");
                            int size = Integer.parseInt(fstNmElmnt.getAttribute("Size"));
                            String content = fstNmElmnt.getAttribute("Contents");
                            String tempcolorR = fstNmElmnt.getAttribute("ColorR");
                            String tempcolorG = fstNmElmnt.getAttribute("ColorG");
                            String tempcolorB = fstNmElmnt.getAttribute("ColorB");
                            String tempquantity = fstNmElmnt.getAttribute("Quantity");
                            String tempstrength = fstNmElmnt.getAttribute("Strength");
                            float initX = Float.parseFloat(fstNmElmnt.getAttribute("InitialX"));
                            float initY = Float.parseFloat(fstNmElmnt.getAttribute("InitialY"));


                            id = Integer.parseInt(tempid);
                            if(idlist.contains(id)){
                                Errorcode = ErrorCode.ERROR_FOUND;
                                ErrorList.add("Duplicate id found in attribute of "+ fstNmElmnt.getNodeName());
                            }
                            else{
                                idlist.add(id);
                            }
                            
                            if(!type.equals("Bottle") && !type.equals("Beaker") && !type.equals("Flask") && !type.equals("Pipette") && !type.equals("Burette") && !type.equals("Burner") && !type.equals("TestTube"))
                            {
                                Errorcode = ErrorCode.ERROR_FOUND;
                                ErrorList.add("Type attribute of "+ fstNmElmnt.getNodeName() +  "of id = "+tempid+" contains unknown type value");
                            }

                            if(size>5){
                                Errorcode = ErrorCode.ERROR_FOUND;
                                ErrorList.add("Size attribute of "+ fstNmElmnt.getNodeName() + "of id = "+tempid+ " contains unknown size value" );
                            }

                            if(tempstrength.equals("")){
                                Errorcode = ErrorCode.WARNINGS_FOUND;
                                Warnings.add("Strength attribute of "+ fstNmElmnt.getNodeName() + "of id = "+tempid+ " is missing");
                            }
                            strength = Float.parseFloat(tempstrength);

                            if(tempquantity.equals("")){
                                Errorcode = ErrorCode.WARNINGS_FOUND;
                                Warnings.add("Quantity attribute of "+ fstNmElmnt.getNodeName() + "of id = "+tempid+ " is missing ");
                            }
                            quantity = Float.parseFloat(tempquantity);
                                

                            if(tempcolorR.equals("") || tempcolorG.equals("") || tempcolorB.equals("")){
                                Errorcode = ErrorCode.WARNINGS_FOUND;
                                Warnings.add("Color attribute of "+ fstNmElmnt.getNodeName() + "of id = "+tempid+" is missing");
                            }
                            else{
                                colorR = Integer.parseInt(fstNmElmnt.getAttribute("ColorR"));
                                colorG = Integer.parseInt(fstNmElmnt.getAttribute("ColorG"));
                                colorB = Integer.parseInt(fstNmElmnt.getAttribute("ColorB"));

                                if( (colorR > 255) || (colorG > 255) ||(colorB > 255) )
                                {
                                    Errorcode = ErrorCode.ERROR_FOUND;
                                    ErrorList.add("Color attribute of "+ fstNmElmnt.getNodeName() +  "of id = "+tempid+" contains unknown color value " );
                                }
                            }

                            Point2D.Float origin = new Point2D.Float(initX, initY);
                            Color color = new Color(colorR, colorG, colorB);
                            
                            if(this.type==0)
                            {
                                origin.x*=cup.getWidth();
                                origin.y*=cup.getHeight();
                            }
                            
                            if (type.equals("Pipette")) {     
                                if(this.type==1)
                                    equip = new Pipette(id, type, origin, quantity, size, content, color, strength, environ);
                                else
                                { equip = new Pipette( origin , cup);
                                  equip.setId(id); equip.setOriginShift(origin); equip.setSize(size);equip.setContents(content); equip.setStrength(strength); equip.setContentColor(color);
                                
                                }
                            } else if (type.equals("Burette")) {
                                if(this.type==1)
                                    equip = new Burette(id, type, origin, quantity, size, content, color, strength, environ);
                                else
                                { equip = new Burette( origin , cup);
                                  equip.setId(id); equip.setOriginShift(origin); equip.setSize(size);equip.setContents(content); equip.setStrength(strength); equip.setContentColor(color);
                                
                                }
                            } else if (type.equals("Burner")) {
                                if(this.type==1)
                                    equip = new Burner(id, type, origin, quantity, size, content, color, strength, environ);
                                else
                                { equip = new Burner( origin , cup);
                                  equip.setId(id); equip.setOriginShift(origin); equip.setSize(size);equip.setContents(content); equip.setStrength(strength); equip.setContentColor(color);
                                
                                }
                            } else if (type.equals("Bottle")) {
                                if(this.type==1)
                                    equip = new Bottle(id, type, origin, quantity, size, content, color, strength, environ);
                                else
                                { equip = new Bottle( origin , cup);
                                  equip.setId(id); equip.setOriginShift(origin); equip.setSize(size);equip.setContents(content); equip.setStrength(strength); equip.setContentColor(color);
                                
                                }
                            } else if (type.equals("Beaker")) {
                                if(this.type==1)
                                    equip= new Beaker(id, type, origin, quantity, size, content, color, strength, environ);
                                else
                                { equip = new Beaker( origin , cup);
                                  equip.setId(id); equip.setOriginShift(origin); equip.setSize(size);equip.setContents(content); equip.setStrength(strength); equip.setContentColor(color);
                                
                                }
                            }else if (type.equals("Flask")) {
                                if(this.type==1)
                                    equip = new Flask(id, type, origin, quantity, size, content, color, strength, environ);
                                
                               else
                                { equip = new Flask( origin , cup);
                                  equip.setId(id); equip.setOriginShift(origin); equip.setSize(size);equip.setContents(content); equip.setStrength(strength); equip.setContentColor(color);
                                
                                }
                            } else if (type.equals("TestTube")) {
                                if(this.type==1)
                                    equip= new TestTube(id,type,origin,quantity,size,content,color,strength,environ);
                                else
                                { equip = new TestTube( origin , cup);
                                  equip.setId(id); equip.setOriginShift(origin); equip.setSize(size);equip.setContents(content); equip.setStrength(strength); equip.setContentColor(color);
                                
                                }
                            }
                            
                           
                            if(this.type==0)
                                storeequip.add(equip);
                            else
                                Equipments.add(equip);
                        }                    
                    }                    
            }
            
            

            NodeList perform = doc.getElementsByTagName("Perform");
           
            
            for (int s = 0; s < perform.getLength(); s++) {
                Node fstNode = perform.item(s);
                Activity act = null;

                Element zroElmnt = (Element) fstNode;
                NodeList nList = zroElmnt.getElementsByTagName("Activity");

                for (int temp = 0; temp < nList.getLength(); temp++) {
                   
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element fstNmElmnt = (Element) nNode;

                        String type = fstNmElmnt.getAttribute("Type");
                        int id = 0 ;
                        int sid = 0 ;
                        int did = 0 ;
                        float quantity ;
                        String content ;
                        float initX ;
                        float initY ;
                        float finalX ;
                        float finalY;
                        float inittemp ;
                        float finaltemp;
                       
  
                        if (type.equals("Move")){
                            id = Integer.parseInt(fstNmElmnt.getAttribute("Id"));
                            initX = Float.parseFloat(fstNmElmnt.getAttribute("InitialX"));
                            initY = Float.parseFloat(fstNmElmnt.getAttribute("InitialY"));
                            finalX = Float.parseFloat(fstNmElmnt.getAttribute("FinalX"));
                            finalY = Float.parseFloat(fstNmElmnt.getAttribute("FinalY"));
                            act = new Move(id, new Point2D.Float(initX, initY), new Point2D.Float(finalX, finalY));
                        }

                        if (type.equals("Pour")){
                            sid = Integer.parseInt(fstNmElmnt.getAttribute("SourceId"));
                            did = Integer.parseInt(fstNmElmnt.getAttribute("DestinationId"));
                            quantity = Float.parseFloat(fstNmElmnt.getAttribute("Quantity"));
                            content = fstNmElmnt.getAttribute("Content");
                            initX = Float.parseFloat(fstNmElmnt.getAttribute("InitialX"));
                            initY = Float.parseFloat(fstNmElmnt.getAttribute("InitialY"));                       
                            act = new Pour(sid, did,quantity,new Point2D.Float(initX, initY), content);
                        }
                        
                        
                        if (type.equals("Wash")){
                           id = Integer.parseInt(fstNmElmnt.getAttribute("Id"));
                           initX = Float.parseFloat(fstNmElmnt.getAttribute("InitialX"));
                           initY = Float.parseFloat(fstNmElmnt.getAttribute("InitialY"));
                           quantity=Float.parseFloat(fstNmElmnt.getAttribute("Quantity"));
                           act = new Wash(id, new Point2D.Float(initX, initY),quantity);
                        }

                        if (type.equals("Burn")){
                            //act = new Burn(id, did, new Point2D.Float(initX, initY), inittemp, finaltemp);
                        }

                        Activities.add(act);
                    }
                }
            }
            

            NodeList obser = doc.getElementsByTagName("Observation");
            
            for (int s = 0; s < obser.getLength(); s++) {
                Node fstNode = obser.item(s);
                String rem[] = new String[10];

                Element zroElmnt = (Element) fstNode;
                NodeList nodeList = zroElmnt.getElementsByTagName("Remarks");

                    for (int i = 0; i < nodeList.getLength(); i++) {

                        Node nNode = nodeList.item(i);
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element fstNmElmnt = (Element) nNode;
                            NodeList fstNm = fstNmElmnt.getChildNodes();
                            rem[i] = ((Node) fstNm.item(s)).getNodeValue();
                        }
                    }
                obs = new Observation(rem);
            }

            
            NodeList eval = doc.getElementsByTagName("Evaluation");
            
            for (int s = 0; s < eval.getLength(); s++) {
                Node fstNode = eval.item(s);
                String rem[] = new String[10];
                int mark = 0;
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element zroElmnt = (Element) fstNode;
                   
                    NodeList fstNmElmntLst = zroElmnt.getElementsByTagName("Marks");
                    Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                    NodeList fstNm = fstNmElmnt.getChildNodes();
                    mark = Integer.parseInt(((Node) fstNm.item(0)).getNodeValue());

                    NodeList nodeList = zroElmnt.getElementsByTagName("Remarks");
                    
                    for (int i = 0; i < nodeList.getLength(); i++) {
                            Node nNode = nodeList.item(i);
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element fstNmElmnt1 = (Element) nNode;
                                NodeList fstNm1 = fstNmElmnt1.getChildNodes();
                                rem[i] = ((Node) fstNm1.item(s)).getNodeValue();
                            }
                    }
                }
                evaluate = new Evaluation(mark, rem);
            }
            
            if(Errorcode == ErrorCode.ERROR_FOUND || Errorcode == ErrorCode.WARNINGS_FOUND)
                showError();
        } 
        catch (Exception e) {
        }
    }

    /**
     * For showing the error.
     */
    private void showError() {

    }
    
    /**
     * To return the object of the header class.
     * @return head: object of header class. 
     */
    public Header getHead() {
        return head;
    }
    
    public Evaluation getEvaluation() {
        return evaluate;
    }

    public Observation getObservation() {
        return obs;
    }
    
    /**
     * Function to return the array list for store.
     * @return storeequip : array list for store.
     */
    public ArrayList<Equipment> getEquipmentList()
    {
        return storeequip;
    }
    
    public void removeAll()
    {
        while(ErrorList.size()>0)ErrorList.remove(0);
    }
}