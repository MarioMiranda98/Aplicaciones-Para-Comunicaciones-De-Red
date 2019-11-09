import java.util.*;

public class Mime {
	public static HashMap<String, String> mimeTypes;

	public Mime() {
		mimeTypes = new HashMap<>();
		mimeTypes.put("doc", "application/msword");
		mimeTypes.put("pdf", "application/pdf");
		mimeTypes.put("rar", "application/x-rar-compressed");
		mimeTypes.put("mp3", "audio/mpeg");
		mimeTypes.put("jpg", "image/jpeg");
		mimeTypes.put("jpeg", "image/jpeg");
		mimeTypes.put("png", "image/png");
		mimeTypes.put("html", "text/html");
		mimeTypes.put("htm", "text/html");
		mimeTypes.put("c", "text/plain");
		mimeTypes.put("txt", "text/plain");
		mimeTypes.put("java", "text/plain");
		mimeTypes.put("mp4", "video/mp4");
	}

	public String get(String extension) {
		if(mimeTypes.containsKey(extension))
			return mimeTypes.get(extension);
		else
			return "application/octet-stream";
	}

}