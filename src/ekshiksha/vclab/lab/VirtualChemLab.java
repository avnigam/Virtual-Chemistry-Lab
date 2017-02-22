/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekshiksha.vclab.lab;

import blackboard.Blackboard;
import ekshiksha.vclab.activity.Activities;
import ekshiksha.vclab.activity.Activity;
import ekshiksha.vclab.activity.Move;
import ekshiksha.vclab.equipment.*;
import ekshiksha.vclab.header.Header;
import ekshiksha.vclab.parser.ModifyStoreXML;
import ekshiksha.vclab.parser.XMLReader;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import ekshiksha.vclab.parser.StoreXml;
import ekshiksha.vclab.parser.WriteXMLFile;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @authors mayur12,harsh12
 */

/**
 * The VirtualChemLab class is the main class of the project . 
 * It controls all the modes viz. store mode, perform mode, and play mode, 
 * as well as 
 */

public class VirtualChemLab extends JApplet implements MouseListener, MouseMotionListener, ActionListener, ComponentListener {

    
    private int topLeftX;                   /*
     * X coordinate of Top Left Corner
     */
    private int topLeftY;                   /*
     * X coordinate of Top Left Corner
     */
    private int displayWidth;               /*
     * Width of applet
     */
    private int blackboardHeight;           /*
     * Height of blackboard
     */
    private int panelHeight;
    private Blackboard bb;                  /*
     * Black board to interact with student
     */
    private ControlPanel controls;          /*
     * Panel at bottom to control applet
     */
    private final static int titleMode = 1;           /*
     * demo mode is initialized for the value of bbmode is 1
     */
     private Teacher teacher;
     private boolean firstTime;
     int blackboardFlag = 0;
     int bbmode = 1;
   
    XMLReader XMLreader;
    private Header head;
    private WriteXMLFile writeXMLFile;
    private ModifyStoreXML updateChemicalList;
    
    private WorkBench environ;
    JPanel p = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel bottomPane = new JPanel();
    private Cupboard environ3; // cupboard
    private Cupboard environ2;  // workbench
    JPanel p1 = new JPanel();
    JPanel p3 = new JPanel();
    public StopWatchRunner watch1;
    JPanel p2 = new JPanel();
    
    
    int storeMode = 1;
    int performMode = 2;
    int playMode = 3;
    int mode = performMode;
    
    JButton board;
    Dimension qw;
    Robot robot;                // to move mouse cursor for smooth movement of apparatus from store to workbench
    Equipment holdTemporary;
    
    JMenuBar menubar;
    private JMenu file;
    private JMenuItem save;
    private JMenuItem store;
    private JMenuItem open;
    private JMenuItem perform;
    private JMenuItem play;
    private JMenuItem chemBottleFromList; ///
    private JMenuItem chemBottleToList; ///
    
    private int assignIdWorkbench;
    private Point2D.Float pt1, pt2;             // for positioning of equipments on cupboard
    private int shelf1Y, shelf2Y, shelf3Y;
    private Header header;
    private boolean isHeaderAssigned;
    private StoreXml readChemicalList;
    private LinkedList<BottleChemical> chemicalList;
    private double shelfConstant1;
    private double shelfConstant2;
    private double shelfConstant3;
    private int currentShelfX;          // stores the x where the next equipment is to be added on shelf
    private int cupboardCurrentShelf = 1; // increment it each time the current shelf is filled completely
    private float startingXOfShelf;
    private float endingXOfShelf;
    private float gapBetweenApparatus = 1.0f / 9; // gapBetweenApparatus*width = gap b/w apparatus on workbench
    private int checkOnX;
    int[] shelvesY = {shelf1Y, shelf2Y, shelf3Y};
    int actualGapBetweenApparatus;
    private float buretteX = 0.1f;
    private float buretteY = 0.35f;
    private static final int numberOfBurettesAllowed = 1;
    private static final int numberOfBurnersAllowed = 1;
    private int buretteCount;
    private int burnerCount;
    private static int maxNumberOfChemicals = 100;///
    private String dropDown[] = new String[maxNumberOfChemicals];///
    int dropDownSize; ///
    String currentBottles[] = new String[10];///
    private int swapBottleChemical;///
    
    private String sizeList[] = {"1 (extra-small)", "2 (small)", "3 (medium)", "4 (large)", "5 (extra-large)"};
    private String sizeListShow[];
    private String sizeListBottle[] = {"1 (extra-small)", "2 (small)", "3 (medium)"};
    private boolean isStudent = true;
    private int blackboardmode = 1;
    private JScrollPane jScrollPane1; 
    int boardFlag;
    private int previousW, previousH;  // for resizing
    private Play_module demo;
    String modeChoice[]={"Student","Teacher"};
    String acceptChoice=null;
    
    // int inp=Integer.parseInt((getParameter("mode")));
    

/**
 *
 * Initialization method that will be called after the applet is loaded.
 * into the browser.
 * In this method, all resources such as images will be loaded.
 * and all support objects instantiated.
 * After this method, the applet is ready to run.
 *<ul><li>Store mode:<ul><li>to read the chemical list from the xml file using XMLReader
 * <li>add a new bottle for each chemical(a maximum of 10 at a time will be shown)
 * <li>add apparatus of several types
 * apparatus list: <ol><li> beaker <li> pipette <li> burette <li> test tube 
 * <li>flask <li> burner</ol> </ul></ul>
 
 */
    @Override
    public void init() {
        /*
         * if(inp==storeMode) { mode=storeMode; isStudent=false; } else
         * if(inp==performMode) { mode=performMode; isStudent=true; }
         */

        isStudent=false;
        
        
        if(acceptChoice==null)
        {
                acceptChoice = (String) JOptionPane.showInputDialog(this,
                        "Choose one mode",
                        "Choose mode",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        modeChoice,
                        modeChoice[0]);
                if(acceptChoice!=null)
                {
                    if(acceptChoice=="Student")isStudent=true;
                }
                else acceptChoice="Teacher";
        }
        
       // isStudent = true;
        if (mode == performMode || mode == playMode) {
            watch1 = new StopWatchRunner(new Dimension(200, 100));

            environ = new WorkBench(watch1);
            boardFlag = 0;
            if (mode == performMode) {
                //XMLReader = new XMLreader(environ);
            }

            if (mode == playMode) {
                demo = new Play_module(environ);
            }

            firstTime = true;
            bbmode = titleMode;

            createAndShowGUI();
            setSize(800, 600);
            this.addComponentListener(this);
            ArrayList<Equipment> equipments = Equipments.getEquipments();

        }

        if (mode == storeMode) {
           // setSize(800, 600);
            gapBetweenApparatus = 1.0f / 9;
            assignIdWorkbench = 1;
            /*
             * for assigning ids to apparatus in workbench
             */

            isHeaderAssigned = false;
            /*
             * boolean to store whether the user has input the header fields
             */

            header = new Header();
            /*
             * to read the chemical list from the xml file
             */

            createAndShowGUI();
            shelvesY[0] = shelf1Y;
            shelvesY[1] = shelf2Y;
            shelvesY[2] = shelf3Y;

            /*
             * read the chemical list from the xml using xml reader.
             */
            readChemicalList = new StoreXml();
            if (StoreXml.ErrorList.size() > 0) {
                showErrorChemicals();
                return;
            }
            chemicalList = readChemicalList.getChemInfo();
            /*
             * add a new bottle for each chemical
             */
            startingXOfShelf = 90.0f / 400.0f;
            currentShelfX = (int) (startingXOfShelf * 400);
            cupboardCurrentShelf = 1;
            endingXOfShelf = (285f / 400);
            checkOnX = (int) (endingXOfShelf * this.getWidth()/2);
            actualGapBetweenApparatus = (int) (gapBetweenApparatus * this.getWidth()/2);
            Equipment addChemicalBottle;
            for (int i = 0; i < chemicalList.size(); i++) {
                if (cupboardCurrentShelf > 2) {
                    break;
                }
                pt1 = new Point2D.Float(currentShelfX, shelvesY[cupboardCurrentShelf - 1]);
                addChemicalBottle = new Bottle(pt1, environ3);
                addChemicalBottle.setOriginShift(new Point2D.Float(pt1.x, pt1.y - addChemicalBottle.height));
                addChemicalBottle.setContents(chemicalList.get(i).formula);
                currentBottles[i] = (chemicalList.get(i)).formula;   ///
                addChemicalBottle.setContentColor(new Color(chemicalList.get(i).colorR, chemicalList.get(i).colorG, chemicalList.get(i).colorB));
                environ3.addApparatus(addChemicalBottle);
                currentShelfX += actualGapBetweenApparatus;
                if (currentShelfX > checkOnX) {
                    cupboardCurrentShelf++;
                    currentShelfX = (int) (startingXOfShelf * 400);
                }
            }


            /*
             * add apparatus of several types
             */
            /*
             * apparatus list: 1) beaker 2) pipette 3) burette 4) test tube 5)
             * flask 6) burner
             */
            startingXOfShelf = 90.0f / 400.0f;
            currentShelfX = (int) (startingXOfShelf * 400);
            cupboardCurrentShelf = 1;
            endingXOfShelf = (285f / 400);
            checkOnX = (int) (endingXOfShelf * 400);

            pt1 = new Point2D.Float(currentShelfX, shelf3Y);
            holdTemporary = new Pipette(pt1, environ3);
            holdTemporary.setOriginShift(new Point2D.Float(pt1.x, pt1.y - holdTemporary.height));
            environ3.cupboardEquipmentList.add(holdTemporary);
            currentShelfX += actualGapBetweenApparatus;

            pt1 = new Point2D.Float(currentShelfX, shelf3Y);
            holdTemporary = new TestTube(pt1, environ3);
            holdTemporary.setOriginShift(new Point2D.Float(pt1.x, pt1.y - holdTemporary.height));
            environ3.cupboardEquipmentList.add(holdTemporary);
            currentShelfX += actualGapBetweenApparatus;

            pt1 = new Point2D.Float(currentShelfX, shelf3Y);
            holdTemporary = new Flask(pt1, environ3);
            holdTemporary.setOriginShift(new Point2D.Float(pt1.x, pt1.y - holdTemporary.height));
            environ3.cupboardEquipmentList.add(holdTemporary);
            currentShelfX += actualGapBetweenApparatus;

            pt1 = new Point2D.Float(currentShelfX, shelf3Y);
            holdTemporary = new Beaker(pt1, environ3);
            holdTemporary.setOriginShift(new Point2D.Float(pt1.x, pt1.y - holdTemporary.height));
            environ3.cupboardEquipmentList.add(holdTemporary);
            currentShelfX += actualGapBetweenApparatus;

            pt1 = new Point2D.Float(currentShelfX, shelf3Y);
            try {
                holdTemporary = new Burner(pt1, environ3);
            } catch (IOException ex) {
                Logger.getLogger(VirtualChemLab.class.getName()).log(Level.SEVERE, null, ex);
            }
            holdTemporary.setOriginShift(new Point2D.Float(pt1.x, pt1.y - holdTemporary.height));
            environ3.cupboardEquipmentList.add(holdTemporary);
            currentShelfX += actualGapBetweenApparatus;

            pt1 = new Point2D.Float(350, 500); ///
            holdTemporary = new Burette(pt1, environ3);
            holdTemporary.setOriginShift(new Point2D.Float(pt1.x, pt1.y - holdTemporary.height));
            environ3.cupboardEquipmentList.add(holdTemporary);


            /*
             * now above variables will be used for positioning in the workbench
             */
            initVariablesForPositioning();
           // environ3.firstTime=true;
        }

    }

    /**
     * This method initializes various variables for proper positioning of
     * equipments on workbench.
     */
    public void initVariablesForPositioning() {
        // adjust value of shelf3Y to correspond it to table
        shelfConstant3 = 0.72f;
        shelf3Y = (int) (shelfConstant3 * environ3.getHeight());
        shelvesY[2] = shelf3Y;

        currentShelfX = (int) (startingXOfShelf * environ2.getWidth());
        cupboardCurrentShelf = 1;
        endingXOfShelf = (285f / 400);
        checkOnX = (int) (endingXOfShelf * environ2.getWidth());
        actualGapBetweenApparatus = (int) (gapBetweenApparatus * environ2.getWidth());

        buretteCount = 0;
        burnerCount = 0;
        swapBottleChemical = 0;
    }

   
     /**
     * This method creates the user-interface depending on the current mode.
     * Adds the JComponents to the applet depending on the current mode.
     * 
     * <ul><li>Sets the initial sizes of the JComponents<li>Adds JMenubar options like save, open,switch mode,etc.<li>Add the buttons for play,pause,show/hide blackboard,etc.
     * <li>adds the stopwatch(perform mode)<li>initializes various variables for proper positioning of
     * equipments on cupboard(store mode).<li>Adds the blackboard(perform/play mode) </ul>
     **/
    public void createAndShowGUI() {

        if (mode == performMode || mode == playMode) {


            setSize(800, 600);
            setLayout(new BorderLayout());
            p1 = new JPanel();
            setContentPane(p1);
            p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.setSize(new Dimension((int) (0.3 * this.getWidth()), this.getHeight()));

            p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
            p2.setSize(new Dimension((int) (0.7 * this.getWidth()), this.getHeight()));

            bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.X_AXIS));
            bottomPane.setSize(new Dimension(p2.getWidth(), (int) ((float) p2.getHeight() / 12.0)));
            if (mode == performMode) {
                board = new JButton(new ImageIcon("src/ekshiksha/vclab/lab/blackboard.png"));
                board.addActionListener(this);
                buttonPanel.setPreferredSize(new Dimension((int) ((float) bottomPane.getWidth() * 0.8), bottomPane.getHeight()));//(int)((2.75*(float)((this.getWidth())))),(int)(((double)this.getHeight())/12.0)));
                buttonPanel.add(board);
                bottomPane.add(buttonPanel);
            } else {
                demo.board.addActionListener(this);
                demo.setPreferredSize(new Dimension((int) ((float) bottomPane.getWidth() * 0.8), bottomPane.getHeight()));//(int)((2.75*(float)((this.getWidth())))),(int)(((double)this.getHeight())/12.0)));
                bottomPane.add(demo);
            }
            topLeftX = 0;
            topLeftY = 0;
            displayWidth = (int) (1 * (float) p.getWidth());
            blackboardHeight = (int) (0.9 * (float) (p.getHeight()));
            panelHeight = 50;
            bb = new Blackboard(topLeftX, topLeftY, displayWidth, blackboardHeight);
            bb.setPreferredSize(new Dimension(p.getWidth(), (int) ((11.0 * (float) p.getHeight()) / 12.0)));//(int)(0.15*(double)(this.getWidth())),(int)((11.0/12.0)*(double)this.getHeight())));
            p.add(bb);
            teacher = new Teacher(bb);
            if (teacher.animation != null) {
                teacher.start();
            }

            controls = new ControlPanel(this, topLeftX, blackboardHeight + 5, p.getWidth()/*
                     * (int)(0.15*(double)(this.getWidth()))
                     */, panelHeight);
            controls.setPreferredSize(new Dimension(p.getWidth(), (int) (((float) p.getHeight()) / 12.0)));//(int)(0.15*(double)(this.getWidth())),(int)((1.0/12.0)*(double)this.getHeight())));
            p.add(controls);


            watch1.setPreferredSize(new Dimension((int) ((float) bottomPane.getWidth() * 0.2), bottomPane.getHeight()));//(int)(0.33*this.getWidth()),this.getHeight()/6));

            watch1.setBackground(new Color(0, 128, 128));
            bottomPane.add(watch1, BorderLayout.CENTER);

            p2.setAlignmentX(LEFT_ALIGNMENT);

            environ.setPreferredSize(new Dimension(p2.getWidth(), (int) ((11.0 * (float) p2.getHeight()) / 12.0)));//(int)(0.85*((float)((this.getWidth()/2.0)))),(int)((8.0*(double)this.getHeight())/3.0)));
            p2.add(environ);
            environ.setMode(mode);
            p2.add(bottomPane);
            p1.add(p2);
            p.setAlignmentX(RIGHT_ALIGNMENT);
            p1.add(p);
            menubar = new JMenuBar();
            file = new JMenu("File");
            if (mode == performMode) {

                save = new JMenuItem("Save Experiment");
                save.addActionListener(this);
                file.add(save);

                play = new JMenuItem("Play Experiment");
                play.addActionListener(this);
                file.add(play);

                open = new JMenuItem("Open");
                open.addActionListener(this);
                file.add(open);

                if (!isStudent) {
                    store = new JMenuItem("Setup Experiment");
                    store.addActionListener(this);
                    file.add(store);
                }
            } else if (mode == playMode) {
                menubar = new JMenuBar();
                file = new JMenu("File");



                open = new JMenuItem("Open");
                open.addActionListener(this);
                file.add(open);
                perform = new JMenuItem("Perform Experiment");
                perform.addActionListener(this);
                file.add(perform);
                if (!isStudent) {
                    store = new JMenuItem("Setup Experiment");
                    store.addActionListener(this);
                    file.add(store);
                }
            }

            menubar.remove(file);
            menubar.add(file);
            setJMenuBar(menubar);


        } else if (mode == storeMode) {
            //getContentPane().remove(p1);
            setSize(new Dimension(800, 600));
            
            p = new JPanel();
            p1 = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
            p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));

            environ3 = new Cupboard(false, true); //without basin ... with object
            environ3.setSize(new Dimension(400, 600));
            environ3.setShowTable(false);
            environ3.setDrawThree(true); // cupboard has three shelves
            environ3.cupboardEquipmentList = new ArrayList();

            //environ2=workbench
            environ2 = new Cupboard(true, false); //with basin
            environ2.setSize(new Dimension(400, 600));
            environ2.setShowTable(true);
            environ2.cupboardEquipmentList = new ArrayList();
            previousW = 800;
            previousH = 600;

            p.add(environ3);
            p.add(environ2);

            // assign positioning constants
            shelfConstant1 = 0.35;///
            shelfConstant2 = 0.60;
            shelfConstant3 = 0.80;

            shelf3Y = (int) (shelfConstant3 * environ3.getHeight());
            shelf2Y = (int) (shelfConstant2 * environ3.getHeight());
            shelf1Y = (int) (shelfConstant1 * environ3.getHeight());

            /*
             * set the MENU bar options
             */
            menubar = new JMenuBar();
            file = new JMenu("File");
            save = new JMenuItem("Save");
            save.addActionListener(this);
            file.add(save);
            open = new JMenuItem("Open");
            open.addActionListener(this);
            file.add(open);
            perform = new JMenuItem("Perform Experiment");
            perform.addActionListener(this);
            file.add(perform);
            chemBottleFromList = new JMenuItem("Add Chemical Bottle from List");
            chemBottleFromList.addActionListener(this);
            file.add(chemBottleFromList);
            chemBottleToList = new JMenuItem("Add Chemical to List");
            chemBottleToList.addActionListener(this);
            file.add(chemBottleToList);
            menubar.add(file);
            setJMenuBar(menubar);

            p1.add(p);
            add(p1);
            addMouseListener(this);
            addMouseMotionListener(this);
            addComponentListener(this);
        }

    }

    //BLACKBOARD FUNCTIONS
    /**
     * ********************************************************************************************************************
     */
    /**
     * This method sets the mode for blackboard
     * @param mode : The mode to which the blackboard is to be set
     */
    public void setMode(int mode) {
        if ((mode >= 1))// && (mode <= testMode) )
        {   /*
             * Valid parameter
             */
            /*
             * if mode has changed, invoke actions
             */
            if (mode != getMode()) {
                this.blackboardmode = mode;
                processMode();
            }
        }
    }

    /**
     *
     * This method is called to get value of data element mode.
     *
     */
    public int getMode() {
        return (this.blackboardmode);
    }

    /**
     *
     * This method is called whenever there is a change in mode.
     *
     */
    public void processMode() {
        teacher.changeMode(getMode());
    }

    @Override
    public void start() {
        if (firstTime == true) {
            firstTime = false;

            teacher.start();
        }
    }

 
    // COMPONENT LISTENERS
    /**
     * ********************************************************************************************************************************
     */
    /**
     * Resizes the various positioning variables, and panels whenever the applet is resized
     * @param e Event generated whenever the applet is resized
     */
    @Override
    public void componentResized(ComponentEvent e) {
        if (mode == performMode || mode == playMode) {
            p.setSize(new Dimension((int) (0.3 * this.getWidth()), this.getHeight()));
            p2.setSize(new Dimension((int) (0.7 * this.getWidth()), this.getHeight()));
            bottomPane.setPreferredSize(new Dimension(p2.getWidth(), (int) ((float) p2.getHeight() / 12.0)));
            bb.setPreferredSize(new Dimension(p.getWidth(), (int) ((11.0 * (float) p.getHeight()) / 12.0)));
            bb.setBlackboardHeight((int) (0.84 * (float) (p.getHeight())));
            bb.setBlackboardWidth((int) (0.94 * (float) p.getWidth()));
            controls.setPreferredSize(new Dimension(p.getWidth(), (int) (((float) p.getHeight()) / 12.0)));
            if (mode == performMode) {
                buttonPanel.setPreferredSize(new Dimension((int) ((float) bottomPane.getWidth() * 0.8), bottomPane.getHeight()));
            } else {
                demo.setPreferredSize(new Dimension((int) ((float) bottomPane.getWidth() * 0.8), bottomPane.getHeight()));
            }
            watch1.setPreferredSize(new Dimension((int) ((float) bottomPane.getWidth() * 0.2), bottomPane.getHeight()));
            watch1.watch.setPreferredSize(watch1.getSize());
            watch1.watch.setFont(new Font("DIGITAL", Font.PLAIN, (int) (((5.0 * 22.0) / (12.0 * 250.0)) * (float) this.getHeight())));
            environ.setPreferredSize(new Dimension(p2.getWidth(), (int) ((11.0 * (float) p2.getHeight()) / 12.0)));
            controls.removeAll();
            controls.createPanel();
            teacher.bb.clean();
            teacher = new Teacher(bb);
            teacher.start();
            if (head != null) {
                teacher.setHeader(head);
                teacher.show();
            }


            this.setVisible(true);
        } else if (mode == storeMode) {


            float ratioW, ratioH;
            ratioW = (float) (this.getWidth());
            ratioH = (float) (this.getHeight());
            ratioW /= (float) previousW;
            ratioH /= (float) previousH;

            currentShelfX = (int) (currentShelfX * ratioW);
            previousW = this.getWidth();
            previousH = this.getHeight();

        }

    }

    @Override
    public void componentMoved(ComponentEvent e) {
        
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void componentShown(ComponentEvent e) {
        this.setVisible(true);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    /**
     * ********************************************************************************************************************************
     */
    // ACTION PERFORMED
    /**
     * Abstract method for Action Listener
     * @param e Event corresponding to button or Jmenu option
     * <ul><li>STORE MODE:<ul>
     * <li>open: to open a pre-existing experiment set-up file
     * <li>save: to save the experiment set-up file
     * <li>chemBottleFromList: to add To add a new chemical which is not present in the list.</br> The user is prompted to enter the name, formula and colour of the chemical(as RGB)
     * <li> chemBottleToList:Adding a chemical from the chemical list shown as a drop-down list.
     * <li>perform: to switch to perform mode
     * </ul>
     * <li>PERFORM MODE:<ul><li>board: to show/hide blackboard
     * <li>play:to switch to play mode
     * <li>open: to open an experiment set-up file
     * <li>save: to save the activities performed
     * <li>store: to switch to set-up mode (only for 'teacher' user)
     * </ul>
     * <li>PLAY MODE<ul>
     * <li>open: to open an experiment set-up file
     * <li>store: to switch to set-up mode (only for 'teacher' user)
     * <li>perform: to switch to perform mode</ul>
     * </ul>
     */
    public void actionPerformed(ActionEvent e) {

        if (mode == performMode) {
            
            if (e.getSource() == board) {
                if (boardFlag % 2 == 0) {
                    p2.setSize(this.getSize());
                    p.setSize(new Dimension(0, 0));
                    p.setVisible(false);
                    boardFlag++;
                } else {
                    p.setSize(new Dimension((int) (0.3 * this.getWidth()), this.getHeight()));
                    p2.setSize(new Dimension((int) (0.7 * this.getWidth()), this.getHeight()));
                    p.setVisible(true);
                    boardFlag++;
                }
            }
            if (e.getSource() == play) {
                file.remove(play);
               
                file.remove(open);
                file.remove(save);
                if (!isStudent) {
                    file.remove(store);
                }
                mode = playMode;
                p2.remove(environ);
                buttonPanel.remove(board);
                bottomPane.remove(buttonPanel);
                bottomPane.remove(watch1);
                p2.remove(bottomPane);
                p1.remove(p2);
                p.remove(bb);
                p.remove(controls);
                p1.remove(p);
                remove(p1);
                Equipments.removeAll();
                Activities.removeAll();
                init();

                this.validate();
            } else if (e.getSource() == open) {
                JFileChooser chooser = new JFileChooser("EXP_SETUP");
                chooser.showOpenDialog(null);

                File selectedFile;
                selectedFile = chooser.getSelectedFile();

                if (selectedFile != null) {
                    Equipments.removeAll();
                    Activities.removeAll();
                    environ.removeAll();
                    environ.isSet = false;
                    Activities.removeAll();
                    XMLreader = new XMLReader(environ, selectedFile);
                    if (XMLreader.ErrorList.size() > 0) {
                        showError();
                        return;
                    }

                    head = new Header();
                    head.setAuthor_Name(XMLreader.getHead().getAuthor_Name());
                    head.setDescription(XMLreader.getHead().getDescription());
                    head.setInstruction(XMLreader.getHead().getInstruction());
                    head.setLevel(XMLreader.getHead().getLevel());
                    head.setMarks(XMLreader.getHead().getMarks());
                    head.setStudent_Name(XMLreader.getHead().getStudent_Name());
                    head.setTitle(XMLreader.getHead().getTitle());

                    teacher = new Teacher(bb);

                    teacher.setHeader(head);
                    teacher.start();
                    teacher.show();
                    repaint();
                }

            } else if (e.getSource() == save) {
                try {
                    JFileChooser chooser = new JFileChooser("SAVED_EXPERIMENTS");
                    //chooser.setFileFilter(new FileFilterNew());
                    chooser.showSaveDialog(null);

                    File selectedFile;
                    selectedFile = chooser.getSelectedFile();
                    header = XMLreader.getHead();


                    /*
                     * call writer to store the setup into a xml file
                     */
                    writeXMLFile = new WriteXMLFile(header, Equipments.getEquipments(), Activities.getActivities(), XMLreader.getObservation(), XMLreader.getEvaluation(), selectedFile);

                } catch (Exception ee) {
                }

            }
            if (!isStudent) {
                if (e.getSource() == store) {
                    file.remove(play);
                    //file.remove(perform);
                    file.remove(open);
                    file.remove(save);
                    
                        file.remove(store);
                    remove(menubar);
                        
                    mode = storeMode;
                    p2.remove(environ);
                    buttonPanel.remove(board);
                    bottomPane.remove(buttonPanel);
                    bottomPane.remove(watch1);
                    p2.remove(bottomPane);
                    p1.remove(p2);
                    p.remove(bb);
                    p.remove(controls);
                    p1.remove(p);
                    remove(p1);
                    Equipments.removeAll();
                    Activities.removeAll();
                    init();

                    this.validate();

                }
            }


        } else if (mode == storeMode) {
            if (e.getSource() == open) {

                JFileChooser chooser = new JFileChooser("EXP_SETUP");
                //chooser.setFileFilter(new FileFilterNew());
                chooser.showOpenDialog(null);

                File selectedFile;
                selectedFile = chooser.getSelectedFile();

                removeAllObjects();
                XMLreader = new XMLReader(environ2, environ2.cupboardEquipmentList, selectedFile);
                if (XMLreader.ErrorList.size() > 0) {
                    showError();
                }
                header = new Header();
                header = XMLreader.getHead();
                initVariablesForPositioning();
                burnerCount = burnerCount = 0;
                for (int i = 0; i < environ2.cupboardEquipmentList.size(); i++) {
                    if ("Burette".equals(environ2.cupboardEquipmentList.get(i).getType())) {
                        buretteCount++;
                    }
                    if ("Burner".equals(environ2.cupboardEquipmentList.get(i).getType())) {
                        burnerCount++;
                    }
                }
                assignIdWorkbench = environ2.cupboardEquipmentList.size() + 1;
                cupboardCurrentShelf = 1 + ((-burnerCount + environ2.cupboardEquipmentList.size()) / 5);
                currentShelfX = (int) (startingXOfShelf * environ2.getWidth());
                actualGapBetweenApparatus = (int) (gapBetweenApparatus * environ2.getWidth());
                currentShelfX += (environ2.cupboardEquipmentList.size() - burnerCount - (cupboardCurrentShelf - 1) * 5) * actualGapBetweenApparatus;
                environ2.repaint();

            } else if (e.getSource() == save) {

                for (int i = 0; i < environ2.getListSize(); i++) {
                    environ2.cupboardEquipmentList.get(i).setX(environ2.cupboardEquipmentList.get(i).getOriginShift().x / environ2.getWidth());
                    environ2.cupboardEquipmentList.get(i).setY(environ2.cupboardEquipmentList.get(i).getOriginShift().y / environ2.getHeight());
                }

                try {
                    JFileChooser chooser = new JFileChooser("EXP_SETUP");
                    //chooser.setFileFilter(new FileFilterNew());
                    chooser.showSaveDialog(null);

                    File selectedFile;
                    selectedFile = chooser.getSelectedFile();

                    /*
                     * call writer to store the setup into a xml file
                     */
                    writeXMLFile = new WriteXMLFile(header, environ2.cupboardEquipmentList, 0, selectedFile);

                } catch (Exception ee) {
                }

            } else if (e.getSource() == perform) {
                /*
                 * change the mode to perform mode -> remove old panels -> add
                 * new panels -> update JMenubar -> revalidate the applet
                 */
                mode = performMode;
                p.remove(environ3);
                p.remove(environ2);
                p1.remove(p);
                remove(p1);
                
                
                file.remove(perform);
                file.remove(open);
                file.remove(save);
                file.remove(chemBottleToList);
                file.remove(chemBottleFromList);
                menubar.remove(file);
                remove(menubar);
                
                init();
                
                this.validate();
              

            } else if (e.getSource() == chemBottleFromList) {
                ///
                dropDownSize = 0;
                boolean flag;///
                //delete dropDown;
                char ch, ch2;
                int id;
                Color tempColor;
                dropDown = new String[Math.max(0, chemicalList.size() - 10)];
                JTextField title = new JTextField();
                JTextField authorName = new JTextField();
                for (int j = 0; j < chemicalList.size(); j++) {
                    flag = false;
                    for (int k = 0; k < 10; k++) {
                        if (currentBottles[k].equals(chemicalList.get(j).formula)) {
                            flag = true;
                            break;
                        } ///
                    }
                    if (!flag) {
                        ch = (char) ((j % 10) + '0');
                        ch2 = (char) ((j / 10) + '0');
                        //ch2=' ';

                        dropDown[dropDownSize] = ch2 + "" + ch + " -> " + chemicalList.get(j).formula; // add chemical to dropDown if it is not there in drop down
                        dropDownSize++;

                    }
                }
                ///
                String acceptChemical = (String) JOptionPane.showInputDialog(this,
                        "Which chemical bottle do you want to add?",
                        "Add Bottle To Store",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        dropDown,
                        dropDown[0]);

                if (acceptChemical == null) {
                    return;
                }

                id = (acceptChemical.charAt(0) - 48) * 10 + (acceptChemical.charAt(1) - 48);

                tempColor = new Color(chemicalList.get(id).getColorR(), chemicalList.get(id).getColorG(), chemicalList.get(id).getColorB());
                environ3.cupboardEquipmentList.get(swapBottleChemical).setContents(acceptChemical.substring(6));
                environ3.cupboardEquipmentList.get(swapBottleChemical).setContentColor(tempColor);
                currentBottles[swapBottleChemical] = acceptChemical.substring(6);
                /*
                 * change chemical name of bottle [swapBottle] to acceptChemical
                 * change colour
                 */
                swapBottleChemical++;
                swapBottleChemical %= 10;

                //updateChemicalList=new ModifyStoreXML(new BottleChemical(""));

                environ3.repaint();

            } else if (e.getSource() == chemBottleToList) {

                boolean filledCorrectly = false;
                int loopCounter = 0;
                JDialog dialog;
                BottleChemical addToList;

                String tempString;
                JTextField name = new JTextField();
                String nameOfChemical = "";
                JTextField formula = new JTextField();
                String formulaOfChemical = "";
                JTextField colorR = new JTextField();
                int r = 0;
                JTextField colorG = new JTextField();
                int g = 0;
                JTextField colorB = new JTextField();
                int b = 0;
                Object[] msg = {"Name of Chemical:", name, "Formula of Chemical:", formula, "COLOUR : e.g. for white R=255,G=255,B=255 ", "ColourR(0-255):", colorR, "ColourG(0-255):", colorG, "ColourB(0-255)", colorB};

                JOptionPane op = new JOptionPane(
                        msg,
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.OK_CANCEL_OPTION,
                        null,
                        null);

                while (!filledCorrectly) {
                    filledCorrectly = true;
                    loopCounter++;

                    if (loopCounter == 1) {
                        dialog = op.createDialog(this, "Enter Chemical Details");
                    } else {
                        dialog = op.createDialog(this, "Enter Chemical Details(One or more fields were not filled)");
                    }

                    dialog.setVisible(true);

                    int result = JOptionPane.OK_OPTION;

                    try {
                        result = ((Integer) op.getValue()).intValue();

                        if (result == JOptionPane.CANCEL_OPTION) {
                            return;
                        }
                        if (result == JOptionPane.OK_OPTION) {

                            tempString = name.getText();
                            if (tempString == null) {
                                filledCorrectly = false;
                            } else {
                                nameOfChemical = tempString;
                            }

                            tempString = formula.getText();
                            if (tempString == null) {
                                filledCorrectly = false;
                            } else {
                                formulaOfChemical = tempString;
                            }

                            try {
                                tempString = colorR.getText();
                                r = Integer.parseInt(tempString);
                                tempString = colorG.getText();
                                g = Integer.parseInt(tempString);
                                tempString = colorB.getText();
                                b = Integer.parseInt(tempString);

                            } catch (Exception eq) {
                                filledCorrectly = false;
                            }

                        } else {
                            filledCorrectly = false;
                        }

                    } catch (Exception eq) {
                        filledCorrectly = false;
                    }

                    if (filledCorrectly && (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255)) {
                        filledCorrectly = false;
                    }

                } ///while(!filledCorrectly)

                if (filledCorrectly) {
                    updateChemicalList = new ModifyStoreXML(new BottleChemical(nameOfChemical, formulaOfChemical, r, g, b));
                    chemicalList.add(new BottleChemical(nameOfChemical, formulaOfChemical, r, g, b));
                }


            } // else if(e.getSource()==chemBottleToList) ends

        } // if(mode==storeMode ends
        else if (mode == playMode) {
            if (e.getSource() == demo.board) {
                if (boardFlag % 2 == 0) {
                    //board.setText("Show Blackboard");
                    p2.setSize(this.getSize());
                    p.setSize(new Dimension(0, 0));
                    p.setVisible(false);
                    boardFlag++;
                } else {
                    //board.setText("Hide Blackboard");
                    p.setSize(new Dimension((int) (0.3 * this.getWidth()), this.getHeight()));
                    p2.setSize(new Dimension((int) (0.7 * this.getWidth()), this.getHeight()));
                    p.setVisible(true);
                    boardFlag++;
                }
            }
            if (e.getSource() == open) {
                JFileChooser chooser = new JFileChooser("SAVED_EXPERIMENTS");
                //chooser.setFileFilter(new FileFilterNew());
                chooser.showOpenDialog(null);

                File selectedFile;
                selectedFile = chooser.getSelectedFile();

                if (selectedFile != null) {
                    Equipments.removeAll();
                    Activities.removeAll();
                    environ.removeAll();
                    environ.isSet = false;
                    XMLreader = new XMLReader(environ, selectedFile);
                    if (XMLreader.ErrorList.size() > 0) {
                        showError();
                    }

                    head = new Header();
                    head.setAuthor_Name(XMLreader.getHead().getAuthor_Name());
                    head.setDescription(XMLreader.getHead().getDescription());
                    head.setInstruction(XMLreader.getHead().getInstruction());
                    head.setLevel(XMLreader.getHead().getLevel());
                    head.setMarks(XMLreader.getHead().getMarks());
                    head.setStudent_Name(XMLreader.getHead().getStudent_Name());
                    head.setTitle(XMLreader.getHead().getTitle());
                    teacher = new Teacher(bb);

                    teacher.setHeader(head);
                    teacher.start();
                    teacher.show();
                    repaint();
                }

            } else if (e.getSource() == perform) {
                file.remove(open);
                file.remove(perform);
                if (!isStudent) {
                    file.remove(store);
                }
                mode = performMode;
                demo.remove(demo.play);
                demo.remove(demo.undo);
                demo.remove(demo.pause);
                demo.remove(demo.board);


                bottomPane.remove(demo);

                bottomPane.remove(watch1);
                p2.remove(bottomPane);
                p2.remove(environ);
                p1.remove(p2);
                p.remove(bb);
                p.remove(controls);
                p1.remove(p);
                remove(p1);

                Equipments.removeAll();
                Activities.removeAll();
                init();


                this.validate();
            }
            if (!isStudent) {
                if (e.getSource() == store) {
                    file.remove(open);
                    file.remove(perform);
                    if (!isStudent) {
                        file.remove(store);
                    }
                    mode = storeMode;
                    demo.remove(demo.play);
                    demo.remove(demo.undo);
                    demo.remove(demo.pause);
                    demo.remove(demo.board);


                    bottomPane.remove(demo);

                    bottomPane.remove(watch1);
                    p2.remove(bottomPane);
                    p2.remove(environ);
                    p1.remove(p2);
                    p.remove(bb);
                    p.remove(controls);
                    p1.remove(p);
                    remove(p1);

                    Equipments.removeAll();
                    Activities.removeAll();
                    init();


                    this.validate();

                }
            }


        }
        
         
        
    }

    public void removeAllObjects() {
        int temp;
        temp = environ2.cupboardEquipmentList.size();
        for (int i = 0; i < temp; i++) {
            environ2.cupboardEquipmentList.remove(0);
        }

    }

    /**
     * ********************************************************************************************************************************
     */
    /**
     * This method prompts the teacher to enter header information.
     * Header fields : String title; String author_Name; String level; int marks
     * String description; String student_Name; String instructions
     */
    public void promptForHeader() {
        
        
        boolean filledCorrectly = false, marksCorrectlyFilled = true, levelCorrectlyFilled = true;
        int loopCounter = 0;
        JDialog dialog;

        /*
         * Header fields : String title; String author_Name; String level; int
         * marks; String description; String student_Name; String instructions
         */

        JTextField title = new JTextField();
        JTextField authorName = new JTextField();
        JTextField level = new JTextField();
        JTextField marks = new JTextField();
        JTextField description = new JTextField();
        description.setColumns(20);
        JTextField studentName = new JTextField();
        JTextArea instructions = new JTextArea(2, 2);
        instructions.setRows(2);
        instructions.setLineWrap(true);
        jScrollPane1 = new JScrollPane(instructions);


        Object[] msg = {"Title: (e.g. Titration of NaOH and HCl )", title, "Author (e.g VCLab )", authorName, "Level: (Class e.g 9) ", level, "Description", description, "Maximum Marks", marks, "Student Name", studentName, "Instructions", jScrollPane1};

        JOptionPane op = new JOptionPane(
                msg,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION,
                null,
                null);
        // op.add(jScrollPane1);
        while (!filledCorrectly) {
            marksCorrectlyFilled = true;
            levelCorrectlyFilled = true;
            filledCorrectly = true;
            loopCounter++;

            if (loopCounter == 1) {
                dialog = op.createDialog(this, "Enter Header Details");
            } else {
                dialog = op.createDialog(this, "Enter Header Details(One or more fields were not filled)");
            }

            dialog.setVisible(true);

            int result = JOptionPane.OK_OPTION;

            try {
                result = ((Integer) op.getValue()).intValue();

                if (result == JOptionPane.OK_OPTION) {
                    header.setAuthor_Name(authorName.getText());
                    header.setDescription(description.getText());
                    //header.setLevel(level.getText());
                    header.setTitle(title.getText());
                    header.setInstruction(instructions.getText());
                    String tempString = marks.getText();
                    int marksTemp = 0;
                    try {
                        marksTemp = Integer.parseInt(tempString);
                        if (marksTemp < 0) {
                            filledCorrectly = false;
                            marksCorrectlyFilled = false;
                        }
                        header.setMarks(marksTemp);
                    } catch (Exception e) {
                        filledCorrectly = false;
                        marksCorrectlyFilled = false;
                        //
                    }
                    if (filledCorrectly) {
                        header.setMarks(marksTemp);
                    }
                    tempString = level.getText();
                    marksTemp = 0;
                    try {
                        marksTemp = Integer.parseInt(tempString);
                        if (marksTemp < 0) {
                            filledCorrectly = false;
                            levelCorrectlyFilled = false;
                        }
                        header.setLevel(Integer.toString(marksTemp));
                    } catch (Exception e) {
                        filledCorrectly = false;
                        levelCorrectlyFilled = false;
                    }
                    header.setStudent_Name(studentName.getText());


                } else {
                    filledCorrectly = false;
                }

                if (result == JOptionPane.OK_OPTION) {

                    if ("".equals(header.getTitle())) {
                        filledCorrectly = false;
                        JOptionPane.showMessageDialog(this, "Title is compulsory");
                    } else if ("".equals(header.getAuthor_Name())) {
                        filledCorrectly = false;
                        JOptionPane.showMessageDialog(this, "Author name is compulsory");
                    } else if (!levelCorrectlyFilled) {
                        filledCorrectly = false;
                        JOptionPane.showMessageDialog(this, "Level can only be a whole number");
                    } else if ("".equals(header.getDescription())) {
                        filledCorrectly = false;
                        JOptionPane.showMessageDialog(this, "Description is compulsory");
                    } else if (!marksCorrectlyFilled) {
                        JOptionPane.showMessageDialog(this, "Marks can only be a whole number");
                    } else if ("".equals(header.getInstruction())) {
                        filledCorrectly = false;
                        JOptionPane.showMessageDialog(this, "Instruction is compulsory");
                    }
                    
                    if ("".equals(header.getStudent_Name())) {
                        header.setStudent_Name("VCLAB");
                    } 
                }

            } catch (Exception e) {
                filledCorrectly = false;

            }


        }


    }

// MOUSE LISTENERS
    /*
     * ********************************************************************************************************************************
     */
    
  
    
    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    
    @Override
    public void mousePressed(MouseEvent me) {

        if (mode != storeMode) {
            return;
        }
        Equipment temp;
        Point2D.Float l = new Point2D.Float(me.getX(), me.getY());
        qw = environ3.getSize(); // for the beaker in workbench side, we have to check getX()-environ3.width since location of this beaker is wrt origin of workbench panel

        /*
         * for each object in workbench identify whether mouse is currectly
         * pressed on it
         */
       for (int i = 0; i < environ2.getListSize(); i++) {
            if (0 <= (l.x - environ2.cupboardEquipmentList.get(i).originShift.x - qw.width) && (l.x - environ2.cupboardEquipmentList.get(i).originShift.x - qw.width) < environ2.cupboardEquipmentList.get(i).width && (l.y - environ2.cupboardEquipmentList.get(i).originShift.y) < environ2.cupboardEquipmentList.get(i).height
                    && 0 <= l.y - environ2.cupboardEquipmentList.get(i).originShift.y) {
                environ2.cupboardEquipmentList.get(i).isClicked = true;
            } else {
                environ2.cupboardEquipmentList.get(i).isClicked = false;
            }

        }

        /*
         * for each object in cupboard identify whether mouse is currectly
         * pressed on it
         */
       boolean found1 = false;
        for (int i = 0; i < environ3.getListSize() && !found1; i++) ///31 1022 :
        {

            if (0 <= (l.x - environ3.cupboardEquipmentList.get(i).originShift.x) && (l.x - environ3.cupboardEquipmentList.get(i).originShift.x) < environ3.cupboardEquipmentList.get(i).width && (l.y - environ3.cupboardEquipmentList.get(i).originShift.y) < environ3.cupboardEquipmentList.get(i).height
                    && 0 <= l.y - environ3.cupboardEquipmentList.get(i).originShift.y) {


                environ3.cupboardEquipmentList.get(i).isClicked = true;
                if (environ3.cupboardEquipmentList.get(i).isDraggable) {
                    found1 = true;
                }
            } else {
                environ3.cupboardEquipmentList.get(i).isClicked = false;
            }

        }



        /*
         * for each of the equipments originally placed in the cupboard for
         * transfer, check whetehr mouse is currectly pressed on it. If it is
         * so, then create a new equipment of the same type at that place. Note:
         * for originally placed equipments in cupboard, isDraggable is set to
         * false so they don't move but for new equipments isDraggable=true
         */
     for (int i = 0; i < environ3.getListSize() && !found1; i++) {


            if (environ3.cupboardEquipmentList.get(i).isClicked && !found1 && !environ3.cupboardEquipmentList.get(i).isDraggable && environ3.cupboardEquipmentList.get(i).isDrawable) {
                temp = null;
                if ("Beaker".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                    temp = new Beaker(new Point2D.Float(environ3.cupboardEquipmentList.get(i).originShift.x + (int) environ3.cupboardEquipmentList.get(i).width / 10, l.y - (int) environ3.cupboardEquipmentList.get(i).height / 2), environ3);
                } //31 1016: create a copy of apparatus when clicke in cupboard
                else if ("Bottle".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                    temp = new Bottle(new Point2D.Float(environ3.cupboardEquipmentList.get(i).originShift.x + (int) environ3.cupboardEquipmentList.get(i).width / 10, l.y - (int) environ3.cupboardEquipmentList.get(i).height / 2), environ3);
                } else if ("Pipette".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                    temp = new Pipette(new Point2D.Float(environ3.cupboardEquipmentList.get(i).originShift.x + (int) environ3.cupboardEquipmentList.get(i).width / 10, l.y - (int) environ3.cupboardEquipmentList.get(i).height / 2), environ3);
                } else if ("Burner".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                    try {
                        temp = new Burner(new Point2D.Float(environ3.cupboardEquipmentList.get(i).originShift.x + (int) environ3.cupboardEquipmentList.get(i).width / 10, l.y - (int) environ3.cupboardEquipmentList.get(i).height / 2), environ3);
                    } catch (IOException ex) {
                        Logger.getLogger(VirtualChemLab.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if ("Burette".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                    temp = new Burette(new Point2D.Float(environ3.cupboardEquipmentList.get(i).originShift.x + (int) environ3.cupboardEquipmentList.get(i).width / 10, l.y - (int) environ3.cupboardEquipmentList.get(i).height / 2), environ3);
                } else if ("TestTube".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                    temp = new TestTube(new Point2D.Float(environ3.cupboardEquipmentList.get(i).originShift.x + (int) environ3.cupboardEquipmentList.get(i).width / 10, l.y - (int) environ3.cupboardEquipmentList.get(i).height / 2), environ3);
                } else if ("Flask".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                    temp = new Flask(new Point2D.Float(environ3.cupboardEquipmentList.get(i).originShift.x + (int) environ3.cupboardEquipmentList.get(i).width / 10, l.y - (int) environ3.cupboardEquipmentList.get(i).height / 2), environ3);
                }

                temp.setId(0);
                temp.setDraggable(true); // the copy must be draggable
                temp.isClicked = true;
                temp.setContents((environ3.cupboardEquipmentList.get(i)).getContents());
                temp.setContentColor((environ3.cupboardEquipmentList.get(i)).getContentColor());
                //environ3.addApparatus(temp);
                environ3.cupboardEquipmentList.add(temp);
                environ3.repaint();
                environ3.cupboardEquipmentList.get(i).isClicked = false;
                break;
            }
        }

    }
    
    
    /**
     * This method is implementation of the abstract method mouseReleased 
     * @param me
     * <ul>
     * <li>On releasing the chemical bottle/equipment in the workbench, it is automatically placed in a suitable position on shelf or table.
     * <li> In case of chemical bottle, the user is asked the concentration of the chemical bottle he wants to provide
     * <li>In case of equipments, the user is asked to choose one of the various sizes available for that particular equipment.
     * </ul>
     */
    @Override
    public void mouseReleased(MouseEvent me) {

        if (mode != storeMode) {
            return;
        }
      float conc = 0.0f;
        Point2D.Float pt1;
        for (int i = 0; i < environ2.getListSize(); i++) {
            if (("Bottle".equals((environ2.cupboardEquipmentList.get(i)).getType()))) {

                if (0 == ((environ2.cupboardEquipmentList.get(i)).getStrength())) {
                    while (conc <= 0) {
                        String enterconc = JOptionPane.showInputDialog("enter the conc.");
                        try {
                            conc = Float.parseFloat(enterconc);
                        } catch (Exception e) {
                            conc = 1.0f; // in case of wrong input, the defaul value of concentration is set to 1.0
                            break;
                        }
                    }

                    (environ2.cupboardEquipmentList.get(i)).setStrength(conc);

                }

            }
            if (("Burette".equals((environ2.cupboardEquipmentList.get(i)).getType())) && (environ2.cupboardEquipmentList.get(i)).getIsDraggable()) {
                (environ2.cupboardEquipmentList.get(i)).setOriginShift(new Point2D.Float(buretteX * environ2.getWidth(), buretteY * environ2.getHeight()));
                //environ2.cupboardEquipmentList.get(i).setOriginShift(new Point2D.Float(environ2.cupboardEquipmentList.get(i).getOriginShift().x , environ2.cupboardEquipmentList.get(i).getOriginShift().y-holdTemporary.height));
                if (!(environ2.cupboardEquipmentList.get(i)).getIsDraggable()) {
                    continue;
                }
                (environ2.cupboardEquipmentList.get(i)).setIsDraggable(false);
                continue;
            }
            if ((environ2.cupboardEquipmentList.get(i)).getIsDraggable() && !(("Burner".equals((environ2.cupboardEquipmentList.get(i)).getType()))) && !(("Bottle".equals((environ2.cupboardEquipmentList.get(i)).getType())))) {
                sizeListShow = new String[5];
                for (int iterator = 0; iterator <= 4; iterator++) {
                    sizeListShow[iterator] = sizeList[iterator] + " -> " + (Float.toString((environ2.cupboardEquipmentList.get(i)).sizearr[iterator]) + " ml");
                }
                String acceptSize;
                acceptSize = (String) JOptionPane.showInputDialog(this,
                        "What size of equipment do you want to add?",
                        "Enter Size",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        sizeListShow,
                        sizeListShow[0]);
                int size = 1;
                if (acceptSize != null) {
                    size = Integer.parseInt(acceptSize.substring(0, 1));
                }
                (environ2.cupboardEquipmentList.get(i)).setSize(size);
            } else if ((environ2.cupboardEquipmentList.get(i)).getIsDraggable() && (("Bottle".equals((environ2.cupboardEquipmentList.get(i)).getType())))) {
                sizeListShow = new String[3];
                for (int iterator = 0; iterator <= 2; iterator++) {
                    sizeListShow[iterator] = sizeListBottle[iterator] + " -> " + (Float.toString((environ2.cupboardEquipmentList.get(i)).sizearr[iterator]) + " ml");
                }
                String acceptSize;
                acceptSize = (String) JOptionPane.showInputDialog(this,
                        "What size of equipment do you want to add?",
                        "Enter Size",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        sizeListShow,
                        sizeListShow[0]);
                int size = 1;
                if (acceptSize != null) {
                    size = Integer.parseInt(acceptSize.substring(0, 1));
                }
                (environ2.cupboardEquipmentList.get(i)).setSize(size);
            }


            /*
             * following code deals with POSITIONING IN WORKBENCH
             */
          if (!((environ2.cupboardEquipmentList.get(i)).getIsDraggable())) {
                continue;
            }
            if (cupboardCurrentShelf > 3) {
                continue;
            }
            shelf3Y = (int) (shelfConstant3 * environ2.getHeight());
            shelf2Y = (int) (shelfConstant2 * environ2.getHeight());
            shelf1Y = (int) (shelfConstant1 * environ2.getHeight());
            shelvesY[0] = shelf1Y;
            shelvesY[1] = shelf2Y;
            shelvesY[2] = shelf3Y;

            checkOnX = (int) (endingXOfShelf * environ2.getWidth());
            actualGapBetweenApparatus = (int) (gapBetweenApparatus * environ2.getWidth());

            pt1 = new Point2D.Float(currentShelfX, shelvesY[cupboardCurrentShelf - 1]);
            (environ2.cupboardEquipmentList.get(i)).setOriginShift(pt1);
            (environ2.cupboardEquipmentList.get(i)).setOriginShift(new Point2D.Float(pt1.x, pt1.y - (environ2.cupboardEquipmentList.get(i)).height));
            (environ2.cupboardEquipmentList.get(i)).setIsDraggable(false);///
            currentShelfX += actualGapBetweenApparatus;
            if (currentShelfX > checkOnX) {
                cupboardCurrentShelf++;
                currentShelfX = (int) (startingXOfShelf * environ2.getWidth());
            }

        }
        environ2.repaint();

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
    }
    
   /**
    * This method is implementation of the abstract method mouseMoved for MouseListener 
    * @param me 
    * <ul><li>Prompts for header of the experiment if not yet filled(Store/set-up mode)
    * <li> Change the border colour of the equipment in cupboard when mouse is brought on it
    * </ul>
    */
    @Override
    public void mouseMoved(MouseEvent me) {

        if (mode != storeMode) {
            return;
        }
        
        
        
        if (!isHeaderAssigned) // prompt user to input header information if header is not yet set
        {
            promptForHeader();
            isHeaderAssigned = true;
            /*
             * isHeaderAssigned=true; header.setAuthor_Name("harsh");
             * header.setDescription("mayur"); header.setLevel("aviral");
             * header.setTitle("qwerty"); header.setStudent_Name("qwqw");
             * header.setInstruction("hi"); header.setMarks(10);
             */
        }

       if (mode == storeMode) { 
            qw = environ3.getSize();
            Point2D.Float l = new Point2D.Float(me.getX(), me.getY());
            for (int i = 0; i < environ3.getListSize(); i++) {
                if (0 <= (l.x - environ3.cupboardEquipmentList.get(i).originShift.x) && (l.x - environ3.cupboardEquipmentList.get(i).originShift.x) < environ3.cupboardEquipmentList.get(i).width && (l.y - environ3.cupboardEquipmentList.get(i).originShift.y) < environ3.cupboardEquipmentList.get(i).height
                        && 0 <= l.y - environ3.cupboardEquipmentList.get(i).originShift.y) {
                    environ3.cupboardEquipmentList.get(i).borderColor = Color.red;
                } else {
                    environ3.cupboardEquipmentList.get(i).borderColor = Color.black;
                }

            }

            environ3.repaint();
        }
    }
    
    
    
    /**
     * This method is implementation of the abstract method mouseDragged
     * @param me
     * <ul><li>Moves equipments when mouse is dragged
     * <li>create a new equipment of the same type if the current
     * equipment reaches at the border of the cupboard and workbench
     * </ul>
     */

    @Override
    public void mouseDragged(MouseEvent me) {

        if (mode != storeMode) {
            return;
        }
        qw = environ3.getSize();
        for (int i = 0; i < environ3.getListSize(); i++) {
            /*
             * do not drag if either the equipment is not Draggable or the
             * equipment is not clicked
             */
            if (environ3.cupboardEquipmentList.get(i).isDraggable && environ3.cupboardEquipmentList.get(i).isClicked) {

                environ3.cupboardEquipmentList.get(i).setOriginShift(new Point2D.Float(me.getX(), me.getY())); //drag

                /*
                 * create a new equipment of the same type if the current
                 * equipment reaches at the border of the two panels
                 */
                if (me.getX() + environ3.cupboardEquipmentList.get(i).width >= qw.width && environ3.cupboardEquipmentList.get(i).isDrawable) {
                    environ3.cupboardEquipmentList.get(i).setDraggable(false);
                    // TO DO: delete the cupboardEquipmentList.get(i) copy of the object or try to use coords.clear()
                    environ3.cupboardEquipmentList.get(i).isDrawable = false;

                    Point2D.Float forNew = new Point2D.Float(environ3.cupboardEquipmentList.get(i).getOriginShift().x + (int) environ3.cupboardEquipmentList.get(i).width, environ3.cupboardEquipmentList.get(i).getOriginShift().y);

                    if ("Beaker".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                        holdTemporary = new Beaker(forNew, environ2);
                    } else if ("Bottle".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                        holdTemporary = new Bottle(forNew, environ2);
                    } else if ("Pipette".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                        holdTemporary = new Pipette(forNew, environ2);
                    } else if ("Burner".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                        try {
                            holdTemporary = new Burner(forNew, environ2);
                        } catch (IOException ex) {
                            Logger.getLogger(VirtualChemLab.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        burnerCount++;
                    } else if ("Burette".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                        holdTemporary = new Burette(forNew, environ2);
                        buretteCount++;
                    } else if ("TestTube".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                        holdTemporary = new TestTube(forNew, environ2);
                    } else if ("Flask".equals((environ3.cupboardEquipmentList.get(i)).getType())) {
                        holdTemporary = new Flask(forNew, environ2);
                    }

                    holdTemporary.setContents((environ3.cupboardEquipmentList.get(i)).getContents());
                    holdTemporary.setContentColor((environ3.cupboardEquipmentList.get(i)).getContentColor());
                    holdTemporary.setSize((environ3.cupboardEquipmentList.get(i)).getSize());

                    if (cupboardCurrentShelf > 3 && holdTemporary.getType() != "Burette") { ///
                        JOptionPane.showMessageDialog(this,
                                "Workbench is full");
                        break;

                    }


                    if ((!("Burette".equals((environ3.cupboardEquipmentList.get(i)).getType())) && !("Burner".equals((environ3.cupboardEquipmentList.get(i)).getType()))) || (("Burette".equals((environ3.cupboardEquipmentList.get(i)).getType())) && buretteCount <= numberOfBurettesAllowed) || (("Burner".equals((environ3.cupboardEquipmentList.get(i)).getType())) && burnerCount <= numberOfBurnersAllowed)) {
                        holdTemporary.setId(assignIdWorkbench++);
                        environ2.addApparatus(holdTemporary);
                        environ2.cupboardEquipmentList.get(environ2.getListSize() - 1).setDraggable(true);
                        environ2.cupboardEquipmentList.get(environ2.getListSize() - 1).isClicked = true;
                        environ2.cupboardEquipmentList.get(environ2.getListSize() - 1).setContents((environ3.cupboardEquipmentList.get(i)).getContents());

                        try {
                            robot = new Robot();
                            // NOTE: the first parameter in the next statement must be modified to ( starting x of the applet + environ3.getWidth() )
                            robot.mouseMove( (int)(getLocationOnScreen().getX())+environ3.getWidth() , me.getY()+environ2.getHeight()*95/580 );

                        } catch (AWTException ex) {
                            Logger.getLogger(VirtualChemLab.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (environ3.cupboardEquipmentList.get(i).type == "Burette") {
                        JOptionPane.showMessageDialog(this,
                                "Only one burette is allowed");
                    } else if (environ3.cupboardEquipmentList.get(i).type == "Burner") {
                        JOptionPane.showMessageDialog(this,
                                "Only one burner is allowed");
                    }
                    environ2.repaint();

                }
                break; // if multiple objects overlap, drag only one of them
            }
        }
        environ3.repaint(); // cupboard

        for (int i = 0; i < environ2.getListSize(); i++) {
            if (environ2.cupboardEquipmentList.get(i).isClicked && environ2.cupboardEquipmentList.get(i).isDraggable) // if workbench equipment is being dragged
            {
                environ2.cupboardEquipmentList.get(i).setOriginShift(new Point2D.Float(me.getX() - qw.width, me.getY()));
                break; /// 31 1006 :this is to allow only one equipment out of more than one possible overlapping equipments to move
            }  // if cupboard beaker is being dragged ends.....
        }
        environ2.repaint();

    }

   
    private void showError() {

        JTextArea instructions2 = new JTextArea(10, 2);
        JDialog dialog;
        jScrollPane1 = new JScrollPane(instructions2);


        for (int i = 0; i < XMLReader.Warnings.size(); i++) {
            instructions2.append("Warnings: " + XMLReader.Warnings.get(i) + "\n");

        }
        for (int i = 0; i < XMLReader.ErrorList.size(); i++) {
            instructions2.append("Error: " + XMLReader.ErrorList.get(i) + "\n");
        }

        Object msg[] = {"Instructions", jScrollPane1};
        JOptionPane op = new JOptionPane(
                msg,
                JOptionPane.ERROR_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                null);
        dialog = op.createDialog(this, "Errors in xml file");
        dialog.setBounds(100, 100, 500, 300);
        dialog.setVisible(true);

    }

    private void showErrorChemicals() {

        JTextArea instructions2 = new JTextArea(10, 2);
        JDialog dialog;
        jScrollPane1 = new JScrollPane(instructions2);


        for (int i = 0; i < StoreXml.Warnings.size(); i++) {
            instructions2.append("Warnings: " + StoreXml.Warnings.get(i) + "\n");

        }
        for (int i = 0; i < StoreXml.ErrorList.size(); i++) {
            instructions2.append("Error: " + StoreXml.ErrorList.get(i) + "\n");
        }

        Object msg[] = {"Instructions", jScrollPane1};
        JOptionPane op = new JOptionPane(
                msg,
                JOptionPane.ERROR_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                null);
        dialog = op.createDialog(this, "Errors in xml file");
        dialog.setBounds(100, 100, 500, 300);
        dialog.setVisible(true);

    }
}