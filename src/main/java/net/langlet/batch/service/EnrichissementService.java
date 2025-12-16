package net.langlet.batch.service;

import net.langlet.batch.model.AdresseSociete;
import net.langlet.batch.model.ReponseAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnrichissementService {

    private static final Logger logger = LoggerFactory.getLogger(EnrichissementService.class);

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final ThreadLocal<Map<String, AdresseSociete>> chunkCache = new ThreadLocal<>();
    private final ThreadLocal<List<String>> siretsLus = ThreadLocal.withInitial(ArrayList::new);
    private final ThreadLocal<Integer> nombreDeSirets = ThreadLocal.withInitial(() -> 0);

    public ThreadLocal<Integer> getNombreDeSirets() {
        return nombreDeSirets;
    }

    public ThreadLocal<List<String>> getSiretsLus() {
        return siretsLus;
    }

    public EnrichissementService(
            RestTemplate restTemplate,
            @Value("${enrichment.api.url}") String apiUrl

    ) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    /**
     * Précharge les enrichissements pour une liste de siret (appel API )
     */
    public void prechargerEnrichissements() {
        if (siretsLus.get() == null || siretsLus.get().isEmpty()) {
            logger.warn("Aucune société à enrichir");
            return;
        }

        logger.info("Préchargement des enrichissements pour {} sociétés", siretsLus.get().size());

        try {
            // Appel API groupé
            ReponseAPI response = restTemplate.postForObject(
                    apiUrl,
                    siretsLus.get(),
                    ReponseAPI.class
            );

            if (response != null && response.getData() != null) {
                // Mise en cache des résultats indexés par clé
                Map<String, AdresseSociete> enrichments = response.getData().stream()
                        .collect(Collectors.toMap(
                                AdresseSociete::getSiret,
                                data -> data,
                                (existing, replacement) -> replacement
                        ));

                chunkCache.set(enrichments);
                logger.info("Enrichissements préchargés: {} résultats", enrichments.size());
            } else {
                logger.warn("Réponse API vide ou nulle");
                chunkCache.set(new HashMap<>());
            }

        } catch (Exception e) {
            logger.error("Erreur lors du préchargement des enrichissements", e);
            // En cas d'erreur, on initialise un cache vide pour éviter les NPE
            chunkCache.set(new HashMap<>());
        }
    }

    /**
     * Récupère l'enrichissement pour un siret depuis le cache
     */
    public AdresseSociete recupererEnrichissement(String siret) {
        Map<String, AdresseSociete> cache = chunkCache.get();

        if (cache == null) {
            logger.warn("Cache non initialisé pour le siret: {}", siret);
            return null;
        }

        AdresseSociete enrichment = cache.get(siret);

        if (enrichment == null) {
            logger.debug("Aucun enrichissement trouvé pour le siret: {}", siret);
        }

        return enrichment;
    }

    /**
     * Nettoie le cache du thread courant
     */
    public void viderCache() {
        chunkCache.remove();
        siretsLus.get().clear();
        logger.debug("Cache des enrichissements nettoyé");
    }

    /**
     * Retourne la taille du cache actuel
     */
    public int recupererTailleDuCache() {
        Map<String, AdresseSociete> cache = chunkCache.get();
        return cache != null ? cache.size() : 0;
    }
}