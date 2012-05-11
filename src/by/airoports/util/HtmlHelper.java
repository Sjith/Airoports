package by.airoports.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlHelper {
	Document doc;

	/**
	 * Connect to web page
	 */
	public HtmlHelper(String url) throws IOException {
		Connection connection = Jsoup.connect(url);
		connection.timeout(60 * 1000);
		doc = connection.get();
	}

	public String getTitle() {
		return doc.title();
	}

	/**
	 * Grab data
	 */
	public String getArriveSchedule() {
		Element body = doc.select("tbody").first();
		List<List<String>> list = new ArrayList<List<String>>();

		Elements tr = body.getElementsByTag("tr");

		Iterator<Element> trIterator = tr.iterator();
		while (trIterator.hasNext()) {
			List<String> tdList = new ArrayList<String>();
			Elements td = trIterator.next().getElementsByTag("td");

			Iterator<Element> tdIterator = td.iterator();
			while (tdIterator.hasNext()) {
				Element element = tdIterator.next();
				if (element.hasText()) {
					tdList.add(element.text());
				} else {
					tdList.add("NO TEXT");// TODO empty line
				}
			}
			list.add(tdList);
		}

		// for (int i = 0; i < list.size(); i++) {
		// System.out.println("LIST" + list.get(i));
		// }

		return "";
	}
}
