package io.caden.transformers.amazon.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.caden.transformers.amazon.dtos.rainforest.RainforestRequestProductResultDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@Slf4j
public class RainforestService {

  @Value("${rainforest.api.url}")
  private String rainforestApiUrl;

  @Value("${rainforest.api.key}")
  private String rainforestApiKey;

  public RainforestRequestProductResultDto getProduct(final String amazonDomain, final String asin) throws JsonProcessingException {
    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("api_key", this.rainforestApiKey);
    uriVariables.put("type", "product");
    uriVariables.put("amazon_domain", amazonDomain);
    uriVariables.put("asin", asin);

    long rainforestTimer = System.currentTimeMillis();
    ResponseEntity<String> response = new RestTemplate().getForEntity(
      String.format(
        "%s/request?api_key={api_key}&type={type}&amazon_domain={amazon_domain}&asin={asin}",
        this.rainforestApiUrl
      ),
      String.class,
      uriVariables
    );

    long totalElapsedTimeRainforestRequest = System.currentTimeMillis() - rainforestTimer;
    log.info("Rainforest getProduct took {}ms", totalElapsedTimeRainforestRequest,
            kv("time_ms", totalElapsedTimeRainforestRequest));

    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    RainforestRequestProductResultDto result = mapper.readValue(response.getBody(), RainforestRequestProductResultDto.class);

    if (result == null) {
      result = new RainforestRequestProductResultDto();
    }

    result.setJsonData(mapper.readTree(response.getBody()));

    return result;
  }

  public String getGtin(final String amazonDomain, final String asin) {
    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("api_key", this.rainforestApiKey);
    uriVariables.put("type", "asin_to_gtin");
    uriVariables.put("amazon_domain", amazonDomain);
    uriVariables.put("asin", asin);

    long rainforestTimer = System.currentTimeMillis();
    ResponseEntity<String> response = new RestTemplate().getForEntity(
            String.format(
                    "%s/request?api_key={api_key}&type={type}&amazon_domain={amazon_domain}&asin={asin}",
                    this.rainforestApiUrl
            ),
            String.class,
            uriVariables
    );

    long totalElapsedTimeRainforestRequest = System.currentTimeMillis() - rainforestTimer;
    log.info("Rainforest getGtin took {}ms", totalElapsedTimeRainforestRequest,
            kv("time_ms", totalElapsedTimeRainforestRequest));
    try {
      JSONObject jsonObjectResponse = new JSONObject(response.getBody());
      for(Object element : jsonObjectResponse.getJSONArray("asin_to_gtin_results")) {
        JSONObject gtin = (JSONObject) element;
        return gtin.getString("value");
      }
    } catch (JSONException ex) {
      // TODO: handle exception
    }

    return "";
  }
}
