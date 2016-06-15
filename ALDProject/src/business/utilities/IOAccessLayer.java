package business.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class IOAccessLayer {
	
	private static IOAccessLayer theInstance;
	
	private IOAccessLayer() {}
	
	public static IOAccessLayer getTheInstance() throws IOException {
		if(theInstance == null) {
			theInstance = new IOAccessLayer();
		}
		return theInstance;
	}
	
	public File chooseFileFromFileSystem(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.showOpenDialog(new JFrame("FileChooser"));
		//TODO?
		return null;
	}
	
	public List<String> readFile(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line = "";
		String[] splitLine = null;
		List<String> fileContentList = new ArrayList<>();
		while ((line = bufferedReader.readLine()) != null) {
			splitLine = line.split(";");
			//TODO: We know we can do better than n²
			//Maybe directly create streets?!
			for (String element: splitLine) {
				fileContentList.add(element);
			}
		}
		bufferedReader.close();
		return fileContentList;
	}
	
	
	public void writeFile(String path ,String...fileContent) {
		//TODO write File
	}
	
	
	

}
