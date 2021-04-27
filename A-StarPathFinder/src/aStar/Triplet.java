package aStar;

import java.util.Comparator;

import GraphNode.GraphNode;

public class Triplet implements Comparable<Triplet>{
	
	private Integer elem1;
	private Integer elem2;
	private GraphNode elem3;
	
	public Triplet(Integer elem1, Integer elem2, GraphNode elem3) {
		
		this.elem1 = elem1;
		this.elem2 = elem2;
		this.elem3 = elem3;
		
	}
	
	public Integer getElem1() {
		return elem1;
	}


	public Integer getElem2() {
		return elem2;
	}


	public GraphNode getElem3() {
		return elem3;
	}

	@Override
	public int compareTo(Triplet o) {
		int comp = elem1.compareTo(o.elem1);
		if(comp>0)
			return 1;
		else if(comp<0)
			return -1;
		if(comp == 0)
		{
			int comp2 = elem2.compareTo(o.elem2);
			if(comp2>0)
				return 1;
			else if(comp2<0)
				return -1;
		}
		
		return 0;
	}

	@Override
	public String toString() {
		return elem1+" "+elem2;
	}


	

}
