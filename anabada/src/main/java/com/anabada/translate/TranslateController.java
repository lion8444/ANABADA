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
    public Testdto naverPapagoTranslate(int listid, String source, String target, String text) {
    	log.debug("@TranslateController naverPapagoTranslate. listid : {}, source : {}, target : {}, text : {}",listid, source, target, text);
    	log.debug("{}",translateService.naverPapagoTranslate(listid, source, target, text));
        return translateService.naverPapagoTranslate(listid, source, target, text);
    }
    
}