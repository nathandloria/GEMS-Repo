package jgrader.scraper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class scraper {
  public ArrayList<String> getLinksArr(String message) {
    ArrayList<String> links = new ArrayList<>();
    if (message.contains("(.*)")) {
      message = message.replace("(.*)", "");
      if (message.contains("  ")) {
        message = message.replace("  ", " ");
      }
    }
    String messageEncoded = encode(message);

    try {
      Document doc = Jsoup.connect("https://stackoverflow.com/search?q=" + messageEncoded + "+Java+error").get();
      Elements resultLinks = doc.select(("a[class=question-hyperlink]"));
      for (Element link : resultLinks) {
        if (link.attr("title").contains(message)) {
          links.add("https://stackoverflow.com" + link.attr("href"));
        }
      }
    } catch(Exception x) {
      System.out.println(x);
    }
    return links;
  }

  public ArrayList<String> scrapeData(ArrayList<String> paths) {
    ArrayList<String> data = new ArrayList<>();
    int count = 0;
    try {
      for (int i = 0; i < paths.size(); i++) {
        Document doc = Jsoup.connect(paths.get(i)).timeout(0).get();
        Elements resultLinks = doc.select(("div[class=post-text]"));
        for (Element link : resultLinks) {
          if(count == 0) {
            data.add("Question #" + (i + 1) + ": \n\n" + link.text());
          } else {
            data.add("Answer #" + count + ": \n\n" + link.text());
          }
          count++;
        }
        count = 0;
      }
    } catch (Exception x) {
      System.out.println(x);
    }
    return data;
  }

  public void writeToFile(ArrayList<String> dataArr, String fname) {
    try {
      File file = new File(System.getProperty("user.dir") + "/" + fname.replaceAll(" ", "_") + ".txt");
      FileWriter fw = new FileWriter(file, false);
      BufferedWriter bw = new BufferedWriter(fw);
      for(int i = 0; i < dataArr.size(); i++) {
        fw.write(dataArr.get(i));
        fw.write("\n+--------------------------------------------------------------------------------------------------------------+\n");
      }
      bw.close();
    } catch(Exception x) {
      System.out.println(x);
    }
  }

  public static String encode(String query) {
    try {
      return URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException ex) {
      throw new RuntimeException(ex.getCause());
    }
  }
}
