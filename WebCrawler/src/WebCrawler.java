import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebCrawler implements Runnable {
	private static final File file = new File("output.txt");

	private ArrayDeque<String> linkQueue = new ArrayDeque<>();
	private ArrayList<String> visited = new ArrayList<>();

	public WebCrawler(String link) {
		this.linkQueue.add(link);
	}

	@Override
	public void run() {
		while (!linkQueue.isEmpty()) {
			String link = linkQueue.poll();
			this.crawl(link);
		}
	}

	private void crawl(String url) {
		Document webPage = request(url);
		if (webPage == null)
			return;

		for (Element element : webPage.select("a[href]")) {
			String link = element.absUrl("href");
			if (!visited.contains(link))
				linkQueue.add(link);
		}

	}

	private Document request(String url) {
		try {
			Connection connect = Jsoup.connect(url);
			Document doc = connect.get();

			// Unable to connect
			if (connect.response().statusCode() != 200)
				return null;

			visited.add(url);
			// Write to file here, beware of critical section
			System.out.println(doc.title() + " - " + url); // This is just to see it working
			return doc;
		} catch (IOException e) {
			return null;
		}
	}

}
