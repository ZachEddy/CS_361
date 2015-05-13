public class QUUnionFinder extends UnionFinder
{
	public QUUnionFinder(int nodes)
	{
		super(nodes);
	}

	//JavaDoc defined in super class
	public void unite(int p, int q) 
	{
		int i = find(p);
		int j = find(q);
		if(i == j) //make sure they're not already in the same component
			return;
		
		nodeId[i] = j; //create a parent-child connection in the tree
		componentCount--; //decrease number of existing components
	}
	
	//JavaDoc defined in super class
	public int find(int p) 
	{
		while(p != nodeId[p]) //iterate up the tree of nodes (component) in the graph until the root is found
			p = nodeId[p]; //stop once a node points to itself
		return p; 
	}

	/*
	* Main method to run QUUnionFinder.java
	*/
	public static void main(String[] args) 
	{
		//call UnionFinder's main method statically and specify the type of UnionFinder
		String filename = args[0];
		args = new String[]{"U", filename};
		UnionFinder.main(args);
	}	
}