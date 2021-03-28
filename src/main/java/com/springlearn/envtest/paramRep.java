package com.springlearn.envtest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface paramRep extends CrudRepository<Boparams, Boparams.PKClass> {

}
