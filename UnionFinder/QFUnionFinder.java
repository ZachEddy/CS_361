public class QFUnionFinder extends UnionFinder
{
	public QFUnionFinder(int nodes)
	{
		super(nodes);
	}

	//JavaDoc defined in super class
	public void unite(int p, int q) 
	{
		int pId = find(p);
		int qId = find(q);
		
		if(pId == qId) //make sure they're not already in the same component
			return;
		
		for(int i = 0; i < nodeId.length; i++) //every node currently connected to p must now have q's identifier
		{
			if(nodeId[i] == pId)
			{
				nodeId[i] = qId;
			}
		}
		componentCount--; //decrease number of existing components
	}

	//JavaDoc defined in super class
	public int find(int p) 
	{
		return nodeId[p];
	}

	/*
	* Main method to run QFUnionFinder.java
	*/
	public static void main(String[] args) 
	{
		//call UnionFinder's main method statically and specify the type of UnionFinder
		String filename = args[0];
		args = new String[]{"F", filename};
		UnionFinder.main(args);
	}
}