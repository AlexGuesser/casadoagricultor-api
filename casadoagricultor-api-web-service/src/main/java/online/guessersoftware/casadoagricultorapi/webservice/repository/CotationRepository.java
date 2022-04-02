package online.guessersoftware.casadoagricultorapi.webservice.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import online.guessersoftware.casadoagricultorapi.webservice.model.Cotation;

@Repository
public interface CotationRepository extends JpaRepository<Cotation, Long> {

	// @formatter:off
	@Query(value = "SELECT c.* FROM cotation c "
					+ "inner join cotation_file cf "
					+ "on (c.cotation_file_FK = cf.id and c.deleted = 0 and cf.deleted = 0 and cf.successfully_processed = 1 )" 
					+ "where cf.ceasa_FK = :ceasaId " 
					+ "and c.from_day = :day " 
	, nativeQuery = true)
	// @formatter:on
	List<Cotation> getCotationsBy(LocalDate day, Long ceasaId, Pageable pageable);

}
