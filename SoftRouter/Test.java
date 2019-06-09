package SoftRouter;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Test {

  public static void main(String[] args) throws UnknownHostException, SocketException {
    System.out.println("[INFO] Starting UDP send and receive procedure");

    /**
     * *UDPReceiver initialize: (int port)
     * !Run UDP receiver thread: UDPReceiver.start()
     * ?Why .start() not .run()? See:
     *  https://www.geeksforgeeks.org/difference-between-thread-start-and-thread-run-in-java/
     */
    UDPReceiver udpReceiver = new UDPReceiver(50001);
    udpReceiver.start();

    /**
     * *RouterPacket initialize: (int source, int target, int TLL, String message)
     * *UDPSender initialize: (int currentPort, int nextNodePort, RouterPacket packet)
     * !Run UDP Sender thread: UDPSender.run()
     */
    RouterPacket packet1 = new RouterPacket(64342, 50001, 30, "Hello 1");
    UDPSender udpSender1 = new UDPSender(54321, 50001, packet1);
    udpSender1.run();

    RouterPacket packet2 = new RouterPacket(64310, 50001, 30, "Hello 2");
    UDPSender udpSender2 = new UDPSender(54321, 50001, packet2);
    udpSender2.run();
  }
}