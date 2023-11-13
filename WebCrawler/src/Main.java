import java.io.File;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		File file = new File("output.txt");
		
		// Continues from last url
		if (file.exists()) {
			System.out.println("Would you like to continue crawling from the last url (Y/N)? ");
			if(scanner.nextLine().toLowerCase().contains("y")) {
				// Get last line of file and start a web crawling thread from its url
			}
		}
		
		// Input urls to crawl from separated by white space
		System.out.print("Enter website urls to crawl: ");
		String websites = scanner.nextLine();
		scanner.close();
		
		String[] urls = websites.trim().replaceAll(" +", " ").split(" ");
		
		// Creates a thread for each url
		for (String url: urls) {
			Thread thread = new Thread(new WebCrawler(url));
			thread.start();
		}
		
	}
}
