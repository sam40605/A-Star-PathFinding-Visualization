public class Node {
	public int row , col;
	public int G_cost = 0 , F_cost = 0 , H_cost = 0;

	public Node Parent;
	public String Status = "Road";

	public boolean IsWalkable = true;

	public Node(int _row , int _col) {
		row = _row;
		col = _col;
	}

	public void SetStatus(String s) {
		Status = s;

		//Set unWalkable if Status is Target or Start or Barrier
		if(s == "Barrier" || s == "Target" || s == "Start") {
			IsWalkable = false;
		}
	}

	//Ste it to Default
	public void Reset() {
		G_cost = 0;
		F_cost = 0;
		H_cost = 0;
		Parent = null;
		Status = "Road";
		IsWalkable = true;
	}

	//Check if mouse in on the Node
	public boolean contains(int _row , int _col) {
		return (row == _row && col == _col);
	}
}