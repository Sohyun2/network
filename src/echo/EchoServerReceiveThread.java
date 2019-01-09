package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {

	   private Socket socket;
	   
	   public EchoServerReceiveThread(Socket socket) {
	      this.socket = socket;
	   }

	   public void run() {
	      
	      InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

	      // 클라이언트 정보
	      String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
	      // 포트번호
	      int remotePort = inetRemoteSocketAddress.getPort();

	      System.out.println(remoteHostAddress + " : " + remotePort);
	      EchoServer.log("connected by client[" + remoteHostAddress + ":" + remotePort + "]");

	      try {
	         BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
	         PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

	         while (true) {

	            String data = br.readLine();

	            if (data == null) {
	               EchoServer.log("closed by client");
	               return;
	            }

	            EchoServer.log("received : " + data);
	            pw.println(data);
	         }
	      } catch (SocketException e) {
	         EchoServer.log("abnormal closed by client");
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	   }
	}