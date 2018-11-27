package com.agileengine;

import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlAnalyzer {

	private static Logger LOGGER = LoggerFactory.getLogger(HtmlAnalyzer.class);

	//example: "a[class="button"]"
	public static String createCssQueryByAttribute(Element element, String attribute) {
		return element.tag() + "[" + attribute + "=\"" + element.attributes().get(attribute) + "\"]";
	}

	//example: "a[class="button"],a[href="#ok"]"
	public static String createCssQueryByAttributes(Element element, List<String> attributes) {
		return attributes.stream().map(attribute -> createCssQueryByAttribute(element, attribute))
				.collect(Collectors.joining(", "));
	}

	//same as previous but for all attributes of element
	public static String createCssQueryByAttributes(Element element) {
		return element.attributes().asList().stream()
				.map(attribute -> createCssQueryByAttribute(element, attribute.getKey()))
				.collect(Collectors.joining(", "));
	}

	//compare elements and original by attributes
	//return element with most values of equal attributes
	public static Element chooseMostSimilarElement(Element original, Elements elements) {
		if (elements.size() == 1)
			return elements.get(0);

		Element winner = null;
		int score = 0;
		for (Element el : elements) {
			int tmpScore = 0;
			for (Attribute attr : original.attributes()) {
				if (el.attributes().get(attr.getKey()).equals(attr.getValue()))
					tmpScore++;
			}
			LOGGER.debug("score: [{}]", tmpScore);
			if (tmpScore > score) {
				score = tmpScore;
				winner = el;
			}
		}
		return winner;
	}
}
