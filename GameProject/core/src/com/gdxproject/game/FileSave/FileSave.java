
package com.gdxproject.game.FileSave;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class FileSave<T> {
    
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
