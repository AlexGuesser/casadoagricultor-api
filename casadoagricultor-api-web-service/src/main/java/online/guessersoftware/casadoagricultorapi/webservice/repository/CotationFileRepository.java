package online.guessersoftware.casadoagricultorapi.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import online.guessersoftware.casadoagricultorapi.webservice.model.CotationFile;

@Repository
public interface CotationFileRepository extends JpaRepository<CotationFile, Long> {

}
