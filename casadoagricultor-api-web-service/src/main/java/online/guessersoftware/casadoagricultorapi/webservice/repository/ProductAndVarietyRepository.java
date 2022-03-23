package online.guessersoftware.casadoagricultorapi.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import online.guessersoftware.casadoagricultorapi.webservice.model.ProductAndVariety;

@Repository
public interface ProductAndVarietyRepository extends JpaRepository<ProductAndVariety, Long> {

	ProductAndVariety findByName(String name);

}
