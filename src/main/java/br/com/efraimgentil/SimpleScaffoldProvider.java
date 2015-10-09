package br.com.efraimgentil;

import java.util.List;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.WebResourcesFacet;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.scaffold.spi.AccessStrategy;
import org.jboss.forge.addon.scaffold.spi.ScaffoldGenerationContext;
import org.jboss.forge.addon.scaffold.spi.ScaffoldProvider;
import org.jboss.forge.addon.scaffold.spi.ScaffoldSetupContext;
import org.jboss.forge.addon.ui.result.NavigationResult;

public class SimpleScaffoldProvider implements ScaffoldProvider {
	
	private Project project;
	
	@Override
	public List<Resource<?>> generateFrom(ScaffoldGenerationContext genContext) {
		return null;
	}

	@Override
	public AccessStrategy getAccessStrategy() {
		return new FacesAccessStrategy(project);
	}

	@Override
	public String getDescription() {
		return "Simple scaffold command";
	}

	@Override
	public NavigationResult getGenerationFlow(ScaffoldGenerationContext genContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "GoodForge Scaffold";
	}

	@Override
	public NavigationResult getSetupFlow(ScaffoldSetupContext setupContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSetup(ScaffoldSetupContext setupContext) {
		//Here you can validate your setup to run this scaffold
		return true;
	}

	@Override
	public List<Resource<?>> setup(ScaffoldSetupContext setupContext) {
		project = setupContext.getProject();
		return null;
	}

}
