package Main;

import java.awt.Color;
import java.util.Dictionary;
import java.util.Hashtable;

public class Constants {

	/*
	 * BELOW IS WHERE ALL CONSTANTS TO THIS PROGRAM ARE HELD. FEEL FREE TI CHANGE ANY OF THE BELOW VALUES, AS IT WILL NOT AFFECT 
	 *				 THE RUNNABILITY OF THE PROGRAM
	 * */
	

	
	
	
	public final static int WIDTH = 800;
	public final static int ROWS = 50;
	public final static Color RED = new Color(255,0,0);
	public final static Color GREEN = new Color(0,255,128);
	public final static Color BLUE = new Color(0,255,0);
	public final static Color YELLOW = new Color(255,0,0);
	public final static Color WHITE = new Color(255,255,255);
	public final static Color BLACK = new Color(0,0,0);
	public final static Color PURPLE = new Color(128,0,128);
	public final static Color ORANGE = new Color(255, 170, 4);
	public final static Color TURQUOISE = new Color(64,224,208);
	public static Hashtable<String, Color> Colors = new Hashtable<String, Color>();  
	
	
	public static Integer hValue(int[] one, int[] two) {		
		int x1 = one[0]; int x2 = two[1];
		int y1 = two[0]; int y2 = two[1];		
		return (Math.abs(x2-x1) + Math.abs(y2-y1));		
	}
	
}
