package io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

public class IOUtils
{

	public static String getBzippedText(String pathToBzippedFile)
	{
		InputStream is;
		String text = null;
		try
		{
			is = Files.newInputStream(Paths.get(pathToBzippedFile));
			BufferedInputStream bis = new BufferedInputStream(is);
			CompressorInputStream input = new CompressorStreamFactory().createCompressorInputStream(bis);
			BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			text = br.readLine();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return text;
	}
}
