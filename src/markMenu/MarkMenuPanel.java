package markMenu;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.TimerTask;
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
	
	protected Directions currentDir;

	protected TimerTask menuTask;
	
	public MarkMenuPanel() {
		super();

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				erase();
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
				displayMenu();
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
				drawSegment(prevPos, currentPos);

				//System.out.println(currentPos.toString());
				System.out.println(Math.hypot(currentPos.x - prevPos.x, currentPos.y - prevPos.y));

				if (Math.hypot(currentPos.x - prevPos.x, currentPos.y - prevPos.y) <= 1 && 
						Math.hypot(currentPos.x - checkpointsPos.getLast().x, currentPos.y - checkpointsPos.getLast().y) > 100) {
					
					int dist_model = (int) Math.hypot(currentPos.x - checkpointsPos.getLast().x, currentPos.y - checkpointsPos.getLast().y);
					
					Point lastCheckPoint = checkpointsPos.getLast();
					Point lastModelPoint = modelPoints.getLast();
					Point newModelPoint;
					if (Math.abs(currentPos.x - lastCheckPoint.x) > Math.abs(currentPos.y - lastCheckPoint.y)) {
						// EAST or WEST
						if ((currentPos.x - lastCheckPoint.x) > 0) {
							// EAST
							System.out.println("east");
							newModelPoint = new Point(lastModelPoint.x + dist_model,
									lastCheckPoint.y);

						} else {
							// WEST
							System.out.println("west");
							newModelPoint = new Point(lastModelPoint.x - dist_model,
									lastCheckPoint.y);

						}

					} else {
						// NORTH or SOUTH
						if ((currentPos.y - lastCheckPoint.y) > 0) {
							// SOUTH
							System.out.println("south");
							newModelPoint = new Point(lastModelPoint.x,
									lastCheckPoint.y + dist_model);

						} else {
							// NORTH
							System.out.println("north");
							newModelPoint = new Point(lastModelPoint.x,
									lastCheckPoint.y - dist_model);

						}

					}
					modelPoints.add(newModelPoint);
					checkpointsPos.add(currentPos);
					erase();
					paintCheckPoints();
					//paintModel();
				}
			}
		});
	}

	protected Point getCurrentPosition(MouseEvent e) {
		return new Point(e.getX(), e.getY());
	}

	protected void drawSegment(Point p1, Point p2) {
		this.getGraphics().drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	protected void paintCheckPoints() {
		Point p1, p2;
		Iterator<Point> iter = checkpointsPos.iterator();
		if (iter.hasNext()) {
			p1 = iter.next();
			while (iter.hasNext()) {
				p2 = iter.next();
				drawSegment(p1, p2);
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
				drawSegment(p1, p2);
				p1 = p2;
			}
		}
	}
	
	protected void erase() {
		getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());
	}

	
	protected void displayMenu() {
		
		this.setLayout(null);
		int distance = 100;
		
		
		JLabel northLabel = new JLabel("Boulitre");
		JLabel eastLabel = new JLabel("Boulitre");
		JLabel southLabel = new JLabel("Boulitre");
		JLabel westLabel = new JLabel("Boulitre");
		this.add(northLabel);
		this.add(westLabel);
		this.add(southLabel);
		this.add(eastLabel);
		
        northLabel.setSize(northLabel.getPreferredSize());
        eastLabel.setSize(eastLabel.getPreferredSize());
        southLabel.setSize(southLabel.getPreferredSize());
        westLabel.setSize(westLabel.getPreferredSize());

		northLabel.setBounds(new Rectangle(startPos.x-northLabel.getWidth()/2         ,startPos.y-northLabel.getHeight()/2-distance,northLabel.getWidth(),northLabel.getHeight()));
		eastLabel.setBounds(new Rectangle( startPos.x- eastLabel.getWidth()/2+distance,startPos.y- eastLabel.getHeight()/2         , eastLabel.getWidth(), eastLabel.getHeight()));
		southLabel.setBounds(new Rectangle(startPos.x-southLabel.getWidth()/2         ,startPos.y-southLabel.getHeight()/2+distance,southLabel.getWidth(),southLabel.getHeight()));
		westLabel.setBounds(new Rectangle( startPos.x- westLabel.getWidth()/2-distance,startPos.y- westLabel.getHeight()/2         , westLabel.getWidth(), westLabel.getHeight()));

		
		repaint();
	}

}
