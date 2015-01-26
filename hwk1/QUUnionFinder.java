public class QUUnionFinder extends UnionFinder
{
	public QUUnionFinder(int elems)
	{
		super(elems);
	}
	
	public static void main(String[] args) 
	{
		String filename = "src/mediumUF.txt"; //CHANGE TO ARGS BEFORE SUBMITTING
		args = new String[]{"U", filename};
		UnionFinder.main(args);
	}

	public void unite(int p, int q) 
	{
		int i = find(p);
		int j = find(q);
		if(i == j)
			return;
		
		id[i] = j;
		elemCount--;
	}
	
	public int find(int p) 
	{
		while(p != id[p])
			p = id[p];
		return p;
	}

	public boolean isConnected(int p, int q) 
	{
		return find(p) == find(q);
	}
	
	public int countComponents()
	{
		return elemCount;
	}
}