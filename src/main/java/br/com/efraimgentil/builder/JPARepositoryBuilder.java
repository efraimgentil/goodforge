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
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.JavaInterfaceSource;
import org.jboss.forge.roaster.model.source.TypeHolderSource;
import org.jboss.forge.roaster.model.source.TypeVariableSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.efraimgentil.samples.GenericRepository;
import br.com.efraimgentil.util.JPAClassUtil;

public class JPARepositoryBuilder {
	
	private Project project;
	
	public JPARepositoryBuilder(Project project) {
		this.project = project;
	}
	
	public JavaResource buildFor( JavaResource entityResource) throws FileNotFoundException{
		JavaSourceFacet javaSourceFacet = project.getFacet(JavaSourceFacet.class);
		
		JavaClassSource entityJavaType = entityResource.getJavaType();
		String entityName = entityJavaType.getName();
		
		Type<JavaClassSource> idType = JPAClassUtil.getIdTypeFrom(entityJavaType);
		
		JavaInterfaceSource source = Roaster.create(JavaInterfaceSource.class);
		
		source.setPackage( javaSourceFacet.getBasePackage() + ".repositories" );
		source.setName(entityName + "Repository");
		source.addImport( entityJavaType );	
		source.addImport( idType.getQualifiedName() );
		
		source.addImport(Lazy.class);
		source.addAnnotation(Lazy.class);
		AnnotationSource<JavaInterfaceSource> annotation = source.getAnnotation( Lazy.class);
		annotation.setLiteralValue("true");
		
		source.addImport(Repository.class);
		source.addAnnotation(Repository.class);
		
		source.addImport(JpaRepository.class);
		source.addInterface("JpaRepository<" +entityName + "," + idType.getName()  + ">");

		javaSourceFacet.saveJavaSource(source);
		
		return javaSourceFacet.saveJavaSource(source);
	}
	
}
