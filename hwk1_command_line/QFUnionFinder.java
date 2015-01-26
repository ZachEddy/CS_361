public class QFUnionFinder extends UnionFinder
{
	public QFUnionFinder(int elems)
	{
		super(elems);
	}
	
	public static void main(String[] args) 
	{
		String filename = args[0];
		args = new String[]{"F", filename};
		UnionFinder.main(args);
	}

	public void unite(int p, int q) 
	{
		int pId = find(p);
		int qId = find(q);
		
		if(pId == qId)
			return;
		
		for(int i = 0; i < id.length; i++)
		{
			if(id[i] == pId)
			{
				id[i] = qId;
			}
		}
		elemCount--;
	}

	public int find(int p) 
	{
		return id[p];
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