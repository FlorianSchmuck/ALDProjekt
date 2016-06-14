package business.server;

import java.io.File;
import java.io.IOException;

public interface BasicServer {

	int Server_Port = 1111;
	String ressourcePath = "src\\ressources\\";
	
	void initializeServer() throws IOException;
	File getFileFromRessource(String fileName) throws IOException;
	void startServerRoutine() throws Exception;
}
