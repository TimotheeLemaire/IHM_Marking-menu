package markMenu;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MarkMenuPanel extends JPanel {

	/**
	 * 
	 */

	static final int DIST_MODEL = 100;

	private static final long serialVersionUID = 1L;

	protected JButton northOption;
	protected JButton eastOption;
	protected JButton southOption;
	protected JButton westOption;

	protected Point startPos;
	// point of the real shape
	protected LinkedList<Point> checkpointsPos = new LinkedList<Point>();
	// points of the recognized model (with 90° angles and normalized distance)
	protected LinkedList<Point> modelPoints = new LinkedList<Point>();
	protected Point lastCheckPoint;
	protected Point lastModelPoint;
	protected Point prevPos;
	protected Point currentPos;
	protected Direction oldDirection = Direction.north;
	protected Direction newDirection;
	protected Direction currentDir;

	protected TimerTask menuTask;

	public MarkMenuPanel() {
		super();

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

				detectCheckPoints(e);

				erase();
				paintModel();
				checkpointsPos = new LinkedList<Point>();
				modelPoints = new LinkedList<Point>();
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}

			@Override
			public void mousePressed(MouseEvent e) {

				startPos = getCurrentPosition(e);
				lastCheckPoint = startPos;
				checkpointsPos.add(lastCheckPoint);
				lastModelPoint = startPos;
				modelPoints.add(lastModelPoint);
				currentPos = getCurrentPosition(e);
				displayMenu(startPos);
				setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
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

				// System.out.println(currentPos.toString());
				// System.out.println(Math.hypot(currentPos.x - prevPos.x, currentPos.y -
				// prevPos.y));

				detectCheckPoints(e);

				drawSegment(prevPos, currentPos);

			}
		});
	}

	protected Direction detectDirection(Point oldPos, Point newPos) {
		if (Math.abs(newPos.x - oldPos.x) > Math.abs(newPos.y - oldPos.y)) {
			// EAST or WEST
			if ((newPos.x - oldPos.x) > 0) {
				return Direction.east;
			} else {
				// WEST
				return Direction.west;
			}

		} else {
			// NORTH or SOUTH
			if ((newPos.y - oldPos.y) > 0) {
				return Direction.south;
			} else {
				return Direction.north;
			}
		}
	}

	protected Point getCurrentPosition(MouseEvent e) {
		return new Point(e.getX(), e.getY());
	}

	protected void drawSegment(Point p1, Point p2) {
		this.getGraphics().drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	protected void detectCheckPoints(MouseEvent e) {

		prevPos = currentPos;
		currentPos = getCurrentPosition(e);
		oldDirection = newDirection;
		newDirection = detectDirection(prevPos, currentPos);

		if (Math.hypot(currentPos.x - checkpointsPos.getLast().x, currentPos.y - checkpointsPos.getLast().y) > 100
				&& ((oldDirection != newDirection)
						|| (Math.hypot(currentPos.x - prevPos.x, currentPos.y - prevPos.y) <= 1))) {

//			int dist_model = DIST_MODEL;
			int dist_model = (int) Math.hypot(currentPos.x - checkpointsPos.getLast().x,
					currentPos.y - checkpointsPos.getLast().y);

			Point newModelPoint;

			switch (detectDirection(lastCheckPoint, currentPos)) {
			case north:
				newModelPoint = new Point(lastModelPoint.x, lastModelPoint.y - dist_model);
				break;
			case east:
				newModelPoint = new Point(lastModelPoint.x + dist_model, lastModelPoint.y);
				break;
			case south:
				newModelPoint = new Point(lastModelPoint.x, lastModelPoint.y + dist_model);
				break;
			case west:
				newModelPoint = new Point(lastModelPoint.x - dist_model, lastModelPoint.y);
				break;
			default:
				newModelPoint = lastModelPoint;
				break;
			}

			lastCheckPoint = currentPos;
			checkpointsPos.add(lastCheckPoint);
			lastModelPoint = newModelPoint;
			modelPoints.add(lastModelPoint);
			erase();
			displayMenu(currentPos);
			paintCheckPoints();
		}
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
		this.remove(northOption);
		this.remove(eastOption);
		this.remove(southOption);
		this.remove(westOption);
		getGraphics().clearRect(0, 0, this.getWidth(), this.getHeight());

	}

	protected void displayMenu(Point position) {

		this.setLayout(null);
		int distance = 100;

		northOption = new JButton();
		eastOption = new JButton();
		southOption = new JButton();
		westOption = new JButton();

		// placeholder

		northOption.setText("north");
		eastOption.setText("east");
		southOption.setText("south");
		westOption.setText("west");

		this.add(northOption);
		this.add(eastOption);
		this.add(southOption);
		this.add(westOption);

		northOption.setSize(northOption.getPreferredSize());
		eastOption.setSize(eastOption.getPreferredSize());
		southOption.setSize(southOption.getPreferredSize());
		westOption.setSize(westOption.getPreferredSize());

		northOption.setBounds(position.x - northOption.getWidth() / 2,
				position.y - northOption.getHeight() / 2 - distance, northOption.getWidth(), northOption.getHeight());
		eastOption.setBounds(position.x - eastOption.getWidth() / 2 + distance, position.y - eastOption.getHeight() / 2,
				eastOption.getWidth(), eastOption.getHeight());
		southOption.setBounds(position.x - southOption.getWidth() / 2,
				position.y - southOption.getHeight() / 2 + distance, southOption.getWidth(), southOption.getHeight());
		westOption.setBounds(position.x - westOption.getWidth() / 2 - distance, position.y - westOption.getHeight() / 2,
				westOption.getWidth(), westOption.getHeight());

	}

}
