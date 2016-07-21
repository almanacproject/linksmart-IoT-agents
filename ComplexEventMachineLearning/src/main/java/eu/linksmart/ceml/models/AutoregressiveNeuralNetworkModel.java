package eu.linksmart.ceml.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.linksmart.api.event.ceml.data.DataDescriptors;
import eu.linksmart.api.event.ceml.model.Model;
import eu.linksmart.api.event.ceml.model.ModelInstance;
import eu.linksmart.api.event.datafusion.JsonSerializable;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.*;

/**
 * Created by José �?ngel Carvajal on 18.07.2016 a researcher of Fraunhofer FIT.
 * Class implementing Autoregressive neural networks.
 */

// TODO TBD
public class AutoregressiveNeuralNetworkModel extends ModelInstance<List<Double>,List<Double>,List<MultiLayerNetwork>> {

    static {
        Model.loadedModels.put(AutoregressiveNeuralNetworkModel.class.getSimpleName(),AutoregressiveNeuralNetworkModel.class);
    }
    @JsonIgnore
    protected DataDescriptors descriptors;

    private int numInputsPerNet;
    private int numOutputsPerNet = 24;

    private int ArP;
    private int ArSeasonalP;

    private int seasonalityPeriod;


	/*
	 * Cache for holding future points. his is not exactly future points. But
	 * this is the training data for output. Acting` as the future values to be
	 * predicted for evaluation.
	 */



    /**
     * Returns the network configuration, 2 hidden DenseLayers of size 50.
     */
    private MultiLayerConfiguration getSimpleDenseLayerNetworkConfiguration(
            int numHiddenNodes) {

        double learningRate = 0.01;
        return new NeuralNetConfiguration.Builder()
                .seed((new Random()).nextInt())
                .learningRate(learningRate)
                .momentum(0.9)
                .list()
                .layer(0,
                        new DenseLayer.Builder().nIn(numInputsPerNet)
                                .nOut(numHiddenNodes).activation("tanh")
                                .build())
                .layer(1,
                        new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                                .activation("identity").nIn(numHiddenNodes)
                                .nOut(numOutputsPerNet).build()).pretrain(false)
                .backprop(true).build();
    }

    public AutoregressiveNeuralNetworkModel() {
      //  super(descriptors,AutoregressiveNeuralNetworkModel.class.getSimpleName(),AutoregressiveNeuralNetworkModel.class);



    }



    private List<Double> getPrevSeasonalPoints(
            List<Double> seasonalCache, int netIndex) {
        List<Double> prevSeasonalPoints = new LinkedList<>();

        int counter = 0;
        for (Iterator<Double> iterator = seasonalCache.iterator(); iterator
                .hasNext() && ArSeasonalP*seasonalityPeriod > counter ; ) {


            if (counter % seasonalityPeriod == ((netIndex)* numOutputsPerNet)) {
                // add numOutPut number of points
                for (int i = 0; i < numOutputsPerNet; i++) {
                    Double dataPoint =  iterator.next();
                    prevSeasonalPoints.add(dataPoint);


                }
                counter+=24;
            }else{
                iterator.next();
                counter++;
            }

        }
        return prevSeasonalPoints;
    }


    private INDArray getINDArray(List<Double> inpuList) {
        double[] doubleArr = new double[inpuList.size()];

        int index = 0;
        for (Double dataPoint :inpuList) {
            doubleArr[index++] =  dataPoint;

        }
        return Nd4j.create(doubleArr);
    }

    @Override
    public boolean learn(List<Double> input) throws Exception {
        List<Double> trainOutputCache ;
        // first fill the future buffer
        if (input.size() < (seasonalityPeriod * (ArSeasonalP+1))) {
            //input size is less than expected
            return false;
        } else {
            trainOutputCache = input.subList(seasonalityPeriod * ArSeasonalP,seasonalityPeriod * (ArSeasonalP+1));
        }

        List<Double> seasonalCache = input.subList(0,seasonalityPeriod * ArSeasonalP);
        List<Double> recentPointsCache = input.subList(0, ArP);



        Iterator<Double> outputIterator = trainOutputCache.iterator();

        int netIndex = 0;
        for (MultiLayerNetwork nnet : lerner) {

            List<Double> inpuList = new LinkedList<>();
            inpuList.addAll(recentPointsCache);
            inpuList.addAll(getPrevSeasonalPoints(seasonalCache,netIndex++));

            LinkedList<Double> outpuList = new LinkedList<>();
            for (int i = 0; i < numOutputsPerNet; i++) {
                outpuList.add(outputIterator.next());
            }

            INDArray inputNDArray =  getINDArray(inpuList);
            INDArray outputNDArray = getINDArray(outpuList);
            DataSet dataSet = new DataSet(inputNDArray, outputNDArray);

            // iterator.reset();
            nnet.fit(dataSet);
        }


        return true;
    }

    //This method is just to localize the supperesswarning annotation.

    @Override
    public List<Double> predict(List<Double> input) throws Exception {
        List<Double> tempSeasonalCache =  input.subList(0,seasonalityPeriod * ArSeasonalP);
        List<Double> tempRecentPointersCache = input.subList(0, ArP);


        List<Double> returnList = new LinkedList<>();
        for (int netindex = 0; netindex < lerner.size()  ; netindex++) {
            MultiLayerNetwork nnet = lerner.get(netindex);
            // create the input list
            List<Double> inpuList = new LinkedList<>();
            inpuList.addAll(tempRecentPointersCache);
            inpuList.addAll(getPrevSeasonalPoints(tempSeasonalCache,netindex));

            final INDArray inputArr = getINDArray(inpuList);
            INDArray out = nnet.output(inputArr, false);

            for (int i = 0; i < numOutputsPerNet; i++) {
                returnList.add(out.getDouble(i));
            }

        }

        return  returnList;
    }

    @Override
    public void setDescriptors(DataDescriptors descriptors) {
        this.descriptors = descriptors;
    }

    @Override
    public DataDescriptors getDescriptors() {
        return this.descriptors;
    }


    @Override
    public JsonSerializable build() throws Exception {
        super.build();
        int seasonalityPeriod= 168;
        int P = descriptors.getInputSize()/seasonalityPeriod;
        int p = 48;
        int numNodes=24;


        ArP = p;
        ArSeasonalP = P;
        numInputsPerNet = p + (P * numOutputsPerNet);
        this.seasonalityPeriod = seasonalityPeriod;

        int numNnets = descriptors.getTargetSize() / numOutputsPerNet;


        lerner = new ArrayList<>(numNnets);
        for (int i = 0; i < numNnets; i++) {
            // Switch these two options to do different functions with different
            // networks

            final MultiLayerConfiguration conf = getSimpleDenseLayerNetworkConfiguration(numNodes);

            // Create the network
            MultiLayerNetwork nnet = new MultiLayerNetwork(conf);
            nnet.init();

            lerner.add(nnet);
        }
        return this;
    }
}