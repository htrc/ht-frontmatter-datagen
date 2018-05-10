package bibliographic;

import bibliographic.PageSection.PageSections;
import utils.FeatureVector;

public class Page
{
	private String seqString; // the raw, unchanged seq element from the JSON
	private int seq; // parsed and coerced into an integer

	private PageSection header;
	private PageSection body;
	private PageSection footer;

	private int lineCount;
	private int emptyLineCount;
	private int capAlphaSeq;
	private int sentenceCount;

	public Page() 
	{
		header = new PageSection();
		body   = new PageSection();
		footer = new PageSection();
	}
	
	public void addTokenValue(PageSections section, String feature, double value)
	{
		switch(section)
		{
			case HEADER:
				header.addTokenValue(feature, value);
			case BODY:
				body.addTokenValue(feature, value);
			case FOOTER:
				footer.addTokenValue(feature, value);
		}
	}
	
	public double getTokenValue(PageSections section, String feature)
	{
		switch(section)
		{
			case HEADER:
				return header.getTokenValue(feature);
			case BODY:
				return body.getTokenValue(feature);
			case FOOTER:
				return footer.getTokenValue(feature);
		}
		return -1;
	}

	public void addBeginChar(PageSections section, String feature, double value)
	{
		switch(section)
		{
			case HEADER:
				header.addBeginChar(feature, value);
			case BODY:
				body.addBeginChar(feature, value);
			case FOOTER:
				footer.addBeginChar(feature, value);
		}
	}
	
	public double getBeginChar(PageSections section, String feature)
	{
		switch(section)
		{
			case HEADER:
				return header.getBeginChar(feature);
			case BODY:
				return body.getBeginChar(feature);
			case FOOTER:
				return footer.getBeginChar(feature);
		}
		return -1;
	}
	
	public FeatureVector getBeginCharVector(PageSections section)
	{
		switch(section)
		{
			case HEADER:
				return header.getBeginChars();
			case BODY:
				return body.getBeginChars();
			case FOOTER:
				return footer.getBeginChars();
		}
		return null;
	}
	
	public void addEndChar(PageSections section, String feature, double value)
	{
		switch(section)
		{
			case HEADER:
				header.addEndChar(feature, value);
			case BODY:
				body.addEndChar(feature, value);
			case FOOTER:
				footer.addEndChar(feature, value);
		}
	}
	
	public double getEndChar(PageSections section, String feature)
	{
		switch(section)
		{
			case HEADER:
				return header.getEndChar(feature);
			case BODY:
				return body.getEndChar(feature);
			case FOOTER:
				return footer.getEndChar(feature);
		}
		return -1;
	}
	
	public FeatureVector getEndCharVector(PageSections section)
	{
		switch(section)
		{
			case HEADER:
				return header.getEndChars();
			case BODY:
				return body.getEndChars();
			case FOOTER:
				return footer.getEndChars();
		}
		return null;
	}
	
	public int getTokCount(PageSections section)
	{
		switch(section)
		{
			case HEADER:
				return header.getTokCount();
			case BODY:
				return body.getTokCount();
			case FOOTER:
				return footer.getTokCount();
		}
		return -1;
	}
	public void setTokCount(PageSections section, int tokCount)
	{
		switch(section)
		{
			case HEADER:
				header.setTokCount(tokCount);
			case BODY:
				body.setTokCount(tokCount);
			case FOOTER:
				footer.setTokCount(tokCount);
		}
	}

	public int getLineCount(PageSections section)
	{
		switch(section)
		{
			case HEADER:
				return header.getLineCount();
			case BODY:
				return body.getLineCount();
			case FOOTER:
				return footer.getLineCount();
		}
		return -1;
	}
	public void setLineCount(PageSections section, int lineCount)
	{
		switch(section)
		{
			case HEADER:
				header.setLineCount(lineCount);
			case BODY:
				body.setLineCount(lineCount);
			case FOOTER:
				footer.setLineCount(lineCount);
		}
	}
	

	public int getEmptyLineCount(PageSections section)
	{
		switch(section)
		{
			case HEADER:
				return header.getEmptyLineCount();
			case BODY:
				return body.getEmptyLineCount();
			case FOOTER:
				return footer.getEmptyLineCount();
		}
		return -1;
	}
	public void setEmptyLineCount(PageSections section, int lineCount)
	{
		switch(section)
		{
			case HEADER:
				header.setEmptyLineCount(lineCount);
			case BODY:
				body.setEmptyLineCount(lineCount);
			case FOOTER:
				footer.setEmptyLineCount(lineCount);
		}
	}

	public int getCapAlphaSeq(PageSections section)
	{
		switch(section)
		{
			case HEADER:
				return header.getCapAlphaSeq();
			case BODY:
				return body.getCapAlphaSeq();
			case FOOTER:
				return footer.getCapAlphaSeq();
		}
		return -1;
	}
	public void setCapAlphaSeq(PageSections section, int lineCount)
	{
		switch(section)
		{
			case HEADER:
				header.setCapAlphaSeq(lineCount);
			case BODY:
				body.setCapAlphaSeq(lineCount);
			case FOOTER:
				footer.setCapAlphaSeq(lineCount);
		}
	}
	

	public String getSeqString()
	{
		return seqString;
	}
	public void setSeqString(String seqString)
	{
		this.seqString = seqString;
	}
	public int getSeq()
	{
		return seq;
	}
	public void setSeq(int seq)
	{
		this.seq = seq;
	}

	
	
	public int getLineCountGlobal()
	{
		return lineCount;
	}

	public void setLineCountGlobal(int lineCountGlobal)
	{
		this.lineCount = lineCountGlobal;
	}

	public int getEmptyLineCountGlobal()
	{
		return emptyLineCount;
	}

	public void setEmptyLineCountGlobal(int emptyLineCountGlobal)
	{
		this.emptyLineCount = emptyLineCountGlobal;
	}

	public int getCapAlphaSeq()
	{
		return capAlphaSeq;
	}

	public void setCapAlphaSeq(int capAlphaSeq)
	{
		this.capAlphaSeq = capAlphaSeq;
	}
	public void setSentenceCount(int sentenceCount)
	{
		this.sentenceCount = sentenceCount;
	}
	public int getSentenceCount() 
	{
		return sentenceCount;
	}
	
	public PageSection getSection(PageSections section)
	{
		switch(section)
		{
			case HEADER:
				return header;
			case BODY:
				return body;
			case FOOTER:
				return footer;
			default:
				return body;
		}
	}
	
	
	public String toString() 
	{
		StringBuilder b = new StringBuilder();
		b.append("<SEQ " + seq + ">\n");
		
		b.append("<header>\n");
		b.append("Begin chars...\n");
		b.append(header.getBeginChars() + "\n");
		b.append("End chars...\n");
		b.append(header.getEndChars() + "\n");
		b.append("Tokens...\n");
		b.append(header.getTokenCounts() + "\n</header>\n");
		
		b.append("<body>\n");
		b.append("Begin chars...\n");
		b.append(body.getBeginChars() + "\n");
		b.append("End chars...\n");
		b.append(body.getEndChars() + "\n");
		b.append("Tokens...\n");
		b.append(body.getTokenCounts() + "\n</body>\n");
		
		b.append("<footer>\n");
		b.append("Begin chars...\n");
		b.append(footer.getBeginChars() + "\n");
		b.append("End chars...\n");
		b.append(footer.getEndChars() + "\n");
		b.append("Tokens...\n");
		b.append(footer.getTokenCounts() + "\n</footer>\n");
		
		b.append("</SEQ " + seq + ">\n");
		return b.toString();
	}
}
