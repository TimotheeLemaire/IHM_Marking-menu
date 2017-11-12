package markMenu;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

public class run {

	public static JEditorPane log;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		
		JFrame frame = new JFrame();
		MenuTree tree = constructMenu();
		MarkMenuPanel panel = new MarkMenuPanel(tree);
		frame.setTitle("marking menu");
				
		log = new JEditorPane();
		log.setEditable(false);
		
		frame.add(log,BorderLayout.SOUTH);
		
		frame.add(panel);
		
		panel.setPreferredSize(new Dimension(750,750));
		
		frame.pack();
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}

	private static MenuTree constructMenu() {

		// Menu...

		MenuLeaf nws = new MenuLeaf("NWS");
		MenuLeaf nww = new MenuLeaf("NWW");
		MenuLeaf nwn = new MenuLeaf("NWN");

		MenuLeaf nnw = new MenuLeaf("NNW");
		MenuLeaf nnn = new MenuLeaf("NNN");
		MenuLeaf nne = new MenuLeaf("NNE");

		MenuLeaf nen = new MenuLeaf("NEN");
		MenuLeaf nee = new MenuLeaf("NEE");
		MenuLeaf nes = new MenuLeaf("NES");

		MenuLeaf enw = new MenuLeaf("ENW");
		MenuLeaf enn = new MenuLeaf("ENN");
		MenuLeaf ene = new MenuLeaf("ENE");

		MenuLeaf een = new MenuLeaf("EEN");
		MenuLeaf eee = new MenuLeaf("EEE");
		MenuLeaf ees = new MenuLeaf("EES");

		MenuLeaf ese = new MenuLeaf("ESE");
		MenuLeaf ess = new MenuLeaf("ESS");
		MenuLeaf esw = new MenuLeaf("ESW");

		MenuLeaf sen = new MenuLeaf("SEN");
		MenuLeaf see = new MenuLeaf("SEE");
		MenuLeaf ses = new MenuLeaf("SES");

		MenuLeaf sse = new MenuLeaf("SSE");
		MenuLeaf sss = new MenuLeaf("SSS");
		MenuLeaf ssw = new MenuLeaf("SSW");

		MenuLeaf sws = new MenuLeaf("SWS");
		MenuLeaf sww = new MenuLeaf("SWW");
		MenuLeaf swn = new MenuLeaf("SWN");

		MenuLeaf wse = new MenuLeaf("WSE");
		MenuLeaf wss = new MenuLeaf("WSS");
		MenuLeaf wsw = new MenuLeaf("WSW");

		MenuLeaf wws = new MenuLeaf("WWS");
		MenuLeaf www = new MenuLeaf("WWW");
		MenuLeaf wwn = new MenuLeaf("WWN");

		MenuLeaf wnw = new MenuLeaf("WNW");
		MenuLeaf wnn = new MenuLeaf("WNN");
		MenuLeaf wne = new MenuLeaf("WNE");

		MenuNode nw = new MenuNode(nws, nww, nwn, "NW");
		MenuNode nn = new MenuNode(nnw, nnn, nne, "NN");
		MenuNode ne = new MenuNode(nen, nee, nes, "NE");

		MenuNode en = new MenuNode(enw, enn, ene, "EN");
		MenuNode ee = new MenuNode(een, eee, ees, "EE");
		MenuNode es = new MenuNode(ese, ess, esw, "ES");

		MenuNode se = new MenuNode(sen, see, ses, "SE");
		MenuNode ss = new MenuNode(sse, sss, ssw, "SS");
		MenuNode sw = new MenuNode(sws, sww, swn, "SW");

		MenuNode ws = new MenuNode(wse, wss, wsw, "WS");
		MenuNode ww = new MenuNode(wws, www, wwn, "WW");
		MenuNode wn = new MenuNode(wnw, wnn, wne, "WN");

		MenuNode n = new MenuNode(nw, nn, ne, "N");
		MenuNode e = new MenuNode(en, ee, es, "E");
		MenuNode s = new MenuNode(se, ss, sw, "S");
		MenuNode w = new MenuNode(ws, ww, wn, "W");

		return new MenuTree(n, e, s, w, "root");
		// tree.setChild1(n);
		// tree.setChild2(e);
		// tree.setChild3(s);
		// tree.setChild4(w);

	}

}
