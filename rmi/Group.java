import java.util.ArrayList;

public class Group
{
	public ArrayList<Group> groups;
	public ArrayList<Contact> contacts;
	
	public Group()
	{
		contacts = new ArrayList<Contact>();
		groups = new ArrayList<Group>();
	}
	
	public void Add(Contact c)
	{
		contacts.add(c);
	}
	
	public void Add(Group g)
	{
		groups.add(g);
	}
	
	public void Del(Contact c)
	{
		contacts.remove(c);
	}
	
	public void Del(Group g)
	{
		groups.remove(g);
	}
	
	public Group GetGroup(int index)
	{
		return groups.get(index);
	}
	
	/*public Group GetGroup(Group group)
	{
		int index = groups.indexOf(group);
		
		if (index != -1)
			groups.get(index);
		else
			return null;
	}
	*/
	
	// index valable entre 0 et TotalSize() (parcours "récursif" en profondeur)
	public Contact GetContact(int index)
	{
		int size = contacts.size();
		Contact c = null;
		
		if (index < size)
		{
			c = contacts.get(index);
		}
		else if (index < TotalSize())
		{
			index -= size;
			for (int i=0; i<GroupSize(); i++)
			{
				c = GetGroup(i).GetContact(index);
				if (c != null)
					break;
				else
					index -= GetGroup(i).TotalSize();
			}
		}
		
		return c;
	}
	
	// recherche "récursive" d'un contact via un nom ou une adresse
	public Contact GetContact(String s)
	{
		Contact c;
		
		for (int i=0; i<ContactSize(); i++)
		{
			c = GetContact(i);
			
			if (c.HasName(s) || c.HasAddress(s))
				return c;
		}
		
		for (int i=0; i<GroupSize(); i++)
		{
			c = GetGroup(i).GetContact(s);
			if (c != null)
				return c;
		}
		
		return null;
	}
	
	/*public Contact GetContact(Contact contact)
	{
		int index = contacts.indexOf(contact);
		
		if (index != -1)
			contacts.get(index);
		else
			return null;
	}
	*/
	
	public boolean Contains(Contact c)
	{
		return contacts.contains(c);
	}
	
	public boolean Contains(Group g)
	{
		return groups.contains(g);
	}
	
	public int GroupSize()
	{
		return groups.size();
	}
	
	public int ContactSize()
	{
		return contacts.size();
	}
	
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
}