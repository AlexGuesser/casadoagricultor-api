package online.guessersoftware.casadoagricultorapi.webservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.guessersoftware.casadoagricultorapi.webservice.model.User;
import online.guessersoftware.casadoagricultorapi.webservice.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User getUserByName(String name) {
    return userRepository.findByName(name);
  }

}
