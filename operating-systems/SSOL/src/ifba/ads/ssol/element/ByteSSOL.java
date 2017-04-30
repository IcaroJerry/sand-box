package ifba.ads.ssol.element;

import ifba.ads.ssol.interfaces.IProjectController;

public class ByteSSOL {

    public String getContent() {
        if (this.content != null) {
            String tempValue = "";

            for (int i = 0; i < this.content.length; i++) {
                tempValue += this.content[i];
            }
            return tempValue;
        }

        return null;
    }

    public void setContent(String content) {
        if (content != null) {
            char[] tempValue = content.toCharArray();

            for (int i = 0; i < IProjectController.BYTE_SIZE; i++) {
                this.content[i] = tempValue[i];
            }
        } else {
            this.content = null;
        }
    }

    public ByteSSOL(String value) {
        this.content = new char[IProjectController.BYTE_SIZE];
        this.setContent(value);
    }

    @Override
    public String toString() {
        String temp = "";
        for (int i = 0; i < IProjectController.BYTE_SIZE; i++) {
            temp += String.valueOf(this.content[i]);
        }
        return temp;
    }

    public static ByteSSOL[] bytePerString(String value) {
        int aux = 0;
        char[] values = value.toCharArray();
        int qntBytes = values.length / IProjectController.BYTE_SIZE;
        ByteSSOL[] tempReturn = new ByteSSOL[qntBytes];

        for (int i = 0; i < qntBytes; i++) {
            String tempValue = "";
            for (int j = 0; j < IProjectController.BYTE_SIZE; j++) {
                tempValue += String.valueOf(values[j]);
            }
            tempReturn[aux] = new ByteSSOL(tempValue);
            aux++;
        }

        return tempReturn;
    }

    public ByteSSOL() {
    }
    private char[] content;
}
