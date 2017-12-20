package org.mcpg.ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase que facilita la gestión de archivos básicos.
 * <p>
 * Usa métodos típicos de Java 7 y Java 8, así como lambda expresiones, streams
 * y for extendido.
 * <p>
 * El método de escritura es mediante <i>Buffers</i>
 * 
 * @version 1.0
 * @since 1.0
 * @author manpergut
 *
 * @see BufferedReader
 * @see BufferedWriter
 *
 */
public class UtilFicheros {

	// Publicos
	/**
	 * Escribe una sola línea en el fichero dado por el "path". Si el fichero no
	 * existe, crea uno nuevo y escribe en él la "linea" parámetro.
	 * <p>
	 * Si el archivo ya tenía información <b>NO</b> la sobreescibe, si no que
	 * escribe a continuación de la última línea.
	 * <p>
	 * Escribirá el texto en formato <i>UTF-8</i>
	 * 
	 * @param path
	 *            -El directorio donde se encuentra o se creará el archivo-
	 * @param linea
	 *            -La línea a escribir.-
	 * @return boolean -<i>true</i> si la operación es correcta; <i>false</i> si no
	 *         lo es.-
	 * @since 1.0
	 */
	public static boolean escribeLineaFichero(String path, String linea) {

		boolean res = false;
		Path dir = Paths.get(path);

		if (Files.exists(dir)) {
			String data = leeFicheroExistente(dir);
			System.out.println("Habia: " + data);
			data += "\n" + linea;
			System.out.println("Escribiendo: " + data);
			res = escribeFicheroExistente(dir, data);
		} else {
			if (creaFichero(dir)) {
				linea += "\n";
				res = escribeFicheroExistente(dir, linea);
			}
		}

		return res;

	}

	/**
	 * Escribe en un fichero una lista de líneas.
	 * <p>
	 * Si el fichero no existe, crea uno nuevo en el directorio "path" y escribe en
	 * él las lineas pasadas como parámetro.
	 * <p>
	 * Si el archivo ya tenía información <b>NO</b> la sobreescibe, si no que
	 * escribe a continuación de la última línea.
	 * <p>
	 * Escribirá el texto en formato <i>UTF-8</i>
	 * 
	 * @param path
	 *            -El directorio donde se encuentra o se creará el archivo-
	 * @param lineas
	 *            -Lista de líneas a escribir-
	 * @return boolean -<i>true</i> si la operación es correcta; <i>false</i> si no
	 *         lo es.-
	 * 
	 * @since 1.0
	 */
	public static boolean escribeLineasFichero(String path, List<String> lineas) {
		boolean res = false;
		Path dir = Paths.get(path);

		if (Files.exists(dir)) {
			String data = leeFicheroExistente(dir);
			System.out.println("Había: " + data);
			String li = "";
			for (String s : lineas) {
				li += s + "\n";
			}
			data += li;
			System.out.println("Escribiendo: " + data);
			res = escribeFicheroExistente(dir, data);
		} else {
			if (creaFichero(dir)) {
				String data = "";
				System.out.println("Creando fichero en... " + path);
				for (String s : lineas) {
					data += s + "\n";
				}
				System.out.println("Escribiendo: " + data);
				res = escribeFicheroExistente(dir, data);
			}
		}

		return res;
	}

	/**
	 * Escribe en un fichero existente o, lo crea, un texto completo delimitado por
	 * un separador conocido.
	 * <p>
	 * Si el archivo ya tenía información <b>NO</b> la sobreescibe, si no que
	 * escribe a continuación de la última línea.
	 * <p>
	 * Escribirá el texto en formato <i>UTF-8</i>
	 * 
	 * @param path
	 *            -El directorio donde se escribirá el texto-
	 * @param texto
	 *            -Texto a escribir-
	 * @param separador
	 *            -Separador conocido-
	 * @return boolean -<i>true</i> si la operación es correcta; <i>false</i> si no
	 *         lo es.-
	 * @since 1.0
	 */
	public static boolean escribeFicheroCompleto(String path, String texto, String separador) {
		boolean res = false;
		Path dir = Paths.get(path);

		if (Files.exists(dir)) {
			String data = leeFicheroExistente(dir);
			System.out.println("Había: " + data);
			String[] t = texto.trim().split(separador);
			for (String s : t) {
				data += s + "\n";
			}
			System.out.println("Escribiendo: " + data);
			res = escribeFicheroExistente(dir, data);
		} else {
			if (creaFichero(dir)) {
				String data = "";
				System.out.println("Creando fichero en... " + path);
				String[] t = texto.trim().split(separador);
				for (String s : t) {
					data += s.trim() + "\n";
				}
				System.out.println("Escribiendo: " + data);
				res = escribeFicheroExistente(dir, data);
			}
		}

		return res;
	}

	/**
	 * Escribe y/o crea un fichero en el directorio pasado como parámetro. Escribirá
	 * el texto de manera común, es decir, usando como separador el <b>punto</b>
	 * <b>"."</b>.
	 * <p>
	 * Si el archivo ya tenía información <b>NO</b> la sobreescibe, si no que
	 * escribe a continuación de la última línea.
	 * <p>
	 * Escribirá el texto en formato <i>UTF-8</i>
	 * 
	 * @param path
	 *            -El directorio donde se escribirá el texto-
	 * @param texto
	 *            -Texto a escribir-
	 * @return boolean -<i>true</i> si la operación es correcta; <i>false</i> si no
	 *         lo es.-
	 * @since 1.0
	 */
	public static boolean escribeFicheroCompletoComun(String path, String texto) {
		return escribeFicheroCompleto(path, texto, "\\.");
	}

	/**
	 * Lee la primera línea del archivo en el directorio "path".
	 * <p>
	 * Si el fichero no existe, devolverá: <b>No existe el fichero</b>.
	 * <p>
	 * Si la línea no existe, devolverá: <b>No hay línea en esa posición</b>.
	 * 
	 * @param path
	 *            -El directorio donde se encuentra el fichero-
	 * @return String -Primera linea del fichero o, en su defecto, la frase
	 *         predeterminada.-
	 * @since 1.0
	 */
	public static String leePrimeraLineaFichero(String path) {
		String res = "";
		Path dir = Paths.get(path);

		if (Files.exists(dir)) {
			res += leeLineaFicheroExistente(dir, 0);
		} else {
			res += "No existe el fichero";
		}

		return res;
	}

	/**
	 * Lee una linea del fichero que se encuentre en el directorio "path"; dicha
	 * linea será indicada mediante el parámetro "i".
	 * <p>
	 * Si el fichero no existe, devolverá: <b>No existe el fichero</b>.
	 * <p>
	 * Si la línea no existe, devolverá: <b>No hay línea en esa posición</b>.
	 * 
	 * @param path
	 *            -Dirección del fichero-
	 * 
	 * @param i
	 *            -Posición de la línea referente al fichero-
	 * @return String -La línea deseada o, la frase por defecto.-
	 * @since 1.0
	 */
	public static String leeLineaFichero(String path, int i) {

		String res = "";
		Path dir = Paths.get(path);

		if (Files.exists(dir)) {
			res += leeLineaFicheroExistente(dir, i);
		} else {
			res += "No existe el fichero";
		}

		return res;
	}

	/**
	 * Lee el fichero completo, ubicado en la dirección "path", y lo devuelve en una
	 * String con cada línea separada por un salto de línea.
	 * <p>
	 * Si el fichero no existe, devolverá: <b>No existe el fichero</b>.
	 * <p>
	 * Si el fichero está vacío, devolverá una cadena vacía.
	 * 
	 * @param path
	 *            -Direccion del fichero.-
	 * @return String -Fichero completo.-
	 * @since 1.0
	 */
	public static String leeFicheroCompletoAString(String path) {
		String res = "";
		Path dir = Paths.get(path);

		if (Files.exists(dir)) {
			res += leeFicheroExistente(dir);
		} else {
			res += "No existe el fichero";
		}

		return res;
	}

	/**
	 * Lee el fichero completo, ubicado en la dirección "path", y lo devuelve en una
	 * Lista<String> con cada línea separada por un salto de línea.
	 * <p>
	 * Si el fichero no existe, devolverá: <b>No existe el fichero</b>.
	 * <p>
	 * Si el fichero está vacío, devolverá la lista vacía.
	 * 
	 * @param path
	 *            -Direccion del fichero.-
	 * @return List<String> -Fichero completo.-
	 * @since 1.0
	 */
	public static List<String> leeFicheroCompletoALista(String path) {
		List<String> res = new ArrayList<>();
		Path dir = Paths.get(path);

		if (Files.exists(dir)) {
			res.addAll(leeFicheroLista(dir));
		}
		return res;
	}

	/**
	 * Lee el fichero completo, ubicado en la dirección "path", y lo devuelve en una
	 * Lista<String> con cada línea diferanciada por el "separador".
	 * <p>
	 * Si el fichero no existe, devolverá: <b>No existe el fichero</b>.
	 * <p>
	 * Si el fichero está vacío, devolverá la lista vacía.
	 * 
	 * @param path
	 *            -Direccion del fichero.-
	 * @param separador
	 *            -Separador para generar la lista.-
	 * @return List<String> -Fichero completo.-
	 * @since 1.0
	 */
	public static List<String> leeFicheroCompletoALista(String path, String separador) {
		List<String> res = new ArrayList<>();
		Path dir = Paths.get(path);

		if (Files.exists(dir)) {
			res.addAll(leeFicheroListaSeparador(dir, separador));
		}

		return res;
	}

	// Privados

	/**
	 * <b>Privado</b>
	 * <p>
	 * Lee un fichero y lo devuelve en forma de lista, diferenciando cada elemento
	 * por el "separador".
	 * 
	 * @throws IOException
	 * @param dir
	 *            -Ruta del archivo.-
	 * @param separador
	 *            -Separador.-
	 * @return List<String> -Contendrá el texto <i>'parseado'</i> o vacía si no
	 *         contiene nada el archivo.-
	 * @since 1.0
	 */
	private static List<String> leeFicheroListaSeparador(Path dir, String separador) {
		List<String> res = new ArrayList<>();

		try {
			BufferedReader bf = Files.newBufferedReader(dir);
			for (String s : bf.lines().collect(Collectors.toList())) {
				String[] p = s.trim().split(separador);
				res.addAll(Arrays.asList(p));
			}
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * <b>Privado</b>
	 * <p>
	 * Lee un fichero y lo devuelve en forma de lista.
	 * 
	 * @throws IOException
	 * @param dir
	 *            -Ruta del archivo.-
	 * @return List<String> -Contendrá el texto <i>'parseado'</i> o vacía si no
	 *         contiene nada el archivo.-
	 * @since 1.0
	 */
	private static List<String> leeFicheroLista(Path dir) {
		List<String> res = new ArrayList<>();

		try {
			BufferedReader br = Files.newBufferedReader(dir);
			res.addAll(br.lines().collect(Collectors.toList()));
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * <b>Privado</b>
	 * <p>
	 * Devuelve una línea deseada dentro del fichero ubicado en la ruta "dir".
	 * <p>
	 * Si no existe la línea, devolverá: <b>No hay línea en esa posición.</b>
	 * 
	 * @throws IOException
	 * @param dir
	 *            -Ruta del archivo-
	 * @param i
	 *            -Posición de la línea respecto al archivo.-
	 * @return String -La linea deseada o la frase por defecto.-
	 * @since 1.0
	 */
	private static String leeLineaFicheroExistente(Path dir, int i) {
		String res = "";

		try {
			BufferedReader br = Files.newBufferedReader(dir);
			List<String> l = br.lines().collect(Collectors.toList());
			if (i > l.size() - 1 || i < 0) {
				res += "No hay línea en esa posición.";
			} else {
				res += l.get(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * <b>Privado</b>
	 * <p>
	 * Crea un nuevo archivo en la ruta "dir"
	 * 
	 * @throws IOException
	 * @param dir
	 *            -Ruta donde crear el fichero.-
	 * @return boolean -<i>true</i> si la operación es correcta; <i>false</i> si no
	 *         lo es.-
	 * @since 1.0
	 */
	private static boolean creaFichero(Path dir) {
		boolean res = false;

		try {
			Files.createFile(dir);
			res = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * <b>Privado</b>
	 * <p>
	 * Lee el fichero existente y devuelve en una linea todo el fichero, separado
	 * por saltos.
	 * 
	 * @param dir
	 *            -Ruta donde leer el fichero.-
	 * @return String -Fichero transformado.-
	 * @throws IOException
	 * @since 1.0
	 */
	private static String leeFicheroExistente(Path dir) {
		String res = "";
		try {
			BufferedReader bf = Files.newBufferedReader(dir);
			List<String> data = bf.lines().collect(Collectors.toList());
			for (String s : data) {
				res += s + "\n";
			}
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * <p>
	 * Escribe en el fichero marcado por "path" la información "data".
	 * <p>
	 * Si el archivo ya tenía información <b>NO</b> la sobreescibe, si no que
	 * escribe a continuación de la última línea.
	 * <p>
	 * Escribirá el texto en formato <i>UTF-8</i>.
	 * <p>
	 * El fichero <b>TIENE</b> que existir.
	 * 
	 * @param dir
	 *            -Ruta del archivo.-
	 * @param data
	 *            -Información a grabar.-
	 * @return boolean -<i>true</i> si la operación es correcta; <i>false</i> si no
	 *         lo es.-
	 */
	private static boolean escribeFicheroExistente(Path dir, String data) {
		boolean res = false;

		try {
			BufferedWriter bw = Files.newBufferedWriter(dir, StandardCharsets.UTF_8);
			bw.write(data);
			bw.flush();
			System.out.println("He escrito: " + data);
			res = true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

}
