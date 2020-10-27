/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sachy.v0;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random; 


/**
 *
 * @author Jan Přibyl, Štefan Pilát
 */
public class Hra_X implements Cloneable {

    private byte size_x;
    private byte size_y;
    private byte actual_x;
    private byte actual_y;
    private boolean end = false;
    private int interval = 500; // interval automaticeho chodu v ms

    public Hra_X(byte size_x, byte size_y, byte actual_x, byte actual_y) {
        this.size_x = size_x;
        this.size_y = size_y;
        this.actual_x = actual_x;
        this.actual_y = actual_y;
    }

   
    public void execute() throws IOException {
        clearScreen();
        System.out.println("***Vítej ve hře***");
        wait(1500);
        char druh_pohybu;        
        
        do{
            clearScreen();   
            System.out.println("Zadej druh pohybu (A - automatický, M - manuální) nebo K pro konec:");
            Scanner vstup = new Scanner(System.in, "Windows-1250");       
            druh_pohybu = vstup.next().charAt(0);
            druh_pohybu = Character.toLowerCase(druh_pohybu);
        
            while (druh_pohybu == 'm' && this.end == false) {
                clearScreen();
                tisk_sachovnice(this.size_x, this.size_y, this.actual_x, this.actual_y); 
                move();            
            }
            this.end = false;
        
            while (druh_pohybu == 'a' && System.in.available() == 0) {
                clearScreen();
                System.out.println("Stiskni Enter pro zastaveni");
                tisk_sachovnice(this.size_x, this.size_y, this.actual_x, this.actual_y);            
                autoplay();          
            }        
        }while(druh_pohybu != 'k');
        
        if(is_end() == true){
            System.out.println("Nashledanou");
            System.exit(0);
        }
        else
            execute();
        
    }

    private void clearScreen() {
        for (int clear = 0; clear < 1000; clear++) {
            System.out.println("\b");

        }
    }

    private void tisk_sachovnice(byte a, byte b, byte c, byte d) {

        System.out.print("     ");
        for (int i = 1; i < a + 1; i++) {
            System.out.print(i);
            System.out.print("   ");
        }
        for (int i = 1; i < b + 1; i++) {
            for (int j = 1; j < a + 1; j++) {
                if (j == 1) {
                    System.out.println();
                    System.out.print(" " + Character.toString((char) (i + 64)) + " ");
                }
                if (i == d && j == c) {
                    System.out.print(" [*]");
                } else {
                    System.out.print(" [ ]");
                }
            }
        }
        System.out.println();
    }

    private boolean can_left() {
        if (this.actual_x > 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean can_right() {
        if (this.actual_x < this.size_x) {
            return true;
        } else {
            return false;
        }
    }

    private boolean can_up() {
        if (this.actual_y > 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean can_down() {
        if (this.actual_y < this.size_y) {
            return true;
        } else {
            return false;
        }
    }
    
    private void autoplay(){
    Random rand = new Random();
    int rand_int = rand.nextInt(4);
    char pohyb;
    
    if(rand_int == 0){
        pohyb = 'l';
        if (can_left() == true) {
            this.actual_x--;
            wait(interval);
            return;
        }
        else{ 
            return;
        }
    }
    
    if(rand_int == 1){
        pohyb = 'p';
        if (can_right() == true) {
            this.actual_x++;
            wait(interval);
            return;
        } 
        else {
            return;
        }
    }
    
    if(rand_int == 2){
        pohyb = 'd';
        if (can_down() == true) {
            this.actual_y++;
            wait(interval);
            return;
        } 
        else {
            return;
        }
    }
    
    if(rand_int == 3){
        pohyb = 'n';
        if (can_up() == true) {
            this.actual_y--;
            wait(interval);
            return;
        }
        else {
            return;
        }
    }
    return;
    }
    
    private void wait(int cas){
    try
{
    Thread.sleep(cas);
}
catch(InterruptedException ex)
{
    Thread.currentThread().interrupt();
}
    }
    
    private boolean can_move(char direction) {
        switch (direction) {
            case ('l'):
                if (can_left() == true) {
                    this.actual_x--;
                    return true;
                } else {
                    return false;
                }
            case ('p'):
                if (can_right() == true) {
                    this.actual_x++;
                    return true;
                } else {
                    return false;
                }
            case ('n'):
                if (can_up() == true) {
                    this.actual_y--;
                    return true;
                } else {
                    return false;
                }
            case ('d'):
                if (can_down() == true) {
                    this.actual_y++;
                    return true;
                } else {
                    return false;
                }
            case ('x'):
                this.end = true;
                return true;
            default:
                return false;
        }
       
    }

    private char nacti_direction() {
        Scanner sc = new Scanner(System.in, "Windows-1250");
        char c;
        do {
            System.out.println("Zadej směr pohybu (L - levo, P - pravo, D - dolů, N - nahoru, X pro návrat do menu):");
            c = sc.next().charAt(0);
            c = Character.toLowerCase(c);
        } while (c != 'l' && c != 'p' && c != 'n' && c != 'd' && c != 'x');
        return c;
    }

    boolean move() {
        if (can_move(nacti_direction()) == true) {
            return true;
            
        } else {
            System.out.println("!!!Chyba, pohyb mimo šachovnici!!!");
            wait(1000);
            return false;
        }
    }

    boolean is_end() {
        
            System.out.print("Opravdu ukončit ? (A/N):");
            Scanner sc = new Scanner(System.in, "Windows-1250");            
            char c;
            do {
                c = sc.next().charAt(0);
                c = Character.toLowerCase(c);
            } while (c != 'a' && c != 'n');
            if (c == 'a') {
                return true;
            } else {
                this.end = false;
                return false;
            }
        
    }          
    

    public Object clone() throws CloneNotSupportedException {
        Hra_X hra = (Hra_X) super.clone();
        return hra;
    }

}
