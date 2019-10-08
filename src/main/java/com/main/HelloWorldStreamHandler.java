package com.main;
import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.example.handlers.CancelandStopIntentHandler;
import com.example.handlers.HelloWorldIntentHandler;
import com.example.handlers.HelpIntentHandler;
import com.example.handlers.LaunchRequestHandler;
import com.example.handlers.SessionEndedRequestHandler;

public class HelloWorldStreamHandler extends SkillStreamHandler {

	private static Skill getSkill() {
		return Skills.standard()
				.addRequestHandlers(
						new CancelandStopIntentHandler(),
						new HelloWorldIntentHandler(),
						new HelpIntentHandler(),
						new LaunchRequestHandler(),
						new SessionEndedRequestHandler())
				.withSkillId("amzn1.ask.skill.f7bc29e0-58ad-4724-9ec2-c622424a11a3")
				.build();
	}
	public HelloWorldStreamHandler() {
		super(getSkill());
	}
}
