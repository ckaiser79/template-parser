package de.sz.thymeleaf.cmd;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import de.sz.thymeleaf.cmd.Main;

public class MainTest {

	@Before
	public void configure() throws IOException {
		File d;
		d = new File("target/css");
		if (d.exists())
			FileUtils.deleteDirectory(d);

		d = new File("target/js");
		if (d.exists())
			FileUtils.deleteDirectory(d);

		File f;
		
		f = new File("target/outfile.html.zip");
		FileUtils.deleteQuietly(f);
		
		f = new File("target/outfile.html");
		FileUtils.deleteQuietly(f);
		
		f = new File("target/outfile.txt");
		FileUtils.deleteQuietly(f);
		
	}
	
	@Test
	public void testUsage() throws Exception {
		final String[] args = new String[] {
				"--help"
		};
				
		Main.main(args);
	}
	
	@Test
	public void testStaticZip() throws Exception {
		final String[] args = new String[] {
				"--template", "report.html",
				"--out", "target/outfile.html",
				"--static", "src/test/resources/static", "--zip"
		};
				
		Main.main(args);
		assertTrue(new File("target/css/bootstrap.css").exists());
		assertTrue(new File("target/outfile.html.zip").exists());
	}
	
	@Test
	public void testStaticCopy() throws Exception {
		final String[] args = new String[] {
				"--template", "report.html",
				"--out", "target/outfile.html",
				"--static", "src/test/resources/static"
		};
				
		Main.main(args);
		assertTrue(new File("target/css/bootstrap.css").exists());
	}

	@Test
	public void testParsingDataCsv() throws Exception {
		final String[] args = new String[] {
				"--template", "report.html",
				"--out", "target/outfile.html",
				"--variables", "src/test/resources/variables.properties",
				"--data", "src/test/resources/data.csv",
		};

		Main.main(args);

		final String parsedContent = IOUtils.toString(new FileReader("target/outfile.html"));

		assertFalse(new File("target/css/bootstrap.css").exists());
		assertNotEquals(-1, parsedContent.indexOf("<h1>FOO</h1>"));
		assertNotEquals(-1, parsedContent.indexOf("<p>BAR</p>"));
		assertNotEquals(-1, parsedContent.indexOf("<td>456</td>"));
	}

	@Test
	public void testParsingDataAsText() throws Exception {
		final String[] args = new String[] {
				"--template", "report.txt",
				"--variables", "src/test/resources/variables.properties",
				"--template-type", "txt",
				"--out", "target/outfile.txt",
				"--data", "src/test/resources/data.csv",
		};

		Main.main(args);

		final String parsedContent = IOUtils.toString(new FileReader("target/outfile.txt"));

		assertFalse(new File("target/css/bootstrap.css").exists());
		assertNotEquals(-1, parsedContent.indexOf("123"));
		assertNotEquals(-1, parsedContent.indexOf("aaa"));
		assertNotEquals(-1, parsedContent.indexOf("ccc"));
	}
	
	@Test
	public void testParsingDataTabSeparated() throws Exception {
		final String[] args = new String[] {
				"--template", "report.html", 
				"--out", "target/outfile.html",
				"--variables", "src/test/resources/variables-tab.properties",
				"--data", "src/test/resources/data.tsv",
		};

		Main.main(args);

		final String parsedContent = IOUtils.toString(new FileReader("target/outfile.html"));

		assertFalse(new File("target/css/bootstrap.css").exists());
		assertNotEquals(-1, parsedContent.indexOf("<h1>FOO</h1>"));
		assertNotEquals(-1, parsedContent.indexOf("<p>BAR</p>"));
		assertNotEquals(-1, parsedContent.indexOf("<td>456</td>"));
	}
}
