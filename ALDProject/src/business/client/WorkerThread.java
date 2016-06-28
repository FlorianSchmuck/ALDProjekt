package business.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;

import business.server.BasicServer;
import business.server.TelnetServer;

public class WorkerThread implements Runnable{

	private Socket socket;
	private BufferedReader serverReader;
	private BufferedWriter serverCommand;
	private TelnetServer server;
	
	public WorkerThread(Socket socket, TelnetServer server) throws IOException {
		this.socket = socket;
		serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
		serverCommand = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.server = server;
	}
	
	public void sendToServer(String msg) throws IOException{
		
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
		int plauseCheck = 3;
		try {
			
			while(true) {
				line = serverReader.readLine();
				if (line.split(BasicServer.fileSeparator).length == plauseCheck){
					server.responseToClient(socket,line);
				}
				else if (line.equals("exit")){
						break;
					}
				else {
					sendToServer("wrong Input");
				}
			}	
			close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
