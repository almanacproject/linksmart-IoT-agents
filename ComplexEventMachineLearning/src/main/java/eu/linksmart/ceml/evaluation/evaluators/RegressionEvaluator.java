package eu.linksmart.ceml.evaluation.evaluators;

import eu.linksmart.api.event.ceml.evaluation.TargetRequest;
import eu.linksmart.ceml.evaluation.evaluators.base.GenericEvaluator;
import eu.linksmart.ceml.evaluation.metrics.base.ModelEvaluationMetricBase;
import eu.linksmart.api.event.ceml.evaluation.metrics.EvaluationMetric;
import eu.linksmart.api.event.ceml.evaluation.metrics.ModelEvaluationMetric;

import java.util.*;

/**
 * Created by devasya on 7/20/2016.
 * For evaluating regression
 */
public class RegressionEvaluator extends GenericEvaluator<Collection<Number>> {


    LinkedList<Map.Entry<Number,Number>> fixedSizeList = new LinkedList<>();
    List<Map.Entry<Number,Number>> latestEntries = new LinkedList<>();

    private int maxQueueSize = 200;

    public RegressionEvaluator( ArrayList<TargetRequest> targets) {
        super( targets);
    }

    @Override
    public RegressionEvaluator build() throws Exception {
         super.build();

        return this;
    }


    private void addTofixedsizeList(LinkedList<Map.Entry<Number, Number>> list, Map.Entry<Number, Number> entry){
        if(list.size()== maxQueueSize){
            list.remove();//removes the first most element
        }
        list.add(entry);
    }


    @Override
    public double evaluate(Collection<Number> predicted, Collection<Number>  actual) {
        latestEntries.clear();

        Iterator<Number> iteratorPred = predicted.iterator();
        for (Number actualEntry : actual) {
            Number predEntry = iteratorPred.next();
            Map.Entry<Number,Number> entry = new AbstractMap.SimpleEntry<>(predEntry, actualEntry);
            latestEntries.add(entry);
            addTofixedsizeList(fixedSizeList, entry);

        }

        double accumulateMetric =0;
        int i=0;
        for(EvaluationMetric algorithm: evaluationAlgorithms.values()) {
            if(algorithm instanceof ModelEvaluationMetric){
                ((ModelEvaluationMetric) algorithm).calculate();
            }else{
                loggerService.error("Evaluation algorithm " + algorithm.getClass().getName() + " is not a ModelEvaluationMetric subclass");
            }
            if(!algorithm.isControlMetric()) {
                accumulateMetric += algorithm.getNormalizedResult();
                i++;
            }
        }
        return accumulateMetric/(i);
    }


    public class RMSEEvaluationMetric extends ModelEvaluationMetricBase{
        private static final int MAX_NUMBER_FOR_AVG = 10000;
        private long N = 0; //fading increment
        public RMSEEvaluationMetric(ComparisonMethod method, Double target) {
            super(method, target);
            currentValue = 100.0;
        }

        @Override
        public Double calculate() {
            double squaredRMSE = currentValue*currentValue;
            double squaredSum = 0.0;
            for(Map.Entry entry:latestEntries){
//                Map.Entry entry = latestEntries.get(latestEntries.size()-1);
                Double predicted = (Double) entry.getKey();
                Double actual = (Double) entry.getValue();
                double diff =  actual-predicted;
                squaredSum += diff * diff;
            }
            double squaredError = squaredSum/latestEntries.size();

            if(N != MAX_NUMBER_FOR_AVG){//ignore very old values
                N++;
            }

            squaredRMSE =((N-1) * squaredRMSE  + squaredError)/N;
            currentValue = Math.sqrt(squaredRMSE);
            return  currentValue;
        }


    }

    public class MAEEvaluationMetric extends ModelEvaluationMetricBase{
        private static final int MAX_NUMBER_FOR_AVG = 10000;
        private long N = 0; //fading increment
        public MAEEvaluationMetric(ComparisonMethod method, Double target) {
            super(method, target);
            currentValue = 100.0;
        }

        @Override
        public Double calculate() {
            double absErrorSum = 0.0;
            for(Map.Entry entry:latestEntries){
                Double predicted = (Double) entry.getKey();
                Double actual = (Double) entry.getValue();
                double diff =  actual-predicted;
                absErrorSum += Math.abs(diff);

            }
            double absError = absErrorSum/latestEntries.size();
            if(N != MAX_NUMBER_FOR_AVG){//ignore very old values
                N++;
            }

            currentValue = ((N-1) * currentValue  + absError)/N;
            return  currentValue;
        }


    }

    /*
    Akaike information criterion: Statistical model for finding goodness of a fit.
    Overal Picture: https://en.wikipedia.org/wiki/Akaike_information_criterion
    More can be found here :http://www.ijcaonline.org/journal/number5/pxc387242.pdf
     */
    public class AICcEvaluationMetric extends ModelEvaluationMetricBase{
        private static final int DAYS_A_WEEK = 7 ;
        final int HOURS_A_DAY =24;
        private long N = 0; //fading increment
        double avgResidualSquare =0;

        int prev = (Integer) parameters.get("prev");
        int prevSeasonal=  (Integer) parameters.get("prevSeasonal");
        int numHidden= (Integer) parameters.get("numHiddenNodes");

        int freeParamCount =((prev+prevSeasonal*24)*numHidden+ numHidden* HOURS_A_DAY)*DAYS_A_WEEK;

        public AICcEvaluationMetric(ComparisonMethod method, Double target) {
            super(method, target);
            avgResidualSquare = 0;
        }

        @Override
        public Double calculate() {
            for(Map.Entry entry:latestEntries){
                Double predicted = (Double) entry.getKey();
                Double actual = (Double) entry.getValue();
                double diff =  actual-predicted;
                double  squaredError= diff*diff;
                N++;

                avgResidualSquare = ((N-1) * avgResidualSquare + squaredError)/N;

            }
            double AIC = N * Math.log(avgResidualSquare)+ 2 * freeParamCount ;
            currentValue = AIC + (2 * freeParamCount*(freeParamCount+1))/(N-freeParamCount-1);//assign  AICc to currentValue
            return  currentValue;
        }


    }
}


