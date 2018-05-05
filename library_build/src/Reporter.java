// $Id$

package mylib;

class Reporter implements Runnable
{
  // the server we are reporting on
  private Server server;

  //our background thread
  private Thread thread;

  //Set this to true to tell the thread to stop accepting conenctions
  private boolean mustQuit = false;

  Reporter( Server server ) {
    this.server = server;

    // create a bacground thread
    thread = new Thread( this );
    thread.start();
  }

  public void run() {
    while (!mustQuit) {
      // do the reporting
      Server.debug( "server has had "+server.numConnections+" connections " );
      
      //then pause a while
      try {
        Thread.sleep( 5000 );
      } catch( InterruptedException ie ) {}
    }
  }

  public void close() {
    mustQuit = true;
  }

}
