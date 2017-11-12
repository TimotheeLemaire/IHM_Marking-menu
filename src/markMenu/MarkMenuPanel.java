package markMenu;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;

public class MarkMenuPanel extends JPanel {

	/**
	 * 
	 */

	static final int DIST_MODEL = 100;

	private static final long serialVersionUID = 1L;

	protected Rectangle northOption;
	protected Rectangle eastOption;
	protected Rectangle southOption;
	protected Rectangle westOption;

	protected String northLabel;
	protected String eastLabel;
	protected String southLabel;
	protected String westLabel;

	protected Point startPos;
	// point of the real shape
	protected LinkedList<Point> checkpointsPos = new LinkedList<Point>();
	// points of the recognized model (with 90° angles and normalized distance)
	protected LinkedList<Point> modelPoints = new LinkedList<Point>();
	protected Point lastCheckPoint;
	protected Point lastModelPoint;
	protected Direction lastCheckpointDirection;
	protected Point prevPos;
	protected Point currentPos;
	protected Direction oldDirection = Direction.north;
	protected Direction newDirection;

	protected MenuTree menu;
	protected MenuNode currentEntry;

	public MarkMenuPanel(MenuTree menu) {
		super();

		this.menu = menu;

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

				detectCheckPoints(e);

				erase();
				paintModel();
				checkpointsPos = new LinkedList<Point>();
				modelPoints = new LinkedList<Point>();
				lastCheckpointDirection = null;
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

				if (currentEntry.isleaf()) {
					((MenuLeaf) currentEntry).action();
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {

				currentEntry = menu;

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

				erase();
				paintCheckPoints();
				displayMenu(lastCheckPoint);

				detectCheckPoints(e);

				drawSegment(lastCheckPoint, currentPos);

			}
		});

	}

	protected Direction detectDirection(Point oldPos, Point newPos) {
		if (Math.abs(newPos.x - oldPos.x) > Math.abs(newPos.y - oldPos.y)) {
			// EAST or WEST
			if ((newPos.x - oldPos.x) > 0) {
				// EAST
				if (lastCheckpointDirection != null && lastCheckpointDirection == Direction.west) {
					if ((newPos.y - oldPos.y) > 0) {
						return Direction.south;
					} else {
						return Direction.north;
					}
				} else {
					return Direction.east;
				}
			} else {
				// WEST
				if (lastCheckpointDirection != null && lastCheckpointDirection == Direction.east) {
					if ((newPos.y - oldPos.y) > 0) {
						return Direction.south;
					} else {
						return Direction.north;
					}
				} else {
					return Direction.west;
				}
			}

		} else {
			// NORTH or SOUTH
			if ((newPos.y - oldPos.y) > 0) {
				// SOUTH
				if (lastCheckpointDirection != null && lastCheckpointDirection == Direction.north) {
					if ((newPos.x - oldPos.x) > 0) {
						return Direction.east;
					} else {
						return Direction.west;
					}
				} else {
					return Direction.south;
				}
			} else {
				// NORTH
				if (lastCheckpointDirection != null && lastCheckpointDirection == Direction.south) {
					if ((newPos.x - oldPos.x) > 0) {
						return Direction.east;
					} else {
						return Direction.west;
					}
				} else {
					return Direction.north;
				}
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
						|| (Math.hypot(currentPos.x - prevPos.x, currentPos.y - prevPos.y) <= 2)
						|| Math.hypot(currentPos.x - checkpointsPos.getLast().x,
								currentPos.y - checkpointsPos.getLast().y) > 300)) {

			int dist_model = DIST_MODEL;
			// int dist_model = (int) Math.hypot(currentPos.x - checkpointsPos.getLast().x,
			// currentPos.y - checkpointsPos.getLast().y);

			Point newModelPoint;
			Direction newCheckpointDirection = detectDirection(lastCheckPoint, currentPos);

			if (!currentEntry.isleaf()) {

				switch (newCheckpointDirection) {
				case north:
					newModelPoint = new Point(lastModelPoint.x, lastModelPoint.y - dist_model);
					if (lastCheckpointDirection == null)
						currentEntry = currentEntry.getChild1();
					else
						switch (lastCheckpointDirection) {
						case east:
							currentEntry = currentEntry.getChild1();
							break;
						case north:
							currentEntry = currentEntry.getChild2();
							break;
						case west:
							currentEntry = currentEntry.getChild3();
							break;
						default:
							break;
						}
					break;
				case east:
					newModelPoint = new Point(lastModelPoint.x + dist_model, lastModelPoint.y);
					if (lastCheckpointDirection == null)
						currentEntry = currentEntry.getChild2();
					else
						switch (lastCheckpointDirection) {
						case north:
							currentEntry = currentEntry.getChild3();
							break;
						case east:
							currentEntry = currentEntry.getChild2();
							break;
						case south:
							currentEntry = currentEntry.getChild1();
							break;
						default:
							break;
						}
					break;

				case south:
					newModelPoint = new Point(lastModelPoint.x, lastModelPoint.y + dist_model);
					if (lastCheckpointDirection == null)
						currentEntry = currentEntry.getChild3();
					else
						switch (lastCheckpointDirection) {
						case east:
							currentEntry = currentEntry.getChild3();
							break;
						case south:
							currentEntry = currentEntry.getChild2();
							break;
						case west:
							currentEntry = currentEntry.getChild1();
							break;
						default:
							break;
						}
					break;
				case west:
					newModelPoint = new Point(lastModelPoint.x - dist_model, lastModelPoint.y);
					if (lastCheckpointDirection == null)
						currentEntry = ((MenuTree) currentEntry).getChild4();
					else
						switch (lastCheckpointDirection) {
						case south:
							currentEntry = currentEntry.getChild3();
							break;
						case west:
							currentEntry = currentEntry.getChild2();
							break;
						case north:
							currentEntry = currentEntry.getChild1();
							break;
						default:
							break;
						}
					break;
				default:
					newModelPoint = lastModelPoint;
					break;
				}
				lastCheckpointDirection = newCheckpointDirection;
				lastCheckPoint = currentPos;
				checkpointsPos.add(lastCheckPoint);
				lastModelPoint = newModelPoint;
				modelPoints.add(lastModelPoint);
				erase();
				paintCheckPoints();
				displayMenu(currentPos);
			}
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

	protected void displayMenu(Point position) {

		this.setLayout(null);
		int distance = 100;

		northOption = new Rectangle();
		eastOption = new Rectangle();
		southOption = new Rectangle();
		westOption = new Rectangle();

		if (!currentEntry.isleaf()) {

			// placeholder
			if (currentEntry instanceof MenuTree) {
				northLabel = currentEntry.getChild1().getLabel();
				eastLabel = currentEntry.getChild2().getLabel();
				southLabel = currentEntry.getChild3().getLabel();
				westLabel = ((MenuTree) currentEntry).getChild4().getLabel();
			} else {
				LinkedList<MenuNode> childs = new LinkedList<MenuNode>();
				childs.add(currentEntry.child1);
				childs.add(currentEntry.child2);
				childs.add(currentEntry.child3);
				int next = (lastCheckpointDirection.ordinal() + 3) % 4;
				for (int i = 0; i < 3; i++) {
					switch (next) {
					case 0:
						northLabel = childs.get(i).getLabel();
						break;
					case 1:
						eastLabel = childs.get(i).getLabel();
						break;
					case 2:
						southLabel = childs.get(i).getLabel();
						break;
					case 3:
						westLabel = childs.get(i).getLabel();
						break;
					}
					next = (next + 1) % 4;
				}
				switch (next) {
				case 0:
					northLabel = "";
					break;
				case 1:
					eastLabel = "";
					break;
				case 2:
					southLabel = "";
					break;
				case 3:
					westLabel = "";
					break;
				}
			}

			this.northOption.setSize((int) (getGraphics().getFontMetrics().stringWidth(northLabel) + 14), 20);
			this.eastOption.setSize((int) (getGraphics().getFontMetrics().stringWidth(eastLabel) + 14), 20);
			this.southOption.setSize((int) (getGraphics().getFontMetrics().stringWidth(southLabel) + 14), 20);
			this.westOption.setSize((int) (getGraphics().getFontMetrics().stringWidth(westLabel) + 14), 20);

			if (northLabel != "")
				getGraphics().clearRect(position.x - (int) northOption.getWidth() / 2,
						position.y - (int) northOption.getHeight() / 2 - distance, (int) northOption.getWidth(),
						(int) northOption.getHeight());
			if (eastLabel != "")
				getGraphics().clearRect(position.x - (int) eastOption.getWidth() / 2 + distance,
						position.y - (int) eastOption.getHeight() / 2, (int) eastOption.getWidth(),
						(int) eastOption.getHeight());
			if (southLabel != "")
				getGraphics().clearRect(position.x - (int) southOption.getWidth() / 2,
						position.y - (int) southOption.getHeight() / 2 + distance, (int) southOption.getWidth(),
						(int) southOption.getHeight());
			if (westLabel != "")
				getGraphics().clearRect(position.x - (int) westOption.getWidth() / 2 - distance,
						position.y - (int) westOption.getHeight() / 2, (int) westOption.getWidth(),
						(int) westOption.getHeight());

			if (Math.hypot(currentPos.x - checkpointsPos.getLast().x,
					currentPos.y - checkpointsPos.getLast().y) > 100) {
				Graphics graph = this.getGraphics();
				graph.setColor(Color.CYAN);
				switch (newDirection) {
				case north:
					if (northLabel != "")
						graph.fillRect(position.x - (int) northOption.getWidth() / 2,
								position.y - (int) northOption.getHeight() / 2 - distance, (int) northOption.getWidth(),
								(int) northOption.getHeight());
					break;
				case east:
					if (eastLabel != "")
						graph.fillRect(position.x - (int) eastOption.getWidth() / 2 + distance,
								position.y - (int) eastOption.getHeight() / 2, (int) eastOption.getWidth(),
								(int) eastOption.getHeight());
					break;
				case south:
					if (southLabel != "")
						graph.fillRect(position.x - (int) southOption.getWidth() / 2,
								position.y - (int) southOption.getHeight() / 2 + distance, (int) southOption.getWidth(),
								(int) southOption.getHeight());
					break;
				case west:
					if (westLabel != "")
						graph.fillRect(position.x - (int) westOption.getWidth() / 2 - distance,
								position.y - (int) westOption.getHeight() / 2, (int) westOption.getWidth(),
								(int) westOption.getHeight());
					break;
				default:
					break;
				}
			}

			if (northLabel != "") {
				this.getGraphics().drawString(northLabel, position.x - (int) northOption.getWidth() / 2 + 7,
						position.y - (int) northOption.getHeight() / 2 - distance + 15);
				this.getGraphics().drawRect(position.x - (int) northOption.getWidth() / 2,
						position.y - (int) northOption.getHeight() / 2 - distance, (int) northOption.getWidth(),
						(int) northOption.getHeight());
			}
			if (eastLabel != "") {
				this.getGraphics().drawString(eastLabel, position.x - (int) eastOption.getWidth() / 2 + 7 + distance,
						position.y - (int) eastOption.getHeight() / 2 + 15);
				this.getGraphics().drawRect(position.x - (int) eastOption.getWidth() / 2 + distance,
						position.y - (int) eastOption.getHeight() / 2, (int) eastOption.getWidth(),
						(int) eastOption.getHeight());
			}
			if (southLabel != "") {
				this.getGraphics().drawString(southLabel, position.x - (int) southOption.getWidth() / 2 + 7,
						position.y - (int) southOption.getHeight() / 2 + 15 + distance);
				this.getGraphics().drawRect(position.x - (int) southOption.getWidth() / 2,
						position.y - (int) southOption.getHeight() / 2 + distance, (int) southOption.getWidth(),
						(int) southOption.getHeight());
			}
			if (westLabel != "") {
				this.getGraphics().drawString(westLabel, position.x - (int) westOption.getWidth() / 2 + 7 - distance,
						position.y - (int) westOption.getHeight() / 2 + 15);
				this.getGraphics().drawRect(position.x - (int) westOption.getWidth() / 2 - distance,
						position.y - (int) westOption.getHeight() / 2, (int) westOption.getWidth(),
						(int) westOption.getHeight());
			}
		}
	}
}
