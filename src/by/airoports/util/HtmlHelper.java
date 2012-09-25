package by.airoports.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import by.airoports.item.Airoport;
import by.airoports.item.AiroportItem;
import by.airoports.item.Arrive;
import by.airoports.item.Departure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Connect to web page via url
 */
public class HtmlHelper {
	private Document doc;
	private static List<String> arriveKeys = ImmutableList.of("flight",
			"flightFrom", "time", "timeInFact", "type", "status");
	private static List<String> departureKeys = ImmutableList.of("flight",
			"flightTo", "time", "timeInFact", "type", "status");

	public HtmlHelper() {

	}

	public HtmlHelper(String url) throws IOException {
		Connection connection = Jsoup.connect(url);
		connection.timeout(60 * 1000);
		doc = connection.get();
	}

	public String getTitle() {
		return doc.title();
	}


	/**
	 * Grab data by date from arrive URL
	 * 
	 * @throws IOException
	 * @throws JSONException
	 */

	public List<Arrive> saveArrive(String url) throws IOException,
			JSONException {
		Connection connection = Jsoup.connect(url);
		connection.timeout(60 * 1000);
		Document doc = connection.get();
		Element body = doc.select("body").first();		
		Element attr = body.select("table[class =tbl]").first();
		Elements elements = attr.getElementsByTag("tr");
		Iterator<Element> iterator = elements.iterator();
		List<Arrive> arrives = Lists.newArrayList();
		while (iterator.hasNext()) {
			Element next = iterator.next();
			Elements elementsByTag = next.getElementsByTag("td");
			JSONObject object = new JSONObject();
			for (int i = 0; i < elementsByTag.size(); i++) {
				String text = elementsByTag.get(i).text();
				object.put(arriveKeys.get(i), text);
			}
			if (object.has(arriveKeys.get(0)) && object.has(arriveKeys.get(1))) {
				Arrive arrive = new Arrive(object, arriveKeys);
				arrives.add(arrive);
			}
		}
		return arrives;
	}

	public List<Departure> saveDepature(String url)
			throws IOException, JSONException {		
		Connection connection = Jsoup.connect(url);
		connection.timeout(60 * 1000);
		Document doc = connection.get();
		Element body = doc.select("body").first();		
		Element attr = body.select("table[class =tbl]").first();
		Elements elements = attr.getElementsByTag("tr");
		Iterator<Element> iterator = elements.iterator();
		List<Departure>departures = Lists.newArrayList();
		while (iterator.hasNext()) {
			Element next = iterator.next();
			Elements elementsByTag = next.getElementsByTag("td");
			JSONObject object = new JSONObject();
			for (int i = 0; i < elementsByTag.size(); i++) {
				object.put(departureKeys.get(i), elementsByTag.get(i).text());
			}			
			if (object.has(departureKeys.get(0)) && object.has(departureKeys.get(1))) {
				Departure departure = new Departure(object, departureKeys);	
				departures.add(departure);
			}
		}
		return departures;
	}

	/**
	 * Parse airoports names
	 */
	public void saveAiroportsShortcuts(String url) throws IOException {
		Connection connection = Jsoup.connect("http://belavia.by/table/");
		connection.timeout(60 * 1000);
		Document doc = connection.get();
		Element body = doc.select("body").first();
		Element first = body.select("div[class =indboard-prop]").first();
		Element first2 = first.select("select[name =airport]").first();
		String airoports = first2.getElementById("Items").text();
		List<String> list = Lists.newArrayList();
		while (airoports.indexOf(')') != -1) {
			int i = airoports.indexOf('(');
			int j = airoports.indexOf(')');
			String substring = airoports.substring(i + 1, j);
			if (substring.length() < 4) {
				list.add(substring);
			}
			String newString = airoports.substring(j + 1);
			airoports = newString;
		}
	}

	/**
	 * Parse airoports names
	 * 
	 * @return
	 */
	public List<AiroportItem> saveAiroports(String url) throws IOException {
		Connection connection = Jsoup.connect(url);
		connection.timeout(60 * 1000);
		Document doc = connection.get();
		Element body = doc.select("body").first();
		Element first = body.select("div[class =indboard-prop]").first();
		Element first2 = first.select("select[name =airport]").first();
		Elements allElements = first2.getElementById("Items").getAllElements();
		List<AiroportItem> list = Lists.newArrayList();
		for (Element element : allElements) {
			list.add(new Airoport(element.text()));
		}
		list.remove(0);// remove unnecessary item
		return list;
	}
}
