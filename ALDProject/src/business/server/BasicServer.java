package business.server;

import java.io.File;
import java.io.IOException;

public interface BasicServer {

	final int SERVER_PORT = 1111;
	final boolean isChooseFromFileSystemEnabled = false;
	final String ressourcePath = "src\\ressources\\";
	final String fileSeparator = ";";
	
	void initializeServer() throws IOException;
	File getFileFromRessource(String fileName) throws IOException;
	void startServerRoutine() throws Exception;
	void destroyServer() throws IOException;
}
