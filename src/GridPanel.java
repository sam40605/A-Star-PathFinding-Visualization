import java.awt.*;
import javax.swing.*;

public class GridPanel extends JPanel {
	Style style;

	int rows , cols , size;

	int panel_Width , panel_Height;
	int grid_Width  , grid_Height;
	int x_start , y_start;

	boolean HasSetup = false , finish = false;
	String Status;

	Node[][] board;
	Node StartNode , TargetNode;

	public GridPanel(Style _style) {
		style = _style;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		//Set Once
		if (!HasSetup) setup();

		//Center the Grid
		g2d.translate(x_start , y_start);

		//Draw Grid
		Draw_Grid(g2d);
		
		//Draw Grid Line
		Draw_Grid_Line(g2d);


		g2d.dispose();
	}

	public void setup(){
		//Set Node's Width to 30
		size = 30;

		//Get the Panel's Size
		panel_Width  = getWidth();
		panel_Height = getHeight();

		//Get How many rows we have
		//We also want to have some edges
		rows = (panel_Height - 10) / size;
		cols = (panel_Width  -  7) / size; 

		//Get the Grid's Size
		grid_Width  = cols * size;
		grid_Height = rows * size;

		//We want to center the Grid
		x_start = (panel_Width  - grid_Width)  / 2;
		y_start = (panel_Height - grid_Height) / 2;

		//Create the Grid
		board = new Node[rows][cols];

		for(int row = 0 ; row < rows ; row++) {
			for(int col = 0 ; col < cols ; col++) {
				board[row][col] = new Node(row , col);
			}
		}

		//Set up Start and Target
		StartNode = board[0][0];
		StartNode.SetStatus("Start");

		TargetNode = board[rows - 1][cols - 1];
		TargetNode.SetStatus("Target");

		//Set HasSetup to true
		HasSetup = true;
	}

	public void Draw_Grid_Line(Graphics2D g2d) {
		//Set Stroke to 3 ( Looks Better!! )
		g2d.setStroke(new BasicStroke(3));

		//Set Line's Color to Black
		g2d.setColor(style.Black);

		//Draw Horizontal Line
		for(int row = 0 ; row <= rows ; row++) {
			int y = row * size;
			g2d.drawLine(0 , y , grid_Width , y);
		}

		//Draw Vertical Line
		for(int col = 0 ; col <= cols ; col++) {
			int x = col * size;
			g2d.drawLine(x , 0 , x , grid_Height);
		}
	}

	public void Draw_Grid(Graphics g2d) {
		//Change Color , depend on Node's Status
		for(int row = 0 ; row < rows ; row++) {
			for(int col = 0 ; col < cols ; col++) {
				switch(board[row][col].Status) {
					case "Road":
						continue;
					case "Start":
						g2d.setColor(style.DBlue);
						break;
					case "Target":
						g2d.setColor(style.Red);
						break;
					case "Barrier":
						g2d.setColor(style.Gray);
						break;
					case "Path":
						g2d.setColor(style.Blue);
						break;
					case "Visited":
						g2d.setColor(style.Green);
						break;
					case "Closed":
						g2d.setColor(style.Purple);
						break;
				}

				g2d.fillRect(col * size , row * size , size , size);
			}
		}
	}

	//Clear (Visited , Closed , Path)
	public void ClearPath() {
		for(int row = 0 ; row < rows ; row++) {
            for(int col = 0 ; col < cols ; col++) {
                if (board[row][col].Status != "Barrier") {
                    board[row][col].Reset();
                }
            }
        }

        StartNode.SetStatus("Start");
        TargetNode.SetStatus("Target");

        repaint();
	}
}