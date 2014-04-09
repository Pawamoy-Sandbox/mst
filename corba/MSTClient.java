import org.omg.CosNaming.*;
import org.omg.CORBA.*;

public class MSTClient
{
    public static void main (String[] args)
    {
        try
        {
            ORB orb = ORB.init(args, null);
            
            org.omg.CORBA.Object obj = orb.string_to_object(args[0]);
            ICommand service = ICommandHelper.narrow(obj);

            service.SendCommand("msg", "Jerome fumier");
            service.SendCommand("msg2", "Bande de batards");
        }
        catch (org.omg.CORBA.SystemException se)
        {
            System.err.println(se);
        }
    }
}
