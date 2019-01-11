package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientSendThread extends Thread {
	private Socket socket;
	private String nickName;
	
	public ChatClientSendThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), false);

			sc = new Scanner(System.in);
			// 닉네임 입력받기
			System.out.print("닉네임>>");
			nickName = sc.nextLine();
			pw.println("join:" + nickName);
			pw.flush();

			while (true) {
				// 채팅내용 입력받기
				
				String line = sc.nextLine();

				if ("quit".equals(line)) {
					pw.println(line);
					pw.flush();
					
					break;
				} else {
					// 메시지 보내기
					pw.println("message:" + line);
					pw.flush();
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
