package com.bbva.openpay.api.marvel.service.api.impl;

import com.bbva.openpay.api.marvel.exception.MarvelException;
import com.bbva.openpay.api.marvel.service.api.MarvelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//Author: Jose Calderon
@Service
public class MarvelServiceImpl implements MarvelService {
    private final String charactersPath;
    private final String characterPathId;
    private final String timeStamp;
    private final String apiKey;
    private final String hash;
    private RestTemplate restTemplate;
    private Long characterId;

    public MarvelServiceImpl(@Value("${url.characters}") String charactersPath,
                             @Value("${url.characters.id}") String characterPathId,
                             @Value("${timestamp}") String timeStamp,
                             @Value("${api.key}") String apiKey,
                             @Value("${hash}") String hash) {

        this.charactersPath = charactersPath;
        this.characterPathId = characterPathId;
        this.timeStamp = timeStamp;
        this.apiKey = apiKey;
        this.hash = hash;
    }

    /**
     * Obtiene información sobre todos los personajes de Marvel.
     * Se realiza una solicitud al servicio web de Api Marvel y se procesa la respuesta para obtener
     * la información detallada de los personajes de marvel. En caso de error durante el procesamiento de la
     * respuesta, se lanza una excepción personalizada del tipo MarvelException.
     */
    @Override
    public String getCharacters() {
        restTemplate = new RestTemplate();
        try {
            String baseUrl = buildUrl(charactersPath, timeStamp, apiKey, hash, characterId);
            return restTemplate.getForObject(baseUrl, String.class);
        } catch (Exception e) {
            throw new MarvelException(e.getMessage());
        }
    }

    /**
     * Obtiene información sobre un personajes de Marvel.
     * Se realiza una solicitud al servicio web de Api Marvel y se procesa la respuesta para obtener
     * la información detallada de un personajes de marvel. En caso de error durante el procesamiento de la
     * respuesta, se lanza una excepción personalizada del tipo MarvelException.
     */
    @Override
    public String getCharacterId(Long characterId) {
        restTemplate = new RestTemplate();
        try {
            String url = buildUrl(characterPathId, timeStamp, apiKey, hash, characterId);
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            throw new MarvelException(e.getMessage());
        }
    }

    /**
     * Construye una URL completa para la consulta de personajes de la api
     *
     * @param path        La ruta base para la API de personajes de Marvel.
     * @param timeStamp   La marca de tiempo para la autenticación.
     * @param apiKey      La clave de la API de Marvel.
     * @param hash        El hash para la autenticación.
     * @param characterId El ID del personaje.
     */
    private String buildUrl(String path, String timeStamp, String apiKey, String hash, Long characterId) {
        return UriComponentsBuilder.fromUriString(path)
                .queryParam("ts", timeStamp)
                .queryParam("apikey", apiKey)
                .queryParam("hash", hash)
                .buildAndExpand(characterId)
                .toUriString();
    }
}
