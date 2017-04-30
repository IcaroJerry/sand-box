package ifba.ads.ssol.filemenager.management;

import ifba.ads.ssol.filemenager.fat.Fat2;
import ifba.ads.ssol.filemenager.fat.HardDisk;
import ifba.ads.ssol.filemenager.fat.Fat1;
import ifba.ads.ssol.shell.Core;
import ifba.ads.ssol.element.ByteSSOL;
import java.io.BufferedReader;
import java.io.FileReader;
import ifba.ads.ssol.interfaces.SignalProcessManager;
import ifba.ads.ssol.interfaces.IProjectController;
import ifba.ads.ssol.element.FileSSOL;
import java.util.ArrayList;
import java.util.Random;

public class FileSystemManager extends SignalProcessManager {

    public ArrayList readText(String fileLocation) throws Exception {
        BufferedReader text = new BufferedReader(new FileReader(fileLocation));
        ByteSSOL[] temp = new ByteSSOL[IProjectController.SECTOR_SIZE];
        ArrayList<ByteSSOL[]> data = new ArrayList<>();
        int i = 0;
        String str = "";
        while (text.ready()) {
            str += text.readLine();
        }

        char[] elements = str.toCharArray();
        int sizeElements = elements.length;
        int j = 0;
        str = "";

        while (j < sizeElements) {
            str += elements[j];
            if (str.length() == IProjectController.BYTE_SIZE) {
                temp[i] = new ByteSSOL(str);
                i++;
                str = "";
            }

            if (temp[IProjectController.SECTOR_SIZE - 1] != null) {
                data.add(temp);
                i = 0;
            }
            j++;
        }

        return data;
    }

    public int createIniSector() {
        Fat2 fat2;
        fat2 = Fat2.getInstance();
        Random random = new Random();
        int i = random.nextInt(IProjectController.DISK_SIZE * 2) + 1;
        while (!fat2.isSectorFree(i)) {
            i = random.nextInt(IProjectController.DISK_SIZE * 2) + 1;
        }
        return i;
    }

    public ArrayList createSectors(FileSSOL file) {
        ArrayList<Integer> sectors = new ArrayList<>();
        sectors.add(file.getIniSector());
        HardDisk.getInstance().receiveSectors(file.getIniSector());
        Random random = new Random();
        int i = file.getFileSize() * 2;
        for (int a = 0; a < i - 1; a++) {
            int z = random.nextInt(IProjectController.DISK_SIZE * 2) + 1;
            while ((!Fat2.getInstance().isSectorFree(z)) || sectors.contains(z)) {
                z = random.nextInt(IProjectController.DISK_SIZE * 2) + 1;
            }
            sectors.add(z);
            HardDisk.getInstance().receiveSectors(z);
        }
        return sectors;
    }

    @Override
    public boolean recieveProcessData(String processId, String processName, String address, int sizeByte) throws Exception {
        if (!Fat2.getInstance().isFull(sizeByte / 1024)) {
            FileSSOL file = new FileSSOL(processName, createIniSector(), (sizeByte / 1024), processId);
            file.setAllocatedSectors(createSectors(file));
            Fat1.getInstance().allocateFile(file);
            Fat2.getInstance().allocateFile(file);
            HardDisk.getInstance().allocateData(readText(address));
            Core.getWindowGerenciaDeDisco().notifyFat1();
            Core.getWindowGerenciaDeDisco().notifyFat2();
            return true;
        }
        return false;
    }

    @Override
    public void deallocate(String processId) {
        FileSSOL file;
        for (int i = 0; i < Fat1.getInstance().getFat1().size(); i++) {
            file = (FileSSOL) Fat1.getInstance().getFat1().get(i);
            if (processId.equalsIgnoreCase(file.getFileID())) {
                Fat2.getInstance().deallocateFile(file);
                Fat1.getInstance().deallocateFile(i);
            }
        }
    }
}
