/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.activity;

import ekshiksha.vclab.equipment.*;
import java.awt.Point;
import java.awt.geom.Point2D;
/**
 *
 * @author mayank
 */
public class Move extends Activity {

  
    public float x; //for new location
    public float y; //for new location
    public int number;
    public String position;
  

       
    public Move(int id,Point2D.Float fromDest,Point2D.Float toDest)
    {
        super("Move");
        this.id=id;
        this.fromDest=fromDest;
        this.toDest=toDest;
    }

    public void setId(int id){
        this.id= id;
    }
    public int getId(){
        return id;
    }

    public void setX(int x){
        this.x= x;
    }
    public float getX(){
        return x;
    }

    public void setY(int y){
        this.y= y;
    }
    public float getY(){
        return y;
    }

    public void setNumber(int number){
        this.number= number;
    }
    public int getNumber(){
        return number;
    }

    public void setPosition(String position){
        this.position= position;
    }
    public String getPosition(){
        return position;
    }
    

    @Override
    public void execute() {
        Equipment equip = Equipments.getEquipmentByID(id);
       
        
    }

}
