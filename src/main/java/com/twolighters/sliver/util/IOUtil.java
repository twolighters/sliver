package com.twolighters.sliver.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class IOUtil
{
	
	public static void getRemoteContent(URLConnection conn, String destPath) throws IOException
	{
		InputStream is = conn.getInputStream();
		FileOutputStream os = new FileOutputStream(destPath);
		
		try
		{
			IOUtil.transfer(is, os);
		}
		finally
		{
			IOUtil.close(is);
			IOUtil.close(os);
		}
		
	}
	
	
	/**
	 * Transfers bytes from InputStream to OutputStream.
	 * 
	 * @param is
	 * @param os
	 * @throws IOException 
	 */
	public static void transfer(InputStream is, OutputStream os) throws IOException
	{		
		byte[] b = new byte[4096];
		int length = is.read(b);
		while (length != -1)
		{
			os.write(b, 0, length);
			length = is.read(b);
		}
		os.flush();
	}
	
	/**
	 * Read lines of text from a stream.
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(InputStream is) throws IOException
	{
		List<String> result = new ArrayList<String>();
		
		BufferedReader reader = new BufferedReader( new InputStreamReader(is) );
		
		String strLine;
	    while ((strLine = reader.readLine()) != null)
	    {
	    	result.add(strLine);
	    }
	    
	    reader.close();
		
		return result;
	}

	
	
	
	/**
	 * Close InputStream without throwing exceptions.
	 * 
	 * @param is
	 */
	public static void close(InputStream is)
	{
		if (is != null)
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				
			}
		}
	}
	
	
	/**
	 * Close OutputStream without throwing exceptions.
	 * 
	 * @param is
	 */
	public static void close(OutputStream os)
	{
		if (os != null)
		{
			try
			{
				os.close();
			}
			catch (IOException e)
			{
				
			}
		}
	}

}
