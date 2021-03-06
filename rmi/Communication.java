import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.io.*;

public class Communication extends UnicastRemoteObject implements CommInterface
{
	private MSTClient local_client;
	private ArrayList<BC_MSG> broadcast_ids;
	
	private class BC_MSG
	{
		public int id;
		public int num;
		
		public BC_MSG(int i, int n)
		{
			id = i;
			num = n;
		}
		
		public boolean Equals(int i, int n)
		{
			return (id == i && num == n);
		}
	}
	
	public Communication(MSTClient client) throws RemoteException
	{
		local_client = client;
		broadcast_ids = new ArrayList<BC_MSG>();
	}
    
    public boolean Wizz(int id) throws RemoteException
    {
		String print = "";
		String unknown = "?";
		
		String sender = local_client.WhoSentIt(id);
		if ( !sender.isEmpty() )
			print = sender.concat(print);
		else
			print = unknown.concat(print);
			
        local_client.app.mf.Print(print + " sent you a wizz !", "wizz");
        local_client.app.mf.Wizz();
        
		return true;
    }
	
	public boolean Message(String msg, int id) throws RemoteException
	{
		String print = "> ";
		String unknown = "?";
		print = print.concat(msg);
		
		String sender = local_client.WhoSentIt(id);
		if ( !sender.isEmpty() )
			print = sender.concat(print);
		else
			print = unknown.concat(print);
			
        local_client.app.mf.Print(print, "received_message");
		return true;
	}
	
	public boolean Broadcast(int id, int num, String msg) throws RemoteException
	{
		if ( !AlreadyGot(id, num) )
		{
			ReceivedBroadcast(id, num);
			Message(msg, id);
			local_client.Broadcast(id, num, msg);
			return true;
		}
		
		return false;
	}
	
	public boolean AlreadyGot(int id, int num)
	{
		for (int i=0; i<broadcast_ids.size(); i++)
			if (broadcast_ids.get(i).Equals(id, num))
				return true;
		
		return false;
	}
	
	public void ReceivedBroadcast(int id, int num) throws RemoteException
	{
		broadcast_ids.add(new BC_MSG(id, num));
	}
	
	public int Search(String name) throws RemoteException
	{
		Contact c = local_client.app.contacts.GetContact(name);
		if (c != null)
			return c.port;
		else
			return -1;
	}
	
	public void Write(String name, byte[] bFile) throws RemoteException
	{
		try {
			String decoupage[] = name.split("/");
			String relative_name = decoupage[decoupage.length-1];
			//~ String new_name = System.getProperty("user.dir")+"/"+relative_name;
			String new_name = "../downloads/"+relative_name;
			
			//convert array of bytes into file
			FileOutputStream fileOuputStream = 
					  new FileOutputStream(new_name); 
			fileOuputStream.write(bFile);
			fileOuputStream.close();
			
			local_client.app.mf.Print("Received file "+name, "info");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
}
