package de.sz.samples.thymeleaf;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class MainTest {

	@Test
	public void testParsing() throws Exception {
		final String[] args = new String[]{
			"--template", "report.html",
			"--out", "target/outfile.html",
			"--data", "src/test/resources/variables.properties",
			"--static", "src/test/resources/static"
		};
		
		Main.main(args);
		
		final String parsedContent = IOUtils.toString(new FileReader("target/outfile.html"));
		assertEquals(191, parsedContent.indexOf("<li>FOO</li>"));
		assertEquals(205, parsedContent.indexOf("<li>BAR</li>"));
	}
	
}
