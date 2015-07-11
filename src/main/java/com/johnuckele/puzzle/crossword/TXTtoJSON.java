package com.johnuckele.puzzle.crossword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
		JSONArray jsonArray = new JSONArray();

		try {
			String line;
			while ((line = reader.readLine()) != null) {
				JSONObject word = makeJSONWordObj(line);
				jsonArray.put(word);
			}
			return jsonArray;
		} finally {
			reader.close();
		}
	}

	private void saveJSONtoFile(JSONArray jsonArray, String outputFileName) throws IOException {
		FileWriter file = new FileWriter(outputFileName);
		file.write(jsonArray.toString());
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
