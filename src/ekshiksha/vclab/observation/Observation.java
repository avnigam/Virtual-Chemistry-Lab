/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.observation;

/**
 *
 * @author mayank
 */
public class Observation {

    public String[] remarks;


    public Observation(String[] remarks){
        this.remarks= remarks;
    }

    public Observation(){

    }


    public void setRemarks(String[] remarks){
        this.remarks= remarks;
    }

    public String[] getRemarks(){
        return remarks;
    }
}
