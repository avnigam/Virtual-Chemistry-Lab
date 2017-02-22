/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ekshiksha.vclab.lab;

import java.awt.Color;

/**
 *
 * @author harsh12
 */
public class BottleChemical 
{
	public String name;
	public String formula;
	public int colorR;
	public int colorG;
	public int colorB;

        public BottleChemical(String a, String b, int c, int d, int e)
	{
		name = a;
		formula = b;
		colorR = c;
		colorG = d;
		colorB = e;
	}
         public BottleChemical(String a, String b, Color c)
	{
		name = a;
		formula = b;
		colorR = c.getRed();
		colorG = c.getGreen();
		colorB = c.getBlue();
	}

        public BottleChemical() {}

        public int getColorB() {
            return colorB;
        }

        public void setColorB(int colorB) {
            this.colorB = colorB;
        }

        public int getColorG() {
            return colorG;
        }

        public void setColorG(int colorG) {
            this.colorG = colorG;
        }

        public int getColorR() {
            return colorR;
        }

        public void setColorR(int colorR) {
            this.colorR = colorR;
        }

        public String getFormula() {
            return formula;
        }

        public void setFormula(String formula) {
            this.formula = formula;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}