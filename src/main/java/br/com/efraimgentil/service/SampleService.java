package br.com.efraimgentil.service;

import br.com.efraimgentil.entity.Sample;
import br.com.efraimgentil.CrudService;
import br.com.efraimgentil.repository.SampleRepository;
import static br.com.efraimgentil.specs.SampleSpecs.*;
import java.lang.Integer;

public class SampleService extends CrudService<Sample, Integer, SampleRepository>
{
}