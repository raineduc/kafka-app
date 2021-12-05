package com.example.kafkainternapp.repositories;

import com.example.kafkainternapp.entities.ProducedEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProducedEntityRepository extends CrudRepository<ProducedEntity, Long> {}
