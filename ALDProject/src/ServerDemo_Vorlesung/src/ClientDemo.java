import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientDemo {

	public static void main(String[] args) throws IOException {

		Socket clientSocket = new Socket("localhost", 1111);

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader tbr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		BufferedWriter bbr = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
		String line;

		while (!(line = br.readLine()).equals("exit")) {
			bbr.write(line);
			bbr.newLine();
			bbr.flush();
			String sLine = tbr.readLine();
			System.out.println(sLine);
		}
		clientSocket.close();
	}
}
