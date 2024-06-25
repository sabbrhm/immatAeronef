package estiam.projets.immataeronef;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.exceptions.CsvException;

@Service
public class ImmatService {

	private final ImmatCSVReader immatCSVReader;
	public static final String UNKNOWN = "unknown";
	
	public ImmatService(@Autowired ImmatCSVReader immatCSVReader, @Autowired AppConf appConf) throws CsvException, IOException {
		this.immatCSVReader = immatCSVReader;
		immatCSVReader.importFile(new File(appConf.getFilename()));
	}

	public Optional<AeronefDTO> getAeronefFromImmat(String immat) {
		var entry = immatCSVReader.getEntryByImmat(immat);
		if (entry.isEmpty()) {
			return Optional.empty();
		}
		
		var constructeur = entry.getOrDefault("CONSTRUCTEUR", UNKNOWN);
		var modele = entry.getOrDefault("MODELE", UNKNOWN);
		var aerodromeAttache = entry.getOrDefault("AERODROME_ATTACHE", UNKNOWN);
		
		return Optional.ofNullable(new AeronefDTO(immat, constructeur, modele, aerodromeAttache));
	}
/* Récupérer toutes les entrées CSV et calculer le nombre d'appareils par constructeur */
	public Map<String, Long> getConstructeurCount() {
       return immatCSVReader.getAllEntries().stream()
             .collect(Collectors.groupingBy(entry -> entry.get("CONSTRUCTEUR"), Collectors.counting()));
    }
	
}
