/**
 * Sunseeker Telemetry
 *
 * @author Alec Carpenter <alecgunnar@gmail.com>
 * @date July 2, 2016
 */

package sunseeker.telemetry;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.util.List;
import java.util.ArrayList;

class LinePanel extends AbstractLinePanel {
    protected int width = 0;
    protected int height = 0;

    protected double previousValue;

    protected Graphics2D artist;

    protected boolean active = true;

    protected DataTypeInterface type;

    protected ArrayList<Integer> points;

    public LinePanel (DataTypeInterface type) {
        /*
         * This is where we will be getting the data from
         */
        this.type = type;

        /*
         * Need to see the other lines and graph
         */
        setOpaque(false);

        /*
         * Initialize the collection of points
         */
        points = new ArrayList<Integer>();
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        width = getWidth();
        height = getHeight();

        artist = (Graphics2D) g;

        /*
         * Only when this line is active should it be drawn
         */
        if (type.isEnabled()) {
            loadPoints();
            drawSegments();
        }
    }

    protected void loadPoints () {
        List<Double> data = type.getData();

        points.clear();

        for (Double value : data)
            pushPoint(AbstractGraphPanel.getYPos(previousValue = value));
    }

    protected void pushPoint (Integer point) {
        if (points.size() == AbstractGraphPanel.MAX_POINTS)
            points.remove(0);

        points.add(point);
    }

    protected void drawSegments () {
        /*
         * We need a colored line with the required thickness
         */
        artist.setStroke(new BasicStroke(
            LINE_WIDTH,
            BasicStroke.CAP_SQUARE,
            BasicStroke.JOIN_MITER
        ));

        artist.setColor(type.getColor());

        /*
         * Run through the data to be displayed
         */
        int index = 0,
            prev  = 0;

        for (Integer point : points) {
            if (index > 0) {
                artist.drawLine(
                    (index - 1) * AbstractGraphPanel.X_AXIS_SCALE,
                    prev,
                    index * AbstractGraphPanel.X_AXIS_SCALE,
                    point
                );
            }

            prev = point;

            index++;
        }
    }
}
