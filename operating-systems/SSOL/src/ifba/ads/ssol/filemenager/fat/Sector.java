package ifba.ads.ssol.filemenager.fat;

import ifba.ads.ssol.element.ByteSSOL;
import ifba.ads.ssol.interfaces.IProjectController;
import java.util.ArrayList;
import java.util.List;

public class Sector {

    public Sector() {
        sector = new ByteSSOL[IProjectController.SECTOR_SIZE];
    }

    public void addData(ByteSSOL[] data) {
        for (int i = 0; i < sector.length; i++) {
            sector[i] = data[i];
        }
    }

    public ByteSSOL getData(int index) {
        return this.sector[index];
    }

    private final ByteSSOL[] sector;
}
