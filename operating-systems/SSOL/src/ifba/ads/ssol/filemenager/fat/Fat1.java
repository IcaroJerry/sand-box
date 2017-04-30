/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifba.ads.ssol.filemenager.fat;

import ifba.ads.ssol.shell.Core;
import ifba.ads.ssol.interfaces.IFat;
import ifba.ads.ssol.element.FileSSOL;
import java.util.ArrayList;

/**
 *
 *
 */
public class Fat1 implements IFat {

    public static Fat1 getInstance() {
        if (instance == null) {
            instance = new Fat1();
        }
        return instance;
    }

    @Override
    public void allocateFile(FileSSOL file) {
        this.fat1.add(file);
        Core.getWindowGerenciaDeDisco().notifyFat1();
    }

    public void deallocateFile(int index) {
        this.fat1.remove(index);
    }

    public ArrayList getFat1() {
        return this.fat1;
    }

    @Override
    public ArrayList getFatString() {
        ArrayList<String[]> uiFat1 = new ArrayList<>();
        String[] str;
        for (int i = 0; i < fat1.size(); i++) {
            str = new String[3];
            str[0] = fat1.get(i).getFileName();
            str[1] = String.valueOf(fat1.get(i).getIniSector());
            str[2] = String.valueOf(fat1.get(i).getFileSize());
            uiFat1.add(str);
        }
        return uiFat1;
    }

    private Fat1() {
        this.fat1 = new ArrayList<>();
    }

    private ArrayList<FileSSOL> fat1;
    private static Fat1 instance;
}
