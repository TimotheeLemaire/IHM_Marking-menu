package markMenu;

public class MenuNode {
	
	protected String label;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	protected MenuNode child1;
	protected MenuNode child2;
	protected MenuNode child3;
		
	public MenuNode(String label) {
		this.label=label;
		// TODO Auto-generated constructor stub
	}
	
	public MenuNode(MenuNode child1, MenuNode child2, MenuNode child3, String label) {
		// TODO Auto-generated constructor stub
		this(label);
		this.child1=child1;
		this.child2=child2;
		this.child3=child3;
	}
	
	public MenuNode getChild1() {
		return child1;
	}

	public void setChild1(MenuNode child1) {
		this.child1 = child1;
	}

	public MenuNode getChild2() {
		return child2;
	}

	public void setChild2(MenuNode child2) {
		this.child2 = child2;
	}

	public MenuNode getChild3() {
		return child3;
	}

	public void setChild3(MenuNode child3) {
		this.child3 = child3;
	}
	
	public Boolean isleaf() {
		return false;
	}
	
}
