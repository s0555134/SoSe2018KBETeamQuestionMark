package de.htwBerlin.ai.kbe.songWebStore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(name = "SongsServlet", urlPatterns = "/*", initParams = {
		@WebInitParam(name = "json", value = "songs.json") })
public class SongsStoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String APPLICATION_JSON = "application/json";
	
	private static final String APPLICATION_XML = "text/xml";

	private static final String TEXT_HTML = "text/html";

	private static final String TEXT_PLAIN = "text/plain";

	private static final String WRONG_ID = "Fehler, Song mit Id %d nicht vorhanden";

	private static final String WRONG_PARAMETERS = "Fehler, bitte mit Parameter songId oder all versuchen. Bsp. ?songId=\"1\" oder ?all";

	private static final String WRONG_VALUE_FORMAT = "Fehler. Falsches Format fuer Parameter %s angegeben";

	private static final String NO_VALUE = "Fehler. Kein Wert fuer Parameter %s angegeben";

	private static final String JSON_EXCEPTION = "Fehler. Die JSON payload war leider nicht wohlgeformt oder invalide.";

	private static final String DO_POST_SUCCESS = "\"Song %s von %s aus dem Album %s erschienen in %s mit der Id %d hinzugefuegt\"";

	private String songFilename = null;

	private Map<Integer, Song> songStore = null;
	
	private Map<Integer, Songs> songStoreXml = null;

	private AtomicInteger currentId = null;

	@SuppressWarnings("unchecked")
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		try {
			currentId = new AtomicInteger(-1);

			songFilename = servletConfig.getInitParameter("json");
			InputStream url = this.getClass().getClassLoader().getResourceAsStream(songFilename);
			
			List<Song> songsList = (List<Song>) new ObjectMapper().readValue(url, new TypeReference<List<Song>>() {
			});

			songStore = new ConcurrentHashMap<>();

			for (Song song : songsList) {

				songStore.put(song.getId(), song);

				currentId.set(currentId.get() < song.getId() ? song.getId() : currentId.get());

			}

		} catch (JsonParseException e) {
			e.printStackTrace();

		} catch (JsonMappingException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType(APPLICATION_JSON);

		try (PrintWriter out = response.getWriter()) {
			if (request.getParameter("songId") != null)
				printAll(out, request, response);

			else if (request.getParameter("all") != null)
				printSong(out, request, response);

			else
				out.println(WRONG_PARAMETERS);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		int id = 0;
		String line = "";
		String jsonBody = "";
		
		

		Song song = null;

		ObjectMapper oMap = new ObjectMapper();

		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));

		while ((line = in.readLine()) != null) {
			jsonBody += line;
		}

		PrintWriter out = response.getWriter();
		
		
//			 try {
//				 
//				 Songs xmlSongs = new Songs();
//				 xmlSongs = oMap.readValue(jsonBody, Songs.class);
//				 
//				 
//				 id = currentId.incrementAndGet();
//				 xmlSongs.setId(id);
//				 songStoreXml.put(id,  xmlSongs);
//				 
//				 response.setContentType(TEXT_PLAIN);
//				 
//				 out.print(String.format(DO_POST_SUCCESS, xmlSongs.getTitle(), xmlSongs.getArtist(), xmlSongs.getAlbum(),
//						 xmlSongs.getReleased(), xmlSongs.getId()));
//
//					out.flush();
//				 
//				writeSongsToXML(xmlSongs, "songs.xml");
//			} catch (JAXBException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
		
		
		
		try {
			song = oMap.readValue(jsonBody, Song.class);

			id = currentId.incrementAndGet();

			song.setId(id);

			songStore.put(id, song);

			response.setContentType(TEXT_PLAIN);

			out.print(String.format(DO_POST_SUCCESS, song.getTitle(), song.getArtist(), song.getAlbum(),
					song.getReleased(), song.getId()));

			out.flush();

		} catch (JsonParseException | JsonMappingException e) {
			out.print(String.format(JSON_EXCEPTION));
			out.flush();

		}
	}
	
	// @Override
	// public void doPut(HttpServletRequest request, HttpServletResponse response)
	// throws IOException {
	// }
	//
	// @Override
	// public void doDelete(HttpServletRequest request, HttpServletResponse
	// response) throws IOException {
	// }

	@Override
	public void destroy() {
		try {
			ObjectMapper mapper = new ObjectMapper();

			String path = this.getClass().getClassLoader().getResource("songs.json").getPath();

			mapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(path), songStore.values());

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	/**
	 * Gibt alle Songs als respond aus
	 * 
	 * @param out
	 *            der Ausgabestream fuer den respond
	 * @param request
	 *            das request objekt
	 * @param response
	 *            das response objekt
	 * @throws JsonProcessingException
	 */
	private void printAll(PrintWriter out, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {
		try {
			ObjectMapper oMap = new ObjectMapper();

			int id = Integer.parseInt(request.getParameter("songId"));

			if (songStore.containsKey(id)) {

				if (request.getParameter("html") != null) {

					response.setContentType(TEXT_HTML);

					out.println(getHTMLFormattedMessage(songStore.get(id)));

				} else
					out.println(oMap.writeValueAsString(songStore.get(id)));

			} else
				out.println(String.format(WRONG_ID, id));

		} catch (NumberFormatException e) {

			if (request.getParameter("songId").isEmpty())
				out.println(String.format(NO_VALUE, "songId"));
			else
				out.println(String.format(WRONG_VALUE_FORMAT, "songId"));
		}
	}

	/**
	 * Gibt einen Songs als respond aus
	 * 
	 * @param out
	 *            der Ausgabestream fuer den respond
	 * @param request
	 *            das request objekt
	 * @param response
	 *            das response objekt
	 * @throws JsonProcessingException
	 */
	private void printSong(PrintWriter out, HttpServletRequest request, HttpServletResponse response)
			throws JsonProcessingException {

		ObjectMapper oMap = new ObjectMapper();

		if (request.getParameter("html") != null) {

			response.setContentType(TEXT_HTML);

			out.println(getHTMLFormattedMessage(songStore));
		} else
			out.println(oMap.writeValueAsString(songStore.values()));
	}

	/**
	 * Gibt ein HTML Dokument als String zurueck fuer alle Songs zurueck
	 * 
	 * @param map
	 *            alle Song als Map
	 * @return HTML Dokument aller Songs als String
	 */
	private String getHTMLFormattedMessage(Map<Integer, Song> map) {
		String message = "<!DOCTYPE html>\n" + "<html>\n<head>\n<!-- HTML Codes by Quackit.com -->\n<title>\n"
				+ "Songs</title>\n<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
				+ "<style>\ntable.GeneratedTable {\nwidth: 100%;\nbackground-color: #ffffff;\n"
				+ "border-collapse: collapse;\nborder-width: 2px;\nborder-color: #ffcc00;\n"
				+ "border-style: solid;\ncolor: #000000;\n}\n\n"
				+ "table.GeneratedTable td, table.GeneratedTable th {\nborder-width: 2px;\n"
				+ "border-color: #ffcc00;\nborder-style: solid;\npadding: 3px;\n}\n\n"
				+ "table.GeneratedTable thead {\nbackground-color: #ffcc00;\n" + "}\n</style>\n"
				+ "</head>\n<body>\n<table class=\"GeneratedTable\">\n<thead>\n<tr>\n"
				+ "<th>ID</th>\n<th>Title</th>\n<th>Artist</th>\n<th>Album</th>\n<th>Released</th>\n"
				+ "</tr>\n</thead>\n<tbody>";

		for (Song s : map.values()) {
			message += String.format("<tr>\n<td>%d</td>\n<td>%s</td>\n<td>%s</td>\n<td>%s</td>\n<td>%d</td>\n</tr>",
					s.getId(), s.getTitle(), s.getArtist(), s.getAlbum(), s.getReleased());
		}
		message += "</tbody>\n" + "</table>\n" + "</body>\n" + "</html>";

		return message;
	}

	/**
	 * Gibt ein HTML Dokument als String zurueck fuer einen Song zurueck
	 * 
	 * @param s
	 *            der Song der ausgegeben werden soll
	 * @return HTML Dokument des Songs als String
	 */
	private String getHTMLFormattedMessage(Song s) {
		String message = "<!DOCTYPE html>\n<html>\n<head>\n<!-- HTML Codes by Quackit.com -->\n"
				+ "<title>\nSongs</title>\n"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n<style>\n"
				+ "table.GeneratedTable {\nwidth: 100%;\nbackground-color: #ffffff;\n"
				+ "border-collapse: collapse;\nborder-width: 2px;\nborder-color: #ffcc00;\n"
				+ "border-style: solid;\ncolor: #000000;\n}\n\n"
				+ "table.GeneratedTable td, table.GeneratedTable th {\nborder-width: 2px;\n"
				+ "border-color: #ffcc00;\nborder-style: solid;\npadding: 3px;\n}\n\n"
				+ "table.GeneratedTable thead {\nbackground-color: #ffcc00;\n}\n</style>\n</head>\n<body>\n"
				+ "<table class=\"GeneratedTable\">\n<thead>\n<tr>\n<th>ID</th>\n<th>Title</th>\n"
				+ "<th>Artist</th>\n<th>Album</th>\n<th>Released</th>\n</tr>\n</thead>\n<tbody>";

		message += String.format("<tr>\n<td>%d</td>\n<td>%s</td>\n<td>%s</td>\n<td>%s</td>\n<td>%d</td>\n</tr>",
				s.getId(), s.getTitle(), s.getArtist(), s.getAlbum(), s.getReleased());

		message += "</tbody>\n</table>\n</body>\n</html>";

		return message;
	}
	
	  static void writeSongsToXML(Songs songs, String filename) throws JAXBException, FileNotFoundException, IOException {
	        JAXBContext context = JAXBContext.newInstance(Songs.class, Song.class);
	        Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(filename))) {
	            marshaller.marshal(songs, os);
	        }
	    }
	
	 static Song readXMLToSongs(String filename) throws JAXBException, FileNotFoundException, IOException {
	        JAXBContext context = JAXBContext.newInstance(Song.class);
	        Unmarshaller unmarshaller = context.createUnmarshaller();
	        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
	            return (Song) unmarshaller.unmarshal(is);
	        }
	 }
}