import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
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
        String[] partition;
        int partIndex = 0;
        int count = 0;

        try {
            Scanner read = new Scanner(input);
            String info = read.nextLine();
            read.close();
            if(info.equals(""))
                return;
            
            partition = new String[info.length()];

            for(int i = 0; i < info.length(); i++){
                if((info.substring(i, i+1).equals("+") || info.substring(i, i+1).equals("-") || i+1 == info.length()) && i != 0){
                    if(i+1 != info.length()){
                        partition[count] = info.substring(partIndex, i);
                        count++;
                        partIndex = i;
                    }
                    else{
                        partition[count] = info.substring(partIndex);
                        count++;
                    }
                }
            }
            coefficients = new double[count];
            exponents = new int[count];

            boolean x;
            for(int i = 0; i < count; i++){
                x = false;
                for(int j = 0; j < partition[i].length(); j++){
                    if(partition[i].substring(j, j+1).equals("x")){
                        coefficients[i] = Double.parseDouble(partition[i].substring(0, j));
                        exponents[i] = Integer.parseInt(partition[i].substring(j+1));
                        x = true;
                    }
                    if(j+1 == partition[i].length() && x == false){
                        coefficients[i] = Double.parseDouble(partition[i]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        //checkExpo for final check before returning
        int[] checkExpo = new int[biggestIndex + 1];
        for(int i = 0; i < checkExpo.length; i++)
            checkExpo[i] = tempExpo[i];

        //checkCoef for final check before returning
        double[] checkCoef = new double[checkExpo.length];
        for(int i = 0; i < this.coefficients.length; i++){
            for(int l = 0; l < checkExpo.length; l++){
                if(this.exponents[i] == checkExpo[l]){
                    checkCoef[l] += this.coefficients[i];
                    break;
                }
            }
        }

        for(int i = 0; i < inputPolynomial.coefficients.length; i++){
            for(int l = 0; l < checkExpo.length; l++){
                if(inputPolynomial.exponents[i] == checkExpo[l]){
                    checkCoef[l] += inputPolynomial.coefficients[i];
                    break;
                }
            }
        }
        int count = 0;
        for(int i = 0; i < checkCoef.length; i++){
            if(checkCoef[i] == 0)
                count++;
        }
        int[] returnExpo;
        double[] returnCoef;
        if(count !=0){
            returnExpo = new int[checkExpo.length - count];
            returnCoef = new double[checkCoef.length - count];
            int l = 0;
            for(int i = 0; i < checkCoef.length; i++){
                if(checkCoef[i] != 0){
                    returnCoef[l] = checkCoef[i];
                    returnExpo[l] = checkExpo[i];
                    l++;
                }
            }
        }
        else{
            returnExpo = checkExpo;
            returnCoef = checkCoef;
        }

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
        return returnPolynomial;
    }

    //Saving polynomial to a file
    public void saveToFile(String fileName){
        File newFile = new File(fileName);

        try {
            newFile.createNewFile();

            if(this.coefficients == null)
                return;

            FileWriter writing = new FileWriter(fileName);

            for(int i = 0; i < this.coefficients.length; i++){
                
                if(i == 0){
                    if(this.exponents[i] == 0)
                        writing.write(Double.toString(this.coefficients[i]));
                    else
                        writing.write(Double.toString(this.coefficients[i])+"x"+Integer.toString(this.exponents[i]));
                }
                else{
                    if(this.coefficients[i] > 0){
                        if(this.exponents[i] == 0)
                            writing.write("+" + Double.toString(this.coefficients[i]));
                        else
                            writing.write("+" + Double.toString(this.coefficients[i])+"x"+Integer.toString(this.exponents[i]));
                    }
                    else{
                        if(this.exponents[i] == 0)
                            writing.write(Double.toString(this.coefficients[i]));
                        else
                            writing.write(Double.toString(this.coefficients[i])+"x"+Integer.toString(this.exponents[i]));
                    }
                }
            }

            writing.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}