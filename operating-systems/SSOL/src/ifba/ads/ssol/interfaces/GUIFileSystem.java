package ifba.ads.ssol.interfaces;

import ifba.ads.ssol.filemenager.fat.Fat2;
import ifba.ads.ssol.filemenager.fat.Fat1;
import java.util.ArrayList;

public class GUIFileSystem {

    public ArrayList showFat1() {
        return Fat1.getInstance().getFatString();
    }

    public ArrayList showFat2() {
        return Fat2.getInstance().getFatString();
    }
}
