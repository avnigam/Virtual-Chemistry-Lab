/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekshiksha.vclab.equipment;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import javax.swing.JButton;

/**
 *
 * @author harsh12
 */
public class Equipment implements EquipmentType {

    public int id;            //for holding index of equipments in equipment arraylist
    public String type;       //for type of equipment
    public int size;       // for size i.e.small,big,largs or extra large
    public String contents;   // for chemicals
    public float quantity;    // for volume of content
    public float strength;    // for molarity
    public Color borderColor; //for border color of the equipment
    public Color contentColor;//for chemical's color
    public Point2D.Float originShift; //for origin(initial position) of equipment
    public boolean isClicked;
    public boolean isDraggable;
    public boolean isDrawable;
    private float x;    // relative position in panel
    private float y;    // relative position in panel
    public float width;
    public float height;
    public float w;
    public float h;
    private boolean cupboardFlag; // true if eqipment in cupboard
    public double angle;
    public float sizearr[] = {20, 40, 60, 80, 100};  //new float[5];
    public int flameHeight;
    public double intensity;
    public boolean onState;
    public JButton up, down;
    public float fraction;
    public int j1;
    public boolean isFull;

    public boolean isIsFull() {
        return isFull;
    }
    public boolean isFill;
    public float capacity;
    //Parameterized constructor

    public Equipment(int id, String type, int size, String contents, float quantity, float strength, float x, float y, String position) {

        this.id = id;
        this.type = type;
        this.size = size;
        this.contents = contents;
        this.quantity = quantity;
        this.strength = strength;
        this.originShift.x = x;
        this.originShift.y = y;
    }
    /*
     * final constructor
     */

    public Equipment(int id, String eqname, Point2D.Float origin, float quantity, int size, String contents, Color contentColor, float strength) {
        this.id = id;
        type = eqname;
        originShift = origin;
        this.quantity = quantity;
        this.size = size;
        this.contents = contents;
        this.contentColor = contentColor;
        this.strength = strength;
        this.x = origin.x;
        this.y = origin.y;
        size = 1;
    }

    public Equipment(String eqname, Point2D.Float origin) {
        originShift = new Point2D.Float();
        originShift.x = origin.x;
        originShift.y = origin.y;
        type = eqname;
        strength = 0.0f;
        contents = "";
        contentColor = Color.BLACK;
        size = 1;

    }

    public Equipment(int id, String type, int size, String contents, float quantity, float strength, int number, String position) {

        this.id = id;
        this.type = type;
        this.size = size;
        this.contents = contents;
        this.quantity = quantity;
        this.strength = strength;
        this.size = 1;
    }

    public void setDraggable(boolean draggable) {
        isDraggable = draggable;
    }

    //Getter and Setter
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public float getStrength() {
        return strength;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    //All methods starts from here...
    public void initEquipment() {
    }

    public void drawEquipment(Graphics g) {
    }

    public void drawContents() {
    }

    public void executeMethods() {
    }

    public boolean contains(float x, float y) {
        float temp1 = x - originShift.x;
        float temp2 = y - originShift.y;
        if ((temp1 >= 0 && temp1 < width) && (temp2 >= 0 && temp2 < height)) {
            return true;
        }
        return false;
    }

    public void setOriginShift(Point2D.Float k) {
        originShift = k;
    }

    public Point2D.Float getOriginShift() {
        return originShift;
    }

    public boolean contains(Point2D.Float p) {
        return contains(p.x, p.y);

    }

    public void fill(float quantity) {
    }

    public void pour(double theta, Equipment eq) {
    }

    public void setIsDraggable(boolean isDraggable) {
        this.isDraggable = isDraggable;
    }

    public boolean getIsDraggable() {
        return isDraggable;
    }

    public void setCupboardFlag(boolean cupboardFlag) {
        this.cupboardFlag = cupboardFlag;
    }

    public boolean getCupboardFlag() {
        return cupboardFlag;
    }

    public void setContentColor(Color contentColor) {
        this.contentColor = contentColor;
    }

    public Color getContentColor() {
        return contentColor;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getIterationSteps(float quantity, Equipment equipment_d) {
        return quantity;
    }

    public void pour(float f) {
    }

    public void rotate(double d) {
    }

    public boolean contains(Point2D.Float c, Point2D.Float org, float wt, float ht) {

        if (c.x > org.x && c.x < wt + (int) (org.x) && c.y > org.y && c.y < ht + (int) (org.y)) {
            return true;
        } else {
            return false;
        }

    }

    public void fill(Graphics graphics, Equipment e2) {
    }

    public void pour(Graphics graphics, Equipment e2) {
    }

    public void pour(Graphics graphics) {
    }
}