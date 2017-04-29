package ifba.ads.abstractfactory.config;


public class LoadConfigBD {
 
    public static String getValue(String key){  
           String temp =  (String)loader.getValue(key); 
        return temp;
    }  

    private static LoaderConfigBD loader = new LoaderConfigBD();  
}
