package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class EchoServer {

	private static final int PORT = 8888;
	

	public static void main(String[] args) {

		List<PrintWriter> listWriters = new ArrayList<PrintWriter>();
		
		ServerSocket serverSocket = null;

		try {
			// 1. create server socket
			serverSocket = new ServerSocket();

			// 2. bind
			// localhost를 사용하기 위해서 아래와 같이 address얻어옴..
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			serverSocket.bind(new InetSocketAddress(hostAddress, PORT));
			log("binding " + hostAddress + ":" + PORT);
			
			while (true) {
				// 3. Wait for connecting( accept )
				Socket socket = serverSocket.accept();
				new ServerThread(socket, listWriters).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 5. 자원정리
			try {
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void log(String log) {
		System.out.println("[server#" + Thread.currentThread().getId() +"]" + log);
	}
}
