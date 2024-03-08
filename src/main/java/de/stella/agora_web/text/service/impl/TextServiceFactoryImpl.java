package de.stella.agora_web.text.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.stella.agora_web.text.service.ITextService;
import de.stella.agora_web.text.service.ITextServiceFactory;

@Component
public class TextServiceFactoryImpl implements ITextServiceFactory {

    @Autowired
    private TextServiceImpl textServiceImpl;

    @Override
    public ITextService createTextService() {
        return textServiceImpl;
    }
}

