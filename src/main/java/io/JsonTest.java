package io;

import java.io.IOException;

import org.apache.commons.compress.compressors.CompressorException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonTest
{

	public static void main(String[] args) throws IOException, CompressorException
	{
		String pathToZippedJson = "/Users/mefron/work/htrc/dbamman/extracted_features/aeu.ark+=13960=t0000ds9d.json.bz2";
		String json = IOUtils.getBzippedText(pathToZippedJson);
		System.out.println(json);

		JSONParser parser = new JSONParser();
		try
		{
			JSONObject jsonObject = (JSONObject) parser.parse(json);
			JSONObject metadataObject = (JSONObject) jsonObject.get("metadata");
			System.out.println(metadataObject.get("title"));

			JSONObject featuresObject = (JSONObject) jsonObject.get("features");
			System.out.println(featuresObject.get("pageCount"));

			JSONArray pagesObject = (JSONArray) featuresObject.get("pages");
			System.out.println("pages: " + pagesObject.size());
			for (int i = 0; i < pagesObject.size(); i++)
			{
				System.out.println(pagesObject.get(i));
			}
		} catch (ParseException e)
		{
			e.printStackTrace();
		}

	}
}
