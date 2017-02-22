/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.activity;

import java.awt.geom.Point2D;

/**
 *
 * @author asl
 */
public class Burn extends Activity {

    public int id;

    public Burn(int id,int burnerId,Point2D.Float fromdest,float cur_temp,float final_temp)
    {
        super("Burn");
        this.id= id;
        this.burnerId= burnerId;
        this.fromDest = fromdest;
        this.cur_temp = cur_temp;
        this.final_temp = final_temp;
        
    }

    @Override
    public void execute(){
       

    }

}
