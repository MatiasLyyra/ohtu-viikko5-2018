
package ohtu.intjoukkosovellus;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntJoukko {

    public final static int KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUSKASVATUS = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        this(KAPASITEETTI, OLETUSKASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUSKASVATUS);
    }

    public IntJoukko(int... luvut) {
        this(luvut.length, OLETUSKASVATUS);
        for (int i = 0; i < luvut.length; i++) {
            lisaa(luvut[i]);
        }
    }
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti väärin");//heitin vaan jotain :D
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("kapasiteetti2");//heitin vaan jotain :D
        }
        ljono = new int[kapasiteetti];
        for (int i = 0; i < ljono.length; i++) {
            ljono[i] = 0;
        }
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public boolean lisaa(int luku) {
        boolean lisatty = false;
        if (alkioidenLkm == 0) {
            ljono[0] = luku;
            alkioidenLkm++;
            lisatty = true;
        } else if (!kuuluu(luku)) {
            ljono[alkioidenLkm] = luku;
            alkioidenLkm++;
            kasvataTarvittaessa();
            lisatty = true;
        }
        return lisatty;
    }

    private void kasvataTarvittaessa() {
        if (alkioidenLkm >= ljono.length) {
            int[] taulukkoOld = ljono;
            kopioiTaulukko(ljono, taulukkoOld);
            ljono = new int[alkioidenLkm + kasvatuskoko];
            kopioiTaulukko(taulukkoOld, ljono);
        }
    }

    public boolean kuuluu(int luku) {
        return Arrays.stream(ljono).anyMatch(i -> i == luku);
    }

    public boolean poista(int luku) {
        int kohta = luvunIndeksi(luku);
        if (kohta != -1) {
            siirraLukujaTaakse(kohta);
            alkioidenLkm--;
            return true;
        }
        return false;
    }

    private void siirraLukujaTaakse(int kohta) {
        int apu;
        ljono[kohta] = 0;
        for (int j = kohta; j < alkioidenLkm - 1; j++) {
            apu = ljono[j];
            ljono[j] = ljono[j + 1];
            ljono[j + 1] = apu;
        }
    }

    private int luvunIndeksi(int luku) {
        return Ints.indexOf(ljono, luku);
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        String luvut =  Arrays.stream(ljono)
                .limit(alkioidenLkm)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", "));
        return String.format("{%s}", luvut);
    }

    public int[] toIntArray() {
        int[] taulu = new int[alkioidenLkm];
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko yhdisteJoukko = new IntJoukko(a.toIntArray());
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < bTaulu.length; i++) {
            yhdisteJoukko.lisaa(bTaulu[i]);
        }
        return yhdisteJoukko;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko leikkausJoukko = new IntJoukko();
        int[] aTaulu = a.toIntArray();
        for (int i = 0; i < aTaulu.length; i++) {
            if (b.kuuluu(aTaulu[i])) {
                leikkausJoukko.lisaa(aTaulu[i]);
            }
        }
        return leikkausJoukko;

    }
    
    public static IntJoukko erotus ( IntJoukko a, IntJoukko b) {
        IntJoukko erotusJoukko = new IntJoukko(a.toIntArray());
        int[] bTaulu = b.toIntArray();
        for (int i = 0; i < bTaulu.length; i++) {
            erotusJoukko.poista(bTaulu[i]);
        }
        return erotusJoukko;
    }
        
}