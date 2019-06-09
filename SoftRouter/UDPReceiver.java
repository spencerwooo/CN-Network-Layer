package SoftRouter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPReceiver extends Thread {
  // UDP Server 监听端口
  private int port;

  private DatagramSocket socket;
  private InetAddress address;
  private boolean running;
  private byte[] buf = new byte[256];

  public UDPReceiver(int port) throws SocketException, UnknownHostException {
    this.port = port;

    this.socket = new DatagramSocket(this.port);
    this.address = InetAddress.getByName("localhost");
  }

  private void listen() throws IOException, ClassNotFoundException {
    DatagramPacket packet = new DatagramPacket(buf, buf.length);
    socket.receive(packet);

    ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(buf));
    RouterPacket receivedPacket = (RouterPacket) iStream.readObject();
    iStream.close();

    System.out.println("UDP Receiver received: " + receivedPacket.toString());

    if (receivedPacket.getData().equals("end")) {
      running = false;
    }
  }

  public void run() {
    System.out.println("[Server] Running Server at: " + address);

    running = true;

    while (running) {
      try {
        listen();
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }

    socket.close();
  }
}