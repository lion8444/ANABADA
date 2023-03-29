package com.anabada.translate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
//@Api(tags = "Papago 번역")
public class TranslateController {
    private final TranslateService translateService;


    @PostMapping("/api/translate")
    public TranslateResponseDto.Result naverPapagoTranslate(String source, String target, String text) {
    	log.debug("@TranslateController naverPapagoTranslate. source : {}, target : {}, text : {}", source, target, text);
    	log.debug("{}",translateService.naverPapagoTranslate(source, target, text));
        return translateService.naverPapagoTranslate(source, target, text);
    }
    
}