package ifba.ads.ssol.filemenager.fat;

import ifba.ads.ssol.element.ByteSSOL;
import ifba.ads.ssol.interfaces.IProjectController;
import java.util.ArrayList;

public class HardDisk {

    public void receiveSectors(int num) {
        sectors.add(num);
    }

    public void allocateData(ArrayList<ByteSSOL[]> data) {
        Sector sector;
        for (int i = 0; i < sectors.size(); i++) {
            sector = new Sector();
            sector.addData(data.get(i));
            hd[sectors.get(i) - 1] = sector;
        }
        sectors.clear();
    }

    public static HardDisk getInstance() {
        if (instance == null) {
            instance = new HardDisk();
        }
        return instance;
    }

    public Sector[] getHd() {
        return this.hd;
    }

    private HardDisk() {
        hd = new Sector[IProjectController.DISK_SIZE * 2];
    }

    private final ArrayList<Integer> sectors = new ArrayList<>();
    private final int size = IProjectController.DISK_SIZE;
    private Sector[] hd;
    private static HardDisk instance;
}
