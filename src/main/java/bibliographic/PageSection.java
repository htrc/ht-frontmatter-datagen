package bibliographic;

import utils.FeatureVector;

public class PageSection
{
	public enum PageSections
	{
		HEADER,
		BODY,
		FOOTER
	}
	
	private String text;
	private FeatureVector termVector;
	private FeatureVector beginCharCounts;
	private FeatureVector endCharCounts;
	private int lineCount;
	private int emptyLineCount;
	private int tokCount;
	private int capAlphaSeq;
	
	public PageSection() 
	{
		text = "";
		termVector = new FeatureVector();
		beginCharCounts = new FeatureVector();
		endCharCounts = new FeatureVector();
	}
	
	public void addTokenValue(String feature, double value)
	{
		termVector.add(feature, value);
	}
	public double getTokenValue(String feature)
	{
		return termVector.getValue(feature);
	}

	public void addBeginChar(String feature, double value)
	{
		beginCharCounts.add(feature, value);
	}
	public double getBeginChar(String feature)
	{
		return beginCharCounts.getValue(feature);
	}
	
	public void addEndChar(String feature, double value)
	{
		endCharCounts.add(feature, value);
	}
	public double getEndChar(String feature)
	{
		return endCharCounts.getValue(feature);
	}
	
	
	/**
	 * Getters and Setters
	 **/
	public int getLineCount()
	{
		return lineCount;
	}

	public void setLineCount(int lineCount)
	{
		this.lineCount = lineCount;
	}

	public int getEmptyLineCount()
	{
		return emptyLineCount;
	}

	public void setEmptyLineCount(int emptyLineCount)
	{
		this.emptyLineCount = emptyLineCount;
	}

	public int getTokCount()
	{
		return tokCount;
	}

	public void setTokCount(int tokCount)
	{
		this.tokCount = tokCount;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}

	public int getCapAlphaSeq()
	{
		return capAlphaSeq;
	}

	public void setCapAlphaSeq(int capAlphaSeq)
	{
		this.capAlphaSeq = capAlphaSeq;
	}
	
	public FeatureVector getBeginChars()
	{
		return beginCharCounts;
	}
	public FeatureVector getEndChars()
	{
		return endCharCounts;
	}
	public FeatureVector getTokenCounts()
	{
		return termVector;
	}
}
