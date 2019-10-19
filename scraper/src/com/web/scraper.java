package com.web;

import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class scraper {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter the url of the website you would like to parse: ");
    String url = scan.next();
    writeToFile(scrapeData(url));
    scan.close();
  }

  public static ArrayList<String> scrapeData(String path) {
    ArrayList<String> data = new ArrayList<>();
    try {
      Document doc = Jsoup.connect(path).get();
      Elements resultLinks = doc.select(("div[class=post-text]"));
      for (Element link : resultLinks){
        data.add(link.text());
      }
    } catch (Exception x) {
      System.out.println(x);
    }
    return data;
  }

  public static void writeToFile(ArrayList<String> dataArr) {
    try {
      File file = new File(System.getProperty("user.dir") + "/data.txt");
      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter fw = new FileWriter(file, false);
      BufferedWriter bw = new BufferedWriter(fw);
      if (dataArr.size() >= 6) {
        for(int i = 0; i < 6; i++) {
          if (i == 0) {
            fw.write("Question:\n\n" + dataArr.get(i));
          } else {
            fw.write("Answer #" + i + ":\n\n" + dataArr.get(i));
          }
          fw.write("\n+--------------------------------------------------------------------------------------------------------------+\n");
        }
      } else {
        for(int i = 0; i < dataArr.size(); i++) {
          if (i == 0) {
            fw.write("Question:\n\n" + dataArr.get(i));
          } else {
            fw.write("Answer #" + i + ":\n\n" + dataArr.get(i));
          }
          fw.write("\n+--------------------------------------------------------------------------------------------------------------+\n");
        }
      }
      bw.close();
    } catch(Exception x) {
      System.out.println(x);
    }
  }
}
