package com.seaboat.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * <pre><b>a bootstrap. </b></pre>
 * @author 
 * <pre>seaboat</pre>
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * @version 0.1
 */
public class Bootstrap {
	public static String input() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("我：");
		String input = "";
		try {
			input = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	public static void main(String[] args) throws Exception {
		SuperEngine engine = new SuperEngine();
		System.err.println("super-robot：>" + engine.respond("欢迎"));
		while (true) {
			String input = input();
			System.err.println("super-robot>" + engine.respond(input));
		}

	}
}
