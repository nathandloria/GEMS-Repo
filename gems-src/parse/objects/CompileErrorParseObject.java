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
  String str;

  public CompileErrorParseObject() {}

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
}
