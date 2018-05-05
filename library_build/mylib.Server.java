// $Id$
//
package mylib;

import java.io.*;
import java.net.*;

abstract public class Server implements Runnable
{
  // The port we will be listening on
  private int port;

  // How many connections weve handled
  int numConnections;

  // The reporter thats reporting on this server
  private Reporter reporter;

  //Set this to true to tell the thread to stop accepting connecitons
  private boolean mustQuit = false;

  public Server(int port) {
    //Remember the port number so the thread can listen on it
    this.port = port;

    //the construction starts a background thread
    new Thread(this).start();

    //and start a reporter
    reporter = new Reporter(this);

  }

  // this is our background thread
  public void run() {
    ServerSocket ss = null;
    try {
      // get read to listen
      ss = new ServerSocket( port );

      while ( !mustQuit ) {
        //give out some debugging info
        debug("Listening on "+port );

        //Wait for an incommming conneciton
        Socket s = ss.accept();

        //record that we got another connection
        numConnections++;

        //more debuggin info
        debug("Got connection on "+s);

        // process the connection -- this is implemented by the subcalss
        handleConnection( s );
      }
    } catch( IOException ie ) {
      debug( ie.toString() );
    }

    debug( "Shutting down "+ss );

    cleanUp();
  }

  // the default implementation does nothing
  abstract public void handleConnection(Socket s);

  // tell the thread to stop accepting connections
  public void close() {
    mustQuit = true;
    reporter.close();
  }

  // Put any last minute clean up stuff in here
  protected void cleanUp() {
  }

  //everything below provides a simple debug system for this package
  //
  //set this to a print stream if you want debug info, otherwise leave null
  static private PrintStream debugStream;

  // We have two versions of this ..
  static public void setDebugStream( PrintStream ps ) {
    debugStream = ps;
  }

  //-- just for convenience
  static public void setDebugStream( OutputStream out ) {
    debugStream = new PrintStream( out );
  }

  // Send debug info to the print stream, if there is one
  static public void debug( String s ){
    if (debugStream != null)
      debugStream.println(s);
  }

}
