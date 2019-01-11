package time;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

import udp.UDPEchoServer;

public class TimeClient {

	// 1. 클라이언트가 요청하면 서버는 다음과 같은 포맷으로 "2019-01-11 14:51:48 오후" 클라이언트에게 타임을 제공해준다.
	// 2. 클라이언트가 서버에 전송하는 요청메시지는 ""이다.

	public static void main(String[] args) {
		Scanner sc = null;
		DatagramSocket socket = null;

		try {

			// 1.키보드 연결
			sc = new Scanner(System.in);
			// 2. 소켓 생성
			socket = new DatagramSocket();

			while (true) {
				System.out.print(">> ");
				String requestMessage = sc.nextLine();

				// 서버에게 메시지 전송
				byte[] data = requestMessage.getBytes("UTF-8");
				DatagramPacket packet = new DatagramPacket(data, data.length,
						new InetSocketAddress(TimeServer.SERVER_IP, TimeServer.PORT));
				socket.send(packet);

				// 메시지 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE],
						UDPEchoServer.BUFFER_SIZE);
				socket.receive(receivePacket);

				String time = new String(receivePacket.getData(), "UTF-8");
				System.out.println("Time " + time);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
