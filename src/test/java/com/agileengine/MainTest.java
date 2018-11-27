package com.agileengine;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MainTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { "./samples/sample-1-evil-gemini.html",
				"#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > a.btn.btn-success" },
				{ "./samples/sample-2-container-and-clone.html",
						"#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > div.some-container > a.btn.test-link-ok" },
				{ "./samples/sample-3-the-escape.html",
						"#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success" },
				{ "./samples/sample-4-the-mash.html",
						"#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success" } });
	}

	@Parameter
	public String fileName;

	@Parameter(1)
	public String cssSelector;

	@Test
	public void test() {
		Element element = Main.findElement("./samples/sample-0-origin.html", fileName, "make-everything-ok-button");
		assertEquals(element.cssSelector(), cssSelector);
	}
}
