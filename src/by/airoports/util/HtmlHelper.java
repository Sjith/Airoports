package by.airoports.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;
import by.airoports.app.Constants;
import by.airoports.item.Arrive;
import by.airoports.item.Departure;

/**
 * Connect to web page via url
 */
public class HtmlHelper {
	Document doc;

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
	public List<Arrive> getArriveSchedule() {
		Element body = doc.select("tbody").first();
		List<Arrive> list = new ArrayList<Arrive>();
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
			Arrive arrive = new Arrive();
			StringTokenizer token = new StringTokenizer(builder.toString(), "@");
			arrive.setCompany(token.nextToken());
			arrive.setTime(token.nextToken());
			arrive.setTimeInFact(token.nextToken());
			arrive.setFlight(token.nextToken());
			arrive.setFlightFrom(token.nextToken());
			arrive.setSector(token.nextToken());
			arrive.setStatus(token.nextToken());
			builder.delete(0, builder.length());
			list.add(arrive);
		}
		return list;
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
			Log.i(Constants.LOG_TAG, "BUILDER DEPARTURES:" + builder.toString());
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
}
