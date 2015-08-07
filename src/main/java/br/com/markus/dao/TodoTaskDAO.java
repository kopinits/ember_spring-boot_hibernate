package br.com.markus.dao;

import br.com.markus.model.TodoTask;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Markus on 07/08/2015.
 */
@Transactional
public interface TodoTaskDAO extends CrudRepository<TodoTask, Long> {
}
