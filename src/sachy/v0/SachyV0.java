/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachy.v0;
import java.util.Scanner; 
 /**
 *
 * @author Jan Pribyl, Štefan Pilát
 */
public class SachyV0 {
    
    public static void main(String[] args) throws CloneNotSupportedException {
                    
        Hra_X hra = new Hra_X((byte)8,(byte)8,(byte)1,(byte)1);
        hra.execute();
        
        Hra_X hra_klon = (Hra_X)hra.clone();
        hra_klon.execute();
    }
   
   
}
    
   

