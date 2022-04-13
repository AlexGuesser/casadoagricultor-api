package online.guessersoftware.casadoagricultorapi.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import online.guessersoftware.casadoagricultorapi.webservice.model.ProductAndVariety;

@Repository
public interface ProductAndVarietyRepository extends JpaRepository<ProductAndVariety, Long> {

	ProductAndVariety findByName(String name);

	// @formatter:off
	@Query(value = "SELECT * FROM product_and_variety " + 
					"WHERE (deleted = 0 AND (name = :name OR name_2 = :name OR name_3 = :name))"
	, nativeQuery = true)
	// @formatter:on
	ProductAndVariety findBySomeName(String name);

}
