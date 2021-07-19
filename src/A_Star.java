import java.util.ArrayList;

public class A_Star {
	GridPanel Grid;

	private int rows , cols;

	private Node[][] board;
	private Node StartNode , TargetNode;

	private ArrayList<Node> OpenSet , ClosedSet;
	
	public String Status;

	public A_Star(GridPanel _Grid) {
		Grid = _Grid;
	}

	//To Set up new Start and Target
	public void Setup(String s) {
		Status = s;

		rows = Grid.rows;
		cols = Grid.cols;

		board = Grid.board;

		OpenSet   = new ArrayList<Node>();
		ClosedSet = new ArrayList<Node>();

		StartNode  = Grid.StartNode;
		TargetNode = Grid.TargetNode;  

		OpenSet.add(StartNode);
	}

	//Since We want to Show Visualization
	//So we Find Next again and again 
	//Until we reach the Target
	//And I don't know how to explain this Algorithm
	public void FindNext() {
		if(OpenSet.isEmpty()) {
			Grid.finish = true;
			return;
		}

		Node current = GetMinFcost();

		if(current == TargetNode) {
			reTrace();
			return;
		}

		OpenSet.remove(current);
		ClosedSet.add(current);

		if(current.Status == "Visited") current.SetStatus("Closed");

		for(Node neighbor : GetNeighbors(current)) {
			if(neighbor.Status == "Barrier" || ClosedSet.contains(neighbor)) {
				continue;
			}

			int newCost = current.G_cost + GetDistance(current , neighbor);
			if(newCost < neighbor.G_cost || !OpenSet.contains(neighbor)) {
				neighbor.G_cost = newCost;
				neighbor.H_cost = GetDistance(neighbor , TargetNode);
				neighbor.F_cost = neighbor.G_cost + neighbor.H_cost;
				
				neighbor.Parent = current;

				if(!OpenSet.contains(neighbor)) {
					OpenSet.add(neighbor);

					if(neighbor.Status == "Road") neighbor.SetStatus("Visited");
				}
			}
		}

		// Find Final Path If Status is Done
		if(Status == "Done") FindNext();
	}

	private Node GetMinFcost() {
		Node min = OpenSet.get(0);

		for(int i = 1 ; i < OpenSet.size() ; i++) {
			Node now = OpenSet.get(i);
			if(now.F_cost <= min.F_cost && now.H_cost < min.H_cost) {
				min = now;
			}
		}

		return min;
	}

	private ArrayList<Node> GetNeighbors(Node current) {
		ArrayList<Node> neighbors = new ArrayList<Node>();

		for(int row = -1 ; row <= 1 ; row++) {
			for(int col = -1 ; col <= 1 ; col++) {
				if(row == 0 && col == 0){
					continue;
				}

				int checkrow = current.row + row;
				int checkcol = current.col + col;

				if(checkrow >= 0 && checkrow < rows && checkcol >= 0 && checkcol < cols) {
					neighbors.add(board[checkrow][checkcol]);
				}
			}
		}

		return neighbors;
	}
	
	private int GetDistance(Node A , Node B) {
		int disrow = Math.abs(A.row - B.row);
		int discol = Math.abs(A.col - B.col);
        
		if(disrow > discol) {
			return 14 * discol + 10 * (disrow - discol); 
		}
		return 14 * disrow + 10 * (discol - disrow);
	}

	private void reTrace() {
		Node currentNode = TargetNode.Parent;

		while(currentNode != StartNode && currentNode != null) {
			currentNode.SetStatus("Path");
			currentNode = currentNode.Parent;
		}

		//Stop Timer 
		Grid.finish = true;
	}
}

