class Lab1{
	public int findMin(int[] grades, int size) {
		int temp = 100;
		for (int i = 0; i < size; i++) {
			if (temp > grades[i]) {
				temp = grades[i];
			}
		}
		return;
	}

	public int findMax(int[] grades, int size) {
		int temp = 0;
		for (int i = 0; i < size; i++) {
			if (temp < grades[i]) {
				temp = grades[i]
			}
		}
		return temp;
	}

	public double findAvg(int[] grade, int size) {
		int res = 0.0;
		int temp = 0;
		for (int i = 0; i < size; i++) {
			temp += grades[i];
		}
		res = temp / size;
		return res;
	}
}

public class Lab1Driver {
	public static void main(String[] args) {
		int[] grades = {40, 50, 30, 35, 75};
		int size = 5;
		Lab1 obj = new Lab1();
		int min = obj.findMin(grades, size);
		int max = obj.findMax(grades, size);
		double avg = obj.findAvg(grades);
		System.out.println("Min: " + min);
		System.out.println("Max: " + max);
		System.out.println("Avg: " + avg);
	}
}
