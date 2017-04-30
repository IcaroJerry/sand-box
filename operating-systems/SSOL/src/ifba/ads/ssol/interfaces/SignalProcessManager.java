package ifba.ads.ssol.interfaces;

import ifba.ads.ssol.element.ByteSSOL;
import ifba.ads.ssol.element.FileSSOL;
import ifba.ads.ssol.element.Page;
import ifba.ads.ssol.filemenager.fat.Fat1;
import ifba.ads.ssol.filemenager.fat.Fat2;
import ifba.ads.ssol.filemenager.fat.Sector;
import ifba.ads.ssol.filemenager.management.FileSystemManager;
import ifba.ads.ssol.virtualmachine.CoreVirtualMachine;

public abstract class SignalProcessManager {

    public abstract boolean recieveProcessData(String processId, String processName, String address, int sizeByte) throws Exception;

    public abstract void deallocate(String processId);

    public static SignalProcessManager getInstance() {
        if (instance == null) {
            instance = new FileSystemManager();
        }
        return instance;
    }

    protected SignalProcessManager() {
    }

    public ByteSSOL[] requestSectors(String processId, int pageNumber) {
        FileSSOL file;
        for (int i = 0; i < Fat1.getInstance().getFat1().size(); i++) {
            file = (FileSSOL) Fat1.getInstance().getFat1().get(i);
            if (file.getFileID().equalsIgnoreCase(processId)) {
                return CoreVirtualMachine.getInstance().getProjectController().ConvertToByteArray(Fat2.getInstance().seekPage(file.getAllocatedSectors(), pageNumber + 1));
            }
        }
        return null;
    }

    private static SignalProcessManager instance;
}
