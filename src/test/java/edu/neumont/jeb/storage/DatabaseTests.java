package edu.neumont.jeb.storage;

import org.apache.commons.io.FileUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTests {

	private final String dbDirectory = new File(System.getProperty("user.dir"), "data").getAbsolutePath();

	@Test
	public void aClean() {
		File dbFile = new File(dbDirectory);
		try {
			FileUtils.deleteDirectory(dbFile);
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void bInsertData() {
		Database<Webpage> db = new Database<>(dbDirectory, Webpage.class);

		db.insert(new Webpage("http://google.com", "Google"));
		db.insert(new Webpage("http://www.neumont.edu", "Neumont"));

		assertEquals(2, db.size(), 0);

		db.close();
	}

	@Test
	public void cGetData() {
		Database<Webpage> db = new Database<>(dbDirectory, Webpage.class);

		assertTrue(db.get(0).getUrl().equals("http://google.com"));
		assertTrue(db.get(0).getTitle().equals("Google"));

		assertTrue(db.get(1).getUrl().equals("http://www.neumont.edu"));
		assertTrue(db.get(1).getTitle().equals("Neumont"));

		db.close();
	}

	@Test
	public void dRemoveData() {
		Database<Webpage> db = new Database<>(dbDirectory, Webpage.class);

		db.remove(0);
		assertEquals(1, db.size(), 0);

		db.remove(0);
		assertEquals(0, db.size(), 0);

		db.close();
	}

}
