package jgrader.parse.objects;

public class CompileErrorParseObject {
    String error;
    String original;
    String shortDescription;

    public CompileErrorParseObject(String error, String original, String shortDescription) {
        this.error = error;
        this.original = original;
        this.shortDescription = shortDescription;
    }

    public String toString() {
        if(error == null) return original;
        else return error + ": " + shortDescription;
    }
}
