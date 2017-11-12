package markMenu;

public class MenuLeaf extends MenuNode{
	
	public MenuLeaf(String label) {
		super(label);
		this.child1=null;
		this.child2=null;
		this.child3=null;

		// TODO Auto-generated constructor stub
	}
	
	public void action() {
		run.log.setText(label);
	}
	
	public void setChild1(MenuNode none) {
		
	}
	public void setChild2(MenuNode none) {
		
	}
	public void setChild3(MenuNode none) {
		
	}
	
	public Boolean isleaf() {
		return true;
	}
}
