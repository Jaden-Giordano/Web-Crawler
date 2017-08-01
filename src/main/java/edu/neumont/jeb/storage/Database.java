package edu.neumont.jeb.storage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Database<T extends IStorable> {

	private RandomAccessFile file;
	private int nextIndex = 8;

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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void insert(T c) {
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
