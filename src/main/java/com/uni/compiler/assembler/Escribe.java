package com.uni.compiler.assembler;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;



/**
 * Clase para la escritura de ficheros de forma sencilla.
 *
 * @author Victor Ant. Torre Villahoz.
 * @version 1.0
 */
public class Escribe {

	private BufferedWriter esc;

	/**
	 * Crea o abre un fichero con la ruta dada.
	 * @param lugar Direccion relativa del ficero.
	 * @throws FileNotFoundException Si se produce un error al crear el nuevo fichero.
	 */
	public Escribe(String lugar) throws FileNotFoundException{
		this.esc = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(lugar)));
	}
	/**
	 * Escribe en un fichero dado.
	 * @param fichero El Stream del fichero.
	 * @throws FileNotFoundException Si no se encuentra el fichero.
	 */
	public Escribe(FileOutputStream fichero) throws FileNotFoundException{
		this.esc = new BufferedWriter(new OutputStreamWriter(fichero));
	}
/*-------------------------------------FUNCIONES DE LA CLASE-------------------------------------*/

	/**
	 * Inserta el texto.
	 * @param txt Texto ha insertar.
	 * @return true Si se escribe correctamente.
	 */
	public boolean esc(String txt){
		try {
			this.esc.write(txt);
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	/**
	 * Inserta el texto y despues un retorno de carro haha.
	 * @param txt Texto ha insertar.
	 * @return true Si se escribe correctamente.
	 */
	public boolean escln(String txt){
		if(this.esc(txt)){
			return this.ln();
		}else{
			return false;
		}
	}
	/**
	 * Insertar un retorno de carro en el fichero.
	 * @return true Si se escribe correctamente.
	 */
	public boolean ln(){
		try {
			this.esc.newLine();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Cierra el fichero inicial(el creado en el constructor) y abre o crea uno
	 * nuevo.
	 *
	 * @param lugar  Direccion relativa del ficero.
	 * @return false si se produce alg??n error.
	 * @throws FileNotFoundException Si se produce un error al crear el nuevo fichero.
	 */
	public boolean crea(String lugar) throws FileNotFoundException{
		if(this.esc!=null){//Si es nulo ya esta cerrado.
			if(this.cierra()){
				this.esc = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(lugar)));
				return true;
			}
		}else{
			this.esc = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(lugar)));
			return true;
		}
		return false;
	}

	/**
	 * Cierra el fichero abierto.
	 * @return true Si se cierra correctamente.
	 */
	public boolean cierra(){
		try{//Intento borrar el esc
			if(this.esc != null){
				this.esc.close();
				this.esc = null;
			}
			return true;
		}catch(IOException ficCloseAnt){//Si falla lo pongo a nulo.
			return false;
		}
	}


}
