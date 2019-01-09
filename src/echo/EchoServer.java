package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	private static final int PORT = 6000;
	
	 public static void main(String[] args) {

	      ServerSocket serverSocket = null;

	      try {
	         // 1.서버소켓 생성
	         serverSocket = new ServerSocket();

	         // 2.Binding
	         InetAddress inetAddress = InetAddress.getLocalHost();
	         String localhostAddress = inetAddress.getHostAddress();
	         serverSocket.bind(new InetSocketAddress(localhostAddress, PORT));
	         log("binding " + localhostAddress + ":" + PORT);

	         // 3.accept
	         Socket socket = serverSocket.accept(); // Blocking
	         Thread thread = new EchoServerReceiveThread(socket);
	         thread.start();

	      } catch (IOException e) {
	         System.out.println("error : "+e);
	      }

	   }

	
	public static void log(String log) {
		System.out.println("[server#" + Thread.currentThread().getId() +"]" + log);
	}
}