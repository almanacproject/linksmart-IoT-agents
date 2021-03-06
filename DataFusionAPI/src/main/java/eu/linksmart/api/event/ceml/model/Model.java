package eu.linksmart.api.event.ceml.model;

import eu.linksmart.api.event.ceml.evaluation.Evaluator;
import eu.linksmart.api.event.ceml.evaluation.TargetRequest;
import eu.linksmart.api.event.ceml.prediction.Prediction;
import eu.linksmart.api.event.ceml.prediction.PredictionInstance;
import eu.linksmart.api.event.datafusion.JsonSerializable;
import eu.linksmart.api.event.ceml.data.DataDescriptors;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by José Ángel Carvajal on 18.07.2016 a researcher of Fraunhofer FIT.
 */
public interface Model<Input,Output,LearningObject> extends JsonSerializable{
    final static public Map<String,Class<? extends Model>> loadedModels = new Hashtable<>();

    public static  Model factory(String name,List<TargetRequest> targetRequests, Map<String, Object> parameters) throws Exception{
        if(!loadedModels.containsKey(name)) {
            Class.forName("eu.linksmart.ceml.models."+name);
        }
        return loadedModels.get(name).getConstructor(targetRequests.getClass(),Map.class).newInstance(targetRequests,parameters);
        //throw new Exception("No valid models had been loaded");
   }

    public  Evaluator<Output> getEvaluator();
    public boolean learn(Input input) throws Exception;
    public Prediction<Output> predict(Input input) throws Exception;
    public void setDescriptors(DataDescriptors descriptors);
    public DataDescriptors getDescriptors();
    public Prediction<Output> getLastPrediction();
    public void setLastPrediction(Prediction<Output> value);
    public void setName(String name);

    public Class getNativeType();

    public void setNativeType(Class nativeType);


    public Map<String, Object> getParameters() ;
    public void setParameters(Map<String, Object> parameters) ;

}
