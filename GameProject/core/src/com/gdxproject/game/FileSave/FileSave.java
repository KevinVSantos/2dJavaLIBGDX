
package com.gdxproject.game.FileSave;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Classe respons�vel por manipular arquivos de pesistencia de dados.
 * @param <T> Gen�rico.
 */
public class FileSave<T> {
	
	/**
	 * M�todo respons�vel por ler o arquivo bin�rio.
	 */
    public T ReadBin(String path){
        
        T ret = null;
        
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
            ret = (T)in.readObject();
            in.close();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            return ret;
        } catch (IOException e) {
           return ret;
        }finally{
            return ret;
        }
    }
    
    /**
     * M�todo respons�vel por escrever o arquivo bin�rio.
     */
    public void WriteBin(String path, T obj){
        try {
           
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            
            out.writeObject(obj);
            
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
