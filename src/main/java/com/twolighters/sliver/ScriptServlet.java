package com.twolighters.sliver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twolighters.sliver.util.ConfigUtil;
import com.twolighters.sliver.util.IOUtil;

/**
 * This servlet is designed to simply serve up Slim script files
 * as the body of the response.
 */
@WebServlet("/script/*")
public class ScriptServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private static final String SLIVER_HOME = ConfigUtil.sliverHome();
	private static final File SCRIPT_DIR = new File(SLIVER_HOME + "/scripts");

    /**
     * Default constructor. 
     */
    public ScriptServlet()
    {
    }
    
    public void init()
    {
    	if (!SCRIPT_DIR.exists())
    	{
    		SCRIPT_DIR.mkdirs();
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		String mode = request.getParameter("mode");
		mode = mode == null ? "" : mode;
		
		if (mode.equalsIgnoreCase("list"))
		{
			list(request, response);
		}
		else
		{
			script(request,response);
		}
		
		response.flushBuffer();
	}
	
	
	private void script(HttpServletRequest request, HttpServletResponse response) 
		throws IOException
	{
		File requestedScript = new File(SCRIPT_DIR,request.getPathInfo());
		response.setContentType("text/plain");
		
		if (requestedScript.exists())
		{
			FileInputStream fis = new FileInputStream(requestedScript);
			IOUtil.transfer(fis, response.getOutputStream());
			IOUtil.close(fis);
		}
		else
		{
			response.getWriter()
				.write("ECHO Cannot locate requested resource: " + request.getPathInfo());
		}
	}
	
	
	private void list(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		File requestedDir = new File(SCRIPT_DIR,request.getPathInfo());
		response.setContentType("text/plain");
		
		if (requestedDir.exists())
		{
			if (requestedDir.isFile())
			{
				response.getWriter()
					.write("ECHO " + requestedDir.getName());				
			}
			else  //a directory
			{
				String[] list = requestedDir.list();
				for (String s : list)
				{
					response.getWriter()
						.write("ECHO " + s + System.getProperty("line.separator"));
				}
			}
		}
		else
		{
			response.getWriter()
				.write("ECHO Cannot locate requested resource: " + request.getPathInfo());
		}
	}


}
