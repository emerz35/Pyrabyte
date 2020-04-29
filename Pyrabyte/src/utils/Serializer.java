/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.*;

/**
 *
 * @author Adam Whittaker
 */
public class Serializer{
    
    
    private Serializer(){
    }
    
    
    public static byte[] toByteArray(Object message){
        try{
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
    
            out.writeObject(message);
            out.flush();
            return byteOut.toByteArray();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static Object fromByteArray(byte[] ary){
        ByteArrayInputStream byteIn = new ByteArrayInputStream(ary);
        try(ObjectInputStream in = new ObjectInputStream(byteIn)){
            
            return in.readObject();
            
        }catch(ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
        return null;
    }
  
}
