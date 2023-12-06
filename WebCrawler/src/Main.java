import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		File file = new File("output.txt");
		ArrayList<Thread> threads = new ArrayList<>();
		Thread thread = null;

		// Continues from last URL
		if (file.exists() && file.length() > 0) {
			System.out.println("Would you like to continue crawling from the last url (Y/N)? ");
			if (scanner.nextLine().toLowerCase().contains("y")) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String last = "";
					String curr;
					while ((curr = br.readLine()) != null) {
						last = curr;
					}
					br.close();
					String lastUrl = last.substring(last.lastIndexOf(" - ") + 3);
					thread = new Thread(new WebCrawler(lastUrl));
					threads.add(thread);
				} catch (FileNotFoundException e) {
					System.out.println("File does not exist");
				} catch (IOException e) {
					System.out.println("An error occured while reading from the file");
				} catch (StringIndexOutOfBoundsException e) {
					System.out.println("The last entry is invalid");
				}
			}
		}

		// Input URLs to crawl from separated by white space
		System.out.print("Enter website urls to crawl: ");
		String websites = scanner.nextLine();
		String[] urls = websites.trim().replaceAll(" +", " ").split(" ");

		System.out.println("Type 'quit' at any time to stop.");
		System.out.println(String.format("Created %d threads. Beginning crawl...\n", urls.length + threads.size()));

		// Give user time to read instructions
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			System.out.println("Something really bad happened.");
		}

		// Starts crawling from last URL if used
		if (thread != null) {
			thread.start();
		}

		// Creates a thread for each URL
		for (String url : urls) {
			thread = new Thread(new WebCrawler(url));
			thread.start();
			threads.add(thread);
		}

		// Stops the program
		String input = "";
		while (!input.toLowerCase().contains("quit")) {
			input = scanner.nextLine();
		}
		scanner.close();
		threads.forEach(crawler -> {
			crawler.interrupt();
			try {
				crawler.join();
			} catch (InterruptedException e) {
				System.out.println("Something unexpected happened");
			}
		});

		System.out.printf("Crawled and outputted %d lines to output.txt starting from %d URLs", WebCrawler.numCrawled,
				threads.size());
	}
}
