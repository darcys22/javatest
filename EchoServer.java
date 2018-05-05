// $Id$
//

import java.io.*;
import java.net.*;
import mylib.*;

public class EchoServer extends Server
{
  public EchoServer( int port ) {
    // The superclass knows what to do with the port number
    // we dont have to care about it
    super( port );
  }

  // This is called by the server class when a connection
  // comes in. in and out come from the incoming socket connection
  public void handleConnection( Socket socket ) {
    try {
      InputStream in = socket.getInputStream();
      OutputStream out = socket.getOutputStream();

      // just copy the input to the output
      while (true)
        out.write( in.read() );

    } catch( IOException ie ) {
      System.out.println( ie );
    }
  }

  protected void cleanUp() {
    System.out.println("Cleaning up");
  }

  static public void main( String args[] ) throws Exception {
    // Grab the port number from the command-line
    int port = Integer.parseInt( args[0] );

    // Have debugging info sent to standard error stream
    Server.setDebugStream( System.err );

    //Create the server, and its up and runnign
    new EchoServer( port );

  }
}
