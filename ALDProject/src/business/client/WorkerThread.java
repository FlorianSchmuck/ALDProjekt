package business.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class WorkerThread implements Runnable{

	private Socket socket;
	private BufferedReader serverReader;
	private BufferedWriter serverCommand;
	
	public WorkerThread(Socket socket,BufferedWriter serverCommand) throws IOException {
		this.socket = socket;
		this.serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.serverCommand = serverCommand;
	}
	@Override
	public void run() {
		String line = "";
		try {
			while((line = serverReader.readLine()) != "Close") {
				System.out.println(line);
				serverCommand.write(line);
			}
			if (line.equals("Close")) {
				System.out.println("Hier is Schluss!");
				this.socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
