/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.evaluation;

/**
 *
 * @author mayank
 */
public class Evaluation {

    public int marks;
    public String[] remarks;


    public Evaluation(int marks, String[] remarks){
        this.marks= marks;
        this.remarks= remarks;
    }

    public Evaluation(){

    }
    

    public void setMarks(int marks){
        this.marks = marks;
    }

    public int getMarks(){
        return marks;
    }


    public void setRemarks(String[] remarks){
        this.remarks = remarks;
    }

    public String[] getRemarks(){
        return remarks;
    }

}
