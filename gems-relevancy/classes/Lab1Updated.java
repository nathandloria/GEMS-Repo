class Lab1{
  public int findMin(int[] grades, int size){
    int temp = 100;
    for (int i = 0; i < size; i++){
      if (temp > grades[i])
        temp = grades[i];
    }
    return;
  }
  public int findMax(int[] grades, int size){
    int temp = 0;
    for (int i = 0; i < size; i++){
      if (temp < grades[i])
        temp = grades[i];
    }
    return;
  }
  public double findAvg1(int[] grades, int size){
    int res = 0.0;
    int temp = 0;
    for (int i = 0; i < size; i++){
      temp += grades[i];
    }
    res = temp/size;
    return res;
  }
  public double findAvg2(int[] grades, int size){
    int res = 0.0;
    int temp = 0;
    for (int i = 0; i < size; i++){
      temp += grades[i];
    }
    res = temp/size;
    return res;
  }
  public double findAvg3(int[] grades, int size){
    int res = 0.0;
    int temp = 0;
    for (int i = 0; i < size; i++){
      temp += grades[i];
    }
    res = temp/size;
    return res;
  }
  public double findAvg4(int[] grades, int size){
    int res = 0.0;
    int temp = 0;
    for (int i = 0; i < size; i++){
      temp += grades[i];
    }
    res = temp/size;
    return res;
  }
  public double findAvg5(int[] grades, int size){
    int res = 0.0;
    int temp = 0;
    for (int i = 0; i < size; i++){
      temp += grades[i];
    }
    res = temp/size;
    return res;
  }
}
public class Lab1Updated{
  public static void main(String[] args){
    int[] grades = {40, 50, 30, 35, 75};
    int size = 5;
    Lab1 obj = new Lab1();
    int min = obj.findMin(grades, size);
    int max = obj.findMax(grades, size);
    double avg1 = obj.findAvg1(grades);
    double avg2 = obj.findAvg2(grades);
    double avg3 = obj.findAvg3(grades);
    double avg4 = obj.findAvg4(grades);
    double avg5 = obj.findAvg5(grades);
    System.out.println("Min: " + min);
    System.out.println("Max: " + gmax);
    System.out.println("Avg: " + avg1);
    System.out.println("Avg: " + avg2);
    System.out.println("Avg: " + avg3);
    System.out.println("Avg: " + avg4);
    System.out.println("Avg: " + avg5);
  }
}
