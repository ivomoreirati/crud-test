package br.com.crud.repositories;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.crud.entities.People;

public interface PeopleRepository extends MongoRepository<People, Integer> {

	Optional<People> findPeopleByCpf(final String cpf);

}
