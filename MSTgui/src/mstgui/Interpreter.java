package mstgui;

public abstract class Interpreter
{
	public static Command StringToCommand(String s)
	{
		Command result = null;
		CommandType res_type;
		String field[];
		String args = "";
		// si la commande commence par ':'
		if (s.startsWith(":"))
		{
			// analyser le premier mot (séparateur = espace, le mot peut être collé aux ':')
			if (s.substring(1).startsWith(" "))
				field = s.substring(2).split(" ");
			else
				field = s.substring(1).split(" ");
				
			if (field.length == 0) return null;
			
			// comparer aux mots-clés de l'appli
			res_type = SwitchType(field[0]);
			switch (res_type) {
			case SEARCH:
				// arg1 = nom à chercher
				// arg2...N = message si contact trouvé
				if (field.length > 1)
				{
					for (int i=2; i<field.length; i++)
						args = args.concat(field[i]+" ");
						
					result = new Command(CommandType.SEARCH, field[1], args);
				}
				else
				{
					System.err.println("Error: interpreter: command search needs a contact name");
				}
				break;
				
			case EXIT:
				result = new Command(CommandType.EXIT);
				break;
				
			case BROADCAST:
				// arg1...N = message à diffuser
				if (field.length > 1)
				{
					for (int i=1; i<field.length; i++)
						args = args.concat(field[i]+" ");
						
					result = new Command(CommandType.BROADCAST, "", args);
				}
				else
				{
					System.err.println("Error: interpreter: command broadcast needs text to be send");
				}
				break;
				
			case HELP:
				if (field.length > 1)
					result = new Command(CommandType.HELP, field[1]);
				else
					result = new Command(CommandType.HELP);
				break;
				
			case REFRESH:
				result = new Command(CommandType.REFRESH);
				break;
				
			case WIZZ:
				// arg1 = nom du contact
				if (field.length > 1)			
					result = new Command(CommandType.WIZZ, field[1]);
				else
					result = new Command(CommandType.WIZZ);
				break;
				
			case LIST:
				// arg1 = nom du contact
				if (field.length > 1)			
					result = new Command(CommandType.LIST, field[1]);
				else
					result = new Command(CommandType.LIST);
				break;
				
			case ADD:
				// arg1 = nom du contact / groupe
				// arg2 = adresse [port]
				// arg2,..,N = liste de contacts / groupes
				if (field.length > 2)
				{
					for (int i=2; i<field.length; i++)
						args = args.concat(field[i]+" ");
						
					result = new Command(CommandType.ADD, field[1], args);
				}
				else if (field.length > 1)
				{
					result = new Command(CommandType.ADD, field[1]);
				}
				else
				{
					System.err.println("Error: interpreter: command add need at least a name");
				}
				break;
				
			case DELETE:
				// arg1 = nom du contact / groupe
				// arg2 = adresse [port]
				if (field.length > 1)
					result = new Command(CommandType.DELETE, field[1]);
				else
					System.err.println("Error: interpreter: command delete need at least a name");
				break;
				
			case MODIFY:
				// A COMPLETER
				break;
			
			case MESSAGE:
				if ( !AddressBook.IllegalCharacter(field[0]) )
				{
					if (field.length > 1)
					{
						for (int i=1; i<field.length; i++)
							args = args.concat(field[i]+" ");
							
						result = new Command(CommandType.MESSAGE, field[0], args);
					}
					else
					{
						if ( !field[0].isEmpty() )
							System.err.println("Error: interpreter: message function needs text to be send");						
					}
				}
				else
				{
					System.err.println("Error: interpreter: wrong command, please type :help");
				}
				break;
			
			default:
				break;
			}
		}
		else
		{
			result = new Command("", s);
		}
			
		return result;
	}
	
	public static String CommandToString(Command c)
	{
		String result = "";
		
		switch(c.type)
		{
			case MESSAGE:
				if ( !c.target.isEmpty() )
					result = result.concat(":"+c.target+" ");
				result = result.concat(c.args);
				break;
				
			case EXIT:
				result = ":exit";
				break;
				
			case BROADCAST:
				result = result.concat(":broadcast "+c.args);
				break;
				
			case SEARCH:
				result = result.concat(":search "+c.target);
				if ( !c.args.isEmpty() )
					result = result.concat(" "+c.args);
				break;
				
			case WIZZ:
				result = ":wizz";
				if ( !c.target.isEmpty() )
					result = result.concat(" "+c.target);
				break;
				
			case REFRESH:
				result = ":refresh";
				break;
				
			case HELP:
				result = ":help";
				if ( !c.target.isEmpty() )
					result = result.concat(" "+c.target);
				break;
				
			// ajouter les add, delete, modify, list
				
			default:
				break;
		}
		
		return result;
	}
		
	public static CommandType SwitchType(String c)
	{
		if (c.compareTo("search") == 0 ||
			c.compareTo("seek") == 0 ||
			c.compareTo("who") == 0)
			return CommandType.SEARCH;
		else if (c.compareTo("bye") == 0 ||
			c.compareTo("exit") == 0 ||
			c.compareTo("end") == 0 ||
			c.compareTo("quit") == 0 ||
			c.compareTo("q") == 0 ||
			c.compareTo("stop") == 0)
			return CommandType.EXIT;
		else if (c.compareTo("broadcast") == 0 ||
			c.compareTo("bc") == 0)
			return CommandType.BROADCAST;
		else if (c.compareTo("help") == 0)
			return CommandType.HELP;
		else if (c.compareTo("refresh") == 0)
			return CommandType.REFRESH;
		else if (c.compareTo("wizz") == 0)
			return CommandType.WIZZ;
		else if (c.compareTo("list") == 0)
			return CommandType.LIST;
		else if (c.compareTo("add") == 0)
			return CommandType.ADD;
		else if (c.compareTo("delete") == 0 ||
			c.compareTo("del") == 0)
			return CommandType.DELETE;
		else if (c.compareTo("modify") == 0 ||
			c.compareTo("mod") == 0)
			return CommandType.MODIFY;
		else
			return CommandType.MESSAGE;
	}
}
	

/*
 * TODO: autoriser les combinaison de commande:
 * :bc      [:wizz]    = :bc :wizz
 */
 
/*
 * TODO: permettre de bloquer des identifiants / adresses
 */
 
/*
 * TODO: ajouter une commande pour l'envoi de fichier (style :file FICHIER)
 */
