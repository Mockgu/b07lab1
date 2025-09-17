class Polynomial{
    
    double[] coefficients;
    public Polynomial(){
        coefficients = new double[] {0};
    }

    public Polynomial(double[] inputArr){
        coefficients = new double[inputArr.length];
        for(int i = 0; i < inputArr.length; i++){
            coefficients[i] = inputArr[i];
        }
    }

    public Polynomial add(Polynomial inputPolynomial){
        Polynomial longer;
        Polynomial shorter;

        if(inputPolynomial.coefficients.length < this.coefficients.length){
            longer = this;
            shorter = inputPolynomial;
        }
        else{
            longer = inputPolynomial;
            shorter = this;
        }

        longer = new Polynomial(longer.coefficients);

        for(int i = 0; i < shorter.coefficients.length; i++){
            longer.coefficients[i] += shorter.coefficients[i];
        }
        return longer;
    }

    public double evaluate(double xValue){
        double result = 0;
        for(int i = 0; i < this.coefficients.length; i++){
            result += this.coefficients[i] * Math.pow(xValue, i);
        }

        return result;
    }

    public boolean hasRoot(double xValue){
        this.evaluate(xValue);
        if(xValue == 0){
            return true;
        }
        return false;
    }

}