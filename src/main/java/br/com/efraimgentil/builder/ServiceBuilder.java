package br.com.efraimgentil.builder;

import java.util.List;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;

import br.com.efraimgentil.GenericRepository;

public class ServiceBuilder {
	
	private JavaResource entityResource;
	private String basePackage;
	
	public ServiceBuilder(JavaResource javaResource , String basePackage) {
		this.entityResource = javaResource;
		this.basePackage = basePackage;
	}
	
	public void build( Project project ){
		JavaSourceFacet javaSourceFacet = project.getFacet(JavaSourceFacet.class);
		
		
		String entityPackage = javaSourceFacet.calculatePackage(entityResource);
		String entityName = javaSourceFacet.calculateName(entityResource);
		String subPackage = entityPackage.replace( basePackage + ".entity" , "" );
		
		JavaInterfaceSource repository = (JavaInterfaceSource) Roaster.create(JavaInterfaceSource.class).setName( entityName + "Service" );
		repository.setPackage(basePackage + ".service" + subPackage );
		repository.addImport( entityPackage + "." + entityName  );
		repository.addImport( GenericRepository.class );
		repository.addInterface("GenericRepository<" + entityName+ ">");
		
		javaSourceFacet.saveJavaSource(repository);
		
	}
	
}
