import java.util.ArrayList;

public class Group extends RootGroup
{
	public String name;
	
	public Group(String name)
	{
		this.name = name;
		contacts = new ArrayList<Contact>();
		groups = new ArrayList<Group>();
	}
	
	// redéfinition
	public static RootGroup getInstance()
	{
		return instance;
	}
	
	// redéfinition
	public void Del(Contact c)
	{
		contacts.remove(c);
	}
	
	// redéfinition
	public void Del(Group g)
	{
		groups.remove(g);
	}
	
	// redéfinition récursive
	//~ public boolean Contains(Contact c)
	//~ {
		//~ return contacts.contains(c);
	//~ }
	
	// redéfinition récursive
	//~ public boolean Contains(Group g)
	//~ {
		//~ return groups.contains(g);
	//~ }
	
	/* méthodes ajoutées */

	// calcul "récursif" du nombre total de contact dans le groupe et ses sous-groupes
	public int TotalSize()
	{
		int total = ContactSize();
		
		for (int i=0; i<GroupSize(); i++)
		{
			total += GetGroup(i).TotalSize();
		}
		
		return total;
	}
	
	public boolean HasName(String n)
	{
		return (name.compareToIgnoreCase(n) == 0);
	}
	
	public ArrayList<Contact> GetAllContacts()
	{
		ArrayList<Contact> result = new ArrayList<Contact>();
		RecGetAll(result);
		return result;
	}
	
	private void RecGetAll(ArrayList<Contact> array)
	{
		for (int i=0; i<ContactSize(); i++)
			if ( !array.contains(GetContact(i)) )
				array.add(GetContact(i));
		
		for (int i=0; i<GroupSize(); i++)
			GetGroup(i).RecGetAll(array);
	}
}
