import java.io.File;

public class Driver {
    public static void main(String [] args) {
        Polynomial p1 = new Polynomial();

        int[] ex1 = {1,8,3,5};
        double[] co1 = {3,6,5,4};
        Polynomial p2 = new Polynomial(co1, ex1);

        int[] ex2 = {0,2,5,4,1};
        double[] co2 = {1,4,2,8,6};
        Polynomial p3 = new Polynomial(co2, ex2);

        File polyFile = new File("test.txt");
        Polynomial p4 = new Polynomial(polyFile);

        System.out.println("Exponents " + p1.exponents);
        System.out.println("Coefficients " + p1.coefficients);

        for(int i : p2.exponents)
            System.out.print(i + ",");
        System.out.println();
        for(double i : p2.coefficients)
            System.out.print(i + ",");
        System.out.println();

        for(int i : p3.exponents)
            System.out.print(i + ",");
        System.out.println();
        for(double i : p3.coefficients)
            System.out.print(i + ",");
        System.out.println();

        for(int i : p4.exponents)
            System.out.print(i + ",");
        System.out.println();
        for(double i : p4.coefficients)
            System.out.print(i + ",");
        System.out.println();
        
        p1 = p1.add(p2);
        p3 = p3.add(p4);

        for(int i : p1.exponents)
            System.out.print(i + ",");
        System.out.println();
        for(double i : p1.coefficients)
            System.out.print(i + ",");
        System.out.println();

        for(int i : p3.exponents)
            System.out.print(i + ",");
        System.out.println();
        for(double i : p3.coefficients)
            System.out.print(i + ",");
        System.out.println();

        p1 = p1.multiply(p3);

        for(int i : p1.exponents)
            System.out.print(i + ",");
        System.out.println();
        for(double i : p1.coefficients)
            System.out.print(i + ",");
        System.out.println();

        System.out.println(p2.evaluate(2));

        p1.saveToFile("newFile");

        File newFile = new File("newFile");
        Polynomial p5 = new Polynomial(newFile);

        for(int i : p5.exponents)
            System.out.print(i + ",");
        System.out.println();
        for(double i : p5.coefficients)
            System.out.print(i + ",");
        System.out.println();

    }
    }