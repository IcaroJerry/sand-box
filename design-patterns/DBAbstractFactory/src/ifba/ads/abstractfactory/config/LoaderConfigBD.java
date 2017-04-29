package  ifba.ads.abstractfactory.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class LoaderConfigBD {
    
    private Properties props;  
    private String propertiesName = "ConfigBD.properties";  
  
    protected LoaderConfigBD(){  
            props = new Properties();  
            InputStream in = this.getClass().getResourceAsStream(propertiesName);  
            try{  
                    props.load(in);  
                    in.close();  
            }  
            catch(IOException e){e.printStackTrace();}  
    }  
  
    protected String getValue(String key){  
            return (String)props.getProperty(key);  
    }
}
