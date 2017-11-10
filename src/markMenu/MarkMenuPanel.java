package markMenu;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MarkMenuPanel extends JPanel {

	/**
	 * 
	 */

	static final int DIST_MODEL = 50;

	private static final long serialVersionUID = 1L;

	protected JLabel northOption;
	protected JLabel eastOption;
	protected JLabel southOption;
	protected JLabel westOption;

	protected Point startPos;
	// point of the real shape
	protected LinkedList<Point> checkpointsPos = new LinkedList<Point>();
	// points of the recognized model (with 90° angles and normalized distance)
	protected LinkedList<Point> modelPoints = new LinkedList<Point>();
	protected Point prevPos;
	protected Point currentPos;

	public MarkMenuPanel() {
		super();

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				repaint();
				paintModel();
				checkpointsPos = new LinkedList<Point>();
				modelPoints = new LinkedList<Point>();

			}

			@Override
			public void mousePressed(MouseEvent e) {

				startPos = getCurrentPosition(e);
				checkpointsPos.add(startPos);
				modelPoints.add(startPos);
				currentPos = getCurrentPosition(e);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				prevPos = currentPos;
				currentPos = getCurrentPosition(e);
				drawNewSegment(prevPos, currentPos);

				System.out.println(Math.hypot(currentPos.x - prevPos.x, currentPos.y - prevPos.y));

				if (Math.hypot(currentPos.x - prevPos.x, currentPos.y - prevPos.y) <= 1) {
					
					Point newCheckPoint;
					if ((currentPos.x - checkpointsPos.getLast().x) > (currentPos.y - checkpointsPos.getLast().y)) {
						// EAST or WEST
						if ((currentPos.x - checkpointsPos.getLast().x) > 0) {
							// EAST
							newCheckPoint = new Point(checkpointsPos.getLast().x + DIST_MODEL,
									checkpointsPos.getLast().y);

						} else {
							// WEST
							newCheckPoint = new Point(checkpointsPos.getLast().x - DIST_MODEL,
									checkpointsPos.getLast().y);

						}

					} else {
						// NORTH or SOUTH
						if ((currentPos.x - checkpointsPos.getLast().x) > 0) {
							// EAST
							newCheckPoint = new Point(checkpointsPos.getLast().x,
									checkpointsPos.getLast().y + DIST_MODEL);

						} else {
							// WEST
							newCheckPoint = new Point(checkpointsPos.getLast().x,
									checkpointsPos.getLast().y - DIST_MODEL);

						}

					}
					modelPoints.add(newCheckPoint);
					checkpointsPos.add(currentPos);
					repaint();
					paintCheckPoints();
				}
			}
		});
	}

	protected Point getCurrentPosition(MouseEvent e) {
		return new Point(e.getX(), e.getY());
	}

	protected void drawNewSegment(Point p1, Point p2) {
		getGraphics().drawLine(prevPos.x, prevPos.y, currentPos.x, currentPos.y);
	}

	protected void paintCheckPoints() {
		Point p1, p2;
		Iterator<Point> iter = checkpointsPos.iterator();
		if (iter.hasNext()) {
			p1 = iter.next();
			while (iter.hasNext()) {
				p2 = iter.next();
				drawNewSegment(p1, p2);
				System.out.println("drawing");
				System.out.println(p1.toString());
				System.out.println(p2.toString());
				p1 = p2;
			}
		}
	}

	protected void paintModel() {
		Point p1, p2;
		Iterator<Point> iter = modelPoints.iterator();
		if (iter.hasNext()) {
			p1 = iter.next();
			while (iter.hasNext()) {
				p2 = iter.next();
				drawNewSegment(p1, p2);
				p1 = p2;
			}
		}
	}

}
