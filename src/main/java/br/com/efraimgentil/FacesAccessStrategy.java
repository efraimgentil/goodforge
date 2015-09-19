package br.com.efraimgentil;

import java.util.List;

import org.jboss.forge.addon.javaee.faces.FacesFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.scaffold.spi.AccessStrategy;

public class FacesAccessStrategy implements AccessStrategy {
	final FacesFacet<?> faces;

	public FacesAccessStrategy(final Project project) {
		this.faces = project.getFacet(FacesFacet.class);
	}

	@Override
	public List<String> getWebPaths(final Resource<?> resource) {
		return this.faces.getWebPaths(resource);
	}

	@Override
	public Resource<?> fromWebPath(final String path) {
		return this.faces.getResourceForWebPath(path);
	}

}