import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Controller implements MouseListener , MouseMotionListener , ActionListener {
	Timer timer;

	A_Star Path;

	GUIPanel  GUI;
	GridPanel Grid;

	boolean SelectStart = false , SelectTarget = false;

	public Controller(GUIPanel _GUI , GridPanel _Grid) {
		GUI = _GUI;
		Grid = _Grid;

		//Create a Timer to Control Visualization 
		timer = new Timer(50 , this);

		//Create a Path , we then use timer to control our Visualization
		Path = new A_Star(Grid);

		//Add Action Listener to GUI.Buttons
		GUI.StartButton.addActionListener(this);
		GUI.PauseButton.addActionListener(this);
		GUI.ClearButton.addActionListener(this);

		//Add Mouse Listener to GridPanel
		Grid.addMouseListener(this);
		Grid.addMouseMotionListener(this);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		updateGird(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		updateGird(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		SelectStart  = false;
		SelectTarget = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String Status = GUI.StartButton.Status;

		if(Status == "Running") {
			if(!Grid.finish) {
				Path.FindNext();
				Grid.repaint();
			}
			else {	
				timer.stop();
				Grid.finish = true;
				GUI.StartButton.SetStatus("Done");
			}
		}
		
		
		//Check Which Button Clicked
		if(e.getSource() == GUI.StartButton) {
			
			if(Status == "Run") {
				GUI.ClearButton.set_unClickable();
				GUI.PauseButton.set_Clickable();
				
				GUI.StartButton.SetStatus("Running");
				
				Path.Setup("");
				timer.start();
			}
			else if(Status == "Resume") {
				GUI.ClearButton.set_unClickable();
				GUI.PauseButton.set_Clickable();
				
				GUI.StartButton.SetStatus("Running");
				
				timer.start();
			}
		}

		else if(e.getSource() == GUI.PauseButton) {

			if(Status == "Running") {
				GUI.StartButton.SetStatus("Resume");
				GUI.ClearButton.set_Clickable();
				GUI.PauseButton.set_unClickable();

				timer.stop();
			}
		}
		
		else if(e.getSource() == GUI.ClearButton) {
			
			if(Status == "Done" || Status == "Resume") {
				Grid.ClearPath();

				GUI.StartButton.SetStatus("Run");
				GUI.PauseButton.set_unClickable();
				GUI.ClearButton.set_unClickable();
				
				Grid.finish = false;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
    
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	public void updateGird(MouseEvent e) {
		//Change the Start position of the mouse
		e.translatePoint(-Grid.x_start , -Grid.y_start);

		//Convert Position to Gird's Node
		int row = e.getY() / Grid.size;
		int col = e.getX() / Grid.size;

		//Get the Status of GUI.StartButton
		String Status = GUI.StartButton.Status;

		//Make Sure it is in the Grid
		//And only when Status is Run or Done can we chage the grid
		if (row >= 0 && row < Grid.rows && col >= 0 && col < Grid.cols && (Status == "Run" || Status == "Done" )) {

			//Check: Press By LeftMouseButton or RightMouseButton
			//If Right --> Cancel "Barrier"
			//If Left  --> Set "Start , Target , Barrier"
			if(SwingUtilities.isLeftMouseButton(e)) {
	    		
				//When Select Start or Target , We change Select to true
				//So we can Drag it
				if(Grid.StartNode.contains(row , col)) {
					//Make Sure We only Select Start or Target
					if(SelectTarget == false) {
						SelectStart = true;
					}
				}
				else if(Grid.TargetNode.contains(row , col)) {
					if(SelectStart == false) {
						SelectTarget = true;
					}
				}

				//Change Status of Node
				//Only Walkable Node can be changed
				if(Grid.board[row][col].IsWalkable) {
					if(SelectStart) {
						Grid.board[row][col].SetStatus("Start");

						//Set up New Start
						Grid.StartNode.Reset();
						Grid.StartNode = Grid.board[row][col];
					}

					else if(SelectTarget) {
						Grid.board[row][col].SetStatus("Target");

						//Set up New Target
						Grid.TargetNode.Reset();
						Grid.TargetNode = Grid.board[row][col];

					}

					else {
						Grid.board[row][col].SetStatus("Barrier");
					}
				}
			}
			//Cancel Barrier
			else if(SwingUtilities.isRightMouseButton(e)) {
				if(Grid.board[row][col].Status == "Barrier") {
					Grid.board[row][col].Reset();
				}
			}
			
			//If Status is Done , We find the result directly
			if(GUI.StartButton.Status == "Done") {
				Grid.ClearPath();
				Path.Setup("Done");
				Path.FindNext();
			}
			
			//We need to repaint after Change Grid
			Grid.repaint();
		}
	}
}
