package utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class FeatureVector
{
	private boolean toLowerCase = false;
	
	private Map<String, Double> data;

	private String spaceRegex = " ";
	private Pattern spacePattern = Pattern.compile(spaceRegex);
	
	
	public FeatureVector()
	{
		data = new HashMap<String, Double>();
	}
	public FeatureVector(String text)
	{
		if(toLowerCase)
			text = text.toLowerCase();
		
		FeatureVector temp = new FeatureVector();
		String[] toks = spacePattern.split(text);
		for(String tok : toks)
			temp.add(tok);
		
		data = temp.getMap();
	}
	public FeatureVector(Map<String,Double> mapInit)
	{
		data = mapInit;
	}

	public Map<String,Double> getMap()
	{
		return data;
	}
	
	public double getValue(String feature)
	{
		Double value = data.get(feature);
		if (value == null || Double.isInfinite(value) || Double.isNaN(value))
			return 0.0;
		return value;
	}

	public void add(String feature, double value)
	{
		if(toLowerCase)
			feature = feature.toLowerCase();
		
		Double current = data.get(feature);
		if (current == null)
			current = 0.0;
		current += value;
		data.put(feature, current);
	}
	
	public void add(FeatureVector newData)
	{
		for(String feature : newData.features())
			add(feature, newData.getValue(feature));
	}

	// increments the count of <em>feature</em> by 1
	public void add(String feature)
	{
		if(toLowerCase)
			feature = feature.toLowerCase();
		
		add(feature, 1);
	}

	public void addText(String textString)
	{
		if(toLowerCase)
			textString = textString.toLowerCase();
		
		String[] toks = spacePattern.split(textString);
		for(String tok : toks)
			add(tok);
	}
	
	public void trimToSize(int k)
	{
		List<FeatureTuple> tuples = new ArrayList<FeatureTuple>(data.keySet().size());
		for (String feature : data.keySet())
			tuples.add(new FeatureTuple(feature, data.get(feature)));

		Comparator<FeatureTuple> comparator = new FeatureTupleComparator();
		tuples.sort(comparator);

		Map<String, Double> ddata = new HashMap<String, Double>(k);
		int i = 0;
		for (FeatureTuple tuple : tuples)
		{
			if (i++ >= k)
				break;
			ddata.put(tuple.getFeature(), tuple.getValue());
			data = ddata;
		}
	}

	public Set<String> features()
	{
		return data.keySet();
	}
	
	
	public String toString()
	{
		List<FeatureTuple> tuples = new ArrayList<FeatureTuple>(data.keySet().size());
		for (String feature : data.keySet())
			tuples.add(new FeatureTuple(feature, data.get(feature)));

		Comparator<FeatureTuple> comparator = new FeatureTupleComparator();
		tuples.sort(comparator);
		StringBuilder b = new StringBuilder();
		for (FeatureTuple tuple : tuples)
			b.append(tuple.toString() + System.lineSeparator());
		return b.toString();
	}

	public static double cosine(FeatureVector x, FeatureVector y)
	{
		FeatureVector xNorm = new FeatureVector(MapUtils.l2Normalized(x.getMap()));
		FeatureVector yNorm = new FeatureVector(MapUtils.l2Normalized(y.getMap()));
		Set<String> vocab = new HashSet<String>();
		vocab.addAll(xNorm.features());
		vocab.addAll(yNorm.features());
		
		double cosine = 0.0;
		for(String feature : vocab)
			cosine += xNorm.getValue(feature) * yNorm.getValue(feature);
		return cosine;
	}
	
	public static void main(String[] args)
	{
		FeatureVector fv = new FeatureVector();
		fv.add("the", 10.0);
		fv.add("an", 10.0);
		fv.add("miles", 3.0);
		fv.add("cooper", 20.0);
		System.out.println(fv);

		fv.trimToSize(2);
		System.out.println(fv);
	}
}
