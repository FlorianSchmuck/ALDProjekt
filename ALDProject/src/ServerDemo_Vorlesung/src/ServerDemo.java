import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class ServerDemo {

	public static void main(String[] args) {
		
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(1111);

			for (int i = 0; i < 2; i++) {
				Socket clientSocket = serverSocket.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				BufferedWriter bbr = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			
				// bbr.write(LocalDateTime.now().toString());
				// bbr.newLine();
				while (true) {
					String line = br.readLine();
					if (line != null) {
						bbr.write(line);
						bbr.newLine();
						bbr.flush();
					}
					else{
						break;
					}
				}
				clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
