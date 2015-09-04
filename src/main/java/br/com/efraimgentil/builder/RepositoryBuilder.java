package br.com.efraimgentil.builder;

import java.io.FileNotFoundException;
import java.util.List;

import javax.persistence.Id;

import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.parser.java.resources.JavaResource;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
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
	
	public void build( Project project ) throws FileNotFoundException{
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
		
		JavaInterfaceSource repository = (JavaInterfaceSource) Roaster.create(JavaInterfaceSource.class).setName( entityName + "Repository" );
		repository.setPackage(basePackage + ".repository" + subPackage );
		repository.addImport( entityPackage + "." + entityName  );
		repository.addImport( GenericRepository.class );
		repository.addImport( idType.getQualifiedName() );
		repository.addInterface("GenericRepository<" + entityName+ " , " + idType.getName() + ">");
		
		
		javaSourceFacet.saveJavaSource(repository);
		
	}
	
}
