package GraphNode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Main.Constants;

public class GraphNode {

	private int row, col;
	private int x, y;
	private Color color;
	private ArrayList<GraphNode> neighbors;
	private final int width;
	private final int totalRows;
	
	
		public GraphNode(int row, int col, int width, int totalRows) {
			this.width = width;
			this.totalRows = totalRows;
			this.col = col;
			this.row = row;
			this.x = this.row*this.width;
			this.y = this.col*this.width;
			this.neighbors = new ArrayList<GraphNode>();
			this.color =  Constants.WHITE;
		}
		
		public int[] getPos() {
			int[] pos = {this.row, this.col};
			return pos;
		}
		public void setColor(String key) {
			this.color = (Color)Constants.Colors.get(key);
		}
		
		public void paint(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(this.color);
			g2d.fillRect(x, y, width, width);
		}
		
		@Override
		public String toString() {
			return "GraphNode [row=" + row + ", col=" + col + ", x=" + x + ", y=" + y + ", color=" + color
					+ ", neighbors=" + neighbors + ", width=" + width + ", totalRows=" + totalRows + "]";
		}

		public void updateNeighbors(GraphNode[][] grid) throws ArrayIndexOutOfBoundsException {
			this.neighbors.clear();
			if(this.row <  grid.length - 1 && !(grid[this.row+1][this.col].isBarrier())) {
				this.neighbors.add(grid[this.row+1][this.col]);
			}//Down Direction			
			if(this.row > 0 && !(grid[this.row-1][this.col].isBarrier())) {
				this.neighbors.add(grid[this.row-1][this.col]);
			}//Up Direction
			if(this.col < grid.length - 1 && !(grid[this.row][this.col+1].isBarrier())) {
				this.neighbors.add(grid[this.row][this.col+1]);	
			}//Right Direction
			if(this.col > 0 && !(grid[this.row][this.col-1].isBarrier())) {
				this.neighbors.add(grid[this.row][this.col-1]);
			}//Left Direction
			
			
		}
		
		public ArrayList<GraphNode> getNeighbors() { return this.neighbors; }
		
		public boolean isClosed() {
			return this.color == Constants.RED;
		}
		
		public boolean isOpen() {
			return this.color == Constants.GREEN;
		}
		
		public boolean isBarrier() {
			return this.color == Constants.BLACK;
		}
		
		public boolean isStart() {
			return this.color == Constants.ORANGE;
		}
		
		public boolean isEnd() {
			return this.color == Constants.TURQUOISE;
		}
		
		public boolean isEmpty() {
			return this.color == Constants.WHITE;
		}
}
