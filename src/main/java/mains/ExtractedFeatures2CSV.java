package mains;

import java.io.FileInputStream;
import java.util.Scanner;

import bibliographic.HTRCExtractedFeatures;
import pageFeatures.GroundTruth;;

public class ExtractedFeatures2CSV
{

	public static void main(String[] args) throws Exception
	{
		if(args.length != 2) 
		{
			throw new Exception("usage: <me> pathToListOfFiles pathToGroundTruth");
		}
		String pathToListOfFiles = args[0];
		String pathToGroundTruth = args[1];
		
		GroundTruth groundTruth = new GroundTruth();
		groundTruth.readFromFile(pathToGroundTruth);
		
		HTRCExtractedFeatures ef = new HTRCExtractedFeatures();
		ef.setGroundTruth(groundTruth);
		
		
		String[] varNames = {
				"volume",
				"seq",
				"log_seq",
				"squared_seq",
				"pct_seq",
				"token_count",
				"token_count_normalized",
				"line_count",
				//"line_count_normalized",
				"empty_line_count",
				"empty_line_count_normalized",
				"cap_alpha_seq",
				"pct_begin_char_caps",
				"pct_end_char_numeric",
				"num_roman_numerals",
				//"imprint_similarity",
				//"pct_bib_names"
				//"seen_chapter"
				//"update_time"
				"pct_all_caps",
				"sentence_count"
		};
		for(int i=0; i<varNames.length; i++)
			System.out.print(varNames[i] + " ");
		System.out.println("target");
		
		Scanner in = new Scanner(new FileInputStream(pathToListOfFiles));
		while(in.hasNextLine())
		{
			String pathToZippedJson = in.nextLine();
			ef.readFromFile(pathToZippedJson);
		}
		in.close();
	}
}
