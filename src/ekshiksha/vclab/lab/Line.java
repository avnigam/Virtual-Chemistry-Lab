/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ekshiksha.vclab.lab;

/**
 *
 * @author mayur12
 */

import java.awt.*;
import java.io.*;

public class Line implements Serializable {

	public Point s, e;
	public Line( Point s, Point e)
	{
		this.s = s;
		this.e = e;
	}
}
