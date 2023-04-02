package com.anabada.translate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TranslateService {

    @Value("${naver.papago.clientId}")
    private String clientId;
    @Value("${naver.papago.clientSecret}")
    private String clientSecret;

    public Testdto naverPapagoTranslate(int listid, String source, String target, String text) {

    	log.debug("papago id : {}", clientId);
    	log.debug("papago secret : {}", clientId);
    	
        WebClient webClient = WebClient.builder()
                .baseUrl("https://openapi.naver.com/v1/papago/n2mt")
                .build();
        TranslateResponseDto response = webClient.post().uri(
                        uriBuilder -> uriBuilder.queryParam("source", source)
                                .queryParam("target", target)
                                .queryParam("text", text)
                                .build()
                )
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .retrieve()
                .bodyToMono(TranslateResponseDto.class).block();
        Testdto test = new Testdto();
        test.setListid(listid);
        test.setTransmsg(response.getMessage().getResult().getTranslatedText());
        return test;
    }
    
    public TranslateResponseDto.Result naverPapagoTranslate(String source, String target, String text) {

    	log.debug("papago id : {}", clientId);
    	log.debug("papago secret : {}", clientId);
    	
        WebClient webClient = WebClient.builder()
                .baseUrl("https://openapi.naver.com/v1/papago/n2mt")
                .build();
        TranslateResponseDto response = webClient.post().uri(
                        uriBuilder -> uriBuilder.queryParam("source", source)
                                .queryParam("target", target)
                                .queryParam("text", text)
                                .build()
                )
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .retrieve()
                .bodyToMono(TranslateResponseDto.class).block();
        return response.getMessage().getResult();
    }
}
