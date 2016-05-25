#WebSocket Log Viewer

To run this application you need Wildfly 9 (10 may work).  

Start up Wildfly, then run the script in configuration with jboss-cli to create the queue.  

After that, run `mvn clean install` and then copy the warfile in target to deployments.
