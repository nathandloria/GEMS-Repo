class Lab1{
	public int findMin(int[] grades, int size){
		int temp = 100;
		for (int i = 0; i < size; i++){
			if (temp > grades[i])
				temp = grades[i];
		}
		return temp;
	}
	public int findMax(int[] grades, int size){
		int temp = 0;
		for (int i = 0; i < size; i++){
			if (temp < grades[i])
				temp = grades[i];
		}
		return temp;
	}
	public double findAvg(int[] grades, int size){
		double res = 0.0; // incompatible type
		int temp = 0;
		for (int i = 0; i < size; i++){
			temp += grades[i]; // missing semicolon
		}
		res = temp/size;
		return; // missing return statement
	}
}
public class Lab1Driver{
	public static void main(String[] args){
		int[] grades = {40, 50, 30, 35, 75};
		int size = 5;

		Lab1 obj = new Lab1();
		int min = obj.findMin(grades, size);
		int max = obj.findMax(grades, size);
		double avg = obj.findAvg(grades, size);

		System.out.println("Min: " + min + "; " +
						"Max: " + max + "; " +
						"Avg: " + avg + "; "); // unclosed string literal

	} // end of file while parsing
}
