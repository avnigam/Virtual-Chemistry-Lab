/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// Container class for equipments
package ekshiksha.vclab.equipment;

import java.util.*;

/**
 *
 * @author mayur
 */
public class Equipments {

    static ArrayList<Equipment> equipments = new ArrayList<Equipment>();

    public static void add(Equipment equipment) {
        equipments.add(equipment);
    }

    public static void remove(Equipment equipment) {
        equipments.remove(equipment);
    }

    public static ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    public static void setEquipments(ArrayList<Equipment> equipmentList) {
        equipments = equipmentList;
    }

    //method to get the equipment by id to use for different activities
    public static Equipment getEquipmentByID(int id) {

        Equipment equip = null;
        for (int i = 0; i < equipments.size(); i++) {
            if (id == equipments.get(i).getId()) {
                equip = equipments.get(i);
                break;
            }
        }
        return equip;
    }

    public static void removeAll() {
        int size = equipments.size();
        for (int i = 0; i < size; i++) {
            remove(equipments.get(0));
        }
    }
}
