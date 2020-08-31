package de.sz.samples.thymeleaf;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class StaticResourceDirectoryAssembler {
	
	private File generatedReport;
	private File staticResources;
	
	public StaticResourceDirectoryAssembler(final File generatedReport, final File staticResources) {
		this.generatedReport = generatedReport;
		this.staticResources = staticResources;
	}

	public void run() throws IOException {
		if(staticResources.isFile()) {
			final File targetStaticResourcesFile = new File(generatedReport.getParentFile(), staticResources.getName());
			FileUtils.copyFile(staticResources, targetStaticResourcesFile);
		}
		else {
			FileUtils.copyDirectory(staticResources, generatedReport.getParentFile());
		}
	}

}
