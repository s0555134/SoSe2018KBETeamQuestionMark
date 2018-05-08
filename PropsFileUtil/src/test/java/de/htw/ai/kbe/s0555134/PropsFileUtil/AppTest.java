package de.htw.ai.kbe.s0555134.PropsFileUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
	@Test
	public void loadPropertiesFileReturnProperties() {
		Properties p = PropsFileUtil.loadPropertiesFile();
		assertEquals(PropsFileUtil.loadPropertiesFile(), p);
	}
	
	@Test
	public void loadPropertiesFileWithFileReturnProperties() {
		Properties p = PropsFileUtil.loadPropertiesFile("files/test.properties");
		assertEquals(PropsFileUtil.loadPropertiesFile("files/test.properties"), p);
	}
	


	@Test
	public void loadPropertiesFileWithoutExistPathShouldReturnNull() {
		assertNull(null, PropsFileUtil.loadPropertiesFile("files/WrongConfig.properties"));
	}

	@Test
	public void loadPropertiesFileNotPropertieFileWithoutEndingPropertiesShouldReturnNull() {
		assertNull(null, PropsFileUtil.loadPropertiesFile("files/notPropertieFile"));
	}

	@Test
	public void loadPropertiesFileNotPropertieFileWithEndingPropertiesShouldReturnNull() {
		assertNull(null, PropsFileUtil.loadPropertiesFile("files/notPropertieFile.properties"));
	}

	@Test
	public void loadPropertiesFileWhichIsEmptyShouldReturnNull() {
		assertNull(null, PropsFileUtil.loadPropertiesFile("files/leer.properties"));
	}
	
	

}
