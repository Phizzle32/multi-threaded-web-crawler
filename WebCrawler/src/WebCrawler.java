import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebCrawler implements Runnable {
	private ArrayDeque<String> linkQueue = new ArrayDeque<>();
	private ArrayList<String> visited = new ArrayList<>();

	public WebCrawler(String link) {
		this.linkQueue.add(link);
	}

	@Override
	public void run() {
		while (!linkQueue.isEmpty() && !Thread.currentThread().isInterrupted()) {
			String link = linkQueue.poll();
			this.crawl(link);
		}
	}

	private void crawl(String url) {
		if (url.indexOf("http") != 0)
			url = "https://" + url;
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
			write(doc.title() + " - " + url);
			return doc;
		} catch (IOException | IllegalArgumentException e) {
			return null;
		}
	}

	private static synchronized void write(String input) {
		try {
			File file = new File("output.txt");
			if (!file.exists())
				file.createNewFile();
			if (file.canWrite()) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
				bw.write(input);
				bw.newLine();
				bw.close();
			}
		} catch (IOException e) {
			System.out.println("Cannot write to file");
		}
	}
}
