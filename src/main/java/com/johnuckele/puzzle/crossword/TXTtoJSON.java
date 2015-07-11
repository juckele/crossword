package com.johnuckele.puzzle.crossword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TXTtoJSON {

	public static JSONObject makeJSONWordObj(String s) {
		int score = (int) Math.pow(s.length(), 2);

		JSONObject word = new JSONObject();
		try {
			word.put("word", s);
			word.put("score", score);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return word;
	}

	public static void main(String[] args) {

		JSONArray jsonArray = new JSONArray();

		BufferedReader reader = null;

		try {
			File file = new File("src/main/resources/words.txt");
			reader = new BufferedReader(new FileReader(file));

			String line;
			while ((line = reader.readLine()) != null) {
				JSONObject word = makeJSONWordObj(line);
				jsonArray.put(word);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {

			FileWriter file = new FileWriter("src/main/resources/test.json");
			file.write(jsonArray.toString());

			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.print(jsonArray);

	}
}
