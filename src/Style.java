import java.awt.Color;
import java.awt.Cursor;

public class Style {
	Color Red;
	Color Blue;
	Color Gray;
	Color Green;
	Color DBlue;
	Color Black;
	Color Purple;

	Cursor Hand;
	Cursor Default;

	public Style() {
		Red    = new Color(255 , 0 , 102);
		Blue   = new Color(102, 194, 255);
		Gray   = new Color(71, 71, 107);
		Green  = new Color(0 , 255 , 153);
		DBlue  = new Color(51, 51, 255);
		Black  = new Color(0 , 0 , 0); 
		Purple = new Color(255, 128, 255);

		Hand    = new Cursor(Cursor.HAND_CURSOR);	
		Default = new Cursor(Cursor.DEFAULT_CURSOR);
	}
}