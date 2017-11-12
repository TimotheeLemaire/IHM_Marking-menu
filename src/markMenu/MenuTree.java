package markMenu;

public class MenuTree extends MenuNode{

	protected MenuNode child4;
	
	public MenuNode getChild4() {
		return child4;
	}

	public void setChild4(MenuNode child4) {
		this.child4 = child4;
	}

	public MenuTree(MenuNode child1, MenuNode child2, MenuNode child3, MenuNode child4, String label) {
		super(child1, child2, child3, label);
		// TODO Auto-generated constructor stub
		this.child4 = child4;
	}

}
