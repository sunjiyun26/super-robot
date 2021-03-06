/*
Copyleft (C) 2005 H�lio Perroni Filho
xperroni@yahoo.com
ICQ: 2490863

This file is part of ChatterBean.

ChatterBean is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.

ChatterBean is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with ChatterBean (look at the Documents/ directory); if not, either write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA, or visit (http://www.gnu.org/licenses/gpl.txt).
 */

package bitoflife.chatterbean.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Searcher implements FilenameFilter {
	/*
	 * Attribute Section
	 */
	private LinkedList<File> fileQueue = new LinkedList<File>();
	private static final String[] STRING_ARRAY = {};
	private static String[] names = { "Book.xml", "Complain.xml", "Computer.xml",
			"Constellation.xml", "Discuss.xml", "Encourage.xml",
			"External.xml", "Film.xml", "Food.xml", "Game.xml", "gossip.xml",
			"Greeting.xml", "Healthy.xml", "Inherence.xml", "Internet.xml",
			"Job.xml", "Joke.xml", "Life.xml", "Main.xml", "MarkResponse.xml",
			"Master.xml", "Music.xml", "Photography.xml", "Plan.xml",
			"Praise.xml", "Rude.xml", "Sentiment .xml", "Sex.xml",
			"SmallWord.xml", "Study.xml", "Travel.xml", };
	private String expression;

	/*
	 * Method Section
	 */

	/**
	 * 
	 * @param path
	 * @param expression
	 * @return
	 */
	protected String[] dir(String path, String expression) {
		if (path.charAt(path.length() - 1) != '/')
			path += "/";
		File file = new File(path);

		List<String> filenames = new ArrayList<String>();

		addFilesToQueue(file.listFiles());

		while (!fileQueue.isEmpty()) {
			File f = fileQueue.poll();
			if (f.isFile() && f.getName().matches(expression)) {
				filenames.add(f.getPath());
			} else if (f.isDirectory()) {
				addFilesToQueue(f.listFiles());
			}
		}
		String[] names = filenames.toArray(new String[0]);
		Arrays.sort(names);
		return names;
	}

	private void addFilesToQueue(File[] files) {
		for (int i = 0; i < files.length; i++) {
			fileQueue.add(files[i]);
		}
	}

	protected String[] dir(URL base, String path, String expression)
			throws IOException {
		if (path.charAt(path.length() - 1) != '/')
			path += "/";

		URL url = new URL(base, path);
		BufferedReader dir = new BufferedReader(new InputStreamReader(
				url.openStream()));

		List<String> files = new LinkedList<String>();
		for (String file = ""; (file = dir.readLine()) != null;)
			if (file.matches(expression))
				files.add(path + file);

		return files.toArray(STRING_ARRAY);
	}

	public boolean accept(File dir, String name) {
		return name.matches(expression);
	}

	public InputStream[] search(String path) throws IOException {
		InputStream[] files = new InputStream[names.length];
		for (int i = 0, n = names.length; i < n; i++) {
			files[i] = this.getClass().getResourceAsStream(path + names[i]);
		}
		return files;
	}

	public InputStream[] search(URL base, String path, String expression)
			throws IOException {
		String[] files = dir(base, path, expression);
		InputStream[] streams = new InputStream[files.length];
		for (int i = 0, n = files.length; i < n; i++) {
			URL aiml = new URL(base, files[i]);
			streams[i] = aiml.openStream();
		}

		return streams;
	}
}
