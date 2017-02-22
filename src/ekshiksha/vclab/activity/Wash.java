/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.activity;

import ekshiksha.vclab.equipment.Equipment;
import ekshiksha.vclab.equipment.Equipments;
import java.awt.Color;
import java.awt.geom.Point2D;

/**
 *
 * @author mayank
 */
public class Wash extends Activity {

    

    public Wash(int id,Point2D.Float fromdest,float quantity){//,String contents ,Color contentColor){
        super("Wash");
        this.id= id;
        this.fromDest = fromdest;
        //this.content=contents;
       // this.contentColor=contentColor;
        this.quantity=quantity;

    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    @Override
    public void execute(){
        Equipment equip = Equipments.getEquipmentByID(id);


    }

}
