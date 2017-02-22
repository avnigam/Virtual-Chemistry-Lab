/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*Container class for activities*/

package ekshiksha.vclab.activity;

import java.util.*;
/**
 *
 * @author asl
 */
public class Activities {

   

     static ArrayList<Activity> activities = new ArrayList<Activity>();
   

    public static void add(Activity activity){
        activities.add(activity);
    }


    public static void remove(Activity activity){
        activities.remove(activity);
    }

    public static ArrayList<Activity> getActivities(){
        return activities;
    }

    public static void setActivities(ArrayList<Activity> activityList){
        activities = activityList;

    }
    public static void removeAll()
    {
        int size=Activities.getActivities().size();
        for(int i=0;i<size;i++)
            remove(Activities.getActivities().get(0));
    }

}
