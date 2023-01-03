import javax.swing.JOptionPane;
/**
 * V triede HraClovece, ktorá reprezentuje kompozíciu všetkých hlavných metód, sa spustí nová hra.
 * Vykreslí sa hracia plocha.
 * 
 * Pri zavolaní metódy nováHra() je možné nastaviť počet hráčov od 2-4 a spustiť novú hru.
 * @author Timea Funtíková
 * 
 * @version 1.0
 */

public class HraClovece {
    private HraClovece hraClovece;
    private HraciaPlocha hraciaPlocha;
    private Kocka kocka;
    private Hrac[] hraci;
    private Hrac aktualnyHrac;
    private int pocetHracov;
    private static final int MAX_POCET_HRACOV = 4;
    private boolean hraPrebieha;
    //metody posunVodorovne a posunZvisle sa INAK VYKRESLUJU NA CANVASEE!!! >:(
    
    /**
     * Konstruktor HryClovece vytvori Hraciu plochu a načíta hráčov.
     *
     * @param zadajPocetHracov umožnuje zadať počet hráčov od 2 - 4. 
     */
    public HraClovece() {
        this.hraciaPlocha = new HraciaPlocha();
        this.hraciaPlocha.nacitajHraciuPlochu();
        this.novaHra();
    }
    
     /**
     * (HraClovece) Podľa zadaného počtu hráčov sa nastavia panáčiky do svojich domčekov. Vytvoria sa hráči, ktorým patria panáčikovia.
     * 
     * @param zadajPocetHracov umožňuje hráčovi zvoliť počet hráčov od 2 - 4.
     */
    public void novaHra() {
       this.clear();
       this.nastavPocetHracov();
       while (this.pocetHracov < 1 || this.pocetHracov > MAX_POCET_HRACOV) {
          this.nastavPocetHracov();
        } 
        zacniHru();
    }
    
    
    private void nastavPocetHracov()
    {
        //https://mkyong.com/swing/java-swing-joptionpane-showinputdialog-example/
        Integer[] options = {2, 3, 4};
        int zadanyPocetInt = (Integer)JOptionPane.showInputDialog(null, "Zadajte počet hráčov :", 
                "Hráči", JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
       
        if (zadanyPocetInt > 0){
            this.pocetHracov = zadanyPocetInt;
        }
    }
       
    private void zacniHru(){
           this.kocka = new Kocka();
           hraPrebieha = true;
           this.nacitajHracov();
           this.vypisStav("ZAČALA SA NOVÁ HRA:", this.aktualnyHrac);
           this.pokracovatHodomKocky();
    }
    
     private void koniecHry()
    {
         if (hraPrebieha) {
           JOptionPane.showMessageDialog(null, "Koniec hry.");
           System.out.println("--- koniec programu ---");
            this.hraPrebieha = false;
           //this.clear();  // -- toto by vymazalo panacikov z plochy
        }
    }
    
     /**
     * (HraClovece) Hod kockou. Metóda v seba zavolá dôležité metódy na získanie náhodného čísla od 1 - 6 a zároveň metódu tahHraca().
     * 
     */
    public void hodKocku() {
        if (hraPrebieha) {
       int  moznost = JOptionPane.showConfirmDialog(null, "Hodiť kocku");
      
       switch (moznost){
       case 0: //stlacene yes             
        if (this.kocka != null) {
            this.kocka.hodKockou(); 
            this.tahHraca(this.kocka.getHodeneCislo()); 
        }
        break;
        
        case 1: //stlacene nie             
               break;
        case 2: //stlacene cancel   
               this.koniecHry();
               break;
        }
    }
    else{
         JOptionPane.showMessageDialog(null, "Hra nebola spustená.");
    }
    }
    
    private void pokracovatHodomKocky()
    {    if (hraPrebieha) {
            hodKocku();  
        }
    }
    
    
    /**
     * (HraClovece) Zavolá metódu na skrytie obrázka.
     * Vymaže prednastavených panačikov z minulej hry aby sa znova neukázali v ďalšej hre. 
     * 
     */
    private void clear() {
        if (this.hraci != null) {
            for (int i = 0; i < this.hraci.length; i++) {
                this.hraci[i].zmazVsetkychPanacikov();
            }
            this.hraci = null;
        } 
        this.aktualnyHrac = null;
        this.pocetHracov = 0;
    }
    //upravované, zistiť akú chybu vypisujke
     /**
     * (HraClovece) Nastaví index hráča tak, aby bol zhodný s jeho ID.
     * 
     * @param idHraca služi na to, aby sme dokázali índex meniť s meniacim sa aktuálnym hráčom.
     * 
     */
    private int dajIndexHraca(int idHraca) {
        if (this.hraci != null && this.hraci.length > 0) {
            for (int i = 0; i < this.hraci.length; i++) {
                if (this.hraci[i].getIDHraca() == idHraca) {
                    return i;
                }                
            }
        }
        return -1;
    }
    
      /**
     * (HraClovece) Keď má niektorý z hráčov všetkých panáčikov v domčeku, hra pokračuje ďalej, aby 
     * aj ostatní hráči mali možnosť prísť do cieľa. Pokiaľ ešte všetkých panáčikov nikto v domčeku nemá,
     * hra pokračuje ďalej. dajVolnehoHraca == je hráč, ktorý môže robiť ťah hneď po tom hráčovi, ktorý už má všetkých hráčov v domčeku.
     *
     */
    private Hrac dajVolnehoHraca() {
        Hrac hrac = this.aktualnyHrac; 
        if (hrac != null) {
            if (hrac.getMaVsetkyPanacikyVCieli()) {
                //pokracuj nasledovnym hracom v poradi
                this.nasledujuciHrac();
                return this.dajVolnehoHraca();
            } else {
                return hrac;
            }
        }
        return null;
    }
    
    /**
     * (HraClovece) Metóda na vypísanie textu.
     * 
     * @param nastavText ako lokálna premenná, kam sa ukladá text, ktorý sa vyhodnocuje podľa (ne)splenenej podmienky. 
     * @param hrac ako vstup pre výpis, kedy sa panáčik vyhodí. 
     * 
     */
    private void vypisStav(String nastavText, Hrac hrac) {
        String textSpravy = nastavText;
        if (textSpravy != null) {
            textSpravy += "\n";
        }
        
        if (this.kocka != null && this.kocka.getHodeneCislo() > 0) { 
            textSpravy += "-> hodená kocka: " + this.kocka.getHodeneCislo();
        }
        
        if (hrac == null ) {
            hrac = this.aktualnyHrac ;
        }
        
        if (hrac != null) {
            textSpravy += "..." + hrac.toString();
        } else {
            textSpravy += "...Hráč nie definovaný!!!";
        }
        System.out.println(textSpravy);
    }
    
    //
    /**
     * (HraClovece) Vyberie ďalšieho hráča zo zoznamu a aktivuje ho tak, aby bol na ťahu.
     * 
     */
    private void nasledujuciHrac() {
        int maxIndex = this.hraci.length - 1;
        int idAktualnyHrac = -1; //schválne neexistujúce id, keby bola chyba tak je tu
        if (this.hraci != null) {
            int idPoslednehoHraca = this.hraci[maxIndex].getIDHraca();
            if (this.aktualnyHrac != null) {
                idAktualnyHrac = this.aktualnyHrac.getIDHraca();
            }
            int idx;
            if (idAktualnyHrac < idPoslednehoHraca) {
                idAktualnyHrac++;
                idx = this.dajIndexHraca(idAktualnyHrac);
            } else {
                idx = 0;  // id prvého hráča
            }
            this.aktualnyHrac = this.hraci[idx];
            this.vypisStav("NASTAVENIE NASLEDUJUCEHO HRACA:", this.aktualnyHrac);
        } 
    }
    
    /**
     * (HraClovece) Každému hráčovi pridelí jeho skupinu štyroch panáčikov, z ktorých má každý 
     * svoje jedinečné ID od 1 po 4.
     * Je definovaná pre štyroch hráčov a štyri farby, inak sa nevykoná. 
     * 
     * @param pocetHracov sa načíta zo zoznamu hráčov. 
     */
    private void nacitajHracov() {
        this.hraci = new Hrac[this.pocetHracov];
        FarbaHraca farba = null;
        int idHraca;
        for (int i = 0; i < this.pocetHracov; i++) {
            idHraca = i + 1;
            if (idHraca == 1) {
                farba = FarbaHraca.ZELENA;
            }
            
            if (idHraca == 2) {
                farba = FarbaHraca.MODRA;
            }
            
            if (idHraca == 3) {
                farba = FarbaHraca.CERVENA;
            }
        
            if (idHraca == 4) {
                farba = FarbaHraca.ORANZOVA;
            }
        
            if (idHraca > 0 && farba != null) {
                this.hraci[i] = new Hrac(idHraca, farba);
            }
        }
        //rovno nastavi prveho v poradi ako aktualneho hraca:
        this.aktualnyHrac = this.hraci[0]; 
    }
    
    /**
     * (HraClovece) Úplne najskôr nastaví aktuálneho hráča a panáčika, čím zaistí, že je daný hráč na rade a môže uskutočniť svoj ťah. 
     * Zistí, či hráč už použil metódu hodkockou() a pokiaľ áno, ďalej zisťuje, či pri hode padla 6. Prvé tri razy má hráč 3 pokusy pričom metóda
     * volá inú() ktorá hráčovi ťahy odpočítava. Keď sa mu minú pokusy, nasleduje ďalší hráč. Ak mu počas toho padne 6, nastaví mu panáčika na štart 
     * a hráč opäť hádže kockou. Potom má každý hráč už len 1 pokus na čo najvyšší hod. Zároveň sa nemôže figúrka pohnúť, keď nie je na dráhe -
     * takže si ďalej zisťuje, či je aj táto podmienka splnená. 
     * 
     * @param hodeneCisloNaKocke určuje posun, o koľko sa má figúrka pohnúť. 
     */
    private void tahHraca(int hodeneCisloNaKocke) {
        if (this.aktualnyHrac != null) {
            this.aktualnyHrac.hodilKocku(); // priráta počet hodení kockou
            Panacik panacik = this.aktualnyHrac.zoberPanacika();
            if (panacik != null) {    
                if (this.aktualnyHrac.getMaPanacikaNaDrahe()) { //rozhoduje, či sa panáčik môže pohnúť alebo musí čakať
                     // v tejto fáze môže panačikom ťahať - môže ťahať, ísť do cieľa alebo vyhadzovať, ale sú tu podmienky:
                    if (panacik.jePredCielom(hodeneCisloNaKocke)) {
                        //ide do ciela:
                        this.aktualnyHrac.nastavitPanacikaDoCiela(panacik);
                        this.vypisStav("PANACIK JE V CIELI: ", this.aktualnyHrac );
                    } else {
                        //posunie na dalsiu poziciu:
                        this.aktualnyHrac.posunPanacika(panacik, hodeneCisloNaKocke);
                        this.vypisStav("POSUN: " + hodeneCisloNaKocke, this.aktualnyHrac);
                        //ak nastane konflikt panacikov -> vzhod konfliktneho panacika ineho hraca:
                        this.skontrolujNovuPoziciuPanacika(panacik);
                    }
                    //potom odovzdaj kocku nasledujucemu hracovi:
                    this.kocka.vynulujKocku();
                    this.aktualnyHrac.vynulujPocetHodovKocky();
                    this.nasledujuciHrac();
                                     
                } else { //keď nie je na dráhe tak:
                    if (this.aktualnyHrac.getPocetHodovKocky() <= 3) {
                        if (hodeneCisloNaKocke == 6) { //bola hodena sestka?
                            this.aktualnyHrac.nastavitPanacikaNaStart(panacik);                            
                            this.vypisStav("PANACIK SA NASTAVIL NA START:", this.aktualnyHrac);
                        } else { 
                            this.vypisStav("NEUSPESNY HOD " + " pokus č. " + this.aktualnyHrac.getPocetHodovKocky() , this.aktualnyHrac);
                            //this.pokracovatHodomKocky();
                        }
                    } else { //keď nehodí 3x ani raz 6tku
                        //potom odovzdaj kocku nasledujucemu hracovi
                        this.kocka.vynulujKocku();
                        this.aktualnyHrac.vynulujPocetHodovKocky();
                        this.nasledujuciHrac();
                        //this.pokracovatHodomKocky();
                    }
                }
            } else {
                //ak nebol zaden panacik aktualneho hraca (napr. su vsetci v cieli)
                //pokracuj nasledovnym hracom v poradi
                this.nasledujuciHrac();
                if (this.aktualnyHrac != null ) {
                    if (!this.aktualnyHrac.getMaVsetkyPanacikyVCieli()){
                    this.tahHraca(hodeneCisloNaKocke);
                    //this.pokracovatHodomKocky();
                }
                this.koniecHry();                  
            }
             
        }
        
    }
    this.pokracovatHodomKocky();
}
    
    
    private void skontrolujNovuPoziciuPanacika(Panacik panacik) {
        Hrac konflitnyHrac = this.vratHracaNaRovnakejPozicii(panacik);
        if (konflitnyHrac != null) {
            Panacik konflitnyPanacik = konflitnyHrac.zoberPanacika();
            if (konflitnyPanacik != null) {
                konflitnyHrac.vyhoditPanacika(konflitnyPanacik);
                this.vypisStav("VYHODENY HRAC: " , konflitnyHrac);
            }
        }
    }
    
   
    
     /**
     * ak je na pozicii iny panacik ineho hraca vrati true     * 
     */
    private Hrac vratHracaNaRovnakejPozicii(Panacik panacik) {
        if (this.hraci != null) {
            for (int i = 0; i < this.hraci.length; i++) {
                Hrac inyHrac = this.hraci[i];
                if (inyHrac.getIDHraca() != panacik.getIDHraca()) {   //ide o ineho hraca
                    Panacik inyPanacik = inyHrac.zoberPanacika();
                    if (inyPanacik != null) {
                        if (inyPanacik.getPoziciaX() == panacik.getPoziciaX() &&
                            inyPanacik.getPoziciaY() == panacik.getPoziciaY()) {
                            return inyHrac; //vrati hraca, ktory ma panacika na zhodnej pozicii ako panacik v parametri
                        }
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * (HraClovece) Nastaví panáčika na štartovacie políčko.
     * 
     * @param hrac volá metódu na nastavenie panáčika na štart z triedy Hráč.
     */
    private void nastavNaStart(Hrac hrac) {
        if (hrac != null) {     
            Panacik panacik = hrac.zoberPanacika();
            if (panacik != null) {
                hrac.nastavitPanacikaNaStart(panacik);
            }
        }
    }
    
    /**
     * (HraClovece) Vráti panáčika naspäť domov.
     * 
     * @param hrac volá metódu na vyhodenie panáčika na štart z triedy Hráč.
     */
    private void vyhodPanacika(Hrac hrac) {
        if (hrac != null) {     
            Panacik panacik = hrac.zoberPanacika();
            if (panacik != null) {
                hrac.vyhoditPanacika(panacik);
            }
        }
    }
}