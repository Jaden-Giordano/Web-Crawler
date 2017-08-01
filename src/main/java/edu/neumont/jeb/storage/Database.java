package edu.neumont.jeb.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neufree.contacts.person.Contact;

public class Database<T extends IStorable> {

	private RandomAccessFile file;
	private int nextIndex = 8;
	private HashMap<String, List<Integer>> wordIndex = new HashMap<>(); 
	private final String indexOfWordsPath = "./src/main/java/edu/neumont/jeb/storage/index.txt"; 
	
	private Class<T> ref;

	public Database() {

	}

	public Database(String path, Class<T> ref) {
		this.ref = ref;

		try {
			File dirs = new File(path);
			dirs.mkdirs();
			boolean readOffset = new File(dirs, "data").exists();

			file = new RandomAccessFile(path + File.separator + "data", "rw");
			if (readOffset) nextIndex = file.readInt();
			wordIndex = loadIndex(indexOfWordsPath); 
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, List<Integer>> loadIndex(String path) throws IOException, ClassNotFoundException {
		HashMap<String, List<Integer>> loaded = new HashMap<>(); 
		if (new File(path).exists()) {
			try(FileInputStream in = new FileInputStream(path)) {
				try(ObjectInputStream objIn = new ObjectInputStream(in)){
					loaded = (HashMap<String, List<Integer>>) objIn.readObject();
				}
			}
		}
		return loaded;
	}

	public void insert(T c) { 
		insertWordIndex(c);
		writeIndex(wordIndex); 
		try {
			file.seek(nextIndex);
			byte[] bytes = c.serialize().getBytes();
			file.write(bytes);

			nextIndex += bytes.length;

			// Update offset
			file.seek(0);
			file.writeInt(nextIndex);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeIndex(HashMap<String, List<Integer>> index) {
		try(FileOutputStream out = new FileOutputStream(indexOfWordsPath)) {
			try(ObjectOutputStream objOut = new ObjectOutputStream(out)){
				objOut.writeObject(index);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void insertWordIndex(T c) {
		List<Integer> tempIndex = wordIndex.get(c.getKey());
		if (tempIndex == null) {
			tempIndex = new ArrayList<>();
			wordIndex.put(c.getKey(), tempIndex); 
		}
		tempIndex.add(nextIndex);
	}
	
	public List<T> searchWord(String word) { 
		List<Integer> indecies = wordIndex.get(word); 
		List<T> results = new ArrayList<>(); 
		if (indecies == null) {
			return new ArrayList<>(); 
		}
		for(Integer index: indecies) {
			T c = this.get(index); 
			results.add(c); 
		}
		return results; 
	}

	public T get(int index) {
		int sizeOf = getRefInstance().sizeOf();
		int offset = 8 + (index * sizeOf);
		try {
			file.seek(offset);
			byte[] b = new byte[sizeOf];
			file.read(b);
			T c = getRefInstance();
			c.deserialize(new String(b, "UTF-8"));
			return c;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void remove(int index) {
		int sizeOf = getRefInstance().sizeOf();
		int offset = 8 + (index * sizeOf);
		try {
			file.seek(offset + sizeOf);
			byte[] b = new byte[nextIndex - (offset + sizeOf)];
			file.read(b);
			file.seek(offset);
			file.write(b);

			file.seek(nextIndex);
			file.write(new byte[sizeOf]);

			nextIndex -= sizeOf;

			file.setLength(nextIndex);

			// Update index
			file.seek(0);
			file.writeInt(nextIndex);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public T getRefInstance() {
		try {
			return ref.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int size() {
		return nextIndex / getRefInstance().sizeOf();
	}

	public void close() {
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
