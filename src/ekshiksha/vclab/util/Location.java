/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayank
 */
public class Location {

    public static String position; //for position on table or shelf-top
    public static float x;         //for position on x-axis on table
    public static float y;         //for position on y-axis on table
    public static int number;      //for position on shelf-top
    public static final String TABLE = "Table";
    public static final String SHELFTOP = "Shelf Top";
    public static final int LOCATIONTABLE = 1;
    public static final int LOCATIONSHELFTOP = 2;
    public static final int UNKNOWN = 3;


    //getter and setter for all variables of class Location
    public static String getPosition()
    {
        return position;
    }

    public static float getX()
    {
        return x;
    }

    public static float getY()
    {
        return y;
    }

    public static int getNumber()
    {
        return number;
    }

    
    public static int parseString(String location)
    {
        int initialResult = UNKNOWN;

        if(location.startsWith(SHELFTOP))
        {
            Pattern p=Pattern.compile("[a-zA-Z\\s0-9]");
            Matcher m = p.matcher("Shelf top 2");
            String result="";
                while(m.find())
                {
                    result+=m.group();
		}
            String[] s2 = result.split("[0-9]", 10);
            position="";  //s3
            String[] s4 = result.split("[a-zA-Z]", 10);
            String s5="";
		for(int i=0;i<s2.length;i++)
                {
			position+=s2[i];   //getting the value of position
		}
                    if(position != null){
                        position = position.trim();
                    
                    }
		for(int i=0;i<s4.length;i++)
                {
			s5+=s4[i];
                        
		}
            number =Integer.parseInt(s5.trim()); //conversion of String s5 to int number

            initialResult = LOCATIONSHELFTOP;
        }
        else if(location.startsWith(TABLE))
        {
            String str="Table 0.5,0.50";
            String []result1=str.split("\\s");
            String[]result2=result1[1].split(",\\s*");
            position=result1[0];                              // getting the value of Table here
            String value2=result2[0];
            x = Float.valueOf(value2.trim()).floatValue();   //conversion from String to float x
            String value3=result2[1];
            y = Float.valueOf(value3.trim()).floatValue();  //conversion from String to float y
          
            initialResult = LOCATIONTABLE;
        }
        else
        {
           position = location;
        }
      return initialResult;
    }

    public static boolean isValidLocation()
    {
        return true;
    }

}
