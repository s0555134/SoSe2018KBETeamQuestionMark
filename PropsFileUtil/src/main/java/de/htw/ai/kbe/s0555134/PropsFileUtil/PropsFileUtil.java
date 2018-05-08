package de.htw.ai.kbe.s0555134.PropsFileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

public class PropsFileUtil {
	
	
protected static FileInputStream fileInput;
	
	protected static File files;

	public PropsFileUtil() {
		
	}
	
	
	public static Properties userInput() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Gib den Namen der Datei ein, welches ausgelesen werden soll: ");
		String readFile = sc.next();
		return loadPropertiesFile(readFile);

	}

	public static Properties loadPropertiesFile() {
		return loadPropertiesFile("files/config.properties");

	}

	public static Properties loadPropertiesFile(String file) {
		if(file==null) {
			return null;
		}
		Properties prop= new Properties();
		try {
			if (file.contains(".properties")) {
				files = new File(file);
				fileInput = new FileInputStream(files);
				prop.load(fileInput);
				if (prop.isEmpty()) { //
					prop = null;
					System.out.println("Propertie-File is empty");
				}

			} else {
				prop = null;
				System.out.println("File is not ending with '.properties'");
			}
		} catch (Exception e) {
			prop = null;
			System.out.println("Error: "+e);
		}

		return prop;			//  java.util.Properties zurueckgeben, falls erfolgreich
	}
}
