import java.awt.*;
import javax.swing.*;

public class Frame {
	JFrame window;
	
	Style style;
	Controller controller;
	GUIPanel  GUI;
	GridPanel Grid;

	public static void main(String[] args) {
		new Frame();
	}

	public Frame() {
		//Set up Window
		window = new JFrame();

		window.setTitle("A* Pathfinding Visualization");
		window.setSize(720 , 720);

		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create Style
		style = new Style();
		
		//Create Panels
		GUI  = new GUIPanel(style);
		Grid = new GridPanel(style);

		//Create Controller
		controller = new Controller(GUI , Grid);

		//Add Panels to Window
		window.add(GUI  , BorderLayout.NORTH);
		window.add(Grid , BorderLayout.CENTER);
		

		window.setVisible(true);
	}
}