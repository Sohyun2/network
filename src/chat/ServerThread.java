package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import echo.EchoServer;

public class ServerThread extends Thread {

	private String nickName;
	private Socket socket;
	List<PrintWriter> listWriters;

	public ServerThread(Socket socket, List<PrintWriter> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		// 클라이언트 접속 정보 보여주기..
		// 1. Remote Host Information
		InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

		// 클라이언트 정보
		String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
		// 포트번호
		int remotePort = inetRemoteSocketAddress.getPort();

		System.out.println(remoteHostAddress + " : " + remotePort);
		EchoServer.log("connected by client[" + remoteHostAddress + ":" + remotePort + "]");

		try {
			// 2. 스트림 얻기
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), false);

			// 3. 요청 처리
			while (true) {
				String request = br.readLine();

				// 클라이언트 연결 끊기면...
				if (request == null) {
					System.out.println("정상종료");
					break;
				}				

				String[] tokens = request.split(":");

				if (tokens[0].equals("join")) {
					doJoin(tokens[1], pw); // token[1] : nickName
				} else if (tokens[0].equals("message")) {
					doMessage(tokens[1]); // token[1] : message
				} else if (tokens[0].equals("quit")) {
					doQuit(pw);
				}
			}

		} catch(SocketException se){
			System.out.println("연결끊김!");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doJoin(String nickName, PrintWriter writer) {
		// token[1] : nickName
		this.nickName = nickName;

		String data = "\n" + nickName + "님이 입장하였습니다.";
		System.out.println(data);
		broadcast(data);

		// writer pool에 저장
		addWriter(writer);

		// ack
//		writer.println("join:ok");
//		writer.flush();
	}

	private void addWriter(PrintWriter writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}

	private void broadcast(String data) {
		synchronized (listWriters) {
			for (PrintWriter writer : listWriters) {
				writer.println(data);
				writer.flush();
			}
		}
	}

	private void doMessage(String message) {
		String data = "[" + nickName + "] " + message;

		// 서버에 메시지 찍기
		System.out.println(data);
		broadcast(data);
	}

	private void doQuit(PrintWriter pw) {
		removeWriter(pw);

		String data = nickName + "님이 퇴장하였습니다.";
		System.out.println(data);
		broadcast(data);
	}

	private void removeWriter(PrintWriter pw) {
		pw.println("quit");
		pw.flush();

		listWriters.remove(pw);
	}
}
