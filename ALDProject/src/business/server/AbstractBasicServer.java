package business.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public abstract class AbstractBasicServer implements BasicServer {
	
	protected ServerSocket serverSocket = null;
	
	@Override
	public void initializeServer() throws IOException {
		this.serverSocket = new ServerSocket(SERVER_PORT);
	}
	
	@Override
	public File getFileFromRessource(String fileName) throws IOException {
		return new File(ressourcePath+fileName);
	}
	
	@Override
	public void destroyServer() throws IOException {
		this.serverSocket.close();
		System.exit(0);
	}
}
