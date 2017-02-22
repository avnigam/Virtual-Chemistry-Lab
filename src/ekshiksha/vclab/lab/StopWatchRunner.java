package ekshiksha.vclab.lab;

/*
       A component that acts as a simple stop-watch.  When the user clicks
       on it, this component starts timing.  When the user clicks again,
       it displays the time between the two clicks.  Clicking a third time
       starts another timer, etc.  While it is timing, the label just
       displays the whole number of seconds since the timer was started.
    */

import java.awt.*;
    import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
    import javax.swing.*;
/**
 * Class to display the stopwatch
 * @author mayur12
 * This class is a JPanel which gets added to the bottom panel of applet.
 * It gets used to time the heating when burner is being used.
 */
    public class StopWatchRunner extends JPanel
                implements /*MouseListener,*/ ActionListener {

       public long startTime;   // Start time of stopwatch.
                                 //   (Time is measured in milliseconds.)

       public boolean running;  // True when the stopwatch is running.

       public Timer timer;  // A timer that will generate events
                             // while the stopwatch is running
       public long minute=0,hours=0;
       public JLabel watch,header;
       long endTime;
/**
 * Constructor for StopWatchRunner
 * @param s Initial dimensions of stopwatch
 * Initializes the global variables and the JLabel that displays time
 */
       public StopWatchRunner(Dimension s) {
             // Constructor.
          this.setPreferredSize(s);
         

          watch=new JLabel("00:00:00", JLabel.CENTER);
          //watch.setHorizontalAlignment(JLabel.RIGHT);
          watch.setPreferredSize(new Dimension(s.width/2,(int)(s.height/3)));
          watch.setFont( new Font("DIGITAL", Font.BOLD, 20) );
          watch.setBackground(new Color(0,128,128));
          watch.setForeground( (Color.black) );
          watch.setOpaque(true);
          
          this.add(watch, BorderLayout.CENTER);
         
         // addMouseListener(this);
       }
/**
 * Abstract Method of ActionListener Interface
 * @param evt ActionListener
 *  This will be called when an event from the
 *  timer is received.  It just sets the stopwatch
 *  to show the amount of time that it has been running.
 *  Time is rounded down to the nearest second.
 */       
    
       public void actionPerformed(ActionEvent evt) {
              // This will be called when an event from the
              // timer is received.  It just sets the stopwatch
              // to show the amount of time that it has been running.
              // Time is rounded down to the nearest second.
          
           long time = (System.currentTimeMillis() - startTime) / 1000;
           if(time==60){
               minute++;
               startTime=System.currentTimeMillis();
           }
           if(minute==60){
               minute=0;
               hours++;
           }
           if(time<10&&minute<10&&hours<10)
           {
             watch.setText("0"+hours+":"+"0"+minute+":"+"0"+time);
           }
           else if(time<10&&minute<10&&hours>=10)
           {
             watch.setText(hours+":"+"0"+minute+":"+"0"+time);
           }
           else if(time<10&&minute>=10&&hours<10)
           {
             watch.setText("0"+hours+":"+minute+":"+"0"+time);
           }
           else if(time<10&&minute>=10&&hours>10)
           {
             watch.setText(hours+":"+minute+":"+"0"+time);
           }
           else if(time>=10&&minute<10&&hours<10)
           {
             watch.setText("0"+hours+":"+"0"+minute+":"+time);
           }
           else if(time>=10&&minute<10&&hours>=10)
           {
             watch.setText(hours+":"+"0"+minute+":"+"0"+time);
           }
           else if(time>=10&&minute>=10&&hours<10)
           {
             watch.setText("0"+hours+":"+minute+":"+time);
           }
           else if(time>=10&&minute>=10&&hours>=10)
           {
             watch.setText(hours+":"+minute+":"+time);
           }
       }
/**
 *This method starts the stopwatch
 * @param time 
 * It records the starting time
 */       
       public void timeStart(long time)
       {
           
           if (running == false) {
                // Record the time and start the stopwatch.
             running = true;
             startTime = time;  // Time when mouse was clicked.
             
             watch.setText("00:00:00");
             if (timer == null) {
                timer = new Timer(100,this);
                timer.start();
             }
             else
                timer.restart();
          }
       }
       /**
        * THis method stops the stopWatch
        * @param time 
        * It records the stop time 
        */
       public void timeStop(long time)
       {
           if(running==true) {
                // Stop the stopwatch.  Compute the elapsed time since the
                // stopwatch was started and display it.
             timer.stop();
             running = false;
             endTime = time;
             double seconds = (int)((endTime - startTime) / 1000.0);
             watch.setText(seconds + " sec.");
          }
       }
       /**
        * This method returns the time elapsed
        * @return 
        */
       public int totalTime()
       {
           return (int)((endTime - startTime) / 1000.0);
       }
       

      /* public void mousePressed(MouseEvent evt) {
              // React when user presses the mouse by
              // starting or stopping the stopwatch.  Also start
              // or stop the timer.
          if (running == false) {
                // Record the time and start the stopwatch.
             running = true;
             startTime = evt.getWhen();  // Time when mouse was clicked.
            
             watch.setText("00:00:00");
             if (timer == null) {
                timer = new Timer(100,this);
                timer.start();
             }
             else
                timer.restart();
          }
          else {
                // Stop the stopwatch.  Compute the elapsed time since the
                // stopwatch was started and display it.
             timer.stop();
             running = false;
             long endTime = evt.getWhen();
             double seconds = (int)((endTime - startTime) / 1000.0);
             watch.setText(seconds + " sec.");
          }
       }

       public void mouseReleased(MouseEvent evt) { }
       public void mouseClicked(MouseEvent evt) { }
       public void mouseEntered(MouseEvent evt) { }
       public void mouseExited(MouseEvent evt) { }*/
     
    }  // end StopWatchRunner
