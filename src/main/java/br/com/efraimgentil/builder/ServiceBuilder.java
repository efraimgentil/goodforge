package br.com.efraimgentil.builder;

import java.io.FileNotFoundException;
import java.util.List;

import javax.persistence.Id;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class ServiceBuilder {
	
	private JavaResource entityResource;
	private String basePackage;
	private JavaResource repository;
	private JavaResource specs;
	
	public ServiceBuilder(JavaResource javaResource , String basePackage , JavaResource repository, JavaResource specs) {
		this.entityResource = javaResource;
		this.basePackage = basePackage;
		this.repository = repository;
		this.specs = specs;
	}
	
	public JavaResource build( Project project ) throws FileNotFoundException{
		JavaSourceFacet javaSourceFacet = project.getFacet(JavaSourceFacet.class);
		
		JavaClassSource javaType = entityResource.getJavaType();
		Type<JavaClassSource> idType = null ;
		List<FieldSource<JavaClassSource>> fields = javaType.getFields();
		for (FieldSource<JavaClassSource> fieldSource : fields) {
			if( fieldSource.hasAnnotation(Id.class) ) ;
				idType = fieldSource.getType();
		}
//		
		String entityPackage = javaSourceFacet.calculatePackage(entityResource);
		String entityName = javaSourceFacet.calculateName(entityResource);
		String repositoryName = javaSourceFacet.calculateName( repository  );
		String subPackage = entityPackage.replace( basePackage + ".entity" , "" );
//		
		JavaClassSource service = (JavaClassSource) Roaster.create(JavaClassSource.class).setName( entityName + "Service" );
//		service.setPackage(basePackage + ".service" + subPackage );
//		service.addImport( entityPackage + "." + entityName  );
//		service.addImport( CrudService.class );
//		service.addImport( repository.getJavaType().getQualifiedName()  );
//		Import addImport = service.addImport( specs.getJavaType().getQualifiedName() + ".*"  );
//		addImport.setStatic(true);
//		service.addImport( idType.getQualifiedName() );
//		service.setSuperType("CrudService<" + entityName+ " , " + idType.getName() + ", " + repositoryName +">");
		
		return javaSourceFacet.saveJavaSource(service);
	}
	
}
