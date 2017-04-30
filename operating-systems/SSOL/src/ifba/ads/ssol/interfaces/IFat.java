package ifba.ads.ssol.interfaces;

import ifba.ads.ssol.element.FileSSOL;
import java.util.ArrayList;

public interface IFat {

    public ArrayList getFatString();

    public void allocateFile(FileSSOL file);
}
