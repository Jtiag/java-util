package com.gome.wa.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class HiProperty extends Properties {

    private void load(String propertiesFile) {
        InputStream in = this.getClass().getResourceAsStream(propertiesFile);
        try {
            this.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    /**
	 * 
	 */
    private static final long serialVersionUID = -4382571842625283586L;

    public HiProperty(String propertiesFile) {
        load(propertiesFile);
    }

    public void setProperties(List<String> files) {
        for (String file : files) {
            load(file);
        }
    }

}
