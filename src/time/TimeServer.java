package time;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {
	public static final String SERVER_IP = "218.39.221.74";
	public static final int PORT = 6847;
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		// 1. socket 생성
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket(PORT);
			while (true) {
				// 2. 데이터 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE); 
				socket.receive(receivePacket);

				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();
				String message = new String(data, 0, length, "UTF-8");
				// System.out.println("[server] received : " + message);

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
				String time = format.format(new Date());

				// 3.데이터 전송
				byte[] sendTime = time.getBytes("UTF-8");
				// DatagramPacket sendPacket=new DatagramPacket(sendData, sendData.length,new
				// InetSocketAddress(PORT));
				DatagramPacket sendPacket = new DatagramPacket(sendTime, sendTime.length, receivePacket.getAddress(),
						receivePacket.getPort());
				socket.send(sendPacket);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
