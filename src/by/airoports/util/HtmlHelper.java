package by.airoports.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;
import static by.airoports.app.Constants.TAG;
import by.airoports.app.Constants;
import by.airoports.item.Arrive;
import by.airoports.item.Departure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Connect to web page via url
 */
public class HtmlHelper {
	private Document doc;
	private static final String ARRIVE_URL = "http://belavia.by/table/?siteid=1&id=5&departure=0&airport=MSQ&date=";
	private static final String DEPARTURE_URL = "http://belavia.by/table/?siteid=1&id=5&departure=1&airport=MSQ&date=";

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

	public List<Departure> getDeparturesSchedule() {
		Element body = doc.select("tbody").last();
		List<Departure> list = new ArrayList<Departure>();
		Elements tr = body.getElementsByTag("tr");
		Iterator<Element> trIterator = tr.iterator();
		StringBuilder builder = new StringBuilder();
		while (trIterator.hasNext()) {
			Elements td = trIterator.next().getElementsByTag("td");
			Iterator<Element> tdIterator = td.iterator();
			while (tdIterator.hasNext()) {
				builder.append("@");
				Element element = tdIterator.next();
				if (element.hasText()) {
					builder.append(element.text());
				} else {
					builder.append("Õ≈“ ƒ¿ÕÕ€’");// TODO empty line
				}
			}
			Departure departure = new Departure();
			Log.i(Constants.TAG, "BUILDER DEPARTURES:" + builder.toString());
			StringTokenizer token = new StringTokenizer(builder.toString(), "@");
			departure.setCompany(token.nextToken());
			departure.setTime(token.nextToken());
			token.nextToken();
			departure.setFlight(token.nextToken());
			departure.setDestination(token.nextToken());
			departure.setSector(token.nextToken());
			departure.setStatus(token.nextToken());
			builder.delete(0, builder.length());
			list.add(departure);
		}
		return list;
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
		JSONArray array = new JSONArray();
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
			if (object.has(arriveKeys.get(0))) {
				Arrive arrive = new Arrive(object, arriveKeys);
				arrives.add(arrive);
			}
		}
		return arrives;
	}

	public void saveDepatureWeekData(String date, BufferedWriter writer)
			throws IOException, JSONException {
		String url = DEPARTURE_URL + date;
		Connection connection = Jsoup.connect(url);
		connection.timeout(60 * 1000);
		Document doc = connection.get();
		Element body = doc.select("body").first();
		JSONArray array = new JSONArray();
		Element attr = body.select("table[class =tbl]").first();
		Elements elements = attr.getElementsByTag("tr");
		Iterator<Element> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Element next = iterator.next();
			Elements elementsByTag = next.getElementsByTag("td");
			JSONObject object = new JSONObject();
			for (int i = 0; i < elementsByTag.size(); i++) {
				object.put(departureKeys.get(i), elementsByTag.get(i).text());
			}
			if (object.getString(arriveKeys.get(0)) != null) {
				array.put(object);
			}
		}
		JSONObject arrayObject = new JSONObject();
		arrayObject.put(date, array);

		writer.write(arrayObject.toString());
		writer.newLine();
		writer.flush();
		System.out.println(arrayObject);
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
	public List<String> saveAiroports(String url) throws IOException {
		Connection connection = Jsoup.connect(url);
		connection.timeout(60 * 1000);
		Document doc = connection.get();
		Element body = doc.select("body").first();
		Element first = body.select("div[class =indboard-prop]").first();
		Element first2 = first.select("select[name =airport]").first();
		Elements allElements = first2.getElementById("Items").getAllElements();
		List<String> list = Lists.newArrayList();
		for (Element element : allElements) {
			list.add(element.text());
		}
		list.remove(0);// remove unnecessary item
		return list;
	}
}
