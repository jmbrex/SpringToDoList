package com.AulaRocketseat.todolist.task;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author João Marcelo
 */
public interface ITaskRepository extends JpaRepository<TaskModel, Long> {
    List<TaskModel> findByidUser(Long idUser);
}
