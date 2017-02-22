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
public class Pour extends Activity {

   
   
    

    public Pour(int sourceId, int destinationId, float quantity,Point2D.Float fromdest,String contents) {
        super("Pour");
        this.sourceId= sourceId;
        this.destinationId= destinationId;
        this.quantity= quantity;
        this.fromDest = fromdest;
        this.contents = contents;

    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
    public int getSourceId() {
        return sourceId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }
    public int getDestinationId() {
        return destinationId;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
    public float getQuantity(){
        return quantity;
    }

    @Override
    public void execute(){
      

    }

}
