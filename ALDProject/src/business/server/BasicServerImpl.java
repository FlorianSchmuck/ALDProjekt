package business.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public abstract class BasicServerImpl implements BasicServer {
	
	protected ServerSocket serverSocket = null;
	private boolean isServerActiv;
	private boolean isClientActiv;
	
	@Override
	public void initializeServer() throws IOException {
		this.serverSocket = new ServerSocket(Server_Port);
		switchToServerActiv();
	}
	
	@Override
	public File getFileFromRessource(String fileName) throws IOException {
		return new File(ressourcePath+fileName);
	}
	
	public void switchToClientActiv() {
		isClientActiv = true;
		isServerActiv = false;
	}
	
	public void switchToServerActiv() {
		isClientActiv = false;
		isServerActiv = true;
	}
	
	public boolean isClientActiv() {
		return isClientActiv;
	}
	
	public boolean isServerActiv() {
		return isServerActiv;
	}
}
