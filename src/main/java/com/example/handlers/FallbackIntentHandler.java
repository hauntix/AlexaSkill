package com.example.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

public class FallbackIntentHandler implements RequestHandler {
	@Override
	public boolean canHandle(HandlerInput handlerInput) {
		return handlerInput.matches(intentName("AMAZON.FallbackIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput handlerInput) {
		String speechTxt = "Sorry buddy, I don't know that. You can say try saying help tho!";
		return handlerInput.getResponseBuilder()
				.withSpeech(speechTxt)
				.withSimpleCard("HelloWorld", speechTxt)
				.withReprompt(speechTxt)
				.build();
	}
}
