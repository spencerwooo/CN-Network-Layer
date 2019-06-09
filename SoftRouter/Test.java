package SoftRouter;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Test {

  public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
    System.out.println("[INFO] Starting UDP send and receive procedure");

    /**
     * *UDPReceiver initialize: (int port)
     * !Run UDP receiver thread: UDPReceiver.start()
     * ?Why .start() not .run()? See:
     *  https://www.geeksforgeeks.org/difference-between-thread-start-and-thread-run-in-java/
     */
    UDPReceiver udpReceiver = new UDPReceiver(0, 50001);
    udpReceiver.start();

    /**
     * *RouterPacket initialize: (int source, int target, int TLL, String message)
     * *UDPSender initialize: (int currentPort, int nextNodePort, RouterPacket packet)
     * !Run UDP Sender thread: UDPSender.run()
     */
    RouterPacket packet1 = new RouterPacket(64342, 50001, 1, "Hello 1");
    UDPSender udpSender1 = new UDPSender(0, 50001, packet1);
    udpSender1.run();

    RouterPacket packet2 = new RouterPacket(64310, 50001, 2, "Hello 2");
    UDPSender udpSender2 = new UDPSender(1, 50001, packet2);
    udpSender2.run();

    RouterPacket packet3 = new RouterPacket(64310, 50001, 3, "end");
    UDPSender udpSender3 = new UDPSender(4, 50001, packet3);
    udpSender3.run();

    Statistics statistics = new Statistics();
    statistics.printStats();
  }
}