package pageFeatures;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import bibliographic.Page;
import bibliographic.PageSection.PageSections;
import bibliographic.Volume;
import utils.FeatureVector;
import utils.MapUtils;

public class PageFeatures
{
	private Volume volume;
	
	private 	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private Date now = new Date();
	
	private String capitalRegex = "[A-Z]";
	Pattern capitalPattern = Pattern.compile(capitalRegex);
	private String digitRegex = "[0-9]";
	Pattern digitPattern = Pattern.compile(digitRegex);
	
	private String romanNumeralRegex = "^(?=[MDCLXVI])M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$";
	Pattern romanNumeralPattern = Pattern.compile(romanNumeralRegex, Pattern.CASE_INSENSITIVE);
	
	
	public PageFeatures() 
	{
		try
		{
			now = df.parse("2018-01-01 00:00:00");
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	public double sequenceFeature(Page page)
	{
		return (double)page.getSeq();
	}
	
	public double logSequenceFeature(Page page)
	{
		double seq = sequenceFeature(page);
		if(seq == 0.0)
			return 0.0;
		return Math.log(seq);
	}
	
	public double sequenceSquaredFeature(Page page)
	{
		return Math.pow((double)page.getSeq(), 2.0);
	}
	
	public double sequenceProportion(Page page)
	{
		double totalPages = volume.getPageCount();
		if(totalPages < 1)
			totalPages = 1.0;
		return page.getSeq() / totalPages;
	}
	
	public double tokenCountFeature(Page page)
	{
		return page.getTokCount(PageSections.BODY);
	}
	public double normalizedTokCountFeature(Page page)
	{
		if(volume == null)
		{
			System.err.println("Can't calculate normalized token count with null Volume!");
			System.exit(1);
		}
		double mu = volume.getMeanBodyTokCount();
		double sd = volume.getStdBodyTokCount();
		if(sd == 0.0)
		{
			System.err.println("Current Volume has 0 std dev body token counts...volumeID=" + volume.getID());
			//System.exit(1);
			sd = 1.0;
		}
		return (tokenCountFeature(page) - mu) / sd;
	}
	
	public double lineCountFeature(Page page)
	{
		return page.getLineCount(PageSections.BODY);
	}
	public double normalizedLineCountFeature(Page page)
	{
		if(volume == null)
		{
			System.err.println("Can't calculate normalized line count with null Volume!");
			System.exit(1);
		}
		double mu = volume.getMeanLineCount();
		double sd = volume.getStdLineCount();
		if(sd == 0.0)
		{
			System.err.println("Current Volume has 0 std dev line counts counts...volumeID=" + volume.getID());
			//System.exit(1);
			sd = 1.0;
		}
		return (lineCountFeature(page) - mu) / sd;
	}
	
	public double emptyLineCountFeature(Page page)
	{
		return page.getEmptyLineCount(PageSections.BODY);
	}
	public double normalizedEmptyLineCountFeature(Page page)
	{
		if(volume == null)
		{
			System.err.println("Can't calculate normalized empty line count with null Volume!");
			System.exit(1);
		}
		double mu = volume.getMeanEmptyLineCount();
		double sd = volume.getStdEmptyLineCount();
		if(sd == 0.0)
		{
			System.err.println("Current Volume has 0 std dev empty line counts...volumeID=" + volume.getID());
			//System.exit(1);
			sd = 1.0;
		}
		return (emptyLineCountFeature(page) - mu) / sd;
	}
	
	public double sentenceCountFeature(Page page)
	{
		return page.getSentenceCount();
	}
	
	public double capAlphaSeqFeature(Page page)
	{
		return (double)page.getCapAlphaSeq(PageSections.BODY);
	}
	
	public double pctBeginCharCapsFeature(Page page)
	{
		Map<String,Double> x = page.getBeginCharVector(PageSections.BODY).getMap();
		double sum = MapUtils.sum(x);
		if(sum == 0.0)
			return 0.0;
		
		double caps = 0.0;
		
		for(String key : x.keySet())
		{
			if(capitalPattern.matcher(key).matches())
				caps += 1.0;
		}
		return caps / sum;
	}
	
	public double pctEndCharDigitsFeature(Page page)
	{
		Map<String,Double> x = page.getEndCharVector(PageSections.BODY).getMap();
		double sum = MapUtils.sum(x);
		if(sum == 0.0)
			return 0.0;
		
		double digits = 0.0;
		
		for(String key : x.keySet())
		{
			if(digitPattern.matcher(key).matches())
				digits += 1.0;
		}
		return digits / sum;
	}
	
	public double romanNumeralsFeature(Page page)
	{
		double obs = 0.0;
		FeatureVector section = page.getSection(PageSections.BODY).getTokenCounts();
		for(String feature : section.features())
		{
			if(feature.length() < 2)
				continue;
			if(romanNumeralPattern.matcher(feature).matches())
			{
				//System.err.println("Matched (BODY): " + feature);
				obs += section.getValue(feature);
			}
		}
		
		section = page.getSection(PageSections.HEADER).getTokenCounts();
		for(String feature : section.features())
		{
			if(feature.length() < 2)
				continue;
			if(romanNumeralPattern.matcher(feature).matches())
			{
				//System.err.println("Matched (HEADER): " + feature);
				obs += section.getValue(feature);
			}
		}
		
		section = page.getSection(PageSections.FOOTER).getTokenCounts();
		for(String feature : section.features())
		{
			if(feature.length() < 2)
				continue;
			if(romanNumeralPattern.matcher(feature).matches())
			{
				//System.err.println("Matched (FOOTER): " + feature);
				obs += section.getValue(feature);
			}
		}
		
		return obs;
	}
	
	public double textSimilarityFeature(Page page, String referenceText)
	{
		FeatureVector referenceVector = new FeatureVector(referenceText);
		double cos = FeatureVector.cosine(page.getSection(PageSections.BODY).getTokenCounts(), referenceVector);
		return cos;
	}
	
	public double containsBibNames(Page page)
	{
		Set<String> bibNames = volume.getBibNames();
		if(bibNames == null || bibNames.size()==0)
			return 0.0;

		FeatureVector bibNamesVector = new FeatureVector();
		for(String bibName : bibNames)
			bibNamesVector.addText(bibName);
		

		FeatureVector pageVector = new FeatureVector();
		pageVector.add(page.getSection(PageSections.HEADER).getTokenCounts());
		pageVector.add(page.getSection(PageSections.BODY).getTokenCounts());
		pageVector.add(page.getSection(PageSections.FOOTER).getTokenCounts());
		
		double count = 0.0;
		for(String tok : bibNamesVector.features())
		{
			if(pageVector.getValue(tok) > 0)
				count += 1.0;	
		}
		
		//System.err.println(bibNames + ": " + count + ", " + bibNamesVector.features().size());
		return count / bibNamesVector.features().size();
	}
	
	public double seenChapterFeature(Page page, Volume volume)
	{
		if(volume.seenChapter())
			return 1.0;
		
		boolean isChapter = false;
		FeatureVector text = page.getSection(PageSections.HEADER).getTokenCounts();
		//if(text.getValue("chapter") > 0 || text.getValue("CHAPTER") > 0)
		if(text.getValue("chapter") == 1)
		{
			isChapter = true;
			volume.setSeenChapter(isChapter);
			return 1.0;
		}
		text = page.getSection(PageSections.BODY).getTokenCounts();
		//if(text.getValue("Chapter") > 0 || text.getValue("CHAPTER") > 0)
		if(text.getValue("chapter") == 1)
		{
			isChapter = true;
			volume.setSeenChapter(isChapter);
			return 1.0;
		}
		text = page.getSection(PageSections.FOOTER).getTokenCounts();
		//if(text.getValue("Chapter") > 0 || text.getValue("CHAPTER") > 0)
		if(text.getValue("chapter") == 1)
		{
			isChapter = true;
			volume.setSeenChapter(isChapter);
			return 1.0;
		}
		return 0.0;
	}
	
	public double pctToksAllCaps(Page page)
	{
		FeatureVector text = page.getSection(PageSections.BODY).getTokenCounts();
		//System.err.println(text);
		
		double count = 0.0;
		double total = 0.0;
		for(String tok : text.features())
		{
			total += text.getValue(tok);
			String upper = tok.toUpperCase();
			if(tok.equals(upper))
				count++;
		}
		if(total < 1)
			total = 1;
		
		return count / total;
	}
	
	public double dateAgeFeature()
	{
		long delta = (now.getTime() - volume.getLastUpdate().getTime()) / (1000 * 1000);
		//System.err.println(now + "\t" + volume.getLastUpdate() + "\t->" + delta);
		return delta;
	}
	
	public void setCurrentVolume(Volume volume)
	{
		this.volume = volume;
	}
	
	
}
