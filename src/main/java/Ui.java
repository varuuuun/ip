import java.util.Scanner;

public class Ui {
    private Scanner sc;

    public Ui(){
        sc = new Scanner(System.in);
    }

    public String getInput() {
        return sc.nextLine();
    }

    public void closeScanner(){
        sc.close();
    }

    public void print(String str){
        System.out.println("____________________________________________________________");
        System.out.println(str);
        System.out.println("____________________________________________________________");
    }

    public void printError(String str){
        System.out.println("____________________________________________________________");
        System.out.println("Error: " + str);
        System.out.println("____________________________________________________________");
    }


}
