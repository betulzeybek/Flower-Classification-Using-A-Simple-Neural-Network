import javax.sound.midi.Soundbank;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
public class NeuralNetwork {
    //Object generation
    List<Double> n1outputs = new ArrayList<>();
    List<Double> n2outputs = new ArrayList<>();
    List<Double> n3outputs = new ArrayList<>();

    //Parameterless Constructer Method
    public NeuralNetwork() throws FileNotFoundException {
    }

    private Neuron N1 = new Neuron();
    private Neuron N2 = new Neuron();
    private Neuron N3 = new Neuron();

    private int counter = 0;  //variable to use in indexes

    //Education Method
    private void education(double lambda){
        Neuron nExpect = null;

        if (counter > 149){
            counter = 0;
        }

        //process of fetching type names from file
        String flower = N1.turler.get(counter);

        //Expected value assignment
        if(flower.equals("Iris-setosa")){
            nExpect= N1;
        }
        if (flower.equals("Iris-versicolor")) {
            nExpect= N2;
        }
        if (flower.equals("Iris-virginica")) {
            nExpect = N3;
        }

        List<Double> inputs = new ArrayList<>();
        double a = N1.tacYaprakGen.get(counter);
        inputs.add(a);

        double b = N1.tacYaprakUz.get(counter);
        inputs.add(b);

        double c = N1.canakYaprakGen.get(counter);
        inputs.add(c);

        double d = N1.canakYaprakUz.get(counter);
        inputs.add(d);

        counter +=1;

        //processes that assign output and enable it to be used

        List<Double> outputs = new ArrayList<>();

        double n1output = N1.calculateOutput(inputs);
        n1outputs.add(n1output);
        outputs.add(n1output);

        double n2output = N2.calculateOutput(inputs);
        n2outputs.add(n2output);
        outputs.add(n2output);

        double n3output = N3.calculateOutput(inputs);
        n3outputs.add(n3output);
        outputs.add(n3output);

        //the process of finding the maximum output

        double max = outputs.get(0);

        for (Double output : outputs) {
            if (max < output) {
                max = output;
            }
        }
        if (n1outputs.contains(max)){
            if(N1 != nExpect){
                wGain(nExpect,inputs,lambda);
                wReduction(N1,inputs,lambda);
            }
        }
        if (n2outputs.contains(max)){
            if(N2 != nExpect){
                wGain(nExpect,inputs,lambda);
                wReduction(N2,inputs,lambda);
            }
        }
        if (n3outputs.contains(max)){
            if(N3 != nExpect){
                wGain(nExpect,inputs,lambda);
                wReduction(N3,inputs,lambda);
            }
        }
    }

    //Method to reduce weight values
    private void wReduction(Neuron neuron, List<Double> input, double lambda){
        for(int i = 0; i<neuron.weights.size(); i++){
            double w = neuron.weights.get(i);
            double x = input.get(i);
            w = w - (lambda * x);
            neuron.weights.set(i, w);
        }
    }

    //Method to increase weight values
    private void wGain(Neuron neuron, List<Double> input, double lambda){
        for(int i = 0; i<neuron.weights.size(); i++){
            double w = neuron.weights.get(i);
            double x = input.get(i);
            w = w + (lambda * x);
            neuron.weights.set(i, w);
        }
    }
    private void epok50 (double x){
        for(int j = 0; j<50; j++) {
            for (int i = 0; i < 150; i++) {
                education(x);
            }
        }
        postProcessing();
    }

    private void epok20(double x){
        for(int j = 0; j<20; j++) {
            for (int i = 0; i < 150; i++) {
                education(x);
            }
        }
        postProcessing();
    }

    private void epok100(double x){
        for(int j = 0; j<100; j++) {
            for (int i = 0; i < 150; i++) {
                education(x);
            }
        }
        postProcessing();
    }

    //The method that performs the final operations
    private void postProcessing(){
        List<Double> outputs = new ArrayList<>();
        List<Double> pn1outputs = new ArrayList<>();
        List<Double> pn2outputs = new ArrayList<>();
        List<Double> pn3outputs= new ArrayList<>();

        int correctAns = 0;

        for (int j = 0; j < 150; j++){
            if (counter > 149){
                counter = 0;
            }
            String flower = N1.turler.get(counter);
            Neuron nExpect = null;

            if(flower.equals("Iris-setosa")){
                nExpect= N1;
            }
            if (flower.equals("Iris-versicolor")) {
                nExpect= N2;
            }
            if (flower.equals("Iris-virginica")) {
                nExpect = N3;
            }

            List<Double> inputs = new ArrayList<>();
            double a = N1.tacYaprakGen.get(counter);
            inputs.add(a);
            double b = N1.tacYaprakUz.get(counter);
            inputs.add(b);
            double c = N1.canakYaprakGen.get(counter);
            inputs.add(c);
            double d = N1.canakYaprakUz.get(counter);
            inputs.add(d);

            counter +=1;

            double n1output = N1.calculateOutput(inputs);
            pn1outputs.add(n1output);
            outputs.add(n1output);

            double n2output = N2.calculateOutput(inputs);
            pn2outputs.add(n1output);
            outputs.add(n2output);

            double n3output = N3.calculateOutput(inputs);
            pn3outputs.add(n1output);
            outputs.add(n3output);

            double max = outputs.get(0);

            for (Double output : outputs) {
                if (max < output) {
                    max = output;
                }
            }

            if (n1outputs.contains(max)){
                if(N1.equals(nExpect)){
                    correctAns +=1;
                }
            }
            if (n2outputs.contains(max)){
                if(N2.equals(nExpect)){
                    correctAns += 1;
                }
            }
            if (n3outputs.contains(max)){
                if(N3.equals(nExpect)){
                    correctAns += 1;
                }
            }
        }
        int accuracy = ((correctAns) * 100) / 150;
        System.out.println("Doğruluk değeri: %" + accuracy );
    }

    //kodun doğruluğunu test etme
    public static void main(String[] args) throws FileNotFoundException {
        NeuralNetwork deneme = new NeuralNetwork();
        System.out.println("----Deney 1----");
        System.out.println("Lambda = 0.005 için 20,50,100 epok sonuçları:");
        System.out.print("20 Epok:");
        deneme.epok20(0.005);
        System.out.print("50 Epok:");
        deneme.epok50(0.005);
        System.out.print("100 Epok:");
        deneme.epok100(0.005);
        System.out.println("Lambda = 0.01 için 20,50,100 epok sonuçları:");
        System.out.print("20 Epok:");
        deneme.epok20(0.01);
        System.out.print("50 Epok:");
        deneme.epok50(0.01);
        System.out.print("100 Epok:");
        deneme.epok100(0.01);
        System.out.println("Lambda = 0.025 için 20,50,100 epok sonuçları:");
        System.out.print("20 Epok:");
        deneme.epok20(0.025);
        System.out.print("50 Epok:");
        deneme.epok50(0.025);
        System.out.print("100 Epok:");
        deneme.epok100(0.025);
        System.out.println("----Deney 2----");
        System.out.println("Lambda = 0.005 için 20,50,100 epok sonuçları:");
        System.out.print("20 Epok:");
        deneme.epok20(0.005);
        System.out.print("50 Epok:");
        deneme.epok50(0.005);
        System.out.print("100 Epok:");
        deneme.epok100(0.005);
        System.out.println("Lambda = 0.01 için 20,50,100 epok sonuçları:");
        System.out.print("20 Epok:");
        deneme.epok20(0.01);
        System.out.print("50 Epok:");
        deneme.epok50(0.01);
        System.out.print("100 Epok:");
        deneme.epok100(0.01);
        System.out.println("Lambda = 0.025 için 20,50,100 epok sonuçları:");
        deneme.epok20(0.025);
        System.out.print("50 Epok:");
        deneme.epok50(0.025);
        System.out.print("100 Epok:");
        deneme.epok100(0.025);
        System.out.println("----Deney 3----");
        System.out.println("Lambda = 0.005 için 20,50,100 epok sonuçları:");
        System.out.print("20 Epok:");
        deneme.epok20(0.005);
        System.out.print("50 Epok:");
        deneme.epok50(0.005);
        System.out.print("100 Epok:");
        deneme.epok100(0.005);
        System.out.println("Lambda = 0.01 için 20,50,100 epok sonuçları:");
        deneme.epok20(0.01);
        System.out.print("50 Epok:");
        deneme.epok50(0.01);
        System.out.print("100 Epok:");
        deneme.epok100(0.01);
        System.out.println("Lambda = 0.025 için 20,50,100 epok sonuçları:");
        deneme.epok20(0.025);
        System.out.print("50 Epok:");
        deneme.epok50(0.025);
        System.out.print("100 Epok:");
        deneme.epok100(0.025);
    }
}
