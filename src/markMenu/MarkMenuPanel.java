package markMenu;

import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MarkMenuPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JLabel northOption;
	protected JLabel eastOption;
	protected JLabel southOption;
	protected JLabel westOption;

	protected JPanel g = this;

	protected Point startPos;
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
				
				repaint();			
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				startPos = getCurrentPosition(e);
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
				drawNewSegment();
			}
		});
	}

	protected Point getCurrentPosition(MouseEvent e) {
		return new Point(e.getX(), e.getY());
	}

	public void repaint() {
		super.repaint();

	}
	protected void drawNewSegment() {
		getGraphics().drawLine(prevPos.x, prevPos.y, currentPos.x, currentPos.y);
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
