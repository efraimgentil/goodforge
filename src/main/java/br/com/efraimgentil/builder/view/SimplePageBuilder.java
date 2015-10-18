package br.com.efraimgentil.builder.view;

import java.util.HashMap;
import java.util.Map;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.WebResourcesFacet;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.addon.scaffold.util.ScaffoldUtil;

import freemarker.template.Template;
import br.com.efraimgentil.freemarker.FreemarkerTemplateProcessor;

public class SimplePageBuilder {
	
	
	private Project project;
	
	public SimplePageBuilder(Project project){
		this.project = project;
	}
	
	public void build(String fileName){
		WebResourcesFacet webFacet = project.getFacet(WebResourcesFacet.class );
		
		Map<String, Object> templateProperties = new HashMap<String, Object>();
		templateProperties.put("title", "Simple Page");
		templateProperties.put("body", "<h1>Simple LOL Body</h1>");

		FileResource<?> webResource = webFacet.getWebResource( fileName );
		Template template = FreemarkerTemplateProcessor.getTemplate("/template/simpleTemplate.jsp.ftl" );
		
		String processTemplate = FreemarkerTemplateProcessor.processTemplate( templateProperties , template);
		ScaffoldUtil.createOrOverwrite( webResource , processTemplate );
		
	}
	
}
