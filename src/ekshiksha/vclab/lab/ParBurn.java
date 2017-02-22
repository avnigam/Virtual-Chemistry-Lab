
package ekshiksha.vclab.lab;

import ekshiksha.vclab.activity.*;
import ekshiksha.vclab.equipment.*;
import ekshiksha.vclab.util.Constants;

/**
 *
 * @author Kaushik Raju,Vikram
 */
public class ParBurn extends Thread
{
    WorkBench environ;
    Play_module demo;
    Thread heat;
    Equipment burner;
    Equipment eq;
    Activity activity;
    int a;
    private int break_pt=0;
    
    /**
     * this constructor is called to initialize the heat activity.
     * @param e :Object of WorkBench
     * @param d : Object of Play_module
     * @param act : Object of the heat activity
     * @param a : id of the equipment to be heated
     */
    ParBurn(WorkBench e,Play_module d,Activity act,int a)
    {
        environ=e;
        demo=d;
        activity=act;
        this.a=a;
        eq=Equipments.getEquipmentByID(a);
        burner=Equipments.getEquipmentByID(act.burnerId);
        start();
    }
    
    /**
     * Started from the constructor, this function creates the flames of the burner for the required amount of time.
     * using the object of the Play_module, it is synchronized with it to play, pause, back-step, forward
     * according to the appropriate working conditions in the Play_module.
     */
    @Override
    public void run()
    {
        try
        {
            for (int i = 0; i < 12 && break_pt==0; i++)
            {
                if(demo.isPaused==true)
                {
                    if(demo.isUndo==true||demo.isForward==true)
                    {
                        burner.intensity=0.0;
                        burner.flameHeight=0;
                        burner.onState=false;
                        break_pt=1;
                    }
                    else
                    {
                        Thread.sleep(100);
                        i--;
                    }
                }
                else if (burner.intensity + Constants.FLAME_INTENSITY_DECREAMENT <= Constants.MAX_FLAME_INTENSITY)
                {
                    burner.intensity += Constants.FLAME_INTENSITY_DECREAMENT;
                    if (burner.flameHeight + (burner.intensity * 5) <= Constants.MAX_FLAME_HEIGHT) 
                    {
                        burner.flameHeight += (burner.intensity * 5);
                    }
                    if (burner.flameHeight > 0) 
                    {
                        burner.onState = true;
                    }
                    environ.repaint();
                    Thread.sleep(100);
                }
            }
            
            for(int i=0;i<activity.heat_time*10 && break_pt==0;i++)
            {
                if(demo.isPaused==true)
                {
                    if(demo.isUndo==true||demo.isForward==true)
                    {
                        burner.intensity=0.0;
                        burner.flameHeight=0;
                        burner.onState=false;
                        break_pt=1;
                    }
                    else
                    {
                        Thread.sleep(100);
                        i--;
                    }
                }
                Thread.sleep(100);
            }
            
            while (burner.intensity != 0.0 && break_pt==0) 
            {
                if(demo.isPaused==true)
                {
                    if(demo.isUndo==true||demo.isForward==true)
                    {
                        burner.intensity=0.0;
                        burner.flameHeight=0;
                        burner.onState=false;
                        break_pt=1;
                    }
                    else
                    {
                        Thread.sleep(100);
                    }
                }

                else if (burner.intensity - Constants.FLAME_INTENSITY_DECREAMENT >= 0)
                {
                    burner.intensity -= Constants.FLAME_INTENSITY_DECREAMENT;
                    if (burner.flameHeight - (burner.intensity * 5) >= 0)
                    {
                        burner.flameHeight -= 3;
                        if (burner.flameHeight <= 1)
                        {
                            burner.flameHeight = 0;
                            burner.onState = false;
                            burner.intensity = 0.0;
                        }
                    }
                    environ.repaint();
                    Thread.sleep(100);
                }
            }
        }
        catch(Exception e)
        {}
    }
}
