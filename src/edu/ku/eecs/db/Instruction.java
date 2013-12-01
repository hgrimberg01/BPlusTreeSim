package edu.ku.eecs.db;
/**
 * 
 * @author hgrimberg
 * Instruction class for demo processing
 */
public class Instruction {
	public static enum Type {
		INSERT, DELETE, SEARCH, OUTPUT
	}

	private Type iType;

	public Type getiType() {
		return iType;
	}

	public void setiType(Type iType) {
		this.iType = iType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	private String key;
	private String val;

}
