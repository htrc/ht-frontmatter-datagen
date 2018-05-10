package utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils
{

	public static double sum(Map<String, Double> x)
	{
		double sum = 0.0;
		for (String key : x.keySet())
			sum += x.get(key);
		return sum;
	}

	public static double vectorNorm(Map<String, Double> x)
	{
		double norm = 0.0;
		for (String key : x.keySet())
			norm += Math.pow(x.get(key), 2.0);
		return norm;
	}
	public static Map<String,Double> l2Normalized(Map<String, Double> x)
	{
		Map<String,Double> normalized = new HashMap<String,Double>(x.size());
		double norm = MapUtils.vectorNorm(x);
		if(norm == 0.0)
			norm = 1.0;
		
		for (String key : x.keySet())
			normalized.put(key, x.get(key)/norm);
		return normalized;
	}
	
	public static Map<String, Double> transformSumToOne(Map<String, Double> x)
	{
		double sum = MapUtils.sum(x);
		Map<String, Double> normalized = new HashMap<String, Double>();
		for (String key : x.keySet())
			normalized.put(key, x.get(key) / sum);
		return normalized;
	}
}
