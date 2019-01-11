package chat;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Client {

	private static final String SERVER_IP = "192.168.0.68";
	private static final int SERVER_PORT = 9999;

	public static void main(String[] args) {

		Socket socket = null;
		try {
			// 1. socket 생성
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			// ChatClienThread시작
			new ChatClientSendThread(socket).start();
			new ChatClientReceiveThread(socket).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
