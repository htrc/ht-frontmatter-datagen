package utils;

public class Formatting
{
	public static String formatVolumeID(String id)
	{
		id = id.replaceAll(":", "+");
		id = id.replaceAll("/", "=");
		return id;
	}
}
