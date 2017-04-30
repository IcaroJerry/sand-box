package ifba.ads.ssol.filemenager.fat;

import ifba.ads.ssol.interfaces.IFat;
import ifba.ads.ssol.interfaces.IProjectController;
import ifba.ads.ssol.filemenager.management.FileSystemManager;
import ifba.ads.ssol.element.FileSSOL;
import ifba.ads.ssol.element.Page;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Fat2 implements IFat {

    public static Fat2 getInstance() {
        if (instance == null) {
            instance = new Fat2();
        }
        return instance;
    }

    @Override
    public void allocateFile(FileSSOL file) {
        for (int i = 0; i < file.getAllocatedSectors().size() - 1; i++) {
            fat2[(int) file.getAllocatedSectors().get(i) - 1] = (int) file.getAllocatedSectors().get(i + 1);
        }
        fat2[(int) file.getAllocatedSectors().get(file.getAllocatedSectors().size() - 1) - 1] = -1;
    }

    public void deallocateFile(FileSSOL file) {
        for (int i = 0; i < file.getFileSize() * 2; i++) {
            this.fat2[(int) file.getAllocatedSectors().get(i) - 1] = 0;
        }
    }

    public boolean isFull(int fileSize) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (this.fat2[i] == 0) {
                count++;
            }
        }
        return fileSize * 2 > count;
    }

    public boolean isSectorFree(int num) {
        return this.fat2[num - 1] == 0;
    }

    public Sector[] seekPage(ArrayList<Integer> allocatedSectors, int pageNumber) {
        int[] pageSectors = new int[2];
        Sector[] content = new Sector[2];
        int count = 0;
        for (int i = 0; i < allocatedSectors.size(); i++) {
            count++;
            if (pageNumber == count / 2) {
                pageSectors[0] = allocatedSectors.get(i - 1);
                pageSectors[1] = allocatedSectors.get(i);
                break;
            }
        }
        content[0] = HardDisk.getInstance().getHd()[pageSectors[0] - 1];
        content[1] = HardDisk.getInstance().getHd()[pageSectors[1] - 1];
        return content;
    }

    @Override
    public ArrayList getFatString() {
        ArrayList<String[]> uiFat2 = new ArrayList<>();
        String[] str = null;
        for (int i = 0; i < size; i++) {
            str = new String[2];
            str[0] = String.valueOf(i + 1);
            str[1] = String.valueOf(fat2[i]);
            uiFat2.add(i, str);
        }
        return uiFat2;
    }

    private Fat2() {
        fat2 = new int[size];
        for (int i = 0; i < size; i++) {
            this.fat2[i] = 0;
        }
        sectors = new ArrayList<>();
    }

    public static int getSize() {
        return size;
    }

    private static Fat2 instance;
    private final static int size = IProjectController.DISK_SIZE * 2;
    private int[] fat2;
    private ArrayList<Integer> sectors;

}
