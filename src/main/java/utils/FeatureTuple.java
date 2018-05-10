package utils;

import java.text.DecimalFormat;

public class FeatureTuple
{
	private String feature;
	private double value;

	DecimalFormat decimalFormat;

	public FeatureTuple(String feature, double value)
	{
		this.feature = feature;
		this.value = value;

		decimalFormat = new DecimalFormat("#.####");
	}

	public String getFeature()
	{
		return feature;
	}

	public double getValue()
	{
		return value;
	}

	public int compareTo(FeatureTuple otherTuple)
	{
		double delta = value - otherTuple.getValue();
		if (delta > 0)
			return 1;
		else if (delta < 0)
			return -1;
		else
			return 0;
	}


	public String toString()
	{
		return (decimalFormat.format(value) + "\t" + feature);
	}
}
