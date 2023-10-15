package com.AulaRocketseat.todolist.user;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<userModel, Long>{
    // criando metodo de buscar o usuario com o username
    userModel findByUsername(String username);
    
}
