package jgrader;

public abstract class SystemInteractor {
    public static final int SUCCESS = 0;
    public static final int ERROR = -1;

    public abstract int run();

}
