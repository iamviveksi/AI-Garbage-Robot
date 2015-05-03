package garbage.robot;
import java.util.*;
import java.io.*;


class PropertiesSupport {
	private Hashtable hash = new Hashtable();

	public PropertiesSupport() {

	}
//method load data for configure from conf.prp
	public void load() {
		Properties properties = new Properties();
		String key, value;

		try {
			properties.load(new InputStreamReader(new FileInputStream(
					"data/conf.prp"), "UTF8"));
		} catch (Exception e) {
			// e.printStackTrace();
		}

		Enumeration keys = properties.propertyNames();
		while (keys.hasMoreElements()) {
			key = keys.nextElement().toString();
			value = properties.getProperty(key);
			hash.put(key, value);

		}

	}
	//method return property by key
	public String getProperty(String key) {
		if (hash.get(key) != null) {
			String res = hash.get(key).toString();
			return res;
		} else {
			return null;
		}
	}
}