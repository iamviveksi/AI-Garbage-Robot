package garbage.robot;
import java.util.*;
import java.io.*;


class PropertiesSupport {
	private Hashtable hash = new Hashtable();

	public PropertiesSupport() {

	}

	public void load() {
		Properties properties = new Properties();
		String key, value;

		try {
			properties.load(new InputStreamReader(new FileInputStream(
					"data/konf.prp"), "UTF8"));
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

	public String getProperty(String key) {
		if (hash.get(key) != null) {
			String res = hash.get(key).toString();
			return res;
		} else {
			return null;
		}
	}

	public void put(String key, String value) {
		hash.put(key, value);
	}

	public void store() {
		Properties props = new Properties();
		Enumeration keys = hash.keys();
		String key;
		while (keys.hasMoreElements()) {
			key = keys.nextElement().toString();
			props.put(key, hash.get(key));
		}
		try {
			props.store(new FileOutputStream("konf.prp"), "Properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}