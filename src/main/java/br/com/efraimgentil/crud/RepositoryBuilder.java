package br.com.efraimgentil.crud;

import java.io.FileNotFoundException;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.TypeHolderSource;
import org.jboss.forge.roaster.model.source.TypeVariableSource;

import br.com.efraimgentil.GenericRepository;

public class RepositoryBuilder {
	
	private JavaResource entityResource;
	private String basePackage;
	
	public RepositoryBuilder(JavaResource javaResource , String basePackage) {
		this.entityResource = javaResource;
		this.basePackage = basePackage;
	}
	
	public void build( Project project ){
		JavaSourceFacet javaSourceFacet = project.getFacet(JavaSourceFacet.class);
		
		
		String entityPackage = javaSourceFacet.calculatePackage(entityResource);
		String entityName = javaSourceFacet.calculateName(entityResource);
		String subPackage = entityPackage.replace( basePackage + ".entity" , "" );
		
		JavaInterfaceSource repository = (JavaInterfaceSource) Roaster.create(JavaInterfaceSource.class).setName( entityName + "Repository" );
		repository.setPackage(basePackage + ".repository" + subPackage );
		repository.addImport( entityPackage + "." + entityName  );
		repository.addImport( GenericRepository.class );
		repository.addInterface("GenericRepository<" + entityName+ ">");
		
//		TypeVariableSource<JavaInterfaceSource> typeVariable = repository.addTypeVariable();
//		typeVariable.setName("T");
//		try {
//			typeVariable.setBounds( entityResource.getJavaType() );
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		
//		Roaster.create(TypeHolderSource.class).
//		repository.addNestedType( 
		
		
		javaSourceFacet.saveJavaSource(repository);
		
	}
	
}
