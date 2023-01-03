/**
 * Trieda Hrac poskytuje rozhranie pre hráča a môžnosti ako uskutočniť svoj ťah. Patria jej panáčikovia.
 * 
 * @author Timea Funtíková
 * @version 1.0
 */
public class Hrac {
    private int idHraca;
    private static final int POCET_PANACIKOV = 4;
    private FarbaHraca farbaHraca;
    private Panacik[] panaciky;
    private int idAktualnyPanacik;
    private boolean maPanacikaNaDrahe;
    private boolean maVsetkyPanacikyVCieli;
    private int pocetHodovKocky;
    
    /**
     * Parametrický konštruktor triedy Hrac.
     * 
     * @param id dáva každému hráčovi jedinečené ID od 1 po 4.
     * @param farba udáva panáčikom ich preddefinovanu farbu typu FarbaHraca.
     *
     */
    public Hrac(int id, FarbaHraca farba) {
        this.idHraca = id;
        this.farbaHraca = farba;
        this.maPanacikaNaDrahe = false;
        this.maVsetkyPanacikyVCieli = false;
        this.nacitajPanacikovHraca();   
        this.pocetHodovKocky = 0;
    }
    
    /**
     * (Hrac) Getter na ID hráča.
     */
    public int getIDHraca() {
        return this.idHraca;
    }
    
     /**
     * (Hrac) Getter na zistenie, či hráč prešiel dráhu. 
     * 
     * @param panacik pracuje s objektom Panacika
     */
    public boolean presielDrahu(Panacik panacik) {
        return panacik.jeNaKonci();
    }
    
    /**
     * (Hrac) Getter na zistenie, či má hráč panáčika na dráhe.
     */
    public boolean getMaPanacikaNaDrahe() {
        return this.maPanacikaNaDrahe;
    }
    
    /**
     * (Hrac) Getter na zistenie, či už má hráč všetkých panáčikov v domčeku.
     */
    public boolean getMaVsetkyPanacikyVCieli() {
        return this.maVsetkyPanacikyVCieli;
    }
    
    /**
     * (Hrac) Getter na počet hodov kocky.
     */
    public int getPocetHodovKocky() {
        return this.pocetHodovKocky;
    }
    
     /**
     * (Hrac) Getter na farbu, ktorou Hráč predvolene disponuje.
     */
    public FarbaHraca getFarbaHraca() {
        return this.farbaHraca;
    }
    
    
    /**
     * (Hrac) Počet pokusov hodu kockou sa každým hodov zvyšuje.
     */
    public void hodilKocku() {  
        this.pocetHodovKocky++;
    }
    
    /**
     * (Hrac) Vyuluje počet hodov kocky.
     */
    public void vynulujPocetHodovKocky() {  
        this.pocetHodovKocky = 0;
    }
    
    /**
     * (Hrac) Vypíše aktuálne aktívneho hráča.
     * 
     * @param idPanacika vypýta si idPanacika od aktuálne zvoleného Panáčika. 
     */
    public int dajIndexPanacika(int idPanacika) {
        for (int i = 0; i < this.panaciky.length; i++) {
            if (this.panaciky[i].getIDPanacika() == idPanacika) {
                return i;
            }
        }
        return -1; //ak sa ukáže -1 tak je čosi zle
    }
    
    /**
     * (Hrac) Vypíše aktuálne aktívneho panáčika.
     */
    public Panacik dajAktualnehoPanacika() {
        int index = this.dajIndexPanacika(this.idAktualnyPanacik);
        if (this.panaciky != null) {
            if (index > (-1) && index < this.panaciky.length) {
                return this.panaciky[index];
            } 
        }
        return null;
    }
    
    /**
     * (Hrac) Posunie panáčikom o počet, ktorý udáva číslo na kocke.
     * 
     * @param panacik pracuje s objektom Panacika
     * @param cisloHoduKocky zisťuje číslo na kocke
     */
    public void posunPanacika(Panacik panacik, int cisloHoduKocky) {
        if (panacik != null) {   
            if (panacik.pripocitajPrejdenePolicka(cisloHoduKocky)) {
                for (int i = 0; i < cisloHoduKocky; i++) {
                    int x = panacik.getPoziciaX();
                    int y = panacik.getPoziciaY();   
                    int posunVodorovne = 0; 
                    int posunZvislo = 0;
                    //vodorovne pohyby:
                    if (y == 4 && (
                        (x >= 0 && x < 4 ) ||
                        (x >= 6 && x < 10 ))) {
                        posunVodorovne = 1; 
                    }
                    
                    if (y == 6 && (  
                        (x > 0 && x <= 4 ) ||
                        (x > 6 && x <= 10))) { 
                        posunVodorovne = -1; 
                    }
                    
                    if (y == 0 && x >= 4 && x < 6) {
                        posunVodorovne = 1; 
                    }  
                 
                    if (y == 10 && x > 4 && x <= 6) { 
                        posunVodorovne = -1; 
                    }
                
                    //zvisle pohyby:     
                    if (x == 6 && (
                        (y >= 0 && y < 4 ) ||
                        (y >= 6 && y < 10))) {
                        posunZvislo = 1; 
                    }
                
                    if (x == 4 && (
                        (y > 0 && y <= 4 ) ||
                        (y > 6 && y <= 10 ))) {
                        posunZvislo = -1; 
                    }
                 
                    if (x == 10 && y >= 4 && y < 6) {
                        posunZvislo = 1; 
                    }
                
                    if (x == 0 && y > 4 && y <= 6) {
                        posunZvislo = -1; 
                    }
                    panacik.posunPanacika(posunVodorovne, posunZvislo);
                }
            }
        }
    }
   
    
    /**
     * (Hrac) Nastaví panačika na príslučné štartovacie políčko.
     * 
     * @param panacik pracuje s objektom Panacika
     */
    public void nastavitPanacikaNaStart(Panacik panacik) {
        int x = 0;
        int y = 0;
        if (panacik.getIDHraca() == 1 ) {
            x = 0;
            y = 4;
        }
        if (panacik.getIDHraca() == 2 ) {
            x = 6;
            y = 0;           
        }
        if (panacik.getIDHraca() == 3 ) {
            x = 4;
            y = 10;
        }
        if (panacik.getIDHraca() == 4) {
            x = 10;
            y = 6;
        }
        panacik.nastavPanacikaNaStartovaciuPoziciu(x, y);
        this.maPanacikaNaDrahe = true;
    }
    
    /**
     * (Hrac) Určuje, kedy je panáčik v domčeku.
     * 
     * @param panacik pracuje s objektom Panacika
     */
    public void nastavitPanacikaDoCiela(Panacik panacik) {
        if (panacik != null) {
            int x = 0;
            int y = 0;
            switch (panacik.getIDHraca()) {
                case  1 :
                    x = 5 - panacik.getIDPanacika();
                    y = 5;
                    break;
                case  2 : 
                    x = 5;
                    y = 5 - panacik.getIDPanacika();
                    break;
                case 3 : 
                    x = 5;
                    y = 5 + panacik.getIDPanacika();
                    break;
                case 4 :
                    x = 5 + panacik.getIDPanacika();
                    y = 5;
                    break;
            }
            panacik.nastavPanacikaNaCielovuPoziciu(x, y);
          
            this.maPanacikaNaDrahe = false;
            
            //zoberie dalsieho panacika v poradi
            this.nasledujuciPanacik();
            
            if (this.idAktualnyPanacik == -1) {
                this.maVsetkyPanacikyVCieli = true;
            }
        }
    }
   
    /**
     * (Hrac) Zoberie panáčika, s ktorým sa práve hráč chystá prejsť celú dráhu.
     * Pracuje s id-čkom panáčika, aby hráč nemusel zadávať ručne index panáčika.
     */
    public Panacik zoberPanacika() {
        int index = this.dajIndexPanacika(this.idAktualnyPanacik);
        if (index > -1) {
            return this.panaciky[index];
        }
        return null;
    }
    
    /**
     * (Hrac) Zoberie ďalsieho panáčika v poradí. Hráč ťahá počas prechádzania dráhy 
     * len jedným panáčikom.
     */
    public void nasledujuciPanacik() {
        int maxIndex = this.panaciky.length - 1;
        this.maPanacikaNaDrahe = false;
        if (this.panaciky != null) {
            int idPoslednehoPanacika = this.panaciky[maxIndex].getIDPanacika();
            if (this.idAktualnyPanacik < idPoslednehoPanacika) {
                this.idAktualnyPanacik++;
            } else {
                //prideli sa  opat prvy:
                //this.idAktualnyPanacik = this.panaciky[0].getIDPanacika();
                
                //po poslednom panáčikovi program skoncil:              
                this.idAktualnyPanacik = -1;
            }
        }
    }
    
    /**
     * (Hrac) Vyhodí zlého panáčika muhahahaa.
     * 
     * @param panacik pracuje s objektom Panacika
     */
    public void vyhoditPanacika(Panacik panacik) {
        this.nastavDomovPanacika(panacik);
    }
    
    /**
     * (Hrac) Inicializuje panáčikov pre každého hráča tak, že každému panáčikovi pridelí ID od 1 - 4.
     */
    public void nacitajPanacikovHraca() {
        this.panaciky = new Panacik[POCET_PANACIKOV];
        for (int i = 0; i < POCET_PANACIKOV; i++) {
            int idPanacika = i + 1;
            this.panaciky[i] = new Panacik(idPanacika, this.idHraca, this.farbaHraca);
            this.nastavDomovPanacika(this.panaciky[i]);
        }
        this.idAktualnyPanacik = this.panaciky[0].getIDPanacika();
    }
    
    /**
     * (Hrac) Zavolá metódu na skrytie panáčika z plochy. 
     * 
     */
    public void zmazVsetkychPanacikov() {
        if (this.panaciky != null) {
            for (int i = 0; i < POCET_PANACIKOV; i++) {
                this.panaciky[i].zmazPanacika();
            }
            this.panaciky = null;
        }
        this.maPanacikaNaDrahe = false;
    }
    
    /**
     * (Hrac) Nastaví panáčikov podľa ID a farby na domčeky. 
     * 
     * @param panacik pracuje s objektom Panacika
     */
    public void nastavDomovPanacika(Panacik panacik) {
        int x = 0;
        int y = 0;
        if (panacik != null) {
            if (panacik.getIDPanacika() > 2) {
                y = 1;
            }
            if (panacik.getIDPanacika() == 2 || panacik.getIDPanacika() == 4) {
                x = 1;
            }
            if (panacik.getIDHraca() == 2) {
                x += 9;
            }
            if (panacik.getIDHraca() == 3) {
                y += 9;
            }
            if (panacik.getIDHraca() == 4) {
                x += 9;
                y += 9;
            }
            panacik.nastavPanacikaNaDomovskuPoziciu(x, y); 
            this.maPanacikaNaDrahe = false;
        }
    }
    
    /**
     * (Hrac) Metóda na prehľadný výpis informácie pre hráčov, ktorý je na rade.
     * Zavolá si metódu vratStav() aby vypísala popis aktuálneho hráča a panáčika.
     */
    public String toString() {
        String result = "hráč č. " + this.idHraca + ", s farbou: " + this.farbaHraca.toString() ;
       
        if (this.maVsetkyPanacikyVCieli) {
            result += "\n má všetky panáčiky v cieli !" ;
            result += "\n -------   HURÁ -------";
        } else {
            result +=  " " + this.popisStavuAktualnehoPanacika();
        } 
        return result;
    }
    
     /**
     * (Hrac) Vypýta si od panáčika ID. 
     */
    public String popisStavuAktualnehoPanacika() {
        String popis = " ";
        Panacik panacik = this.zoberPanacika();
        if (panacik != null) {
            popis = panacik.toString();
        } else {
            popis = "panáčik nie je dostupný";
        }
        return popis;
    }
}
