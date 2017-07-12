package fr.sii.atlantique.siistem.suggestion;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository of the Suggestion service, to work with SQL Server
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@Repository
@Transactional
public interface SuggestionRepository extends CrudRepository<Suggestion, Integer>{

	List<Suggestion> findAll();
}
