import java.awt.*;
import javax.swing.*;

public class GUIPanel extends JPanel {
	Style style;

	TextLabel StartText , TargetText , WallText , PathText;
	TextLabel2 VisitedText;

	Button StartButton , PauseButton , ClearButton;

	public GUIPanel(Style _style) {
		style = _style;

		//Set the GUI's Height to 100 
		//Width is related to Frame , so it's ok to set it 0
		setPreferredSize(new Dimension(getWidth() , 100));

		//We want to set Label's Position to a specific (x , y)
		//So we need to setLayout to null
		setLayout(null);

		//Create Labels
		StartText   = new TextLabel (24  , 70 , "Start Node"  , style.DBlue);
		TargetText  = new TextLabel (160 , 70 , "Target Node" , style.Red); 
		WallText    = new TextLabel (304 , 70 , "Wall Node"   , style.Gray);
		PathText    = new TextLabel (437 , 70 , "Path Node"   , style.Blue);
		VisitedText = new TextLabel2(570 , 70 , "Visited Node" , style.Green , style.Purple);

		//Create Button
		StartButton = new Button(100 , 20 , "Run"   , style.Green);
		PauseButton = new Button(300 , 20 , "Pause" , style.Red);
		ClearButton = new Button(500 , 20 , "Clear Path" , style.Blue);

        //Since There is a Button
        //So We set the Cursor to Hand
		StartButton.setCursor(style.Hand);

		//Add Labels to GUIPanel
		add(StartText);
		add(TargetText);
		add(WallText);
		add(PathText);
		add(VisitedText);

		//Add Button to GUIPanel
		add(StartButton);
		add(PauseButton);
		add(ClearButton);

	}

	public class TextLabel extends JLabel {
		Color c;

		public TextLabel(int x , int y , String s , Color _c) {
			setText(s);

			//Set Label's Position and Size
			setBounds(x , y , getPreferredSize().width + 35 , 25);

			//Set Text's Position
            setHorizontalAlignment(SwingConstants.RIGHT);
            setVerticalAlignment(SwingConstants.CENTER);

            //Set Label's Color
            c = _c;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.setColor(c);
			g.fillRect(0 , 0 , 25 , 25);
		}
	}

	public class TextLabel2 extends TextLabel {
		Color c2;

		public TextLabel2(int x , int y , String s , Color _c , Color _c2) {
			super(x , y , s , _c);

			c2 = _c2;
		}

		// This Label has two color 
		// So we draw a Triangle and a Rectangle
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
            g.setColor(c);
            g.fillRect(0 , 0 , 25 , 25);

            g.setColor(c2);
            g.fillPolygon(new int[] {0 , 25 , 0} , new int[] {0 , 25 , 25} , 3);
		}
	}

	public class Button extends JButton {
		String Status;
		Color c;

		public Button(int x, int y , String s , Color _c) {
			setText(s);

			//Set up Status
			Status = s;

			//Set Button's Position and Size
			setBounds(x , y , 100 , 25);

			//We wnat to create a beautiful Button
			//So we disable some style
            setBorder(null);
            setContentAreaFilled(false);
            setFocusPainted(false);

            //Set Text's Position
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);

            //Set Button's Color
			c = _c; 
		}

		public void paintComponent(Graphics g) {
			g.setColor(c);
			g.fillRoundRect(0 , 0 , 100 , 25 , 20 , 20);

			//We want to draw Rectangle first , then draw Text
			//So call super here
			super.paintComponent(g);
		}

		//If Clickable -> Set Cursor to Hand
		public void set_Clickable() {
			setCursor(style.Hand);
			repaint();
		}

		// If unClickable -> Set Cursor to Default
		public void set_unClickable() {
			setCursor(style.Default);
			repaint();
		}

		//StartButton has 4 kinds of Status
		public void SetStatus(String _s) {
			setText(_s);
			Status = _s;

			switch(_s) {
				case "Run":
				case "Resume":
					c = style.Green;
					set_Clickable();
					break;
				case "Running":
					c = style.Red;
					set_unClickable();
					break;
				case "Done":
					c = style.Green;
					set_unClickable();
					ClearButton.set_Clickable();
					PauseButton.set_unClickable();
					break;
			}

			repaint();
		}
	}
}