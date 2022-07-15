package br.com.crud.repositories;

import br.com.crud.entities.People;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PeopleRepository extends MongoRepository<People, Integer> {

	Optional<People> findOneByCpf(final String cpf);
	Optional<List<People>> findAllByCpf(final String cpf);
	void deleteAllByCpf(final List<People> peopleList);

}
