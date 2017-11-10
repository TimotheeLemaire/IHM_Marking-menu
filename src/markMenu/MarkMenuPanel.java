package markMenu;

import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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

}
