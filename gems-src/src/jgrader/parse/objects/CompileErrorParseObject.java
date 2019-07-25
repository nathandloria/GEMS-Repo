package jgrader.parse.objects;

public class CompileErrorParseObject {
    String original;
    int numErrors;
    String code;
    String kind;
    double startPos;
    double endPos;
    String source;
    String enhanced;

    public CompileErrorParseObject(String original, int numErrors, String code, String kind, double startPos, double endPos, String source) {
      this.original = original;
      this.numErrors = numErrors;
      this.code = code;
      this.kind = kind;
      this.startPos = startPos;
      this.endPos = endPos;
      this.source = source;
    }

    public void setEnhanced(String enhanced) {
      this.enhanced = enhanced;
    }

    public void printSuggestion() {
      if (numErrors == 0) {
        System.out.println("\nError #" + (numErrors + 1));
      } else {
        System.out.println("Error #" + (numErrors + 1));
      }
      System.out.println("----------------------------------------------------");
      System.out.println("Code: " + code);
      System.out.println("Kind: " + kind);
      System.out.println("Start Position: " + startPos);
      System.out.println("End Position: " + endPos);
      System.out.println("Source: " + source);
      System.out.println("Original Error Message: " + original);
      System.out.println("Suggestion: " + enhanced);
      System.out.println("----------------------------------------------------\n");
    }
}
