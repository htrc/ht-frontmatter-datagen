package bibliographic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import bibliographic.PageSection.PageSections;
import io.IOUtils;
import pageFeatures.GroundTruth;
import pageFeatures.PageFeatures;
import utils.Formatting;

public class HTRCExtractedFeatures
{
	private GroundTruth groundTruth;
	private int maxPagesPerVolume = 30;

	public void readFromFile(String pathToZippedJson)
	{
		Volume volume = null;

		String json = IOUtils.getBzippedText(pathToZippedJson);

		try
		{
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(json);

			String id = Formatting.formatVolumeID((String)jsonObject.get("id"));


			JSONObject metadataObject = (JSONObject) jsonObject.get("metadata");
			String title = (String) metadataObject.get("title");
			String imprint = (String) metadataObject.get("imprint");
			String lastUpdated = (String) metadataObject.get("lastUpdateDate");
			JSONArray bibNames = (JSONArray) metadataObject.get("names"); // e.g. "names":["Nickolls, John Sir b. 1722 "]
				
			
			JSONObject featuresObject = (JSONObject) jsonObject.get("features");
			long pageCount = (Long) featuresObject.get("pageCount");


			volume = new Volume((int)pageCount);
			volume.setTitle(title);
			volume.setPageCount((int)pageCount);
			volume.setImprint(imprint);
			volume.setLastUpdate(lastUpdated);
			volume.setID(id);

			for(Object bibName : bibNames)
				volume.addBibName((String)bibName);
			
			PageFeatures pf = new PageFeatures();
			pf.setCurrentVolume(volume);

			JSONArray pagesObject = (JSONArray) featuresObject.get("pages");
			for (int i = 0; i < pagesObject.size(); i++)
			{
				if(i >= maxPagesPerVolume)
					break;

				Page page = new Page();

				JSONObject pageJSON = (JSONObject) pagesObject.get(i);
				page.setSeq(Integer.parseInt((String) pageJSON.get("seq")));

				JSONObject header = (JSONObject) pageJSON.get("header");
				page.setTokCount(PageSections.HEADER, ((Long) header.get("tokenCount")).intValue());
				page.setCapAlphaSeq(PageSections.HEADER, ((Long)header.get("capAlphaSeq")).intValue());

				JSONObject body = (JSONObject) pageJSON.get("body");
				page.setTokCount(PageSections.BODY, ((Long)body.get("tokenCount")).intValue());
				page.setCapAlphaSeq(PageSections.BODY, ((Long)body.get("capAlphaSeq")).intValue());
				page.setLineCount(PageSections.BODY, ((Long)body.get("lineCount")).intValue());
				page.setEmptyLineCount(PageSections.BODY, ((Long)body.get("emptyLineCount")).intValue());
				page.setSentenceCount(((Long)pageJSON.get("sentenceCount")).intValue());
				
				JSONObject footer = (JSONObject) pageJSON.get("footer");
				page.setTokCount(PageSections.FOOTER, ((Long) footer.get("tokenCount")).intValue());
				page.setCapAlphaSeq(PageSections.FOOTER, ((Long)footer.get("capAlphaSeq")).intValue());


				// beginCharCounts
				JSONObject beginCharCountsObj = (JSONObject) body.get("beginCharCounts");
				for (Object key : beginCharCountsObj.keySet())
					page.addBeginChar(PageSections.BODY, (String)key, (double)((Long) beginCharCountsObj.get(key)));

				beginCharCountsObj = (JSONObject) header.get("beginCharCounts");
				for (Object key : beginCharCountsObj.keySet())
					page.addBeginChar(PageSections.HEADER, (String)key, (double)((Long) beginCharCountsObj.get(key)));

				beginCharCountsObj = (JSONObject) footer.get("beginCharCounts");
				for (Object key : beginCharCountsObj.keySet())
					page.addBeginChar(PageSections.FOOTER, (String)key, (double)((Long) beginCharCountsObj.get(key)));

				// endCharCounts
				JSONObject endCharCountsObj = (JSONObject) body.get("endCharCount");
				if(endCharCountsObj != null)
				{
					for (Object key : endCharCountsObj.keySet())
						page.addEndChar(PageSections.BODY, (String)key, (double)((Long) endCharCountsObj.get(key)));
				}

				endCharCountsObj = (JSONObject) header.get("endCharCount");
				if(endCharCountsObj != null)
				{
					for (Object key : endCharCountsObj.keySet())
						page.addEndChar(PageSections.HEADER, (String)key, (double)((Long) endCharCountsObj.get(key)));
				}

				endCharCountsObj = (JSONObject) footer.get("endCharCount");
				if(endCharCountsObj != null)
				{
					for (Object key : endCharCountsObj.keySet())
						page.addEndChar(PageSections.FOOTER, (String)key, (double)((Long) endCharCountsObj.get(key)));
				}

				// tokens
				JSONObject tokenCountsObj = (JSONObject) body.get("tokenPosCount");
				if(tokenCountsObj != null)
				{
					Map<String, Long> tokenCounts = new HashMap<String, Long>();
					for (Object key : tokenCountsObj.keySet())
					{
						String token = key.toString(); //.toLowerCase();
						JSONObject tuple = (JSONObject) tokenCountsObj.get(key);
						for (Object pos : tuple.keySet())
						{
							Long count = (Long) tuple.get(pos);
							Long curr = tokenCounts.get(token);
							if (curr == null)
								curr = 0l;
							curr += count;
							tokenCounts.put((String) token, curr);
						}
					}
					for(String feature : tokenCounts.keySet())
						page.addTokenValue(PageSections.BODY, feature, tokenCounts.get(feature));
				}
				volume.addPage(page);
			}



			List<Page> pages = volume.getPages();
			for(Page page : pages)
			{
				//System.err.println("Roman Numerals -> " + pf.romanNumeralsFeature(page));
				pf.romanNumeralsFeature(page);
				
				String label = groundTruth.getLabel(volume.getID(), page.getSeq());
				if(label == null)
					continue;

				

				System.out.print(volume.getID() + " ");
				System.out.print(pf.sequenceFeature(page) + " ");
				System.out.print(pf.logSequenceFeature(page) + " ");
				System.out.print(pf.sequenceSquaredFeature(page) + " ");
				System.out.print(pf.sequenceProportion(page) + " ");
				System.out.print(pf.tokenCountFeature(page) + " ");
				System.out.print(pf.normalizedTokCountFeature(page) + " ");
				System.out.print(pf.lineCountFeature(page) + " ");
				//System.out.print(pf.normalizedLineCountFeature(page) + " ");
				System.out.print(pf.emptyLineCountFeature(page) + " ");
				System.out.print(pf.normalizedEmptyLineCountFeature(page) + " ");
				System.out.print(pf.capAlphaSeqFeature(page) + " ");
				System.out.print(pf.pctBeginCharCapsFeature(page) + " ");
				System.out.print(pf.pctEndCharDigitsFeature(page) + " ");
				System.out.print(pf.romanNumeralsFeature(page) + " ");
				//System.out.print(pf.textSimilarityFeature(page, imprint) + " ");
				//System.out.print(pf.seenChapterFeature(page, volume) + " ");
				//System.out.print(pf.containsBibNames(page) + " ");
				//System.out.print(pf.dateAgeFeature() + " ");
				System.out.print(pf.pctToksAllCaps(page) + " ");
				System.out.print(pf.sentenceCountFeature(page) + " ");
				System.out.println(label);


			}


		} catch (ParseException e)
		{
			e.printStackTrace();
		}



	}

	public void setGroundTruth(GroundTruth groundTruth)
	{
		this.groundTruth = groundTruth;
	}
}
