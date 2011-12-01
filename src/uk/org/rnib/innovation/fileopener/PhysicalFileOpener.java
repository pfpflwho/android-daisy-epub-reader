package uk.org.rnib.innovation.fileopener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PhysicalFileOpener implements FileOpener{
	public FileInputStream open(String path){
		try {
			return new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
