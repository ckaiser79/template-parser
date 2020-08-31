package de.sz.samples.thymeleaf;

import org.thymeleaf.context.Context;

public interface ContextCreationStrategy {
	
	public Context createContext();
	
}
