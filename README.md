# Multi-threaded Web Crawler
## Description
The web crawler will visit websites on the internet and index them. This web crawler is capable of crawling multiple websites at the same time.
## Running the code
To run the code, you can just clone the GitHub repository to the IDE of your choice and run the program.

Alternatively, you can run the program using only the Main.java, WebCrawler.java, and jsoup.jar files. First, put all three files into a folder. Next, navigate to that folder in the terminal. Then, run these two commands:
### Windows
`javac -cp ";.\jsoup-1.16.2.jar;.\WebCrawler.java" Main.java`<br>
`java -cp ";.\jsoup-1.16.2.jar;.\WebCrawler.java" Main`

### Unix
`javac -cp .:jsoup-1.16.2.jar:WebCrawler.java Main.java`<br>
`java -cp .:jsoup-1.16.2.jar:WebCrawler.java Main`
## Input
To use the web crawler, you will need to supply links. The links you input must start with "http" or "https," and they must be separated by white space.
Additionally, if you have used the program previously, you can also continue from the last website visited by typing “yes” when prompted. After the program starts crawling, type “quit” at any time to stop.
## Output
The output of the program will be stored in a file called "output.txt". There should be a record of every website visited for every line in the output file in the format: "WebsiteTitle" - websiteURL. This file should be located in the same folder as the program.




