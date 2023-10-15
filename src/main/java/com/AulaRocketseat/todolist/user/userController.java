package com.AulaRocketseat.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.status;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class userController {
    
    @Autowired
    private IUserRepository userRepository;
    
    @PostMapping("/")
    //Recebendo dados do user (transformando o RequestBody em um objeto do tipo userModel)
    public ResponseEntity create(@RequestBody userModel userModel){
        
        System.out.println(userModel.getName());
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if (user != null) {
            //Verificando a existencia de um usuario com o mesmo username
            System.out.println("Usuario ja existente");
            //retorna um json com http status
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario já existe");
        }else{
            //salvando usuario novo
            userModel.setPassword(BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray()));
            var userCreated = this.userRepository.save(userModel);
            //retornando o usuario criado json com http status
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        }
        
    }
}
