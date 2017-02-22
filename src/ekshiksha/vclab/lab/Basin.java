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
import ekshiksha.vclab.util.Constants;

/**
 * Class to draw Basin
 *
 * @author mayur12
 */
public class Basin {

    private Point table[] = new Point[4];
    private Point plcmntStart;
    private Point plcmntEnd;
    WorkBench environ;
    Cupboard environ2;
    boolean cupboardFlag;

    /**
     * Constructor for Table class used by Workbench
     *
     * @param environ Object of workbench
     *
     */
    public Basin(WorkBench environ) {
        this.environ = environ;
        cupboardFlag = false;
    }

    /**
     * Constructor for Table class used by Cupboard
     *
     * @param environ Object of Cupboard
     *
     */
    public Basin(Cupboard environ) {
        this.environ2 = environ;
        cupboardFlag = true;
    }

    /**
     * This Method draws the basin
     *
     * @param g The coordinates of basin are decided according to the size of
     * workbench/cupboard and then the basin is drawn accordingly.
     */
    public void drawBasin(Graphics g) {
        double hx;
        double wx;

        if (!cupboardFlag) {
            hx = (double) environ.getHeight();
            wx = (double) environ.getWidth();
        } else {
            hx = (double) environ2.getHeight();
            wx = (double) environ2.getWidth();
        }

        int floorLimit = (int) (hx / Constants.FLOOR_LIMIT);

        int sx = 0;
        int sy = (int) (hx - floorLimit - (int) (hx * Constants.LENGTH_EDGE_HORIZONTAL_LINE));
        int ey = sy - (int) (hx * Constants.TABLE_HEIGHT_FROM_EDGE_STARTINGS);
        int ex = (int) (wx * Constants.FRACTION_FIRST);
        table[0] = new Point(sx, sy);
        table[1] = new Point(ex, ey);

        sx = ex;
        sy = ey;
        ex = sx + (int) (wx * Constants.FRACTION_SECOND);
        ey = sy;
        table[2] = new Point(ex, ey);

        sx = ex;
        sy = ey;
        ex = sx + (int) (wx * Constants.FRACTION_FIRST);
        ey = (int) (hx - floorLimit - (int) (hx * Constants.LENGTH_EDGE_HORIZONTAL_LINE));

        table[3] = new Point(ex, ey);
        double tantheta = (table[3].y - table[2].y) / (double) (table[3].x - table[2].x);
        double tanthetaLeft = (table[0].y - table[1].y) / (double) (table[1].x - table[0].x);

        int xarr[] = new int[4];
        int yarr[] = new int[4];
        xarr[2] = table[2].x - (int) (wx * Constants.DISTANCE_RIGHT_EDGE_BASIN);
        xarr[3] = xarr[2] + (int) ((wx * Constants.BASIN_WIDTH_X) / tantheta);
        xarr[1] = xarr[2] - (int) (wx * Constants.BACK_LENGTH_BASIN);
        xarr[0] = xarr[1] - (int) ((wx * Constants.BASIN_WIDTH_X) / tantheta);

        yarr[3] = table[3].y - (int) (hx * Constants.DISTANCE_BOTTOM_EDGE_BASIN);
        yarr[2] = yarr[3] - (int) (hx * Constants.BASIN_WIDTH_Y);
        yarr[1] = yarr[2];
        yarr[0] = yarr[3];
        // plcmntStart = new Point( (table[0].x + table[1].x)/2, (table[0].y + table[1].y)/2 );
        //plcmntEnd=new Point( xarr[0],plcmntStart.y );

        g.setColor(Color.LIGHT_GRAY);
        g.fillPolygon(xarr, yarr, 4);

        g.setColor(Color.BLACK);
        g.drawLine(xarr[1], yarr[1], xarr[1], yarr[1] + (int) (hx * Constants.BASIN_WIDTH_Y));
        g.drawLine(xarr[2], yarr[2], xarr[2], yarr[2] + (int) (hx * Constants.BASIN_WIDTH_Y));
        g.drawLine(xarr[0], yarr[0], xarr[1], yarr[1]);
        g.drawLine(xarr[1], yarr[1], xarr[2], yarr[2]);
        g.drawLine(xarr[2], yarr[2], xarr[3], yarr[3]);
        g.drawLine(xarr[3], yarr[3], xarr[0], yarr[0]);

        int tapx[] = new int[8];
        int tapy[] = new int[8];

        tapx[3] = xarr[2] + (int) ((hx * Constants.BASIN_WIDTH_X) / ((double) 2 * tantheta));
        tapx[2] = tapx[3];
        tapx[1] = tapx[2] - (int) (wx * Constants.TAP_WIDTH);
        tapx[0] = tapx[1];
        tapx[7] = tapx[0] + (int) (wx * Constants.TAP_THICKNESS_X);
        tapx[6] = tapx[7];
        tapx[5] = tapx[6] + (int) (wx * Constants.TAP_WIDTH) - (int) (2 * (wx * Constants.TAP_THICKNESS_X));
        tapx[4] = tapx[5];

        tapy[3] = yarr[2] + (int) (hx * Constants.BASIN_WIDTH_Y) / 2;//table[2].y + Constants.BASIN_WIDTH/2 + ((Constants.TABLE_HEIGHT_FROM_EDGE_STARTINGS + Constants.LENGTH_EDGE_HORIZONTAL_LINE) - ( Constants.DISTANCE_BOTTOM_EDGE_BASIN + Constants.BASIN_WIDTH));
        tapy[2] = tapy[3] - (int) (hx * Constants.TAP_HEIGHT);
        tapy[1] = tapy[2];
        tapy[0] = tapy[1] + (int) (hx * Constants.TAP_THICKNESS_Y) + 8;
        tapy[7] = tapy[0];
        tapy[6] = tapy[7] - 8;
        tapy[5] = tapy[6];
        tapy[4] = tapy[5] + (int) (hx * Constants.TAP_HEIGHT) - (int) (hx * Constants.TAP_THICKNESS_Y) - (int) (((hx * Constants.TAP_THICKNESS_Y)) * tantheta);

        g.setColor(Constants.TAP_COLOR);
        g.fillPolygon(tapx, tapy, 8);
        g.setColor(Color.BLACK);
        g.drawLine(tapx[0], tapy[0], tapx[1], tapy[1]);
        g.drawLine(tapx[1], tapy[1], tapx[2], tapy[2]);
        g.drawLine(tapx[2], tapy[2], tapx[3], tapy[3]);
        g.drawLine(tapx[4], tapy[4], tapx[5], tapy[5]);
        g.drawLine(tapx[5], tapy[5], tapx[6], tapy[6]);
        g.drawLine(tapx[6], tapy[6], tapx[7], tapy[7]);
    }
}
