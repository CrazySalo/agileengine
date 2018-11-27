package com.agileengine;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

	private static String CHARSET_NAME = "utf8";

	public static void main(String[] args) {
		
		String id = args[2] != null ? args[2] : "make-everything-ok-button"; 
		Element result = findElement(args[0], args[1], id);
		LOGGER.info("Element cssSelector: [{}]", result.cssSelector());
	}
	
	//choose element from original file in other file
	public static Element findElement(String originalFileName, String fileName, String originElementId) {
		
		//parse original file
		File htmlFile = new File(originalFileName);
		Document originDoc;
		try {
			originDoc = Jsoup.parse(htmlFile, CHARSET_NAME, htmlFile.getAbsolutePath());
		} catch (IOException e) {
			LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
			return null;
		}
		
		//parse other file
		htmlFile = new File(fileName);
		Document doc;
		try {
			doc = Jsoup.parse(htmlFile, CHARSET_NAME, htmlFile.getAbsolutePath());
		} catch (IOException e) {
			LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
			return null;
		}
		
		//find original element by id
		Element originElement = originDoc.getElementById(originElementId);

		LOGGER.debug("Origin element: [{}]", originElement.cssSelector());
		LOGGER.debug("Origin element: [{}]", originElement.tag());
		if (LOGGER.isDebugEnabled()) {
			for (Attribute attribute : originElement.attributes()) {
				LOGGER.debug("Origin element attrs: [{}]", attribute);
			}
		}

		//find all similar elements by attributes and choose the best
		String cssQuery = HtmlAnalyzer.createCssQueryByAttributes(originElement);
		LOGGER.debug("--------");
		LOGGER.debug("cssQuery: [{}]", cssQuery);
		LOGGER.debug("--------");
		Elements elements = doc.select(cssQuery);

		if (LOGGER.isDebugEnabled())
		for (Element element : elements) {
			for (Attribute attribute : element.attributes()) {
				LOGGER.debug("Element attrs: [{}]", attribute);
			}
			LOGGER.debug("--------");
		}

		Element result = HtmlAnalyzer.chooseMostSimilarElement(originElement, elements);
		
		if (LOGGER.isDebugEnabled()) {
			for (Attribute attribute : result.attributes()) {
				LOGGER.debug("Winner attrs: [{}]", attribute);
			}
			LOGGER.debug("Winner cssSelector: [{}]", result.cssSelector());
		}
		return result;
	}

}
