package relchecker.parse;

import java.util.ArrayList;

public abstract class Parser<Input, Output> {
	public abstract Output parse(Input toParse);
	public abstract String complete();
	public abstract int getErrorNum();
	public abstract ArrayList<String> getOmessageArr();
}
