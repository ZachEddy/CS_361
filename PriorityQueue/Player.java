import java.util.*;
public class Player implements Comparable<Player>
{
	private int skill;
	private String name;

	public Player(int s, String n)
	{
		skill = s;
		name = n;
	}

	public int compareTo(Player other)
	{
		int otherSkill = other.getSkill();
		if(skill < otherSkill) return -1;
		if(skill == otherSkill) return 0;
		return 1;
	}

	public static void main(String[] args)
	{
		Player test1 = new Player(2, "j");
		Player test2 = new Player(2, "k");
		System.out.println(test1.compareTo(test2));
	}

	public int getSkill(){ return skill; }

	public String getName(){ return name; }
}