import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
public class Neuron {

    //data structures to be able to use values read from the file.
    List<Double> weights = new ArrayList<>();
    List<Double> tacYaprakGen = new ArrayList<>();
    List<Double> tacYaprakUz = new ArrayList<>();
    List<Double> canakYaprakGen = new ArrayList<>();
    List<Double> canakYaprakUz = new ArrayList<>();
    List<String> turler = new ArrayList<>();

    //Constructer method.
    Neuron() throws FileNotFoundException {
        readFile();
        randomWeights();
    }

    //File reading method
    private void readFile() throws FileNotFoundException {
        Scanner input = new Scanner(new FileInputStream("iris.data"));
        while (input.hasNextLine()) {
            String str = input.nextLine();
            if (str.isEmpty()) {
            }
            else{
                double cu = Double.parseDouble(str.split(",")[0]) / 10;
                double a = Math.round(cu * 100.0) / 100.0; //
                canakYaprakUz.add(a);

                double cg = Double.parseDouble(str.split(",")[1]) / 10;
                double b = Math.round(cg* 100.0) / 100.0; //Rounding method using the last two digits
                canakYaprakGen.add(b);

                double tu = Double.parseDouble(str.split(",")[2]) / 10;
                double c = Math.round(tu * 100.0) / 100.0;
                tacYaprakUz.add(c);

                double tg = Double.parseDouble(str.split(",")[3]) / 10;
                double d = Math.round(tg * 100.0) / 100.0;
                tacYaprakGen.add(d);

                String tur = (str.split(",")[4]);
                turler.add(tur);
            }
        }
    }

    //Method that generates random numbers between 0 and 1
    private void randomWeights(){
        Random rand = new Random();
        for (int i = 0; i<4; i++){
            double weight = rand.nextDouble();
            double a = Math.round(weight * 100.0) / 100.0;
            weights.add(a);
        }
    }

    //method that calculates output
    public double calculateOutput(List<Double> input){
        double result = 0.0;
        for(int i = 0; i<input.size(); i++){
            double ans = input.get(i) * weights.get(i);
            result += ans;
        }
        return Math.round(result * 100.0) / 100.0;
    }
}

