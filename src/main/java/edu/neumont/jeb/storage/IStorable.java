package edu.neumont.jeb.storage;

public interface IStorable {

	String serialize();
	void deserialize(String data);
	int sizeOf();
	String getKey(); 
	
}
