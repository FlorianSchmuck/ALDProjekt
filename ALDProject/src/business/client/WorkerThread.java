package business.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import business.server.BasicServer;
import business.server.TelnetServer;

public class WorkerThread implements Runnable{

	private Socket socket;
	private BufferedReader serverReader;
	private BufferedWriter serverCommand;
	
	public WorkerThread(Socket socket) throws IOException {
		this.socket = socket;
		serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		serverCommand = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	
	public void sendToServer(String msg) throws IOException{
		serverCommand.write(msg);
		serverCommand.newLine();
		serverCommand.flush();
	}
	
	public void close() throws IOException{
		this.socket.close();
		this.serverCommand.close();
		this.serverReader.close();
		TelnetServer.isClientActiv = false;
	}
	
	@Override
	public void run() {
		String line = "";
		int plauseCheck = 2;
		try {
			while((line = serverReader.readLine()) != null) {
				if (line.split(BasicServer.fileSeparator).length != plauseCheck){
				sendToServer("wrong Input");
				}
				else {
					sendToServer(line);
				}
			}	
			close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
