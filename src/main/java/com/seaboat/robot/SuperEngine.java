package com.seaboat.robot;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.seaboat.robot.ability.Ability;

import bitoflife.chatterbean.AliceBot;
import bitoflife.chatterbean.parser.AliceBotParser;
import bitoflife.chatterbean.parser.AliceBotParserConfigurationException;
import bitoflife.chatterbean.parser.AliceBotParserException;
import bitoflife.chatterbean.util.Searcher;

/**
 * 
 * <pre><b>SuperRobot's engine,this engine contains AliceBot and AbilityBot. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class SuperEngine implements Engine {

	private AliceBot bot;

	private AbilityBot abilityBot;

	private SessionManager manager;

	public SuperEngine() {
		initAliceBot();
		initAbilityBot();
		initSessionManager();
	}

	public SessionManager getSessionManager() {
		return manager;
	}

	private void initSessionManager() {
		manager = SessionManager.getInstance();
	}

	private void initAbilityBot() {
		this.abilityBot = new AbilityBot();
	}

	private void initAliceBot() {
		Searcher searcher = new Searcher();
		AliceBot bot = null;
		try {
			AliceBotParser parser = new AliceBotParser();
			bot = parser.parse(
					this.getClass().getResourceAsStream(
							"/resources/context.xml"),
					this.getClass().getResourceAsStream(
							"/resources/splitters.xml"),
					this.getClass().getResourceAsStream(
							"/resources/substitutions.xml"), searcher
							.search("/resources/chinese/"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (AliceBotParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AliceBotParserConfigurationException e) {
			e.printStackTrace();
		}
		this.bot = bot;
	}

	public String respond(String input) {
		String response = bot.respond(input);
		if (response != null && !response.equals("#"))
			return response;
		Ability ability = abilityBot.searchAbility(input);
		response = ability.process();
		if (response == null)
			response = "不好意思，不懂你的意思，可以让我的主人先教我！";
		return response;
	}

	public String respond(String input, String sessionId) {
		String response = bot.respond(input);
		if (response != null && !response.equals("#"))
			return response;
		SuperContext context = manager.getContext(sessionId);
		Ability ability = abilityBot.searchAbility(input);
		if (ability != null)
			response = ability.process(context);
		if (response == null)
			response = "不好意思，不懂你的意思，可以让我的主人先教我！";
		return response;
	}

}
