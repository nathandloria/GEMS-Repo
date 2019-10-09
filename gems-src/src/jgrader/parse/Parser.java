package jgrader.parse;

public abstract class Parser<Input, Output> {
	public abstract Output parse(Input toParse);

	public abstract String complete();
}
