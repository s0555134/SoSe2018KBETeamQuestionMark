package de.htw.ai.kbe.s0555134.RunMeRunner1;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;

import de.htw.ai.kbe.s0555134.PropsFileUtil.PropsFileUtil;

public class MethodRunner {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String searchTermProp = "";
		String searchTermReport = "";
		Properties properties = null;
		String eingabe = "";
		String methodenAnzahl = "";
		String nichtausgeführt = "";
		String methodediecncihtausgeführtwerdenkannweileseineexceptiongegebenhat = "";
		String annoAnzahl = "";

		Scanner sc = new Scanner(System.in);
	
		boolean run = true;
		
		int aZaehler = 0;
		int mZaehler = 0;
		
		
		// Der Parser mit Options fuer die Argumente 
		try {

			Options options = new Options();
			options.addOption("p", "configFile", true, "erster Parameter");
			options.addOption("o", "RunMeReport", true, "zweiter Parameter");
			options.addOption("h", "help", false, "zeigt die Hilfe");

			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(options, args);

			// Validation for Values also für -p, -o , -h
			if (cmd.hasOption("h")) {
				HelpFormatter formater = new HelpFormatter();

				formater.printHelp("CommandLineParameter sollte folgenderweise aussehen:", options);		

			}

			searchTermProp = cmd.getOptionValue("p");
			System.out.println("configFile: " + searchTermProp);

			searchTermReport = cmd.getOptionValue("o");
			System.out.println("OutputFile: " + searchTermReport);

			if (searchTermReport == null || searchTermReport.isEmpty()) {
				System.out.println("Error: Argumente sind nicht vollstaendig oder wurden falsch gewaehlt");
				System.out.println("Programm wird neugestartet");
				System.exit(1);
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: Argumente sind nicht vollstaendig oder wurden falsch gewaehlt,  Exception:" + e);
			e.printStackTrace();
		}

		// Reflexion mit classloader 
		ClassLoader classLoader = MethodRunner.class.getClassLoader();

		if (args != null && args.length == 1)
			properties = PropsFileUtil.loadPropertiesFile(args[0]);
		
		while (run) {
			try {

				if (properties == null) {
					System.out.println("Geben Sie den Pfad an und die eingetragene -p : "+ searchTermProp +" , wo sich die Configfile befindet");
					eingabe = sc.nextLine();
					properties = PropsFileUtil.loadPropertiesFile(eingabe);
				}
				if (properties != null && properties.getProperty("classToLoad") != null) {
					Class myClass = classLoader.loadClass((properties.getProperty("classToLoad").toString()));
					Object o =  myClass.newInstance();
					Method[] methods = myClass.getDeclaredMethods();
					if (o != null)
						for (Method m : methods) {

							mZaehler++;
							methodenAnzahl = Integer.toString(mZaehler);
							
							try {
								if (m.isAnnotationPresent(RunMe.class)) {
									aZaehler++;
									annoAnzahl = Integer.toString(aZaehler);
									methodediecncihtausgeführtwerdenkannweileseineexceptiongegebenhat = m.getName();
									System.out.println(m.invoke(o));
								}
							} catch (IllegalArgumentException e) {
								nichtausgeführt += "- "
										+ methodediecncihtausgeführtwerdenkannweileseineexceptiongegebenhat + "\n";
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								System.out.println("Error: " + e);
								System.out.println("3Versuch es erneut");
								nichtausgeführt += "- "
										+ methodediecncihtausgeführtwerdenkannweileseineexceptiongegebenhat + "\n";
							} catch (IllegalAccessException e) {

								System.out.println("Error: " + e);
								System.out.println("1Versuch es erneut");
								nichtausgeführt += "- "
										+ methodediecncihtausgeführtwerdenkannweileseineexceptiongegebenhat + "\n";
							}

						}

				} else {
					System.out.println("Property datei enthält de richtigen key oder falscher Pfad");

				}

			} catch (IllegalArgumentException e) {
				nichtausgeführt += "- " + methodediecncihtausgeführtwerdenkannweileseineexceptiongegebenhat + "\n";
				e.printStackTrace();
			}

			catch (InstantiationException e) {
				System.out.println("Error: " + e);
				System.out.println("4Versuch es erneut");
			} 
			catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("5Versuch es erneut");

			} catch (NullPointerException e) {
				e.printStackTrace();
				System.out.println("6Versuch es erneut");
			}
			catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Argumente und die Anzahl der MEthoden in einem Txt-Datei schreiben
			try {
				List<String> lines = Arrays.asList("Gesamtzahl der Methoden der Klasse: " + methodenAnzahl,
						"GesamtZahl der annortierten Methoden: " + annoAnzahl,
						nichtausgeführt.length() != 0
								? "Folgende MEthoden kontnen nciht ausgeführtw erden: \n" + nichtausgeführt
								: "");
				Path file = Paths.get(searchTermReport);
				Files.write(file, lines, Charset.forName("UTF-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

				// do-While Schleife fuer die Nutzerangaben
			do {

				System.out.println("Wollen Sie weitere Angaben machen? Ja/Nein");
				eingabe = sc.nextLine();
				if (eingabe.equalsIgnoreCase("Nein")) {
					System.out.println("Bye");
					run = false;
				} else if (eingabe.equalsIgnoreCase("Ja")) {
					properties = null;
				} else {
					System.out.println("Eingabe wurde nicht erkannt, versuch es erneut!");

				}

			} while (!eingabe.equalsIgnoreCase("Nein") && !eingabe.equalsIgnoreCase("Ja"));
		}
	}

}