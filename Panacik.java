/**
 * Trieda Panacik, kde sa prideľujú panáčikom ich vlastnosti ako farba, obrázok a ID.
 * 
 * @author Timea Funtíková
 * @version 1.0
 */
public class Panacik {
    private FarbaHraca farba;
    private Obrazok obrazokPanacika;
    private int idPanacika;
    private int idHraca;
    private int poziciaX;
    private int poziciaY;
    //private HraciaPlocha hraciaPlocha;
    private static final int ROZMER_STVORCEKA = 60; //pixelov
    private int pocetPrejdenychPolicok = 0;
    private static final int MAX_POCET_POLICOK_DRAHY = 40;
    private StavPanacika stav;
    
     /**
     * Parametrický konštruktor vytvorí Panáčika s preddefinovanou farbou, vlastným ID a nastaví mu vlastnosť obrázka, ktorá je zhodná s jeho farbou. 
     * 
     * @param idPanacika pripíše Panácikovi jeho jedinečné ID od 1 - 4.
     * @param idHraca pripíše Hracovi jeho jedinečné ID od 1 - 4.
     * @param farba nastaví farbu hráčovi zo zoznamu definovaných farieb v triede FarbaPanačika.
     */
    public Panacik(int idPanacika, int idHraca, FarbaHraca farba) {
        this.farba = farba;
        this.idHraca = idHraca;
        this.idPanacika = idPanacika;
        this.obrazokPanacika = this.nastavObrazok();
        this.stav = StavPanacika.NEDEFINOVANY;
    }
    
    /**
     * (Panacik) Getter na ID Hráča.
     */
    public int getIDHraca() {
        return this.idHraca;
    }
  
    /**
     * (Panacik) Getter na počet prejdených políčok.
     * 
     */
    public int getPocetPrejdenychPolicok() {
        return this.pocetPrejdenychPolicok;
    }
    
    /**
     * (Panacik) Getter na ID Panáčika.
     */
    public int getIDPanacika() {
        return this.idPanacika;
    }
   
   /**
     * (Panáčik) Zobrazí sa.
     */
    private void zobrazObrazokPanacika() {
        this.obrazokPanacika.zobraz();
    }
   
   /**
     * (Panacik) Getter na x-ovú pozíciu panáčika.
     */
    public int getPoziciaX() {
        return this.poziciaX;
    }
    
    /**
     * (Panacik) Getter na y-ovú pozíciu panáčika.
     */
    public int getPoziciaY() {
        return this.poziciaY;
    }
     
    /**
     * (Panacik) Setter na x-ovú pozíciu panáčika.
     */
    public void setPoziciaX(int value) {
        this.poziciaX = value;
    }
    
    /**
     * (Panacik) Setter na y-ovú pozíciu panáčika.
     */
    public void setPoziciaY(int value) {
        this.poziciaY = value;
    }
    
    /**
     * (Panacik) Zisťuje, či sa panáčik dostal na koniec dráhy, PRED 
     * štartovacie políško.
     */
    public boolean jeNaKonci() {
        if (this.pocetPrejdenychPolicok < MAX_POCET_POLICOK_DRAHY - 1) {
            return false;
        }
        return true;
    }
    
    /**
     * (Panacik) Určuje pomyselné rozheranie, od ktorého už zisťuje, či je možné 
     * aby sa po hode kockou panáčik dostal do domčeka.
     * 
     * @param pocetNaKocke zisťuje číslo hodené kockou. 
     */
    public boolean jePredCielom(int pocetNaKocke) {
        if (this.pocetPrejdenychPolicok + pocetNaKocke < MAX_POCET_POLICOK_DRAHY - 1) {
            return false;
        }
        return true;
    }
     ////
     
    /**
     * (Panacik) Pripočíta prejdené políčka do metódy pocetPrejdenychPolicok()
     * o počet, ktorý udáva číslo na kocke. 
     * 
     * @param pocetNaKocke zisťuje číslo hodené kockou. 
     */
    public boolean pripocitajPrejdenePolicka(int pocetNaKocke) { 
        int poziciaVcieli = 4 - idPanacika;
        if (this.pocetPrejdenychPolicok < MAX_POCET_POLICOK_DRAHY + poziciaVcieli) {
            this.pocetPrejdenychPolicok += pocetNaKocke;
            return true;
        }
        return false;
    }
    
    /**
     * (Panacik) Nastaví panáčika na domovskú pozíciu v bloku a zároveň mu 
     * pridelí stav z enumu. 
     * 
     * @param poziciaX si vypýta pozíciu na x-ovej osi.
     * @param poziciaY si vypýta pozíciu na y-ovej osi.
     */
    public void nastavPanacikaNaDomovskuPoziciu(int poziciaX, int poziciaY) {
        this.nastavPanacikaNaPoziciu(poziciaX, poziciaY);
        this.stav = StavPanacika.JE_DOMA;
    }
    
    public void nastavPanacikaNaNovuPoziciu(int poziciaX, int poziciaY) {
        this.nastavPanacikaNaPoziciu(poziciaX, poziciaY);
        this.stav = StavPanacika.JE_NA_DRAHE;;
    }
    
    /**
     * (Panacik) Nastaví panáčika na štartovaciu pozíciu a zároveň mu 
     * pridelí stav z enumu. 
     * 
     * @param poziciaX si vypýta pozíciu na x-ovej osi.
     * @param poziciaY si vypýta pozíciu na y-ovej osi.
     */
   public void nastavPanacikaNaStartovaciuPoziciu(int poziciaX, int poziciaY) {
        this.nastavPanacikaNaPoziciu(poziciaX, poziciaY);
        this.stav = StavPanacika.JE_NA_STARTE;
    }
    
    /**
     * (Panacik) Nastaví panáčika na cieľovú pozíciu a zároveň mu 
     * pridelí stav z enumu. 
     * 
     * @param poziciaX si vypýta pozíciu na x-ovej osi.
     * @param poziciaY si vypýta pozíciu na y-ovej osi.
     */
    public void nastavPanacikaNaCielovuPoziciu(int poziciaX, int poziciaY) {
        this.nastavPanacikaNaPoziciu(poziciaX, poziciaY);
        this.stav = StavPanacika.JE_V_CIELI;
    }
    
    /**
     * (Panacik) Nastavi pozíciu x, y.
     * 
     * @param poziciaX nastaví pozíciu na x-ovej osi.
     * @param poziciaY nastaví pozíciu na y-ovej osi.
     * 
     */
    private void nastavPoziciu(int poziciaX, int poziciaY) {
        this.poziciaX = poziciaX;
        this.poziciaY = poziciaY;
    }
    
    /**
     * (Panacik) Nastavi na stanovenú pozíciu.
     * 
     * @param poziciaX si vypýta pozíciu na x-ovej osi.
     * @param poziciaY si vypýta pozíciu na y-ovej osi.
     */
    private void nastavPanacikaNaPoziciu(int poziciaX, int poziciaY) {
        this.nastavPoziciu(poziciaX, poziciaY);
        if (this.obrazokPanacika != null) {
            this.obrazokPanacika.zmenPolohu(this.pocetPixelovStreduObrazku(this.poziciaX), 
                this.pocetPixelovStreduObrazku(this.poziciaY));
            this.obrazokPanacika.zobraz();
        }
    }
    
    /**
     * (Panacik) Posunie panáčika vodorovne alebo zvislo. 
     * 
     * @param posunVodorovne sa využije pri posúvaní panáčika po dráhe.
     * @param posunZvislo sa využije pri posúvaní panáčika po dráhe.
     */
    public void posunPanacika(int posunVodorovne, int posunZvislo) {
        int novaPoziciaX = this.poziciaX + posunVodorovne; 
        int novaPoziciaY = this.poziciaY + posunZvislo;
        this.nastavPanacikaNaPoziciu(novaPoziciaX, novaPoziciaY );
    }
    
    /**
     * (Panacik) Nastaví vlastnosť Obrázka podľa preddefinovanej farby. 
     */
    private Obrazok nastavObrazok() {
        if (this.farba != null) {
            switch (this.farba) {
                case ZELENA:
                    return new Obrazok ("pics/zelena.png");
                  
                case CERVENA:
                    return new Obrazok ("pics/cervena.png");
                  
                case MODRA:
                    return new Obrazok ("pics/modra.png");
                   
                case ORANZOVA:
                    return new Obrazok ("pics/oranzova.png");
                default: System.out.println("Nedefinované.");
            }
        }
        return null;
    }
   
     /**
     * (Panacik) Nastaví obrázok tak, aby sa na políčku zobrazil celý a nie iba od svojho stredu.
     * 
     * @param pozicia pracuje s aktuálnou pozíciou objektu.
     */
    private int pocetPixelovStreduObrazku(int pozicia) {
        return pozicia * ROZMER_STVORCEKA + 40;
    }
    
    /**
     * (Panacik) Skryje obrázok Panáčika. 
     * 
     */
    public void zmazPanacika() {
        this.obrazokPanacika.skry();
    }
    
    /**
     * (Panacik) Slúži na výpis stavu. 
     */
    public String toString() {
        String result = "panáčik č. " + this.idPanacika + " (X=" + this.poziciaX + ", Y=" + this.poziciaY + ")";
        switch (this.stav) {
            case JE_NA_STARTE: 
                result += " je na štarte..." ;
                break;
            case JE_V_CIELI:
                result += " je v cieli !" ;
                break;
            default:
                if (this.pocetPrejdenychPolicok > 0) { 
                    result += " prešiel " + this.pocetPrejdenychPolicok + " políčok";
                }
        }
        return result;
    }
}

