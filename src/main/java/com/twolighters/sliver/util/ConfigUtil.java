package com.twolighters.sliver.util;

public class ConfigUtil
{
	public static String sliverHome()
	{
		return propertyFinder("SLIVER_HOME","sliver.home");
	}
	
	public static String propertyFinder(String env, String sysprop)
	{
		return System.getenv(env) != null ? 
				System.getenv(env) :
				System.getProperty(sysprop);
	}
}
