/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.activity;

import ekshiksha.vclab.equipment.Equipment;
import ekshiksha.vclab.equipment.Equipments;

/**
 *
 * @author mayank
 */
public class Label extends Activity {

    public String label;
    public int id;

    public Label(String type, int id, String label){
        super(type);
        this.id =id;
        this.label=label;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
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
