//import java.util.Random; 

/**
 * Trieda Kocka náhodne generuje čísla od 1 po 6.
 * 
 * @author Timea Funtíková
 * @version 1.0
 */
public class Kocka {
    private static final int POCET_STRAN_KOCKY = 6;
    private int hodeneCislo;
    /**
     * Konštruktor triedy Kocka.
     */
    public Kocka() {
    }
    
    /**
     * (Kocka) Pomocou funkcie volanej z knižnice vygeneruje náhodné číslo od 1 po 6.
     */
    public void hodKockou() {
        int hod = this.generujHod();
        this.hodeneCislo = hod;
    }
    
    /**
     * (Kocka) Vynuluje kocku.
     */
    public void vynulujKocku() {
        this.hodeneCislo = 0;
    }
    
    /**
     * (Kocka) Náhodne vygenerje číslo od 1 - 6.
     */
    private int generujHod() {
        //https://stackoverflow.com/questions/24562741/how-do-i-have-a-random-int-from-1-to-6-using-math-random-in-java
        return (int)(Math.random() * POCET_STRAN_KOCKY) + 1;
    
        //zdroj z: https://www.freecodecamp.org/news/generate-random-numbers-java/
        //Random rand = new Random();
        //return rand.nextInt(POCET_STRAN_KOCKY+1); 
    }
    
    /**
     * (Kocka) Getter na hodené číslo. 
     */
    public int getHodeneCislo() {
        return this.hodeneCislo;
    }
}

