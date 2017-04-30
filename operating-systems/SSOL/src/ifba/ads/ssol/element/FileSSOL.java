package ifba.ads.ssol.element;

import java.util.ArrayList;

public class FileSSOL {

    public FileSSOL(String fileName, int iniSector, int fileSize, String fileID) {
        this.fileName = fileName;
        this.iniSector = iniSector;
        this.fileSize = fileSize;
        this.fileID = fileID;
        this.allocatedSectors = new ArrayList<>();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getIniSector() {
        return iniSector;
    }

    public void setIniSector(int iniSector) {
        this.iniSector = iniSector;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public void setAllocatedSectors(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            this.allocatedSectors.add(list.get(i));
        }
    }

    public ArrayList getAllocatedSectors() {
        return this.allocatedSectors;
    }

    private String fileName;
    private int iniSector;
    private int fileSize;
    private String fileID;
    private ArrayList<Integer> allocatedSectors;
}
