package me.Superior_Slime.bedhome;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class UpdateChecker {
	
	private main plugin;
	private URL f;
	public static String l;
	public static String v;
	public UpdateChecker(main plugin, String url){
		this.plugin = plugin;
		
		try{
			this.f = new URL(url);
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
	}
	public boolean updateNeeded(){
		try{
			InputStream i = this.f.openConnection().getInputStream();
			Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(i);
			Node lf = d.getElementsByTagName("item").item(0);
			NodeList c = lf.getChildNodes();
			UpdateChecker.v = c.item(1).getTextContent().replaceAll("[a-z-A-Z ]", "");
			UpdateChecker.l = c.item(3).getTextContent();
			Double vd = Double.parseDouble(plugin.getDescription().getVersion());
			Double vp = Double.parseDouble(UpdateChecker.v);
			if (vp > vd){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public String getVersion(){
		return UpdateChecker.v;
	}
	
	public String getLink(){
		return UpdateChecker.l;
	}
}
