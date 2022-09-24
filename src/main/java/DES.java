/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author alumno
 * 
 * Vamos a poregramar bajo un modo de cifrado por boques de 64 bits vamos a manerjar una llave privada de 64 bits 
 * Se debe tomar para la inicializacion de la llave 56 bit
 * formar 16 subllaves 
 * las tablasde permutacion y cajas de sustitucion
 * se van a cargar por parte del proveedor de servicion para poder hacer todo esto vamos a usar librerias
 * crypto y security
 */
import java.security.*;
//es para poder generar las claves y subllaves
import javax.crypto.spec.*;
import java.io.*;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
public class DES {
    public static void main(String[] args)throws Exception{
    //vamos a comprobar la entrada de un archivo o fichero para cifrar 
    if(args.length !=1){
    //no hay archivos cargados 
    mensajeAyuda();
    System.exit(1);
    //Lo primero que tenemos que hacer es cargar una instancia del proveedor del tipo de cifrado, para eso esta la parte de las librerias
    System.out.println("1.-Generar la clave DES: ");
    //vamos a ocupar la clase KeyGenerator
    KeyGenerator generadorDES = KeyGenerator.getInstance("DES");
    //inicializamos la llave 
    generadorDES.init(56);
    //Vamos a generar las claves
    SecretKey clave = generadorDES.generateKey();
    //Estamos creando las 16 subllaves
    System.out.println("La clave es; "+clave);
    /*Recordemos que en la criptografiamoderna, solo se ocupan bitso bytes
    */
    //la llave que se ha generado no tiene formato 
    mostrarBytes(clave.getEncoded());
    System.out.println("");
    /*vamos a cifrar para ello debemos de aplicar los estandares vistos en clase
    PKCS para ello tenemos que crear una instancia de del algoritmo DES en modo de cifrado
    
    ALGORITMO DES
    MODO ECB (ELECTRONIC CODE BOOK)
    RELLENO PCKS5
    */
    System.out.println("2.-Cifrar con DES y el archivo ; "
            + args[0]+", dejar el resultado en: "+
            args[0]+".cifrado");
    //instancia
    Cipher cifrado = Cipher.getInstance("DES/ECB/PKCS5Padding");
    cifrado.init(Cipher.ENCRYPT_MODE, clave);
    //el problema es como vamos a leer los bloques del mensaje
    
    byte [] buffer=new byte[1000];
    byte [] bufferCifrado;
    //generamos los archivos 
    FileInputStream in = new FileInputStream(args[0]);
    FileOutputStream out = new FileOutputStream(args[0]+".cifrado");
    //lectura
    int bytesleidos = in.read(buffer,0, 1000);
    while(bytesleidos != -1){
    //pasar el texto claso leido al cifrador
    bufferCifrado = cifrado.update(buffer,0,bytesleidos);
    //generar la salida
    out.write(bufferCifrado);
    bytesleidos = in.read(buffer, 0, 1000);
    }
    //vamos a reunir todos los bloques
    bufferCifrado = cifrado.doFinal();
    out.write(bufferCifrado);
    in.close();
    out.close();
    }
    }

    private static void mensajeAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void mostrarBytes(byte[] encoded) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
