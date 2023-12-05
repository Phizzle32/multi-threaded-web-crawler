import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static int getLines() throws FileNotFoundException{
		int len = 0;
		BufferedReader br = new BufferedReader(new FileReader("./output.txt"));
		try {
			while (br.readLine() != null) {
				len++;
			}
		}
		catch (IOException e) {
			System.out.println(e);
		}
		
		return len;
	}
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		File file = new File("output.txt");
		ArrayList<Thread> threads = new ArrayList<>();

		// Continues from last url
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
					Thread thread = new Thread(new WebCrawler(lastUrl));
					thread.start();
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

		// Input urls to crawl from separated by white space
		System.out.print("Enter website urls to crawl: ");
		String websites = scanner.nextLine();

		String[] urls = websites.trim().replaceAll(" +", " ").split(" ");

		try {
			System.out.println("Type 'quit' at any time to stop.");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// this should never happen
			System.out.println("Something really bad happened.");
		}
		// Creates a thread for each url
		for (String url : urls) {
			Thread thread = new Thread(new WebCrawler(url));
			thread.start();
			threads.add(thread);
		}
		System.out.println(String.format("Created %d threads. Beginning crawl...\n", threads.size()));

		// Stops the program
		String input = "";
		while (!input.toLowerCase().contains("quit")) {
			System.out.println("Type 'quit' to stop");
			input = scanner.nextLine();
		}
		scanner.close();
		threads.forEach(thread -> {
			thread.interrupt();
		});
		try {
			System.out.printf("Outputted %d lines to output.txt", getLines());
		}
		catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}
}
