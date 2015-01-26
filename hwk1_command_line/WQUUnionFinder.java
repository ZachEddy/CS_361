public class WQUUnionFinder extends UnionFinder
{
	private int[] size;
	
	public WQUUnionFinder(int elems)
	{
		super(elems);
		size = new int[elems];
		for(int i = 0; i < elems; i++)
			size[i] = i;
	}
	
	public static void main(String[] args) 
	{
        String filename = args[0];
		args = new String[]{"W", filename};
		UnionFinder.main(args);
	}

	public void unite(int p, int q) 
	{
		int i = find(p);
		int j = find(q);
		if(i == j)
			return;
		
		if(size[i] < size[j])
		{
			id[i] = j;
			size[j] += size[i];
		}
		else
		{
			id[j] = i;
			size[i] += size[j];
		}
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