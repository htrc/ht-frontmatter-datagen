package pageFeatures;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GroundTruth
{
	private Map<String,Map<Integer,String>> groundTruth;
	
	public GroundTruth()
	{
		groundTruth = new HashMap<String,Map<Integer,String>>();
	}
	
	public void readFromFile(String pathToFile)
	{
		try
		{
			Scanner in = new Scanner(new FileInputStream(pathToFile));
			while(in.hasNext())
			{
				String line = in.nextLine();
				String[] fields = line.split("\t");
				String volumeID = fields[0];
				int seq = Integer.parseInt(fields[1]);
				String label = fields[2];
				
				Map<Integer,String> labelsForVolume = groundTruth.get(volumeID);
				if(labelsForVolume == null)
					labelsForVolume = new HashMap<Integer,String>();
				labelsForVolume.put(seq, label);
				groundTruth.put(volumeID, labelsForVolume);
			}
			in.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getLabel(String volumeID, Integer seq)
	{
		Map<Integer,String> labelsForVolume = groundTruth.get(volumeID);
		if(labelsForVolume == null)
		{
			System.err.println("No ground truth labels for volume: " + volumeID);
			return null;
		}
		String label = labelsForVolume.get(seq);
		if(label == null)
		{
			System.err.println("No ground truth for volume " + volumeID + ", seq " + seq);
		}
		//if(label == null || ! label.equals("open") || ! label.equals("closed"))
		//	return null;
		
		return label;
	}
}
