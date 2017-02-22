/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.activity;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

/**
 *
 * @author asl
 */
public class Activity implements ActivityType {

    public String type;
    public int id,burnerId;
    public Point2D.Float fromDest,toDest;
    public int sourceId,destinationId;
    public float quantity;
    public String content;
    public int heat_time;
    public Color contentColor;

    public Color getContentColor() {
        return contentColor;
    }

    public void setContentColor(Color contentColor) {
        this.contentColor = contentColor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBurnerId() {
        return burnerId;
    }

    public void setBurnerId(int burnerId) {
        this.burnerId = burnerId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public float getCur_temp() {
        return cur_temp;
    }

    public void setCur_temp(float cur_temp) {
        this.cur_temp = cur_temp;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public float getFinal_temp() {
        return final_temp;
    }

    public void setFinal_temp(float final_temp) {
        this.final_temp = final_temp;
    }

    public Float getFromDest() {
        return fromDest;
    }

    public void setFromDest(Float fromDest) {
        this.fromDest = fromDest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPerforming() {
        return performing;
    }

    public void setPerforming(boolean performing) {
        this.performing = performing;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public Float getToDest() {
        return toDest;
    }

    public void setToDest(Float toDest) {
        this.toDest = toDest;
    }
    public boolean performing;
    public String contents;
    public float cur_temp,final_temp;

     public Activity(String type) {
            this.type=type;


    }
   /* public Activity(String type,int id) {
            this.type=type;
            this.id=id;
    }*/
   /* public Activity(String type, int sourceId, int destinationId, float quantity,Point2D.Float fromdest,String contents)
    {
       // this.fromDest=fromDest;
        this.type=type;
        this.sourceId=sourceId;
        this.destinationId=destinationId;
        this.quantity=quantity;
        this.fromDest =fromdest;
        this.contents= contents;

    }*/
    public Activity(){}

    //Setter and getter methods
    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    //Method for executing different activities
    public void execute(){
      //implementation code goes here
    }

    //Method for exporting the xml
    public void exportXml(){
      //code goes here
    }

    //Method to save the states of the activities
    public void saveState(){
      //code goes here
    }


}




  /*  public Activity(String type,int sourceId,int destinationId)
    {
        this.type= type;
        this.sourceId = sourceId;
        this.destinationId = destinationId;

    }*/

    //Setter and getter methods


  /*  public void setsourceID(int sourceId){
        this.sourceId = sourceId;
    }

    public int getsourceId(){
        return sourceId;
    }

    public void setdestinationID(int destinationId){
        this.destinationId = destinationId;
    }

    public int getdestinationId(){
        return destinationId;
    }
    */

    //Method for executing different activities


