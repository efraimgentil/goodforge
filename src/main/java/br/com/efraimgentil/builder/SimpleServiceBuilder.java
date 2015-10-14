package br.com.efraimgentil.builder;

import java.io.FileNotFoundException;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

public class SimpleServiceBuilder {
	
	private Project project;
	
	public SimpleServiceBuilder( Project project ) {
		this.project = project;
	}
	
	public JavaResource buildFor( String targetPackage, String serviceName ) throws FileNotFoundException{
		JavaSourceFacet javaSourceFacet = project.getFacet(JavaSourceFacet.class);
		 
		JavaClassSource service = (JavaClassSource) Roaster.create(JavaClassSource.class).setName( serviceName );
		service.setPackage( targetPackage );
		service.addAnnotation( Service.class );
		service.addAnnotation(Lazy.class);
		AnnotationSource<JavaClassSource> annotation = service.getAnnotation( Lazy.class);
		annotation.setLiteralValue("true");
		
		return javaSourceFacet.saveJavaSource(service);
	}
	
}
