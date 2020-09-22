
package mhy.dict.service;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class DataAccess {	
	
	public static InputStream getStreamByUrl(String _url)
	{
		//�ո�ת��
		_url = _url.replace(" ", "%20");
		URL url;
		InputStream inputStream=null;
		try {
			url = new URL(_url);			
		    URLConnection connection = url.openConnection(); 
		    HttpURLConnection httpConnection = (HttpURLConnection)connection; //���������������
		    int responseCode = httpConnection.getResponseCode(); 
		    if (responseCode == HttpURLConnection.HTTP_OK)
		    { 
		    	inputStream = httpConnection.getInputStream(); 
		    }		    
		} catch (Exception e) {
			return null;
		}
		return inputStream;
	}
}
