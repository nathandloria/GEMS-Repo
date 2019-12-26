package jgrader.html;

import jgrader.scraper.scraper;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Writer {
  public void formatHtml(ArrayList<String> enhErrList, ArrayList<String> ogErrList,
  ArrayList<String> fileNameList, ArrayList<Long> lineNumList, ArrayList<String> compErrorList) {
    scraper scrap = new scraper();
	  ArrayList<String> linkArr = new ArrayList<String>();
	  ArrayList<String> html = new ArrayList<String>();
	  html.add("<div class=\"alert alert-dismissible alert-warning\">" +
		  		  "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>" +
		  		  "<h4 class=\"alert-heading\">Hello!</h4>" +
		  		  "<p class=\"mb-0\">The GEMS tool has parsed your source files and discovered " + ogErrList.size() + " errors! Scroll down for more information!</p>" +
		  		  "</div>");
	  for (int i = 0; i < ogErrList.size(); i++) {
      linkArr = scrap.getLinksArr(compErrorList.get(i));
		  html.add("<div class=\"jumbotron\">" +
				  "<h1 class=\"display-4\"><b>" + "Error #" + (i + 1) + ":</b> </h1>");
          if (ogErrList.get(i).contains("<")) {
            ogErrList.set(ogErrList.indexOf(ogErrList.get(i)), ogErrList.get(i).replaceAll("<", "&lt;"));
          }
          if (ogErrList.get(i).contains(">")) {
            ogErrList.set(ogErrList.indexOf(ogErrList.get(i)), ogErrList.get(i).replaceAll(">", "&gt;"));
          }
          html.add("<p class=\"lead\">" + ogErrList.get(i) + "</p>" +
				  "<hr class=\"my-4\">" +
				  "<p class=\"h4\"><b>Suggestion:</b> </p><p class=\"lead\">" + enhErrList.get(i) + "</p>" +
				  "<hr class=\"my-4\">" +
				  "<p class=\"h4\"><b>Location:</b> </p><p class=\"lead\">Line #" + lineNumList.get(i) + " in " + fileNameList.get(i) + "</p>" +
				  "<hr class=\"my-4\">" +
				  "<p class=\"h4\"><b>StackOverflow links related to this error:</b> </p>" +
				  "</br>" +
				  "<div class=\"panel-group\">" +
				  "<div class=\"panel panel-default\">" +
				  "<div class=\"panel-heading\">" +
				  "<h4 class=\"panel-title\">" +
				  "<p><a class=\"btn btn-primary btn-lg\" role=\"button\"data-toggle=\"collapse\" href=\"#collapse" + i + "\">Click to view links</a></p>" +
				  "</h4>" +
				  "</div>" +
				  "<div id=\"collapse" + i + "\" class=\"panel-collapse collapse\">");
      if (linkArr.size() == 0) {
        html.add("<div class=\"panel-body\">No links found for this error. Sorry!</div>");
      } else if (linkArr.size() <= 5) {
        for (int j = 0; j < linkArr.size(); j++) {
  			  html.add("<div class=\"panel-body\"><a href=\"" + linkArr.get(j) + "\" target=\"_blank\" rel=\"noreferrer\">" + linkArr.get(j) + "</a></div>");
  		  }
      } else {
        for (int j = 0; j < 5; j++) {
          html.add("<div class=\"panel-body\"><a href=\"" + linkArr.get(j) + "\" target=\"_blank\" rel=\"noreferrer\">" + linkArr.get(j) + "</a></div>");
        }
      }
		  html.add("</div>" +
				  "</div>" +
				  "</div>" +
				  "</div>");
      try {
        Thread.sleep(2000);
      } catch (Exception x) {
        System.out.println(x);
      }
	  }
	  try {
		  File file = new File(System.getProperty("user.dir") + "/report.html");
		  FileWriter fw = new FileWriter(file, false);
		  fw.write("<!DOCTYPE html>" +
		  		"<html lang=\"en\">" +
		  		"<head>" +
		  		"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
		  		"<meta charset=\"utf-8\">" +
		  		"<meta name=\"Description\" content=\"Error Report\">" +
		  		"<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">" +
		  		"<link href=\"https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/flatly/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-T5jhQKMh96HMkXwqVMSjF3CmLcL1nT9//tCqu9By5XSdj7CwR0r+F3LTzUdfkkQf\" crossorigin=\"anonymous\">" +
		  		"<title>GEMS Error Summary</title>" +
		  		"</head>" +
		  		"<body>" +
		  		"</br>" +
		  		"<div class=\"container\">");
		  for (int i = 0; i < html.size(); i++) {
			  fw.write(html.get(i));
		  }
		  fw.write("</div>" +
				"<script src=\"https://code.jquery.com/jquery-3.3.1.slim.min.js\" integrity=\"sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo\" crossorigin=\"anonymous\"></script>" +
		  		"<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js\" integrity=\"sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1\" crossorigin=\"anonymous\"></script>" +
		  		"<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>" +
		  		"</body>" +
		  		"</html>");
		  fw.close();
	  } catch (Exception x) {
		  System.out.println(x);
	  }
  }
}
