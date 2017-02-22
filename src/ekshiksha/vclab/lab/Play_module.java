package ekshiksha.vclab.lab;

import ekshiksha.vclab.activity.*;
import ekshiksha.vclab.equipment.Equipment;
import ekshiksha.vclab.equipment.Equipments;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ListIterator;
import javax.swing.*;

/**
 *
 * @author Kaushik Raju
 */

public class Play_module extends JPanel implements ActionListener,Runnable
{
    WorkBench environ;
    public JButton play, undo, pause,forward;
    public JButton board;
    Point obj=new Point(0,0);
    ListIterator li;
    Integer intobj;
    public boolean isPaused=true,isUndo=false,isForward=false,changeValues_u=false,changeValues_f=false,temp_u=false;
    Thread par;
    Equipments current;

    /**
     * constructor to initialize all the buttons and start the independent thread of demonstration mode.
     * @param env : object of WorkBench
     */
    Play_module(WorkBench env)
    {
        par=new Thread(this);
        environ=env;
        undo = new JButton(new ImageIcon("src/ekshiksha/vclab/lab/backward.png"));
        undo.addActionListener( this );
        this.add(undo);
        play = new JButton(new ImageIcon("src/ekshiksha/vclab/lab/play.png"));
        play.addActionListener( this );
        this.add(play);
        pause = new JButton(new ImageIcon("src/ekshiksha/vclab/lab/pause.png"));
        pause.addActionListener( this );
        this.add(pause);
        forward = new JButton(new ImageIcon("src/ekshiksha/vclab/lab/forward.png"));
        forward.addActionListener( this );
        this.add(forward);
        board=new JButton(new ImageIcon("src/ekshiksha/vclab/lab/blackboard.png"));
        this.add(board);
        par.start();
    }
    
    /**
     * this function changes the various boolean values to determine the flow of the experiment 
     * namely play, pause, skip step, backward step.
     */
    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource()==play)
        { 
            isPaused=false;
        } 
        else if (ae.getSource()==pause) 
        { 
            isPaused=true;
        }
        else if(ae.getSource()==undo)
        {
            isUndo=true;
            isPaused=true;
        }
        else if(ae.getSource()==forward)
        {
            isForward=true;
            isPaused=true;
        }
    }
    
    /**
     * This function moves the given equipment from the initial point to the final point,
     * it uses the repaint technique to create an animation effect.
     * the function can be paused, forwarded or back-stepped.
     * @param activity : the object of activity currently being performed
     * @param a : id of the equipment upon which activity is being performed.
     * @return : the completion mode of the function. 
     */
    public int move(Activity activity,int a)
    {
        changeValues_f=false;
        int i=0;
        Equipment equipment=Equipments.getEquipmentByID(a);
        equipment.originShift.x=((activity.fromDest.x)*(environ.getWidth()));
        equipment.originShift.y=((activity.fromDest.y)*(environ.getHeight()));
        changeValues_u=false;
        environ.repaint();
        for(i=0;i<1;i++)
        {  
            try
            {
                if(isPaused==false)
                {
                    Point2D.Float currpt = new Point2D.Float(((activity.fromDest.x)*(environ.getWidth())),((activity.fromDest.y)*(environ.getHeight()))) ;
                    Point2D.Float vector = new Point2D.Float( ((activity.toDest.x)*(environ.getWidth())) -((activity.fromDest.x)*(environ.getWidth())), ((activity.toDest.y)*(environ.getHeight()))- ((activity.fromDest.y)*(environ.getHeight())));
                    double vectorx = vector.x/Math.sqrt(vector.x*vector.x + vector.y*vector.y);
                    double vectory = vector.y/Math.sqrt(vector.x*vector.x + vector.y*vector.y);
                    int cntr_max = Math.abs((int)(((activity.toDest.x*(environ.getWidth())) -(activity.fromDest.x*(environ.getWidth())))/vectorx));
                    if(cntr_max==0)
                    {
                        cntr_max = Math.abs((int)(((activity.toDest.y*(environ.getHeight())) -(activity.fromDest.y*(environ.getHeight())))/vectory));
                    }
                    int cntr=1;
                    while( cntr<=cntr_max )
                    {
                        if(isPaused==false)
                        {
                            currpt.x += (float)(vectorx);
                            currpt.y += (float)(vectory);
                            equipment.originShift.x=currpt.x;
                            equipment.originShift.y=currpt.y;
                            Thread.sleep( 25 );
                            environ.repaint();
                            cntr += 1;
                        }
                        else if(isUndo==true)
                        {
                            equipment.originShift.x=((activity.fromDest.x)*(environ.getWidth()));
                            equipment.originShift.y=((activity.fromDest.y)*(environ.getHeight()));
                            environ.repaint();
                            isUndo=false;
                            return 1;
                        }
                        else if(isForward==true)
                        {
                            equipment.originShift.x=((activity.toDest.x)*(environ.getWidth()));
                            equipment.originShift.y=((activity.toDest.y)*(environ.getHeight()));
                            environ.repaint();
                            changeValues_f=true;
                            isForward=false;
                            return 2;
                        }
                        else
                        {
                            Thread.sleep(100);
                        }
                    }
                    equipment.originShift.x=((activity.toDest.x)*(environ.getWidth()));
                    equipment.originShift.y=((activity.toDest.y)*(environ.getHeight()));
                    environ.repaint();         
                } 
                else if(isUndo==true)
                {
                    isUndo=false;
                    changeValues_u=true;
                    return 0;
                }
                else if(isForward==true)
                {
                    equipment.originShift.x=((activity.toDest.x)*(environ.getWidth()));
                    equipment.originShift.y=((activity.toDest.y)*(environ.getHeight()));
                    environ.repaint();
                    isForward=false;
                    changeValues_f=true;
                    return 2;
                }
                else
                {
                    Thread.sleep(100);
                    i--;
                }
            }
            catch (InterruptedException ex) 
            {
                System.err.println(ex);
            }
        }
        return 2;
    }
    
    /**
     * This function moves the given equipment from the initial point to the destination equipment,
     * then it pours the quantity specified into the destination object 
     * It uses the repaint technique to create an animation effect.
     * All the different combinations of equipments have been considered
     * the function can be paused, forwarded or back-stepped.
     * @param activity : the object of activity currently being performed.
     * @param a : id of the equipment upon which activity is being performed.
     * @return : the completion mode of the function. 
     */
    public int pour(Activity activity,int a)
    {
        changeValues_f=false;
        int return_stat;
        float steps,ini_s,ini_d;
        Activity temp;
        Equipment equipment_d=Equipments.getEquipmentByID(activity.destinationId);
        Equipment equipment_s=Equipments.getEquipmentByID(a);
        Point2D.Float from=activity.fromDest;
        Point2D.Float to=new Point2D.Float(0,0);
        if("Pipette".equals(equipment_s.type))
        {
            if(activity.quantity<0)
                to=new Point2D.Float((equipment_d.originShift.x+equipment_d.width/2)/(environ.getWidth()),(equipment_d.originShift.y+equipment_d.height-equipment_s.height)/environ.getHeight());
            else
                to=new Point2D.Float((equipment_d.originShift.x+equipment_d.width/2)/(environ.getWidth()),(equipment_d.originShift.y+equipment_d.height/2-equipment_s.height)/environ.getHeight());
            temp=new Move(a,from,to);
        }
        else if("Burette".equals(equipment_d.type))
        {           
            to=new Point2D.Float((equipment_d.originShift.x+equipment_d.width/3)/(environ.getWidth()),equipment_d.originShift.y/environ.getHeight());
            temp=new Move(a,from,to);
        }
        else if("Burette".equals(equipment_s.type))
        {
            to=new Point2D.Float((equipment_s.originShift.x-equipment_s.width/2)/(environ.getWidth()),(equipment_s.originShift.y+equipment_s.height)/environ.getHeight());            
            temp=new Move(activity.destinationId,from,to);
        } 
        else
        {
            to=new Point2D.Float((equipment_d.originShift.x+equipment_d.width)/(environ.getWidth()),equipment_d.originShift.y/environ.getHeight());
            temp=new Move(a,from,to);
        }
        if(changeValues_u==true||temp_u==true)
        {
            if("Pipette".equals(equipment_s.type) && activity.quantity<0)
                equipment_s.setContentColor(equipment_d.getContentColor());
            else    
                equipment_d.setContentColor(equipment_s.getContentColor());
            equipment_s.setQuantity(equipment_s.quantity+activity.quantity);
            equipment_d.setQuantity(equipment_d.quantity-activity.quantity);
            environ.repaint();
            changeValues_u=false;
            temp_u=false;
        }
        ini_s=equipment_s.quantity;
        ini_d=equipment_d.quantity;
        if("Burette".equals(equipment_s.type))
            return_stat=move(temp,activity.destinationId);
        else
            return_stat=move(temp,a);
        if(changeValues_f==true)
        {
            if("Pipette".equals(equipment_s.type) && activity.quantity<0)
                equipment_s.setContentColor(equipment_d.getContentColor());
            else    
                equipment_d.setContentColor(equipment_s.getContentColor());
            equipment_s.setQuantity(equipment_s.quantity-activity.quantity);
            equipment_d.setQuantity(equipment_d.quantity+activity.quantity);
            environ.repaint();
            changeValues_f=false;
            isForward=false;
            return 2;
        }
        if(return_stat==0 || return_stat==1)
        {
            if(return_stat==0)
                changeValues_u=true;
            return return_stat;
        }

        steps=equipment_s.getIterationSteps(activity.quantity, equipment_d);

        try
        {
            if("Pipette".equals(equipment_s.type))
            {
                if(activity.quantity<0)
                {
                    equipment_s.fill(environ.getGraphics(),equipment_d);
                    equipment_s.setContentColor(equipment_d.getContentColor());
                }
                else
                {
                    equipment_s.pour(environ.getGraphics(),equipment_d);
                    equipment_d.setContentColor(equipment_s.getContentColor());
                }
                for(double d=1;d<=steps;d=d+1)
                {
                    if(isPaused==false)
                    {
                        environ.repaint();
                        Thread.sleep(100);
                    }
                    else if(isUndo==true)
                    {
                        equipment_s.setQuantity(ini_s);
                        equipment_d.setQuantity(ini_d);
                        environ.repaint();
                        isUndo=false;
                        return 1;      
                    }
                    else if(isForward==true)
                    {
                        equipment_s.setQuantity(ini_s-activity.quantity);
                        equipment_d.setQuantity(ini_d+activity.quantity);
                        environ.repaint();
                        isForward=false;
                        return 2;    
                    }
                    else
                    {
                        Thread.sleep(100);
                        d--;
                    }
                }
                equipment_s.setQuantity(ini_s-activity.quantity);
                equipment_d.setQuantity(ini_d+activity.quantity);
                environ.repaint();
            }
            else if("Burette".equals(equipment_s.type))
            {
                equipment_d.setContentColor(equipment_s.getContentColor());
                for(double d=1;d<=steps;d=d+1)
                {
                    if(isPaused==false)
                    {
                        equipment_s.pour(environ.getGraphics());
                        environ.repaint();
                        Thread.sleep(100);
                    }
                    else if(isUndo==true)
                    {
                        equipment_s.setQuantity(ini_s);
                        equipment_d.setQuantity(ini_d);
                        environ.repaint();
                        isUndo=false;
                        return 1;      
                    }
                    else if(isForward==true)
                    {
                        equipment_s.setQuantity(ini_s-activity.quantity);
                        equipment_d.setQuantity(ini_d+activity.quantity);
                        environ.repaint();
                        isForward=false;
                        return 2;    
                    }
                    else
                    {
                        Thread.sleep(100);
                        d--;
                    }
                }
                equipment_s.setQuantity(ini_s-activity.quantity);
                equipment_d.setQuantity(ini_d+activity.quantity);
                environ.repaint();
            }
            else
            {
                equipment_d.setContentColor(equipment_s.getContentColor());
                for(double d=1;d<=25;d=d+1)
                {
                    if(isPaused==false)
                    {
                        equipment_s.rotate(steps/25);
                        environ.repaint();
                        Thread.sleep(100);
                    }
                    else if(isUndo==true)
                    {
                        equipment_s.rotate(-(steps/25)*(d-1));
                        equipment_s.setQuantity(ini_s);
                        equipment_d.setQuantity(ini_d);
                        environ.repaint();
                        isUndo=false;
                        return 1;    
                    }
                    else if(isForward==true)
                    {
                        equipment_s.rotate(-(steps/25)*(d-1));
                        equipment_s.setQuantity(ini_s-activity.quantity);
                        equipment_d.setQuantity(ini_d+activity.quantity);
                        environ.repaint();
                        isForward=false;
                        return 2;    
                    }
                    else
                    {
                        Thread.sleep(100);
                        d--;
                    }
                }
                equipment_s.setQuantity(ini_s-activity.quantity);
                equipment_d.setQuantity(ini_d+activity.quantity);
                environ.repaint();
                for(double d=1;d<=25;d=d+1)
                {
                    if(isPaused==false)
                    {
                        equipment_s.rotate(-steps/25);
                        environ.repaint();
                        Thread.sleep(100);
                    }
                    else if(isUndo==true)
                    {
                        equipment_s.rotate(-(steps/25)*(25-d+1));
                        equipment_s.setQuantity(ini_s);
                        equipment_d.setQuantity(ini_d);
                        environ.repaint();
                        isUndo=false;
                        return 1;      
                    }
                    else if(isForward==true)
                    {
                        equipment_s.rotate(-(steps/25)*(25-d+1));
                        equipment_s.setQuantity(ini_s-activity.quantity);
                        equipment_d.setQuantity(ini_d+activity.quantity);
                        environ.repaint();
                        isForward=false;
                        return 2;    
                    }
                    else
                    {
                        Thread.sleep(100);
                        d--;
                    }
                }
            }  
        }
        catch(InterruptedException e){}
        isForward=false;
        return 2;
    }

    /**
     * This function moves the given equipment from the initial point to the basin,
     * the equipment is then emptied(i.e washed)
     * it uses the repaint technique to create an animation effect.
     * the function can be paused, forwarded or back-stepped.
     * @param activity : the object of activity currently being performed
     * @param a : id of the equipment upon which activity is being performed.
     * @return : the completion mode of the function. 
     */
    public int wash(Activity activity,int a)
    {
        changeValues_f=false;
        int return_stat;
        float steps,ini_s,ini_d;
        Activity temp;
        Equipment equipment_s=Equipments.getEquipmentByID(a);
        Point2D.Float from=activity.fromDest;
        Point2D.Float to=new Point2D.Float(0,0);
        ini_s=equipment_s.quantity;
        to=new Point2D.Float(environ.getb_rx()/environ.getWidth(),(environ.getb_ry()-equipment_s.height)/environ.getHeight());
        temp=new Move(a,from,to);
        if(changeValues_u==true||temp_u==true)
        {
            equipment_s.setQuantity(equipment_s.quantity+activity.quantity);
            environ.repaint();
            changeValues_u=false;
            temp_u=false;
        }
        return_stat=move(temp,a);
        if(changeValues_f==true)
        {
            equipment_s.setQuantity(equipment_s.quantity-activity.quantity);
            environ.repaint();
            changeValues_f=false;
            isForward=false;
            return 2;
        }
        if(return_stat==0 || return_stat==1)
        {
            if(return_stat==0)
                changeValues_u=true;
            return return_stat;
        }
        equipment_s.setQuantity(0.0f);
        environ.repaint();
              
        isForward=false;
        return 2;
    }
    
    /**
     * This function moves the given equipment from the initial point to the burner,
     * a parallel thread is created for the flames to be active for the given time period 
     * It uses the repaint technique to create an animation effect.
     * the function can be paused, forwarded or back-stepped.
     * @param activity : the object of activity currently being performed
     * @param a : id of the equipment upon which activity is being performed.
     * @return : the completion mode of the function. 
     */
    private int burn(Activity activity, int a)
    {
        changeValues_u=false;
        temp_u=false;
        int return_stat;
        /*float ini_t;*/
        Equipment equipment=Equipments.getEquipmentByID(a);
        Equipment equipment_b=Equipments.getEquipmentByID(activity.burnerId);
        /*ini_t=initial temp*/
        Point2D.Float from=activity.fromDest;
        Point2D.Float to=new Point2D.Float(equipment_b.originShift.x,equipment_b.originShift.y-equipment.height);
        Activity temp=new Move(a,from,to);
        if(changeValues_u==true||temp_u==true)
        {
            changeValues_u=false;
            temp_u=false;
        }
        return_stat=move(temp,a);
        if(changeValues_f==true)
        {
            changeValues_f=false;
            isForward=false;
        }
        if(return_stat==0 || return_stat==1)
        {
            if(return_stat==0)
                changeValues_u=true;
            return return_stat;
        }
        ParBurn heat=new ParBurn(environ,this,activity,activity.id);
        try
        {
            while(heat.isAlive())
            {
                if(isPaused==true)
                {
                    if(isUndo==true)
                    {
                        Thread.sleep(100);
                        /*chane temp to initial*/
                        isUndo=false;
                        return 1;
                    }
                    else if(isForward==true)
                    {
                        Thread.sleep(100);
                        /*change temp to final*/
                        isForward=false;
                        return 2;
                    }
                    else
                    {
                        Thread.sleep(100);
                    }
                }
                else
                {
                    Thread.sleep(100);
                }
            }
        }
        catch(Exception e)
        {}
        return 2;
    }
      
    /**
     * This function begins the infinite loop to constantly call the required activity.
     * even after the experiment is over the experiment can be back-stepped and viewed again.
     */
    public void perform_exp()
    {
        while(Activities.getActivities().size()==0)
        {
            try 
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex) {}
        }

        int i=0,return_stat,noAct=Activities.getActivities().size();
        Activity activity;
        for(i=0;i<=noAct && Activities.getActivities().size()>0;i++)
        {          
            if(i<noAct)
            {
                activity=Activities.getActivities().get(i);
                if ("Move".equals(activity.type))
                {
                    return_stat=move(activity,activity.id);
                    if(return_stat==0)    i=i-2;
                    else if(return_stat==1)  i--;
                }
                else if("Pour".equals(activity.type))
                {
                    return_stat=pour(activity,activity.sourceId);
                    if(return_stat==0)    i=i-2;
                    else if(return_stat==1)  i--;
                }
                else if("Burn".equals(activity.type))
                {
                    return_stat=burn(activity,activity.id);
                    if(return_stat==0)    i=i-2;
                    else if(return_stat==1)  i--;
                }
                else if("Wash".equals(activity.type))
                {
                    return_stat=wash(activity,activity.id);
                    if(return_stat==0)    i=i-2;
                    else if(return_stat==1)  i--;
                }
                if(i<-1)
                {
                    i=-1;
                    changeValues_u=false;
                }
            }
            if(isUndo==true)
            {
                i=i-2;
                temp_u=true;
                isUndo=false;
            }
            if(i==noAct)
            {
                i--;
                try 
                {
                    Thread.sleep(100);
                } 
                catch (InterruptedException ex) {}
            }
        }
    }
    
    /**
     * Begins the execution of the experiment.
     */
    public void run()
    {
       perform_exp();
    }
    
}
