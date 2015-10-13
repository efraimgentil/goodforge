package br.com.efraimgentil.util;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

import org.jboss.forge.roaster.model.Type;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class JPAClassUtil {
	
	
	public static Type<JavaClassSource> getIdTypeFrom( JavaClassSource source ){
		Type<JavaClassSource> idType = null;
		for (FieldSource<JavaClassSource> f : source.getFields() ) {
			if( f.hasAnnotation( Id.class ) || f.hasAnnotation(EmbeddedId.class) ) {
				idType = f.getType();
				break;
			}
		}
		return idType;
	}
	
}
