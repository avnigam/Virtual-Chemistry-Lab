package ekshiksha.vclab.equipment;

import ekshiksha.vclab.lab.Cupboard;
import ekshiksha.vclab.lab.WorkBench;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;


/**
 *
 * @author Pankaj,Aviral,Kaushik
 */
public class Burette extends Equipment implements ActionListener
{

    Equipment filling;
    WorkBench environ;
    private Vector< Point2D.Float> coords= new Vector<Point2D.Float>();
    private Vector< Point2D.Float> stand= new Vector<Point2D.Float>();
    private Vector< Point2D.Float> mark= new Vector<Point2D.Float>();
    float h, w, h1, w1;
    private float hx, wx;
    Polygon chemical;
    private JTextField text;
    boolean isPour = false;
    Point2D.Float mouthLeft = new Point2D.Float(0, 1);
    Point2D.Float mouthRight = new Point2D.Float(18, 1);
    Cupboard environ2;

    /**
     * The constructor for WorkBench.
     * @param id : id of the equipment.
     * @param eqname : type of the equipment.
     * @param origin : initial position of the equipment.
     * @param quantity : initial quantity.
     * @param size : size of the equipment.
     * @param contents : name of the chemical present initially in the equipment.
     * @param contentColor : colour of the chemical present initially in the equipment.
     * @param strength : strength of the chemical present initially in the equipment.
     * @param environ : object of WorkBench.java
     */
    public Burette(int id, String eqname, Point2D.Float origin, float quantity, int size, String contents, Color contentColor, float strength, WorkBench environ)
    {
        super(id, eqname, origin, quantity, size, contents, contentColor, strength);
        this.setCupboardFlag(false);
        isDrawable = true;
        this.environ = environ;
        h1 = environ.getHeight();
        w1 = environ.getWidth();
        capacity = 50.0f;
        this.quantity = 0.0f;
        fraction = 0.0f;
        down = new JButton();
        text = new JTextField();
        down.addActionListener(this);
        down.setEnabled(false);
        initEquipment();
    }

    /**
    * The constructor for store.
    * @param origin : initial position of the equipment.
    * @param e : object of Cupboard.java 
    */
    public Burette(Point2D.Float origin, Cupboard e)
    {

        super("Burette", origin);

        this.setCupboardFlag(true);
        isDraggable = false;
        isDrawable = true;
        environ2 = e;
        h1 = environ2.getHeight();
        w1 = environ2.getWidth();
        down = new JButton(new ImageIcon("src/ekshiksha/vclab/equipment/down.png"));
        text = new JTextField();
        down.addActionListener(this);
        down.setEnabled(false);
        initEquipment();
    }

    /**
     * this function redraws the equipment for the present size of the window and the current angle. 
     */
    @Override
    public void initEquipment()
    {



        boolean temp = getCupboardFlag();
        if (!temp) {
            h = (float) ((double) (environ.getHeight()) / (double) 600.0);
            w = (float) ((double) (environ.getWidth()) / (double) 600.0);
        } else {
            h = (float) ((double) (environ2.getHeight()) / (double) 600.0);
            w = (float) ((double) (environ2.getWidth()) / (double) 600.0);
        }
        coords = new Vector< Point2D.Float>();

        coords.clear();
        mark.clear();
        stand.clear();

        if (!temp) {
            environ.add(down);
            environ.add(text);
        }

        down.setBounds((int) (mouthLeft.x + originShift.x -25 * w), (int) (mouthLeft.y + originShift.y + 170 * h), (int) (20 * w), (int) (20 * h));
        text.setBounds((int) (mouthLeft.x + originShift.x - 41), (int) (mouthLeft.y + originShift.y+120), 40, 25);

        /*burette*/
        coords.addElement(new Point2D.Float((mouthLeft.x * w), ((mouthLeft.y) * h)));
        coords.addElement(new Point2D.Float((mouthLeft.x * w), ((mouthLeft.y + 179) * h)));
        coords.addElement(new Point2D.Float(((mouthLeft.x + 4) * w), ((mouthLeft.y + 192) * h)));
        coords.addElement(new Point2D.Float(((mouthLeft.x + 4) * w), ((mouthLeft.y + 197) * h)));
        coords.addElement(new Point2D.Float(((mouthLeft.x + 6) * w), ((mouthLeft.y + 197) * h)));
        coords.addElement(new Point2D.Float(((mouthLeft.x + 6) * w), ((mouthLeft.y + 192) * h)));
        coords.addElement(new Point2D.Float(((mouthLeft.x + 10) * w), ((mouthLeft.y + 179) * h)));
        coords.addElement(new Point2D.Float(((mouthLeft.x + 10) * w), ((mouthLeft.y) * h)));

 
        
        /*markings*/
        float i;
        for ( i = (mouthLeft.y + 179); i >= 3; i -= 3.6) {
            mark.addElement(new Point2D.Float(((mouthLeft.x + 10) * w), (i * h)));
            mark.addElement(new Point2D.Float(((mouthLeft.x + 8) * w), (i * h)));
            mark.addElement(new Point2D.Float(((mouthLeft.x + 10) * w), (i * h)));
        }

        /*
         * coords for stand
         */

        for ( i = 176; i <= 179;i += 0.25) {
            stand.addElement(new Point2D.Float((-10 * w), (i * h)));
            stand.addElement(new Point2D.Float((0 * w), (i * h)));
        } 
        
        for (i = 50; i <= 53;) {
            stand.addElement(new Point2D.Float((8 * w), (i * h)));
            stand.addElement(new Point2D.Float((52 * w), (i * h)));
            i += 0.25;
        }
        stand.addElement(new Point2D.Float((35 * w), (float)((i-0.25) * h)));
        for (i = 35; i < 42;) {
            stand.addElement(new Point2D.Float((i * w), (6 * h)));
            stand.addElement(new Point2D.Float((i * w), (230 * h)));
            i += 0.25;
        }
        stand.addElement(new Point2D.Float((35 * w), (float)((15) * h)));
        for (i = 30; i <= 45;) {
            stand.addElement(new Point2D.Float((i * w), (0 * h)));
            stand.addElement(new Point2D.Float((i * w), (15 * h)));
            i += 0.25;
        }
        stand.addElement(new Point2D.Float((35 * w), (float)((15) * h)));
        stand.addElement(new Point2D.Float((35 * w), (float)((230) * h)));
        for (i = 230; i <= 240;) {
            stand.addElement(new Point2D.Float((26 * w), (i * h)));
            stand.addElement(new Point2D.Float((50 * w), (i * h)));
            i += 0.25;
        }
        stand.addElement(new Point2D.Float((41 * w), (float)((230) * h)));
        stand.addElement(new Point2D.Float((35 * w), (float)((230) * h)));
        stand.addElement(new Point2D.Float((41 * w), (float)((240) * h)));

        this.width=(18*w);
        this.height=(179*h);
        
        if (!isDrawable) 
        {
            coords.clear();
            mark.clear();
            stand.clear();
        }
    }

    /**
     * function for rounding a number.
     * @param Rval: value to be rounded off.
     * @param Rpl: number of places.
     * @return : rounded value.
     */
    public static float Round(float Rval, int Rpl)
    {
        float p = (float) Math.pow(10, Rpl);
        Rval = Rval * p;
        float tmp = Math.round(Rval);
        return (float) tmp / p;
    }

    /**
     * This function  draws lines between the points plotted in chooseSize(or initEquipment), thereby drawing the equipment.
     * @param g : Object of Graphics class. 
     */
    @Override
    public void drawEquipment(Graphics g)
    {
        int iter;
        g.setColor(borderColor);
        if (!isDrawable) {
            return;
        }
        
        iter=coords.size() - 1;
        for (int i = 0; i < iter; i++)
        {
            Point2D.Float s = coords.elementAt(i);
            Point2D.Float d = coords.elementAt(i + 1);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Float(s.x + originShift.x, s.y + originShift.y, d.x + originShift.x, d.y + originShift.y));
        }
        
        iter=stand.size() - 1;
        for (int i = 0; i < iter; i++) 
        {
            if(i==25)
                continue;
            Point2D.Float s = stand.elementAt(i);
            Point2D.Float d = stand.elementAt(i + 1);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Float(s.x + originShift.x, s.y + originShift.y, d.x + originShift.x, d.y + originShift.y));
        }
 
        if (fraction > 0)
        {
            drawContents(g);
        }
        if(fraction==0)
            text.setText(String.valueOf((float) (Math.round(quantity * 100.0f) / 100.0f)));
        
        iter=mark.size() - 1;
        for (int i = 0; i < iter; i++) 
        {
            Point2D.Float s = mark.elementAt(i);
            Point2D.Float d = mark.elementAt(i + 1);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(new Line2D.Float(s.x + originShift.x, s.y + originShift.y, d.x + originShift.x, d.y + originShift.y));
        }
    }

    /**
     * Sets the quantity to the given value.
     * @param quant : the value to which the quantity of the equipment has to be set.
     */
    @Override
    public void setQuantity(float quant) 
    {
        fraction = quant/ capacity;
        if(fraction<0)fraction=0.0f;
        quantity=fraction*capacity;
        

    }

    /**
     * This function fills the equipment by the given quantity.
     * @param quant : quantity to be filled. 
     */
    @Override
    public void fill(float quant) {

        fraction += (quant / capacity);
        if(fraction<0)fraction=0.0f;
        quantity = fraction * capacity;

    }

    /**
     * This function draws the liquid contained in the equipment according to the quantity.
     * it is also responsible for reducing the quantity of the current equipment and filling the destination equipment by the appropriate quantity.  
     * @param g : Object of Graphics class.
     */
    public void drawContents(Graphics g) 
    {
        int i;
        chemical=new Polygon();
        Point2D.Float temp=new Point2D.Float(0.0f,0.0f);
        temp=coords.get(0);
        chemical.addPoint((int)(temp.x+ originShift.x)+1, (int)(((coords.get(0).y+ coords.get(1).y)*(1.0f-fraction))+originShift.y));
        for(i=1;i<coords.size()-1;i++)
        {
            if(i==1||i==2)
                temp=new Point2D.Float(coords.get(i).x+1,coords.get(i).y);
            else
                temp=coords.get(i);
            chemical.addPoint((int)(temp.x+ originShift.x), (int)(temp.y+ originShift.y));
        }
        temp=coords.get(i);
        chemical.addPoint((int)(temp.x+ originShift.x), (int)(((coords.get(0).y+ coords.get(1).y)*(1.0f-fraction))+originShift.y));
        g.setColor(this.contentColor);
        g.fillPolygon(chemical);
        g.setColor(Color.black);
        if (fraction <= 1 && fraction>0)
        {
            text.setText(String.valueOf((float) (Math.round(quantity * 100.0f) / 100.0f)));
        } 
    }
    
    /**
     * removes 1ml of liquid from the current equipment and fills it in the destination equipment.
     * @param g : Object of Graphics class.
     */
    @Override
    public void pour(Graphics g) 
    {
        fraction=fraction-(1.0f/capacity);
        if(fraction<0)fraction=0.0f;
        quantity=fraction*capacity;
        if (isFill == true) 
        {
            filling.fill(1.0f);
            filling.setContentColor(this.getContentColor());
        }
    }

    /**
     * this function calculates the number of times pour has to be called for pouring the given quantity.
     * @param quant : quantity to be poured.
     * @param eq : object of the destination equipment.
     * @return : no. of steps.
     */
    @Override
    public float getIterationSteps(float quant, Equipment eq) 
    {
        filling = eq;
        isFill = true;
        return quant;
    }

    /**
     * Used in perform mode, to pour into another equipment on button click.
     * @param ae : Object of action event.
     */
    public void actionPerformed(ActionEvent ae)
    {
        JButton btn = (JButton) ae.getSource();
        btn.setBackground(Color.blue);
        if (ae.getSource() == down) 
        {
            if (fraction > 0)
            {
                pour(environ.getGraphics());
                environ.e1.fill(1.0f);
                environ.repaint();
                if (fraction <= 1)
                {
                    text.setText(String.valueOf((float) (Math.round(quantity * 100.0f) / 100.0f)));
                }
            }
        }
    }

    /**
     * Called in the constructor, it draws the equipment according to the given size and calculates all the required fields.
     * @param size : the size of the equipment to be drawn.
     */
    private void chooseSize(int size) 
    {
    }

    /**
     * Sets the size of the equipment.
     * @param size : size of the equipment.
     */
    @Override
    public void setSize(int size) 
    {
        this.size = size;
        chooseSize(size);
    }
    

}