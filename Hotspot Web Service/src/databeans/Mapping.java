package databeans;

public class Mapping {
	public String key;
	public int value;
	public Mapping(String _key, int _value) { key = _key; value = _value; }
	
	public void setKey(String s) { key = s; }
	public void setValue(int s) { value = s; }
	public String getKey() { return key; }
	public int getValue() { return value; }
}