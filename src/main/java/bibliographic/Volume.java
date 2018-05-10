package bibliographic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.stat.descriptive.UnivariateStatistic;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import bibliographic.PageSection.PageSections;

public class Volume
{
	private String id;
	private String title;
	private String author;
	private String pubInfo;
	private String imprint;
	private Set<String> bibNames;
	private Date lastUpdate;
	
	private int pageCount = -1;
	private List<Page> pages;

	private double meanTokCount = -1.0;
	private double stdTokCount  = -1.0;

	private double meanLineCount = -1.0;
	private double stdLineCount  = -1.0;
	
	private double meanEmptyLineCount = -1.0;
	private double stdEmptyLineCount  = -1.0;
	
	private boolean seenChapter = false;
	
	public void addPage(Page page)
	{
		pages.add(page);
	}
	
	public double getMeanBodyTokCount() 
	{
		if(meanTokCount >= 0)
			return meanTokCount;
		
		if(pages == null || pages.size() < 1)
		{
			meanTokCount = 0.0;
			return meanTokCount;
		}
		
		double[] tokCounts = new double[pages.size()];
		for(int i=0; i<pages.size(); i++)
		{
			Page page = pages.get(i);
			tokCounts[i] = page.getTokCount(PageSections.BODY);
		}
		
		UnivariateStatistic stat = new Mean();
		meanTokCount = stat.evaluate(tokCounts);
		return meanTokCount;
	}
	
	public double getStdBodyTokCount() 
	{
		if(stdTokCount >= 0)
			return stdTokCount;
		
		if(pages == null || pages.size() < 1)
		{
			stdTokCount = 0.0;
			return stdTokCount;
		}
		
		double[] tokCounts = new double[pages.size()];
		for(int i=0; i<pages.size(); i++)
		{
			Page page = pages.get(i);
			tokCounts[i] = page.getTokCount(PageSections.BODY);
		}
		
		UnivariateStatistic stat = new StandardDeviation();
		stdTokCount = stat.evaluate(tokCounts);
		return stdTokCount;
	}
	
	
	public double getMeanLineCount() 
	{
		if(meanLineCount >= 0)
			return meanLineCount;
		
		if(pages == null || pages.size() < 1)
		{
			meanLineCount = 0.0;
			return meanLineCount;
		}
		
		double[] counts = new double[pages.size()];
		for(int i=0; i<pages.size(); i++)
		{
			Page page = pages.get(i);
			counts[i] = page.getLineCount(PageSections.BODY);
		}
		
		UnivariateStatistic stat = new Mean();
		meanLineCount = stat.evaluate(counts);
		return meanLineCount;
	}
	
	public double getStdLineCount() 
	{
		if(stdLineCount >= 0)
			return stdLineCount;
		
		if(pages == null || pages.size() < 1)
		{
			stdLineCount = 0.0;
			return stdLineCount;
		}
		
		double[] counts = new double[pages.size()];
		for(int i=0; i<pages.size(); i++)
		{
			Page page = pages.get(i);
			counts[i] = page.getLineCount(PageSections.BODY);
		}
		
		UnivariateStatistic stat = new StandardDeviation();
		stdLineCount = stat.evaluate(counts);
		return stdLineCount;
	}
	
	public double getMeanEmptyLineCount() 
	{
		if(meanEmptyLineCount >= 0)
			return meanEmptyLineCount;
		
		if(pages == null || pages.size() < 1)
		{
			meanEmptyLineCount = 0.0;
			return meanEmptyLineCount;
		}
		
		double[] counts = new double[pages.size()];
		for(int i=0; i<pages.size(); i++)
		{
			Page page = pages.get(i);
			counts[i] = page.getEmptyLineCount(PageSections.BODY);
		}
		
		UnivariateStatistic stat = new Mean();
		meanEmptyLineCount = stat.evaluate(counts);
		return meanEmptyLineCount;
	}
	
	public double getStdEmptyLineCount() 
	{
		if(stdEmptyLineCount >= 0)
			return stdEmptyLineCount;
		
		if(pages == null || pages.size() < 1)
		{
			stdEmptyLineCount = 0.0;
			return stdEmptyLineCount;
		}
		
		double[] counts = new double[pages.size()];
		for(int i=0; i<pages.size(); i++)
		{
			Page page = pages.get(i);
			counts[i] = page.getEmptyLineCount(PageSections.BODY);
		}
		
		UnivariateStatistic stat = new StandardDeviation();
		stdEmptyLineCount = stat.evaluate(counts);
		return stdEmptyLineCount;
	}
	
	public boolean seenChapter()
	{
		return seenChapter;
	}
	public void setSeenChapter(boolean seenChapter)
	{
		this.seenChapter = seenChapter;
	}
	
	public void addBibName(String bibName)
	{
		bibNames.add(bibName);
	}
	public Set<String> getBibNames()
	{
		return bibNames;
	}
	
	/** Constructors **/
	public Volume()
	{
		pages = new ArrayList<Page>();
		bibNames = new HashSet<String>();
	}
	public Volume(int pageCount)
	{
		pages = new ArrayList<Page>(pageCount);
		bibNames = new HashSet<String>();
	}
	
	
	/** Getters and Setters **/

	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}
	public String getPubInfo()
	{
		return pubInfo;
	}
	public void setPubInfo(String pubInfo)
	{
		this.author = pubInfo;
	}
	public int getPageCount()
	{
		return pageCount;
	}
	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}
	public List<Page> getPages()
	{
		return pages;
	}

	public String getImprint()
	{
		return imprint;
	}
	public void setImprint(String imprint)
	{
		this.imprint = imprint;
	}

	public Date getLastUpdate()
	{
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try
		{
			this.lastUpdate = df.parse(lastUpdate);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	public void setID(String id)
	{
		this.id = id;
	}
	public String getID()
	{
		return id;
	}
	
	
}
