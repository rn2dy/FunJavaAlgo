import java.io.*;
import java.util.*;


public class ShortestPath {
	//total number of nodes and links
	private int nodes;
	private int links;
	//predecessor
	private int[] path;
	//the distance from the source s to vertex u
	private float[] d;
	//G(V,E)
	//using this matrix is not that bad if the graph is very dense
	private float[][] gMatrix;
	private LinkedList[] adjList;
	private String str;
	private int source;
	private int dest;
	
	
	//empty constructor reserved
	public ShortestPath(){
		//initialize
	}
	public void initialize1() throws IOException{
		//initialize the gMatrix
		Scanner input = new Scanner(new File("P4_ShortestPath.txt"));
		str = input.nextLine();
		nodes = Integer.parseInt(str.substring(6,7));
		links = Integer.parseInt(str.substring(15));
		//initialize the gMatrix
		gMatrix = new float[nodes][nodes];
		for (int i = 0; i < nodes; i++){
			for(int j = 0 ; j < nodes ; j++){
				//gMatrix[i][j] = Float.MAX_VALUE;
				gMatrix[i][j] = 100;
			}
		}
		//load the graph matrix
		for(int i = 1; i <= links; i++){
			str = input.nextLine();
			String[] s = str.split(",");
			int first = Integer.parseInt(s[0])-1;
			int second = Integer.parseInt(s[1])-1;
			float w = Float.parseFloat(s[2]);
			gMatrix[first][second] = w;
		}

		//initialize d[]
		d = new float[nodes];
		for (int i = 0; i < nodes ; i++){
			d[i] = 100;
		}
		d[source] = 0;
		//initialize path[]
		path = new int[nodes];
		for (int i = 0; i < nodes ; i++){
			path[i] = -1;
		}
	}
	
	public boolean search1() throws IOException{
		for(int i = 1; i <= nodes - 1; i++){	
			for(int j = 0 ; j < nodes ; j++){
				for(int z = 0; z < nodes ; z++){
					if (gMatrix[j][z] != 100 && 
							d[j] != 100){
						relax1(j, z, gMatrix[j][z]);
					}
				}
			}
		}
		for(int j = 0 ; j < nodes ; j++){
			for(int z = 0; z < nodes ; z++){
				if (gMatrix[j][z] != 100 ){
					if (d[z] > d[j] + gMatrix[j][z]){
						return false;
					}
				}
			}
		}
		return true;
	}
	private void relax1(int u, int v, float w){
		if (d[v] > d[u] + gMatrix[u][v]){
			System.out.println("Node " + v + " is relaxed from node " + u);
			d[v] = d[u] + gMatrix[u][v];
			System.out.println("Node " + v + " new value " + d[v]);
			path[v] = u;
			System.out.println("Node " + v + " predecessor is " + u);
			System.out.println();
		}
	}
	
//------------------------------Dijkstra------------------------------
	
	public void initialize2() throws IOException{
		//initialize the gMatrix
		//initialize the gMatrix
		/*
		gMatrix = new float[nodes][nodes];
		for (int i = 0; i < nodes; i++){
			for(int j = 0 ; j < nodes ; j++){
				//gMatrix[i][j] = Float.MAX_VALUE;
				gMatrix[i][j] = 100;
			}
		}
		//load the graph matrix
		for(int i = 1; i <= links; i++){
			str = input.nextLine();
			String[] s = str.split(",");
			int first = Integer.parseInt(s[0])-1;
			int second = Integer.parseInt(s[1])-1;
			float w = Float.parseFloat(s[2]);
			gMatrix[first][second] = w;
		}
		*/
		Scanner input = new Scanner(new File("P4_ShortestPath.txt"));
		str = input.nextLine();
		nodes = Integer.parseInt(str.substring(6,7));
		links = Integer.parseInt(str.substring(15));
		adjList  = new LinkedList[nodes];
		gMatrix = new float[nodes][nodes];
		//just for drawing use-------------------------------------
		for (int i = 0; i < nodes; i++){
			for(int j = 0 ; j < nodes ; j++){
				//gMatrix[i][j] = Float.MAX_VALUE;
				gMatrix[i][j] = 100;
			}
		}
		//-------------------------------------
		int k = 100;
		for (int i = 0 ; i < links ; i ++){
			str = input.nextLine();
			String[] s = str.split(",");
			int first = Integer.parseInt(s[0])-1;
			int second = Integer.parseInt(s[1])-1;
			float w = Float.parseFloat(s[2]);
			//for drawing--------------------------------
			gMatrix[first][second] = w;
			//-------------------------------------------
			AdjNode adjNode = new AdjNode(second, w);
			//if first occurs the first time create a adjList
			//this requires input data is sorted
			if (k != first){
				LinkedList<AdjNode> list = new LinkedList<AdjNode>();
				adjList[first] = list;
				list.add(adjNode);
				k = first;
			}
			else{
				adjList[first].add(adjNode);
			}
		}
		
		//initialize d[]
		d = new float[nodes];
		for (int i = 0; i < nodes ; i++){
			d[i] = 100;
		}
		d[source] = 0;
		
		//initialize path
		path = new int[nodes];
		for (int i = 0; i < nodes ; i++){
			path[i] = -1;
		}
	}
	public boolean search2() throws IOException{
		PriorityQueue<Vertex> vQueue = new PriorityQueue<Vertex>();
		for (int i = 0; i < nodes ; i++){
			Vertex v = new Vertex(i, d[i]);
			vQueue.add(v);
		}
		//System.out.println(vQueue);
		while(!vQueue.isEmpty()){
			//System.out.println(vQueue);
			Vertex x = vQueue.remove();
			//for each vertex x's neighbor
			for (int i = 0; i < adjList[x.id].size(); i++){
				AdjNode v = (AdjNode) adjList[x.id].get(i);
				relax2(x.id, v.vertex, v.weight, i);
			}
			//update the queue
			PriorityQueue<Vertex> temp = new PriorityQueue<Vertex>();
			while(!vQueue.isEmpty()){
				Vertex v = vQueue.poll();
				v.setDist(d[v.id]);
				temp.add(v);
			}
			vQueue = temp;
		}
		return true;
	}
	private void relax2(int u, int v, float w, int i){
		//i indicates the position of the neighbor
		float weight = ((AdjNode) adjList[u].get(i)).weight;
		if (d[v] > d[u] + weight){
			System.out.println("Node " + v + " is relaxed from node " + u);
			d[v] = d[u] + weight;
			System.out.println("Node " + v + " new value " + d[v]);
			path[v] = u;
			System.out.println("Node " + v + " predecessor is " + u);
			System.out.println();
		}
	}
	public void setSource(int s){
		source = s;
	}
	public void setDest(int d){
		dest = d;
	}
	public int getSource(){
		return source;
	}
	public int getDest(){
		return dest;
	}
	public float[][] getMatrix(){
		return gMatrix;
	}
	public Stack getPath(){
		Stack stk = new Stack();
		int k = dest;
		stk.push(k+1);
		System.out.println("<" + k);
		while(k != -1){
			int x = path[k];
			if (x == source){
				stk.push(source+1);
				break;
			}
			else{
				stk.push(x+1);
				k = x;
			}
		}
		return stk;
	}
//---------------------inner class for Dijkstra
	class Vertex implements Comparable{
		int id;
		float distance;
		public Vertex(int i, float d){
			id = i;
			distance = d;
		}
		public void setID(int i){
			id = i;
		}
		public void setDist(float d){
			distance = d;
		}
		public int getID(){
			return id;
		}
		public float getDist(){
			return distance;
		}
		public int compareTo(Object x) {
			Vertex  y = (Vertex)x;
			if (this.distance > y.distance)
				return 1;
			else if (this.distance < y.distance)
				return -1;
			else
				return 0;
		}
		public String toString(){
			return id + ":" + distance;
		}
	}
	/*
	 * the adjacent node stores the vertex and the weight
	 * between the source and the vertex.
	 */
	class AdjNode{
		int vertex;
		float weight;
		public AdjNode(int v, float w){
			vertex = v;
			weight = w;
		}
		public void setVertex(int v){
			vertex = v;
		}
		public void setAdjNode(float w){
			weight = w;
		}
		public int getVertex(){
			return vertex;
		}
		public float getWeight(){
			return weight;
		}
	}
	
	/*
	public static void main(String[] args) throws IOException{
		ShortestPath sp = new ShortestPath();
		sp.setSource(0);
		//sp.setDest();
		sp.initialize2();
		sp.search2();
		sp.initialize2();
		sp.search2();
	}
	*/
	
}
