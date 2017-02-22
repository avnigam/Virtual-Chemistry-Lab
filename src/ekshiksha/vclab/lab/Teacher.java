package ekshiksha.vclab.lab;

import java.util.Random;
import java.awt.Button;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

import blackboard.*;
import ekshiksha.vclab.header.Header;

/**
 *
 * @author harsh12
 *
 */
public class Teacher implements Runnable, ActionListener {

    /**
     * Variable to control number of digits to be added.
     */
    private int addDigits;                  /*
     * Variable to control number of digits to be added
     */

    /**
     * Maximum number to be added (9999 if addDigits = 4).
     */
    private int maxNumber;                  /*
     * maximum Number to be added
     */

    /**
     * Variable to stop current thread (terminate).
     */
    boolean stopRun;                        /*
     * Variable to stop current thread (terminate)
     */

    /**
     * Variable to restart simulation with a new mode/problem.
     */
    boolean restart;                        /*
     * Variable to restart simulation with a new mode/problem.
     *
     * /**
     * Blackboard Resource on which to write.
     */

    Blackboard bb;                          /*
     * Blackboard Resource on which to write
     */

    /**
     * Depending on blackboard size, we wish to position numbers and text.
     * Logical number of lines on blackboard.
     */
    /**
     * Depending on blackboard size, we wish to position numbers and text.
     * Logical number of lines on blackboard.
     */
    private final static int bbLines = 20;       /*
     * Logical number of lines on blackboard
     */

    /**
     * Depending on blackboard size, we wish to position numbers and text.
     * Logical number of chracters per line.
     */
    private final static int bbCharsPerLine = 40;/*
     * Logical number of characters per line
     */

    /**
     * Pixel gap between two logical lines.
     */
    private int lineGapY;                   /*
     * Y Position offset between two lines
     */

    /**
     * Pixel gap between two characters.
     */
    private int charGapX;                   /*
     * X Position offset between two characters
     */

    /**
     * Layout Variable. X Position of numbers(MSB), carry and result.
     */
    private int topNumberPositionX;         /*
     * X Position of numbers(MSB), carry and result
     */

    /**
     * Layout Variable. Y Position of numbers(MSB), carry and result.
     */
    private int topNumberPositionY;         /*
     * Y Position of first number
     */

    /**
     * Layout Variable. X Position of workarea to show addition of single
     * column. (Y position depends on topNumberPositionY and lineGapY).
     */
    private int workAreaX;                  /*
     * X Position of Work Area
     */

    /**
     * Layout Variable. X Position of leftmost button (repeat, step, auto and
     * done buttons).
     */
    private int buttonPositionX;            /*
     * X Position of Buttons
     */

    /**
     * Layout Variable. Y Position of all buttons (repeat, step, auto and done
     * buttons).
     */
    private int buttonPositionY;            /*
     * Y Position of Buttons
     */

    /**
     * To differentiate between step-by-step and automatic demonstration.
     */
    private boolean stepMode;               /*
     * Whether demos should run in step mode
     */

    /**
     * To get out of the delay loop which waits on pressing step button.
     */
    private boolean nextPlay;               /*
     * Whether next Step is to be shown (controls step by step execution)
     */

    /**
     * To get out of the delay loop which waits on pressing step button.
     */
    private boolean donePressed;            /*
     * Whether done button is pressed
     */

    /**
     * Random number generator.
     */
    private Random random;                  /*
     * random number generator
     */

    /**
     * Single thread to control blackboard display and sleep to slow drawing.
     */
    public Thread animation;               /*
     * To animate the actions while teaching
     */

    /**
     * First number to be added.
     */
    int firstNumber;                        /*
     * First Number
     */

    /**
     * Second number to be added.
     */
    int secondNumber;                       /*
     * Second Number
     */

    /**
     * Result of addition.
     */
    int result;                             /*
     * Result
     */

    /**
     * Mode of teaching. demoMode = 1: Demonstration mode to show concept
     * interactionMode = 2: Interaction mode to solve user examples guidedMode =
     * 3: Guided mode to solve examples in step by step manner testMode = 4:
     * Test mode to assess understanding of concept
     */
    int mode;                               /*
     * Current phase of Teacher class
     */

    /**
     * Line thickness.
     */
    private final static float lineThickness = 1.0f;  /*
     * default line thickness
     */

    /*
     * Buttons
     */
    /**
     * Button repeat : show next problem for solving.
     */
    Button repeat;                          /*
     * Button to go for next sum
     */

    /**
     * Button step : (In step mode) Go for next step after understanding the
     * present step.
     */
    Button step;                            /*
     * Button to go for next step
     */

    /**
     * Button automatic : Go for next step automatically (after a delay).
     */
    Button automatic;                       /*
     * Button to go for automatic mode
     */

    /**
     * Button done : Pressed by student after supplying input.
     */
    Button done;                            /*
     * Button to indicate user action is complete
     */

    /*
     * Statistics
     */
    /**
     * Number of demonstration examples.
     */
    private int demoExamples;               /*
     * Number of demonstration examples
     */

    /**
     * Number of interaction examples.
     */
    private int interactionExamples;        /*
     * Number of interaction examples
     */

    /**
     * Number of guided examples.
     */
    private int guidedExamples;             /*
     * Number of guided examples
     */

    /**
     * Number of guided examples solved correctly.
     */
    private int guidedCorrect;              /*
     * Number of guided examples solved correctly
     */

    /**
     * Number of test examples.
     */
    private int testExamples;               /*
     * Number of test examples
     */

    /**
     * Number of test examples solved correctly.
     */
    private int testCorrect;                /*
     * Number of test examples solved correctly
     */

    /*
     * Error Log
     */
    /**
     * Array to store errors in guided and test mode.
     */
    /**
     * Number of errors in array.
     */
    private int numberErrors;               /*
     * Number of errors in the errorLog
     */

    /**
     * Delay defines. All delays in milliseconds. sDelay=100: Short Delay.
     * mDelay=500: Medium Delay. lDelay=1000: Long Delay. xlDelay=10000: Extra
     * long Delay.
     */
    private final static int sDelay = 100;    /*
     * Short Delay
     */

    private final static int mDelay = 500;    /*
     * Medium Delay
     */

    private final static int lDelay = 1000;   /*
     * Long Delay
     */

    private final static int xlDelay = 10000; /*
     * Extra long Delay
     */

    /*
     * Mouse Tracking
     */
    /**
     * X position of Mouse/cursor
     */
    int mouseX;                             /*
     * X position of Mouse/cursor
     */

    /**
     * Y position of Mouse/cursor
     */
    int mouseY;                             /*
     * Y position of Mouse/cursor
     */

    private final static int page[] = new int[10];
    private Header head;
    private String title;
    private String author_Name;
    private String level;
    private int marks;
    private String description;
    private String student_Name;
    private String instruction;
    private String ins[] = new String[100];
    int f = 0;

    /**
     *
     * Constructor for Teacher. This method first gets the dimensions of the
     * blackboard resource. It plans where to position various elements for
     * teaching and interaction. It initializes various teaching objects
     * (buttons etc.).
     *
     * @param bb Handle to blackboard instance.
     * @param numberDigits Maximum number of digits in numbers to be added.
     *
     */
    public Teacher(Blackboard bb) {
        this.mode = 1;
        this.bb = bb;


        /*
         * Create errorLog
         */

        numberErrors = 0;
        /*
         * Teacher decides where to write everything
         */
        setLayoutVariables();
        /*
         * Set stepMode to default (true)
         */
        stepMode = true;
        /*
         * Set stepMode to default (true)
         */
        restart = true;
        stopRun = false;
        for (int i = 0; i < 10; i++) {
            page[i] = i + 1;
        }

        /*
         * Teacher Objects (buttons etc.) are initialised
         */
        initObjects();
        title = "";
    }
    /**
     *
     * This method first gets the dimensions of the blackboard resource. It then
     * decides where to position various elements for teaching and interaction.
     * Finally, it sets the character size of the blackboard text.
     *
     */
    int topLeftX;                           /*
     * Left Top X of blackboard
     */

    int topLeftY;                           /*
     * Left Top Y of blackboard
     */

    int width;                              /*
     * Width of blackboard
     */

    int height;

    private void setLayoutVariables() {
        /*
         * Height of blackboard
         */

        /*
         * Get Blackboard dimensions and position
         */
        topLeftX = bb.getTopLeftX();
        topLeftY = bb.getTopLeftY();
        width = bb.getBlackboardWidth();
        height = bb.getBlackboardHeight();

        /*
         * Set Layout variables for drawing
         */
        /*
         * 10 Lines and 20 characters per line
         */
        lineGapY = height / bbLines;
        charGapX = width / bbCharsPerLine;
        /*
         * X Position of numbers(MSB), carry and result (6 chars from right)
         */
        topNumberPositionX = 27 * charGapX;
        /*
         * Y Position of first number (3rd line from top)
         */
        topNumberPositionY = (int) (5.5 * lineGapY);
        /*
         * X Position of Work Area
         */
        workAreaX = topNumberPositionX - (2 * charGapX);
        /*
         * X Position of Buttons (2 char from left)
         */
        buttonPositionX = topLeftX + (2 * charGapX);
        /*
         * Y Position of Buttons (1 line from bottom)
         */
        buttonPositionY = topLeftY + height - (2 * lineGapY);

        /*
         * Set Blackboard character size
         */
        bb.setFontSize((int) (lineGapY * 0.75));

        /*
         * Add developer credit
         */
        bb.setDeveloperName("Virtual Chemistry Lab");

        /*
         * Add permanent topic
         */
        bb.setForegroundColor(Color.GREEN);
        bb.setLineThickness(2.0f);
        // bb.drawPermanentRectangle(topLeftX, topLeftY, width, height, null);
        //for(int i=0;i<26;i++)
        // bb.writePermanentString("0123456789123456789012345",topLeftX , topLeftY+(int)(lineGapY*0.75), false);
        //bb.drawPermanentLine(topLeftX+8*charGapX, topLeftY +2* lineGapY, topLeftX+(int)(33.5*charGapX) , topLeftY + 2*lineGapY, Color.GREEN, 2.0f);
        bb.setFontSize((int) (lineGapY * 0.7));
        bb.setLineThickness(1.0f);
        bb.setForegroundColor(Color.WHITE);

    }

    /**
     *
     * This method changes the mode of teaching (demo, automatic, guided, test).
     * It sets the mode variable and restarts the teaching by setting restart
     * flag.
     *
     * @param mode New phase of teaching.
     *
     */
    public void changeMode(int mode) {
        this.mode = mode;
        bb.clean();
        restart = true;
    }

    /**
     *
     * This method initialises the objects (buttons etc.) required for teaching.
     *
     */
    private void initObjects() {
        /*
         * Get a new random number generator
         */
        random = new Random();

        /*
         * initioalise errors
         */
        numberErrors = 0;

        /*
         * Load all possible buttons and other accessories and mark them as
         * invisible
         */
        repeat = new Button("repeat");
        repeat.setFont(new Font("Arial", Font.PLAIN, 12));
        step = new Button("step");
        step.setFont(new Font("Arial", Font.PLAIN, 12));
        automatic = new Button("auto");
        automatic.setFont(new Font("Arial", Font.PLAIN, 12));
        done = new Button("done");
        done.setFont(new Font("Arial", Font.PLAIN, 12));

        /*
         * Load Action Commands for the above buttons
         */
        repeat.setActionCommand("repeat");
        step.setActionCommand("step");
        automatic.setActionCommand("automatic");
        done.setActionCommand("done");

        /*
         * Load Action Listeners for the above buttons
         */
        repeat.addActionListener(this);
        step.addActionListener(this);
        automatic.addActionListener(this);
        done.addActionListener(this);

        /*
         * Place the buttons in appropriate places
         */
        repeat.setBounds(buttonPositionX, buttonPositionY, (3 * charGapX), lineGapY);
        step.setBounds(buttonPositionX + (4 * charGapX), buttonPositionY, (3 * charGapX), lineGapY);
        automatic.setBounds(buttonPositionX + (8 * charGapX), buttonPositionY, (3 * charGapX), lineGapY);
        done.setBounds(buttonPositionX + (12 * charGapX), buttonPositionY, (3 * charGapX), lineGapY);

        /*
         * Mark Buttons as invisible
         */
        repeat.setVisible(false);
        step.setVisible(false);
        automatic.setVisible(false);
        done.setVisible(false);

        /*
         * Mark Buttons as disabled
         */
        repeat.setEnabled(false);
        step.setEnabled(false);
        automatic.setEnabled(false);
        done.setEnabled(false);

        /*
         * Change the cursor when enabled
         */
        repeat.setCursor(new Cursor(Cursor.HAND_CURSOR));
        step.setCursor(new Cursor(Cursor.HAND_CURSOR));
        automatic.setCursor(new Cursor(Cursor.HAND_CURSOR));
        done.setCursor(new Cursor(Cursor.HAND_CURSOR));

        /*
         * Add Buttons to blackboard
         */
        bb.add(repeat);
        bb.add(step);
        bb.add(automatic);
        bb.add(done);

    }

    /**
     *
     * Method to perform actions. Handles repeat, step, automatic and done
     * actions. All actions reset flags and return. further processing is
     * controlled by the flags.. The repeat action restarts teaching in the same
     * mode (with a new problem). The step action sets the step mode and
     * continues teaching. The automatic action resets the step mode and
     * continues teaching. The done action resets accept input mode and
     * continues to process unser input.
     *
     * @param e Action event.
     *
     */
    public void actionPerformed(ActionEvent e) {
        mouseY = buttonPositionY + (lineGapY / 2);
        if (e.getActionCommand().equals("repeat")) {
            mouseX = buttonPositionX + 2 * charGapX;
            restart = true;
        } else if (e.getActionCommand().equals("step")) {
            mouseX = buttonPositionX + 6 * charGapX;
            stepMode = true;
            nextPlay = true;
        } else if (e.getActionCommand().equals("automatic")) {
            mouseX = buttonPositionX + 10 * charGapX;
            stepMode = false;
            nextPlay = true;
        } else if (e.getActionCommand().equals("done")) {
            mouseX = buttonPositionX + 14 * charGapX;
            donePressed = true;
        }
    }

    /**
     *
     * Method to reset teacher. Restarts teaching from beginning.
     *
     */
    public void reset() {
        numberErrors = 0;
        stepMode = true;
        initObjects();
        changeMode(page[0]);
        restart = true;
    }

    /**
     *
     * Method to start teaching.
     *
     */
    public void start() {
        if (animation == null) {
            try {
                animation = new Thread(this);
            } catch (Exception e) {
            }
        }
        if (animation != null) {
            animation.start();
        }
    }

    /**
     *
     * Method to pause teaching.
     *
     */
    public void stop() {
        try {
            if (animation != null && animation.isAlive()) {
                animation.interrupt();
            }
        } catch (Exception e) {
        }
    }

    /**
     *
     * Method to run single animation Thread. This method has an unending loop.
     * In the loop: If restart flag is set, the problem is solved followed by a
     * delay till repeat or any other user action
     *
     */
    public void run() {

        /*
         * Never Ending Loop
         */
        while (stopRun == false) {
            if (restart == true) {
                show();
            }

            delaySimple(1 * mDelay);
        }
    }

    /**
     *
     * This method is used as a wrapper to thread sleep. The solveProblem method
     * calls for a delay for user to absorb actions. If this is interrupted, it
     * results in unexpected behaviour. This wrapper traps the interrupt
     * gracefully. It also gets rid of the warning for calling Thread.sleep in a
     * loop.
     *
     */
    private void delaySimple(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) { /*
             * Do Nothing
             */ }
    }






    /**
     *
     * This method displays the header details on the blackboard(perform mode and play mode).
     * <ul><li>The method first calls parsetitle() and parseinstruction() to obtain the required strings to be displayed on blackboard
     * <li>On clicking the next button, blackboard is switched to next page.
     * </ul>
     *
     */
    public void show() {

        restart = false;
        String s;
        bb.clean();

        int size;
        int y;
        int i = 0;
        int j = 0;
        if (mode == 1) {
            if (title == "") {
                return;
            }
            ins = parsetitle(title, 11);
            size = Integer.parseInt(ins[0]);
            y = 2 * lineGapY;
            bb.setFontSize((int) (1 * lineGapY));
            for (i = 1; i <= size; i++) {
                bb.writeString(ins[i], topLeftX + 5 * charGapX, y, false);
                y += (int) (1.5 * lineGapY);
            }
        } else if (mode == 2) {
            if (author_Name == "" || level == "" || description == "") {
                return;
            }
            String des[] = new String[100];
            bb.setFontSize((int) (0.8 * lineGapY));
            des = parseinstruction(description, 18);
            size = Integer.parseInt(des[0]);
            bb.writeString("Author :", topLeftX + 5 * charGapX, 1 * lineGapY, false);
            bb.writeString(author_Name, topLeftX + 5 * charGapX, 2 * lineGapY, false);
            bb.setFontSize((int) (0.8 * lineGapY));
            bb.writeString("Level :", topLeftX + 5 * charGapX, 4 * lineGapY, false);
            bb.writeString(level, topLeftX + 20 * charGapX, 4 * lineGapY, false);
            bb.writeString("Description :", topLeftX + 5 * charGapX, 6 * lineGapY, false);
            y = 7 * lineGapY;
            bb.setFontSize((int) (0.7 * lineGapY));
            for (i = 1; i <= size; i++) {
                bb.writeString(des[i], topLeftX + 5 * charGapX, y, false);
                y += (int) (1.5 * lineGapY);
            }
        } else if (mode >= 3 && mode < 10) {

            ins = parseinstruction(instruction, 20);
            size = Integer.parseInt(ins[0]);
            y = 3 * lineGapY;
            j = mode - 3;


            if (f == 0 || mode == 3) {
                bb.setFontSize((int) (0.75 * lineGapY));
                bb.writeString("-> ", topLeftX, 3 * lineGapY, false);
                f++;
            }

            outerloop:
            for (i = (j * 20) + 1; i < (j * 20) + 21 && i <= size; i++) {

                bb.setFontSize((int) (0.9 * lineGapY));
                bb.writeString("  INSTRUCTIONS", topLeftX + (4 * charGapX), topLeftY + 10, false);
                bb.setFontSize((int) (0.75 * lineGapY));
                s = ins[i];
                bb.writeString(s, topLeftX + 5 * charGapX, y, false);
                if (s != null && !s.endsWith(".")) {
                    y += (int) (0.75 * lineGapY);
                } else {
                    y += (int) (1.5 * lineGapY);
                    bb.writeString("-> ", topLeftX, y, false);
                }

                if (i == 99) {
                    break outerloop;
                }
            }

        }

    }
    
    /**
     * Breaks the instructions for the experiment into strings such that each string is to be displayed on separate line
     * @param s string to be split
     * @param len characters per line to be displayed on blackboard
     * @return 
     */
    public String[] parseinstruction(String s, int len) {
        String instructionString = s;

        String instarray[] = new String[100];
        int charperline = len;
        int j = 1;
        int count = 0;
        int ch = charperline;
        int p = 0;
        for (int i = 1; i < instructionString.length(); i++) {

            count++;
            if (count == charperline - 1) {

                if (instructionString.charAt(p) != ' ') {
                    instarray[j] = instructionString.substring(p, i);
                } else {
                    instarray[j] = instructionString.substring(p + 1, i);
                }

                if (instructionString.charAt(i - 1) == ' ' && instructionString.charAt(i) == ' ') {

                    j++;
                } else if (instructionString.charAt(i) != ' ' || instructionString.charAt(i - 1) != ' ') {

                    instarray[j] = instarray[j].concat("-");
                    j++;
                } else {
                    instarray[j] = instarray[j].concat(Character.toString(instructionString.charAt(charperline - 1)));
                    j++;
                }
                p = i;
                count = 0;
                if (j < instructionString.length() / charperline) {
                    ch = j * charperline;
                }
            } else if (i < instructionString.length() - 1) {
                if (instructionString.charAt(i) == '.' && instructionString.charAt(i + 1) == ' ') {
                    instarray[j] = instructionString.substring(p, i + 1);
                    p = i + 1;
                    count = 0;
                    j++;
                }
            }

        }
        instarray[j] = instructionString.substring(p, instructionString.length());

        for (int i = 1; i <= j; i++) {
            if (instarray[i].charAt(instarray[i].length() - 1) == '-' && instarray[i].charAt(instarray[i].length() - 2) == ' ') {
                instarray[i] = instarray[i].replace(" -", " ");
            }

            instarray[0] = Integer.toString(j);
        }



        return instarray;
    }

    public void setHeader(Header head) {
        this.head = head;
        title = head.getTitle();
        author_Name = head.getAuthor_Name();
        level = head.getLevel();
        marks = head.getMarks();
        description = head.getDescription();
        student_Name = head.getStudent_Name();
        instruction = head.getInstruction();


    }

    /**
     * Breaks the title of the experiment into strings such that each string is to be displayed on separate line
     * @param s string to be split
     * @param len characters per line to be displayed on blackboard
     * @return 
     */
    public String[] parsetitle(String s, int len) {

        int count = 0;
        int j = 0;
        int p = 0;
        String t[] = new String[20];
        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == ' ') {
                j++;
                t[j] = s.substring(p, i);
                p = i + 1;
            }
        }


        j++;
        t[j] = s.substring(p, s.length());
        t[0] = Integer.toString(j);
        return t;

    }
}
/*
 * End of class Teacher
 */