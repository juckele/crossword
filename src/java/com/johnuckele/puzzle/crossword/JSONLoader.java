package com.johnuckele.puzzle.crossword;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONLoader {
	public WordList loadWordListFromFileChooser() {
		String json = _loadJSONFromFileChooser();
		System.out.println("text: " + json);
		return _parseWorldlist(json);
	}

	public WordList loadWordListFromFilename(String filename) {
		String json = _loadJSONFromFilename(filename);
		System.out.println("text: " + json);
		return _parseWorldlist(json);
	}

	private String _loadJSONFromFileChooser() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setPreferredSize(new Dimension(600, 600));
			chooser.setFileFilter(new FileNameExtensionFilter("json files",
					"json"));
			int returnVal = chooser.showOpenDialog(chooser);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();
				Scanner scanner = new Scanner(file);
				String text = scanner.useDelimiter("\\A").next();
				scanner.close();
				return text;
			} else {
				throw new IllegalStateException("Picnic time.");
			}
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	private String _loadJSONFromFilename(String filename) {
		try {
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			String text = scanner.useDelimiter("\\A").next();
			scanner.close();
			return text;
		} catch (FileNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	private WordList _parseWorldlist(String json) {
		WordList words = new WordList();
		JSONArray wordsJSON = new JSONArray(json);

		for (int i = 0; i < wordsJSON.length(); i++) {
			JSONObject childJSONObject = wordsJSON.getJSONObject(i);
			String word = childJSONObject.getString("word");
			int score = childJSONObject.getInt("score");
			Word one = new Word(word, score);
			words.add(one);
		}
		return words;
	}

	public static void main(String[] args) {
		// new JSONLoader().loadWordListFromFileChooser();
		WordList words = new JSONLoader()
				.loadWordListFromFilename("src/resources/simple.json");
		
		/* debugging check
		for (int i = 0; i < words.size(); i++) {
			Word w = words.get(i);
			String s = w.toString();
			int score = w.getScore();
			System.out.println("Word List loaded: " + s + ", " + score);
		} */

		System.out.println("Total value of all words in the wordlist is: "
				+ words.getTotalScore());

	}
}
