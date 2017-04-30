package ifba.ads.ssol.virtualmachine;

import ifba.ads.ssol.element.ByteSSOL;
import ifba.ads.ssol.filemenager.fat.Sector;
import ifba.ads.ssol.interfaces.ICoreVirtualMachine;
import ifba.ads.ssol.interfaces.IProjectController;
import ifba.ads.ssol.virtualmachine.model.cpu.registers.ProgramCounter;
import ifba.ads.ssol.virtualmachine.model.cpu.registers.RegisterInstruction;

public class ProjectController implements IProjectController {

    @Override
    public void valueRI(Object newValue) {
        RegisterInstruction.ModifyValue(newValue);
    }

    @Override
    public void valuePC(Object newValue) {
        ProgramCounter.ModifyRealValue(newValue);
    }

    @Override
    public Object valueRI() {
        return RegisterInstruction.RealValue();
    }

    @Override
    public int valuePC() {
        return ProgramCounter.RealValue();
    }

    @Override
    public void allocRAM(int initiation, Object[] content) {
        CoreVirtualMachine.getRandomAccessMemory().alloc(initiation, content);
    }

    public Object[] seekContentSector(int[] indexSector) {
        return null;
        //Busca conteudo diretamente no disco.

    }

    @Override
    public void allocHD(int initiation, Object[] content) {
        CoreVirtualMachine.getRandomAccessMemory().alloc(initiation, content);
    }

    @Override
    public ByteSSOL[] ConvertToByteArray(String value) {
        return ByteSSOL.bytePerString(value);
    }

    @Override
    public ByteSSOL[] ConvertToByteArray(Sector[] value) {
        int size = value.length;
        int aux = 0;
        ByteSSOL[] dataTemp = new ByteSSOL[IProjectController.SECTOR_SIZE * size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < IProjectController.SECTOR_SIZE; j++) {
                dataTemp[aux] = value[i].getData(j);
                aux++;
            }
        }
        return dataTemp;
    }
}
