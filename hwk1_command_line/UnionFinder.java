import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public abstract class UnionFinder
{
	protected int elemCount;
	protected int[] id;
	
	public UnionFinder(int elems)
	{
		elemCount = elems;
		id = new int[elems];
		for(int i = 0; i < elems; i++)
			id[i] = i;   
	}
	
	public abstract void unite(int p, int q);
	
	public abstract int find(int p);
	
	public abstract boolean isConnected(int p, int q);
	
	public abstract int countComponents();
	
	public static void main(String[] args)
	{
		UnionFinder finder;
		String operation = args[0];
		String filename = args[1];
		
		try
		{
			Scanner scanner = new Scanner(new File(filename));
			if(operation.equals("F"))
			{
				finder = new QFUnionFinder(scanner.nextInt());
			}
			else if(operation.equals("U"))
			{
				finder = new QUUnionFinder(scanner.nextInt());
			}
			else if(operation.equals("W"))
			{
				finder = new WQUUnionFinder(scanner.nextInt());
			}
			else 
			{
				System.out.println("\nIncorrect letter input. 'F', 'U', and 'W' are all acceptable choices.\n");
				return;
			}
			
			System.out.println("\nSuccessfully created new UnionFinder \n");
			long time = System.currentTimeMillis();
			
			while(scanner.hasNextInt())
			{
				
				int p = scanner.nextInt();
				int q = scanner.nextInt();
				
				if(!finder.isConnected(p,q))
				{
					finder.unite(p,q);
					System.out.println(p + " " + q);
				}
			}
			
			time = System.currentTimeMillis() - time;
			System.out.println("\n"+ finder.elemCount + " components found in " + time + " milliseconds using " + finder.getClass().getSimpleName() + " with " + filename + "\n");
		}
		catch(FileNotFoundException fe)
		{
            if(filename.equals(""))
            {
                System.out.println("No filename entered");
            }
			System.out.println("\nFile '" + filename + "' not found\n");
		}
	}
	
}
