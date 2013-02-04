PROXY FACTORY
-------------------------------------------------------
The proxy factory allows you to start a proxy server at runtime by
calling a restful service.

USAGE
-----------------
The proxy factory is setup in a multi-module approach.
The project is contains the following modules:

- Client (Rest client that can be used to call the restful service)
- Domain (domain objects that are use by the client and the server)
- Factory (Web module that serves the restful service)
- Server (Proxy server)

To start the factory do the following:
- mvn clean install (build the modules)
- cd factory
- mvn jetty:run (start the service)

CODE
-----------------
The server is based on the opensource project jhttpp2.sourceforge.net

