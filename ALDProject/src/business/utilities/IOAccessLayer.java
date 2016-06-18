package business.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import beans.City;
import beans.Street;
import business.server.BasicServer;

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
		File file = null;
		if(fileChooser.showOpenDialog(new JFrame("FileChooser")) == JFileChooser.APPROVE_OPTION){
			file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			return file;
		}
		else
		{	new FileNotFoundException();
			return null;
		}
	}
	
	public List<City> readCityFile(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line = "";
		String[] splitLine = null;
		List<City> fileContentList = new ArrayList<>();
		while ((line = bufferedReader.readLine()) != null) {
			splitLine = line.split(BasicServer.fileSeparator);
			if (splitLine.length == 5){
			fileContentList.add(new City(splitLine[0], Integer.parseInt(splitLine[1])));
			}
			else { 
				continue;
			}
		}
		bufferedReader.close();
		return fileContentList;
	}
	
	public List<Street> readStreetsFile(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		String line = "";
		String[] splitLine = null;
		List<Street> fileContentList = new ArrayList<>();
		while ((line = bufferedReader.readLine()) != null) {
			splitLine = line.split(BasicServer.fileSeparator);
			if (splitLine.length == 5){
			fileContentList.add(new Street(splitLine[0], Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]), Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[4])));
			}
			else { 
				continue;
			}
		}
		bufferedReader.close();
		return fileContentList;
	}
	
	
	public void writeFile(String path ,String...fileContent) {
		//TODO write File
	}
	
	
	

}
