package mstgui;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;

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
	
	public boolean Message(String msg) throws RemoteException
	{
		System.out.println(msg);
		return true;
	}
	
	public boolean Broadcast(int id, int num, String msg) throws RemoteException
	{
		if ( !AlreadyGot(id, num) )
		{
			broadcast_ids.add(new BC_MSG(id, num));
			Message(msg);
			
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
}
