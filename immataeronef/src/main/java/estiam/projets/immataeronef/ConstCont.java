package estiam.projets.immataeronef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/* @RestController : marque la classe comme un controleur Rest */
@RestController
public class ConstCont {
/* @Autowired:indique que Spring doit injecter l'instance de ImmatService automatiquement */
    @Autowired
    private ImmatService immatService;
/* GetMapping: Assosié la méthode HTTP GET au chemin '/constructeurs' */
    @GetMapping("/constructeurs")
/* getConstructeurs(): une méthode pour récupérer les données sur les constructeurs d'aéronefs et les renvoie sous forme de liste de ConstDTO */
    public List<ConstDTO> getConstructeurs() {
/* Récupération des données */
        Map<String, Long> constructeurCount = immatService.getConstructeurCount();     
/* Filtrage et transformation des données */
        return constructeurCount.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(entry -> new ConstDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
 