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

import br.com.efraimgentil.CrudService;

public class SpecsBuilder {

	private JavaResource entityResource;
	private String basePackage;
	private JavaResource repository;
	
	public SpecsBuilder(JavaResource javaResource , String basePackage) {
		this.entityResource = javaResource;
		this.basePackage = basePackage;
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
		
		String entityPackage = javaSourceFacet.calculatePackage(entityResource);
		String entityName = javaSourceFacet.calculateName(entityResource);
		String subPackage = entityPackage.replace( basePackage + ".entity" , "" );
		
		JavaClassSource specs = (JavaClassSource) Roaster.create(JavaClassSource.class).setName( entityName + "Specs" );
		specs.setPackage( entityPackage + ".specs" + subPackage );
		specs.addImport( entityResource.getJavaType() );
		specs.addImport( idType.getQualifiedName() );
		specs = createGenericMethods(specs);
		return javaSourceFacet.saveJavaSource(specs);
	}
	
	public JavaClassSource createGenericMethods(JavaClassSource specs){
		
		
		return specs;
	}
	
	
}
