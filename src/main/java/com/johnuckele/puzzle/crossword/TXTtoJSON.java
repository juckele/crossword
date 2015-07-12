package com.johnuckele.puzzle.crossword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TXTtoJSON {

	public void convertFile(String inputFileName, String outputFileName) {
		try {
			JSONArray jsonArray = createJSONFromFile(inputFileName);
			saveJSONtoFile(jsonArray, outputFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private JSONArray createJSONFromFile(String inputFileName) throws IOException {

		File file = new File(inputFileName);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		Set<String> inputWordSet = new HashSet<String>();

		// Load and simplify all words
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				line = stripDiacriticsAndNonLetters(line);
				if (line.length() > 2) {
					inputWordSet.add(line);
				}
			}
		} finally {
			reader.close();
		}

		// Generate a sorted list
		ArrayList<String> inputWordList = new ArrayList<String>(inputWordSet);
		Collections.sort(inputWordList);

		// Build and return the JSONArray
		JSONArray jsonArray = new JSONArray();
		for (String inputWord : inputWordList) {
			JSONObject word = makeJSONWordObj(inputWord);
			jsonArray.put(word);
		}
		return jsonArray;
	}

	private String stripDiacriticsAndNonLetters(String input) {
		String decomposedInput = Normalizer.normalize(input, Form.NFD);
		String filteredOuput = decomposedInput.toLowerCase().replaceAll("[^a-z]", "");
		return filteredOuput;
	}

	private void saveJSONtoFile(JSONArray jsonArray, String outputFileName) throws IOException {
		FileWriter file = new FileWriter(outputFileName);
		file.write(jsonArray.toString(2));
		file.flush();
		file.close();
	}

	private JSONObject makeJSONWordObj(String s) {
		int score = (int) Math.pow(s.length(), 2);

		JSONObject word = new JSONObject();
		try {
			word.put("word", s);
			word.put("score", score);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return word;
	}

	public static void main(String[] args) {
		System.out.println("Converting file to a json rep of a WordList");
		TXTtoJSON converter = new TXTtoJSON();
		converter.convertFile("src/main/resources/words.txt", "src/main/resources/filler.json");
		System.out.println("Conversion finished");
	}
}
