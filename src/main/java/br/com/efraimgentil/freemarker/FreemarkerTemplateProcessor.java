package br.com.efraimgentil.freemarker;

/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * A template processor that processes specified Freemarker files to generate text output.
 * 
 */
public class FreemarkerTemplateProcessor
{

   private static freemarker.template.Configuration freemarkerConfig;

   public static Template getTemplate(String templateLocation)
   {
      try
      {
         return getFreemarkerConfig().getTemplate(templateLocation);
      }
      catch (IOException ioEx)
      {
         throw new RuntimeException(ioEx);
      }
   }
   public static String processTemplate(Map<Object, Object> map, Template template)
   {
      Writer output = new StringWriter();
      try
      {	
         template.process(map, output);
      }
      catch (IOException ioEx)
      {
         throw new RuntimeException(ioEx);
      }
      catch (TemplateException templateEx)
      {
         throw new RuntimeException(templateEx);
      }
      return output.toString();
   }
   
   private static Configuration getFreemarkerConfig()
   {
      if (freemarkerConfig == null)
      {
         freemarkerConfig = new Configuration();
         freemarkerConfig.setClassForTemplateLoading(FreemarkerTemplateProcessor.class, "/");
         freemarkerConfig.setObjectWrapper(new DefaultObjectWrapper());
      }
      return freemarkerConfig;
   }

}