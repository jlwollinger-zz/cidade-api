package br.com.cidade.model;
/***
 * 
 * @author Jose Wollinger
 * 21 Feb, 2018
 */
public class EntryRest {

	private String key;
	
	private String value;
	
	public EntryRest(String key, Long value) {
		this.key = key;
		this.value = value.toString();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public int valueAsInt() {
		return Integer.parseInt(this.value);
	}
}
