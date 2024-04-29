## Ring Topology Simulation in Java

The application simulates a ring network, which has three nodes. The nodes communicate in a unidirectional loop and each node increments the recieved value untill the value 100 is reached.
In our case, we start communication from socket1 (socket1.sendValue(1)). TCP sockets were used for the implementation.

![image](https://github.com/Marian0111/Sockets-communication/assets/113176325/52c8f44e-561c-45da-b5a1-31c1bb101ed0)

**Code documentation**
***
  ### Fields:
   * String ping: Host address of the node.
   * int port: Port number for the node’s server socket.
   * int value: Current value held by the node.
   * SocketTCP nextSocket: Reference to the next node in the ring.
   * ServerSocket serverSocket: Server socket to accept incoming connections.

***
  ### Methods:
   * void sendValue(int value): Connects to the next node and sends the value. Attempts to connect to the next node in the ring and sends the specified value. If the connection is successful, the value is sent through a PrintWriter which writes the value to the output stream of the socket.
   * void receiveValue(int value): Processes the received value by setting the internal value field and then performing an action based on the received value. If the value is less than 100, it increments the value and sends it to the next node using sendValue(). If the value is 100 or greater, it stops communucation.
   * void start(): The server socket opens and listens for incoming connections. Upon receiving a connection, it reads the incoming value, processes it using receiveValue(), and then handles the next connection.
   * void setNextSocket(SocketTCP nextSocket): Links the current node to the next one in the ring.

The methods above allow for each node to act both as a client and as a server.


## Setup
To setup the application, it is enough to just clone the repository: git clone https://github.com/Marian0111/Sockets-communication.
***
After cloning, open a terminal and run: 
```
javac Main.java
java Main
```
After running the commands above, the application should start. You should be able to see each node sending and receiving values, up untill value 100 is reached.

## Students
1. Andrei Marian Iosif
2. Dan Adrian Solomonean
