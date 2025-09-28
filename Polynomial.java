import java.io.File;
import java.util.Arrays;

class Polynomial{
    
    double[] coefficients;
    int[] exponents;

    //constructor that sets the Polynomial value to 0
    public Polynomial(){
    }

    //constructor that sets Polynomial value based on the input
    public Polynomial(double[] inputCoeff, int[] inputExpo){
        if(inputCoeff != null){
            coefficients = new double[inputCoeff.length];
            for(int i = 0; i < inputCoeff.length; i++){
                coefficients[i] = inputCoeff[i];
            }
        }
        if(inputExpo != null){
            exponents = new int[inputExpo.length];
            for(int j = 0; j < inputExpo.length; j++){
                exponents[j] = inputExpo[j];
            }
        }
    }

    //constructor that takes file as an input and sets Polynomial values based on the input
    public Polynomial(File input){
        
    }

    //Takes one input with type Polynomial, and returns the result added with the calling object
    public Polynomial add(Polynomial inputPolynomial){
        //return original polynomial when the other is zero polynomial
        if(this.coefficients == null || this.coefficients.length == 0)
            return inputPolynomial;
        if(inputPolynomial.coefficients == null || inputPolynomial.coefficients.length == 0)
            return this;

        int[] mergeExpo = new int[inputPolynomial.exponents.length + this.exponents.length];
        int[] tempExpo = new int[inputPolynomial.exponents.length + this.exponents.length];
        //Merging exponent array into one
        for(int i = 0; i < this.exponents.length; i++){
            mergeExpo[i] = this.exponents[i];
        }
        int j = 0;
        for(int i = this.exponents.length; i < (this.exponents.length + inputPolynomial.exponents.length); i++){
            mergeExpo[i] = inputPolynomial.exponents[j];
            j++;
        }
        Arrays.sort(mergeExpo);//sorting mergeExpo
        //Remove duplicates from mergeExpo
        int index = 0;
        if(mergeExpo[0] == 0)
            index = 1;
        for(int i = 0; i < mergeExpo.length; i++){

            int count = 0;
            for(int l = 0; l < tempExpo.length; l++){
                if(mergeExpo[i] == tempExpo[l])
                    count++;
            }
            if(count == 0){
                tempExpo[index] = mergeExpo[i];
                index++;
            }
        }
        //Find the index of the biggest element in tempExpo
        int biggestIndex = 0;
        for(int i = 0; i < tempExpo.length; i++){
            if(tempExpo[biggestIndex] <= tempExpo[i])
                biggestIndex = i;
        }
        //returnExpo that will be returned
        int[] returnExpo = new int[biggestIndex + 1];
        for(int i = 0; i < returnExpo.length; i++)
            returnExpo[i] = tempExpo[i];

        //returnCoef that will be returned
        double[] returnCoef = new double[returnExpo.length];
        for(int i = 0; i < this.coefficients.length; i++){
            for(int l = 0; l < returnExpo.length; l++){
                if(this.exponents[i] == returnExpo[l]){
                    returnCoef[l] += this.coefficients[i];
                    break;
                }
            }
        }

        for(int i = 0; i < inputPolynomial.coefficients.length; i++){
            for(int l = 0; l < returnExpo.length; l++){
                if(inputPolynomial.exponents[i] == returnExpo[l]){
                    returnCoef[l] += inputPolynomial.coefficients[i];
                    break;
                }
            }
        }
        
        // for(double i : returnCoef)
        //     System.out.print(i + ",");
        //     System.out.println("");
        // for(int i : returnExpo)
        //     System.out.print(i + ",");

        Polynomial returnPolynomial = new Polynomial(returnCoef, returnExpo);


        return returnPolynomial;
    }

    //Calculates and returns the polynomial with the given input value
    public double evaluate(double xValue){
        //return 0 if the input value is 0
        if(xValue == 0 || this.coefficients == null || this.coefficients.length == 0)
            return 0;
        
        double result = 0;
        for(int i = 0; i < this.coefficients.length; i++){
            result += this.coefficients[i] * Math.pow(xValue, this.exponents[i]);
        }

        return result;
    }

    //Returns true iff the input value is the root of the calling object
    public boolean hasRoot(double xValue){
        if(this.evaluate(xValue) == 0)
            return true;
        
        return false;
    }

    //Returns the product of calling Polynomial and input Polynomial
    public Polynomial multiply(Polynomial inputPolynomial){
        Polynomial returnPolynomial = new Polynomial();
        //return zero polynomial if either calling or input polynomial is zero
        if(inputPolynomial.coefficients == null || this.coefficients == null || this.coefficients.length == 0 || inputPolynomial.coefficients.length == 0){
            return returnPolynomial;
        }

        int numberOfPolynomials = this.coefficients.length;
        int numberOfTerms = inputPolynomial.coefficients.length;
        Polynomial tempPolynomial;
        int[] tempExpo = new int[numberOfTerms];
        double[] tempCoef = new double[numberOfTerms];

        for(int i = 0; i < numberOfPolynomials; i++){
            for(int l = 0; l < numberOfTerms; l++){
                tempExpo[l] = this.exponents[i] + inputPolynomial.exponents[l];
                tempCoef[l] = this.coefficients[i] * inputPolynomial.coefficients[l];
            }
            tempPolynomial = new Polynomial(tempCoef, tempExpo);
            returnPolynomial = returnPolynomial.add(tempPolynomial);
        }

        System.out.println("\nCoeff:");
        for(double j : returnPolynomial.coefficients)
            System.out.print(j + ",");
        System.out.println("\nExponents");
        for(int j : returnPolynomial.exponents)
            System.out.print(j + ",");


        return returnPolynomial;
    }

}