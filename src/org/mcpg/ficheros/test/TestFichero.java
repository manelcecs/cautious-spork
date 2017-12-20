package org.mcpg.ficheros.test;

import java.util.ArrayList;
import java.util.List;

import org.mcpg.ficheros.UtilFicheros;

public class TestFichero {

	public static void main(String[] args) {
		
		String path = "././Ficheros/test03.txt";
		String texto = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras interdum odio non ultricies luctus. Fusce eleifend risus vitae nibh euismod, et facilisis massa ultricies. Ut elementum eros vel arcu aliquet sollicitudin. Duis lectus sapien, placerat finibus pellentesque nec, molestie a purus. Morbi aliquam dui auctor dolor porttitor ornare. Donec sed sagittis orci. Proin suscipit erat quis pellentesque sagittis. ";
		UtilFicheros.escribeFicheroCompletoComun(path, texto);

	}

}
