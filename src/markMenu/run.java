package markMenu;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame ();
		
		JPanel panel = new JPanel();
		frame.setTitle("marking menu");
		
		frame.add(panel);
		panel.setPreferredSize(new Dimension(500,500));
		
		
		
		frame.pack();
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
        
      
	}

}
