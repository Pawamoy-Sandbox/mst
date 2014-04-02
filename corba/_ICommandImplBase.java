
/**
* _ICommandImplBase.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ICommand.idl
* Wednesday, April 2, 2014 5:04:48 PM CEST
*/

public abstract class _ICommandImplBase extends org.omg.CORBA.portable.ObjectImpl
                implements ICommand, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors
  public _ICommandImplBase ()
  {
  }

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("SendCommand", new java.lang.Integer (0));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // ICommand/SendCommand
       {
         String cmd = in.read_string ();
         String args = in.read_string ();
         boolean $result = false;
         $result = this.SendCommand (cmd, args);
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ICommand:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _ICommandImplBase
