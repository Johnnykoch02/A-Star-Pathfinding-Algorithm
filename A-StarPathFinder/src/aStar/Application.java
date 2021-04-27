package aStar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import javax.swing.JPanel;
import javax.swing.Timer;

import GraphNode.GraphNode;
import Main.Constants;

public class Application extends JPanel  {

	
	private GraphNode[][] grid;
	private int gap;
	private boolean running, started;
	private Timer timer;
	private ApplicationListener appListener;
	

	public Application() {init();}
	
	public ApplicationListener getListener() {return this.appListener;}
	public void init() {
		
		appListener = new ApplicationListener();
		this.setVisible(true);
		this.setFocusTraversalKeysEnabled(true);
		this.addKeyListener(appListener);
		this.addMouseListener(appListener);
		this.addMouseMotionListener(appListener);
		this.setBackground(Color.black);
		this.setSize(Constants.WIDTH, Constants.WIDTH);
		running =  false;
		started = false;	
		grid = new GraphNode[Constants.ROWS][Constants.ROWS];
		gap = Constants.WIDTH/Constants.ROWS;
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j< grid.length; j++) {
				GraphNode node = new GraphNode(i,j,gap,Constants.ROWS);
				node.setColor("reset");
				grid[i][j] = node;
			}
		}
		
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j< grid.length; j++) {
			
					grid[i][j].updateNeighbors(grid);	
			
			}
		}
		
		grid[25][25].setColor("barrier");
		
		running = true;
		
	}
	
	public void run() {	
		
		GraphNode start = null;
		GraphNode end = null;
		
		while(running)
		{
			if(appListener.getQPress() && started)
			{
				
				for(int i = 0; i < grid.length; i++) {  //RESET ALL NEIHBORS
					for(int j = 0; j< grid.length; j++) {
					
							grid[i][j].setColor("reset");
					
					}
				start =null;
				end = null;
				started = false;
			}
			}
			
			if(appListener.getMousePress()[0] && started == false)
			{
			
				int[] pos = {appListener.getX()/gap, appListener.getY()/gap}; 
				if(pos[0]>49)
					pos[0]=49;
				if(pos[1]> 49)
					pos[1]=49;
				if(pos[0]<0)
					pos[0]=0;
				if(pos[1]<0)
					pos[1]=0;
				
				
				if(start == null && end == null)
				{
					grid[pos[0]][pos[1]].setColor("start");
					start = grid[pos[0]][pos[1]];
				}
				else if(start == null && end != null)
				{
					grid[pos[0]][pos[1]].setColor("start");
					start = grid[pos[0]][pos[1]];
				}
				
				else if(start != null && end == null && !grid[pos[0]][pos[1]].isStart())
				{
					grid[pos[0]][pos[1]].setColor("end");
					end = grid[pos[0]][pos[1]];
				}
				else if(grid[pos[0]][pos[1]].isEmpty())
				{
					grid[pos[0]][pos[1]].setColor("barrier");
				}
			}
			
			
			if(appListener.getMousePress()[1] && started == false)
			{
				
				int[] pos = {appListener.getX()/gap, appListener.getY()/gap}; 
					if(grid[pos[0]][pos[1]].equals(start))
					{
						start = null;
						grid[pos[0]][pos[1]].setColor("reset");
					}
					else if(grid[pos[0]][pos[1]].equals(end))
					{
						end = null;
						grid[pos[0]][pos[1]].setColor("reset");
					}
					else grid[pos[0]][pos[1]].setColor("reset");
					
			}
			
			
			if(appListener.getSpacePress() && start!= null && end != null && !started)
			{
				started = true;
				boolean finished = aStar(start, end);
			
		}
			
			
			repaint();
		}
	}
	
	private boolean aStar(GraphNode start, GraphNode end) {
		
		for(int i = 0; i < grid.length; i++) {  //RESET ALL NEIHBORS
			for(int j = 0; j< grid.length; j++) {
			
					grid[i][j].updateNeighbors(grid);
			
			}
		}
		
		int count = 0;
		PriorityQueue<Triplet> openSet = new PriorityQueue<Triplet>();
		openSet.add(new Triplet(0, count,  start));
		HashMap<GraphNode, Integer> gScore = new HashMap<>();
		HashMap<GraphNode, GraphNode> cameFrom = new HashMap<GraphNode, GraphNode>();
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j< grid.length; j++) {
			gScore.put(grid[i][j], 100000);
			}
		}
		gScore.replace(start, 0);
		HashMap<GraphNode, Integer> fScore = new HashMap<>();
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j< grid.length; j++) {
			fScore.put(grid[i][j], 100000);
			}
		}
		fScore.replace(start, Constants.hValue( start.getPos(), end.getPos() ));
		
		HashSet<GraphNode> openHash = new  HashSet<>();
		openHash.add(start);
		
		while(!openSet.isEmpty())
		{
			GraphNode current = openSet.poll().getElem3(); 
			openHash.remove(current);
			if(current == end)
			{
				makePath(cameFrom, current);
				end.setColor("end");
				start.setColor("start");
				repaint();
				return true;
			}
			
			for(GraphNode neighbor : current.getNeighbors())
			{
				Integer tempGScore = gScore.get(current)+1;
				System.out.println(gScore.get(current));
				
				if(tempGScore< gScore.get(neighbor))
				{
					cameFrom.put(neighbor, current);
					gScore.replace(neighbor, tempGScore);
					fScore.replace(neighbor, tempGScore + Constants.hValue(neighbor.getPos(), end.getPos()));
					if(!openHash.contains(neighbor))
					{
						count++;
						openSet.add(new Triplet(fScore.get(neighbor), count, neighbor));
						openHash.add(neighbor);
						neighbor.setColor("open");
						System.out.println(fScore.get(neighbor).intValue());
					}
				}
				
			}
			if(current!=start)
			{
				current.setColor("closed");
			}
			repaint();  
			System.out.println("Round "+count);
		}
		
		return false;
	}
	
	private  void makePath(HashMap<GraphNode, GraphNode> cameFrom, GraphNode current) {
		
		while(cameFrom.containsKey(current))
		{
			current = (GraphNode) cameFrom.get(current);
			current.setColor("path");
			repaint();
		}		
	}
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Create Background
		g.setColor(Color.black);
		g.fillRect(0, 0, Constants.WIDTH, Constants.WIDTH);
		
		// Paint the Nodes
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j< grid[i].length; j++) {
						grid[i][j].paint(g);			
			}
		}
		g.setColor(Color.black);

		for(int i = 0; i< Constants.ROWS; i++) {
			g.drawLine(0, i*gap, Constants.WIDTH, i*gap);
		}
		for(int i = 0; i< Constants.ROWS; i++) {
			g.drawLine(i*gap, 0, i*gap, Constants.WIDTH);
		}
		g.dispose();
	}

	}

class ApplicationListener implements KeyListener, MouseMotionListener, MouseListener  {
	
	private int x, y;
	private boolean mouseLeftPressed = false;
	private boolean mouseRightPressed = false;
	private boolean spacePressed = false;
	private boolean qPressed = false;
	
	public ApplicationListener() {}
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}
	
	protected boolean getQPress() { return qPressed;} 

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println();
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			spacePressed = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Q)
		{
			qPressed = true;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println();
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			spacePressed = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Q)
		{
			qPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			spacePressed = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Q)
		{
			qPressed = false;
		}
	}
	
	protected boolean[] getMousePress() {
		boolean[] presses = {mouseLeftPressed, mouseRightPressed};
		return presses;
		}
	
	protected boolean getSpacePress() { return spacePressed;}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		x =e.getX();
		y = e.getY();
		if(e.getButton()== MouseEvent.BUTTON1)
		mouseLeftPressed = true;
		if(e.getButton()== MouseEvent.BUTTON3)
			mouseRightPressed = true;
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		x =e.getX();
		y = e.getY();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		x =e.getX();
		y = e.getY();
		if(e.getButton()== MouseEvent.BUTTON1)
		mouseLeftPressed = true;
		if(e.getButton()== MouseEvent.BUTTON3)
			mouseRightPressed = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()== MouseEvent.BUTTON1)
			mouseLeftPressed = false;
		if(e.getButton()== MouseEvent.BUTTON3)
			mouseRightPressed = false;
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
