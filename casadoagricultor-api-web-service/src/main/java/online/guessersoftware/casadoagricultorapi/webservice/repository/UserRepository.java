package online.guessersoftware.casadoagricultorapi.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import online.guessersoftware.casadoagricultorapi.webservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByName(String name);

}
