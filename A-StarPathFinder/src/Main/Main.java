package Main;

import java.awt.Color;

import javax.swing.JFrame;

import aStar.Application;

public class Main {
	
	public static void main(String[] args) {
		
		Constants.Colors.put("start", Constants.ORANGE);
		Constants.Colors.put("end", Constants.TURQUOISE);
		Constants.Colors.put("open", Constants.GREEN);
		Constants.Colors.put("barrier", Constants.BLACK);
		Constants.Colors.put("reset", Constants.WHITE);
		Constants.Colors.put("path", Constants.PURPLE);
		Constants.Colors.put("closed", Constants.RED);
				
		JFrame frame = new JFrame("A-Star PathFinder by John");
		frame.setSize(Constants.WIDTH, Constants.WIDTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBackground(Color.black);
		frame.setVisible(true);
		Application app = new Application();
		frame.add(app);
		frame.addKeyListener(app.getListener());
		frame.setContentPane(app);
		app.run();
		
	}

}
