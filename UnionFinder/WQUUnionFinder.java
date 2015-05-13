public class WQUUnionFinder extends UnionFinder
{
	private int[] size; //array to store the size of components
	
	public WQUUnionFinder(int nodes)
	{
		super(nodes);
		size = new int[nodes];
		for(int i = 0; i < nodes; i++) //
			size[i] = 1; //size of each component should start out as 1 
	}
	
	//JavaDoc defined in super class
	public void unite(int p, int q) 
	{
		int i = find(p);
		int j = find(q);
		if(i == j) //make sure they're not already in the same component
			return;
		
		if(size[i] < size[j]) //see which component is larger
		{
			nodeId[i] = j; //connect components
			size[j] += size[i]; //add the weight of component i onto j
		}
		else
		{
			nodeId[j] = i; //connect components
			size[i] += size[j]; //add the weight of component j onto i
		}
		componentCount--; //decrease number of existing components
	}
	
	//JavaDoc defined in super class
	public int find(int p) 
	{
		while(p != nodeId[p]) //iterate up the tree of nodes in the graph until the root is found
			p = nodeId[p]; //stop once a node points to itself
		return p;
	}

	/*
	* Main method to run WQUnionFinder.java
	*/
	public static void main(String[] args) 
	{
		//call UnionFinder's main method statically and specify the type of UnionFinder
    String filename = args[0];
		args = new String[]{"W", filename};
		UnionFinder.main(args);
	}
}