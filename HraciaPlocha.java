/**
 * Trieda HraciaPlocha, kde sa vykreslí hracia plocha.
 * 
 * @author Timea Funtíková
 * @version 1.0
 */
public class HraciaPlocha {
    private Stvorec[][] plocha;
    private static HraciaPlocha hraciaPlocha;
    //private Policko[] draha;
    private FarbaHraca[] farbyHracichBlokov;
    private static final int ROZMER_STVORCEKA = 60; //pixelov
    private static final int ROZMER_DRAHY = 11;     //pocet stvorcekov
    private static final int POCET_BLOKOV = 4;     
    
    /**
     * Konštruktor triedy HraciaPlocha.
     */
    public HraciaPlocha() {
    }
    
    /**
     * (Hracia Plocha) Nakreslí hraciu plochu.
     */
    public void nacitajHraciuPlochu() {  
        this.nacitajFarbyBlokov();
        this.nacitajPlochu();
        this.nacitajDrahu();
        this.nacitajBlokyHracom();
    }
    
     /**
     * (Hracia Plocha) Načíta farby 4 blokov, začiatočných domčekov pre panáčikov.
     */
    private void nacitajFarbyBlokov() {
        this.farbyHracichBlokov = new FarbaHraca[POCET_BLOKOV];
        FarbaHraca farba = null;
        for (int i = 0; i < POCET_BLOKOV; i++) {
            int idBloku = i + 1;
            if (idBloku == 1) {
                farba = FarbaHraca.ZELENA;
            }
            
            if (idBloku == 2) {
                farba = FarbaHraca.MODRA;
            }
            
            if (idBloku == 3) {
                farba = FarbaHraca.CERVENA;
            }
        
            if (idBloku == 4) {
                farba = FarbaHraca.ORANZOVA;
            }
            this.farbyHracichBlokov[i] = farba;
        }
    }
    
     /**
     * (Hracia Plocha) Načíta pole Štvorcov o rozmeroch 11 x 11.
     */
    private void nacitajPlochu() {
        this.plocha = new Stvorec[ROZMER_DRAHY][ROZMER_DRAHY];
        for (int x = 0; x < ROZMER_DRAHY; x++) {
            for (int y = 0; y < ROZMER_DRAHY; y++) {
                this.plocha[x][y] = new Stvorec();
                this.plocha[x][y].zmenStranu(59);
                this.plocha[x][y].zmenFarbu("yellow");
                this.plocha[x][y].posunVodorovne(-50 + x * ROZMER_STVORCEKA);
                this.plocha[x][y].posunZvisle(-20 + y * ROZMER_STVORCEKA);
                this.plocha[x][y].zobraz();
            }
        }
    }
    
     /**
     * (Hracia Plocha) Nakreslí biele políčka štvorcov, ktoré vizuálne pôsobia ako dráha pre panáčika. 
     */
    private void nacitajDrahu() {
        
        String farbaDrahy = "black";
        //horizontálna dráha pre panáčika:
        for (int x = 0; x < ROZMER_DRAHY ; x++) {
            for (int y = 4; y <= 6; y++) {
                this.plocha[x][y].zmenFarbu(farbaDrahy);
            }
        }
        //vertikálna dráha pre panáčika:
        //toto porozdelovať aktívnu dráhu a pasívne pozadie!!
        for (int x = 4; x <= 6; x++) {
            for (int y = 0; y < ROZMER_DRAHY; y++) {
                this.plocha[x][y].zmenFarbu(farbaDrahy);
            }
        }
    }
    
    /**
     * (Hracia Plocha) Podľa farby hráča, ktorá je závislá na jeho ID sa hráčom nakreslia príslušné začiatočné domčeky.
     */
    
    private void nacitajBlokyHracom() {        
        if (this.farbyHracichBlokov != null) {
            for (int idBloku = 1; idBloku <= this.farbyHracichBlokov.length; idBloku++) {
                FarbaHraca farbaBloku = this.farbyHracichBlokov[idBloku - 1];//vyberie fafbu z pola podla nacitaneho indexu bloku (idBkloku-1)
                this.nacitajStartovacieBloky(idBloku, farbaBloku);
                //nacita po 4 pozicie pre kazdy domcek a ciel
                for (int idPozicie = 1 ; idPozicie <= 4 ; idPozicie++) {                
                    this.nacitajDomovskeBloky(idBloku, idPozicie, farbaBloku);
                    this.nacitajCieloveBloky(idBloku, idPozicie, farbaBloku);
                }
            }
        }
    }

    /**
     * (Hracia Plocha) Súradnice jednotlivých blokov, kam sa majú vykresliť. 
     * 
     * @param idBloku ID domovského bloku
     * @param idPozicie ID pozície jednotlivých panáčikov
     * @param farba pridelí blokom farbu z enumu podľa id Bloku.
     */
    private void nacitajDomovskeBloky(int idBloku, int idPozicie, FarbaHraca farba) { 
        if (this.plocha != null) {
            int x = 0;
            int y = 0;
            if (idPozicie > 2) {
                y = 1;
            }
            if (idPozicie == 2 || idPozicie == 4) {
                x = 1;
            }
            if (idBloku == 2) {
                x += 9;
            }
            if (idBloku == 3) {
                y += 9;
            }
            if (idBloku == 4) {
                x += 9;
                y += 9;
            }
            this.plocha[x][y].zmenFarbu(this.dajSystemovuFarbu(farba));
        } 
    }
        
     /**
     * (Hracia Plocha) Zafarbí štartovacie políčka na príslušnú farbu podľa hráča.
     * 
     * @param idBloku ID domovského bloku
     * @param farba pridelí štatrovacím políčkam farbu z enumu podľa id Bloku.
     */
    private void nacitajStartovacieBloky(int idBloku, FarbaHraca farba) {
        int x = 0;
        int y = 0;    
        if (idBloku == 1 ) {
            x = 0;
            y = 4;
        }
        if (idBloku == 2 ) {
            x = 6;
            y = 0;           
        }
        if (idBloku == 3 ) {
            x = 4;
            y = 10;
        }
        if (idBloku == 4) {
            x = 10;
            y = 6;
        }
        this.plocha[x][y].zmenFarbu(this.dajSystemovuFarbu(farba));
         /*
          * //test štart:
        this.plocha[0][4].zmenFarbu("green");        
        this.plocha[4][10].zmenFarbu("red");
        this.plocha[6][0].zmenFarbu("blue");
        this.plocha[ROZMER_DRAHY-1][6].zmenFarbu("orange");
        */
    }

     /**
     * (Hracia Plocha) Zafarbí cieľové políčka na príslušnú farbu. 
     * 
     * @param idBloku ID domovského bloku
     * @param idPozicie ID pozície jednotlivých panáčikov
     * @param farba pridelí cieľovým blokom farbu z enumu podľa id Bloku.
     * 
     */
    private void nacitajCieloveBloky(int idBloku, int idPozicie, FarbaHraca farba) { 
        if (this.plocha != null) {
            int x = 0;
            int y = 0;
            switch (idBloku) {
                case  1: 
                    x = 5 - idPozicie;
                    y = 5;
                    break;
                case  2 : 
                    x = 5;
                    y = 5 - idPozicie;
                    break;
                case 3 : 
                    x = 5;
                    y = 5 + idPozicie;
                    break;
                case 4 :
                    x = 5 + idPozicie;
                    y = 5;
                    break;
            }
            this.plocha[x][y].zmenFarbu(this.dajSystemovuFarbu(farba));
        }
    }
    
     /**
     * (Hracia Plocha) Priradí Stringovým výrazom príslušnú farbu, ktorú už trieda Plátno pozná. Farba "magenta" by sa nemala nikdy zobraziť, indikuje chybu pri vykresľovaní."
     * 
     * @param farba bloky sa zafarbia podľa farby z enumu.
     */
    
    private String dajSystemovuFarbu(FarbaHraca farba) {
        if (farba != null) {
            switch (farba) {
                case ZELENA:
                    return "green";
                case CERVENA:
                    return "red";
                case MODRA:
                    return "blue";
                case ORANZOVA:
                    return "orange";
                default: return "magenta";  //ak sa zobrazí, čosi je zle
            } 
        }
        return "magenta";  //pozor vystraha!!
    }
   
     /**
     * (Hracia Plocha) Singleton triedy HraciaPlocha.
     */
    
    public static HraciaPlocha dajHraciaPlocha() {
        if (HraciaPlocha.hraciaPlocha == null) {
            HraciaPlocha.hraciaPlocha = new HraciaPlocha();
        }
        return HraciaPlocha.hraciaPlocha;
    }
}
