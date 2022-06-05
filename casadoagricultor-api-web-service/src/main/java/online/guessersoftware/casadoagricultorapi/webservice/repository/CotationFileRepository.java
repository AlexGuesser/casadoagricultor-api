package online.guessersoftware.casadoagricultorapi.webservice.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import online.guessersoftware.casadoagricultorapi.webservice.model.CotationFile;

@Repository
public interface CotationFileRepository extends JpaRepository<CotationFile, Long> {

	// @formatter:off
	@Query(value = "SELECT * FROM cotation_file c " 
						+ "where c.deleted = 0 " 
						+ "and c.successfully_processed = 1 " 
						+ "and c.filename = :filename "
						+ "and c.ceasa_FK = :ceasaId"
	, nativeQuery = true)
	// @formatter:on
	List<CotationFile> cotationFilesAlreadyProcessedSuccessfully(String filename, Long ceasaId);
	
	@Procedure("closest_cotation_day_of")
	LocalDate closestCotationDayOf(LocalDate desiredDate);

}
