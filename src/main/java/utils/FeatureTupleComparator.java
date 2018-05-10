package utils;

import java.util.Comparator;

public class FeatureTupleComparator implements Comparator<FeatureTuple>
{

	public int compare(FeatureTuple x, FeatureTuple y)
	{
		double delta = x.getValue() - y.getValue();
		if (delta < 0)
			return 1;
		else if (delta > 0)
			return -1;
		else
			return 0;
	}

}
