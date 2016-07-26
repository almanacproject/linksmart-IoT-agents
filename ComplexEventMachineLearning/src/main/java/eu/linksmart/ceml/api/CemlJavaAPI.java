package eu.linksmart.ceml.api;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import eu.linksmart.api.event.ceml.data.*;
import eu.linksmart.api.event.ceml.model.ModelDeserializer;
import eu.linksmart.ceml.core.CEML;
import eu.linksmart.ceml.core.CEMLManager;
import eu.linksmart.ceml.intern.Const;
import eu.almanac.event.datafusion.intern.DynamicConst;
import eu.almanac.event.datafusion.utils.epl.StatementInstance;
import eu.linksmart.api.event.ceml.CEMLRequest;
import eu.linksmart.api.event.ceml.LearningStatement;
import eu.linksmart.api.event.ceml.model.Model;
import eu.linksmart.api.event.datafusion.MultiResourceResponses;
import eu.linksmart.api.event.datafusion.Statement;
import eu.linksmart.api.event.datafusion.GeneralRequestResponse;
import eu.linksmart.gc.utils.configuration.Configurator;
import eu.linksmart.gc.utils.function.Utils;
import eu.linksmart.gc.utils.logging.LoggerService;

import java.util.Hashtable;
import java.util.Map;
import com.google.gson.*;

/**
 * Created by José Ángel Carvajal on 01.02.2016 a researcher of Fraunhofer FIT.
 */
public class CemlJavaAPI {


    /**
     * Created by angel on 13/11/15.
     */

    static private Configurator conf = Configurator.getDefaultConfig();
    static private LoggerService loggerService = Utils.initDefaultLoggerService(CEML.class);

    static private Map<String, CEMLRequest> requests = new Hashtable<>();
    static private ObjectMapper mapper = new ObjectMapper();


    static {

        // Add configuration file of the local package
        Configurator.addConfFile(Const.CEML_DEFAULT_CONFIGURATION_FILE);
        conf = Configurator.getDefaultConfig();


        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        mapper.registerModule(new SimpleModule("Descriptors", Version.unknownVersion()).addDeserializer(DataDescriptors.class, new DataDescriptorsDeserializer()).addSerializer(DataDescriptors.class,new DataDescriptorSerializer()))
                .registerModule(new SimpleModule("Statements", Version.unknownVersion()).addAbstractTypeMapping(Statement.class, StatementInstance.class))
                .registerModule(new SimpleModule("LearningStatements", Version.unknownVersion()).addAbstractTypeMapping(LearningStatement.class, eu.linksmart.ceml.statements.LearningStatement.class))
                .registerModule(new SimpleModule("Model", Version.unknownVersion()).addDeserializer(Model.class, new ModelDeserializer()))
                .registerModule(new SimpleModule("DataDescriptor", Version.unknownVersion()).addDeserializer(DataDescriptor.class, new DataDescriptorDeserializer()));
    }
    public static MultiResourceResponses<CEMLRequest> feedLearningRequest(CEMLRequest request){
        MultiResourceResponses<CEMLRequest> responses = new MultiResourceResponses<>();
        try {
            request.build();
        } catch (Exception e) {
            loggerService.error(e.getMessage(),e);
            responses = new MultiResourceResponses<>();
            responses.addResponse(new GeneralRequestResponse("Internal Server Error", DynamicConst.getId(),null,"Agent", e.getMessage(), 500));
            return responses;
        }

        responses.addResources(request.getName(),request);
        responses.addResponse(new GeneralRequestResponse("Created",DynamicConst.getId(),request.getName(), "Learning Request", "Created",201 ));
        return responses;


    }
    public static MultiResourceResponses<CEMLRequest> create(String name, String body, String requestType){
        MultiResourceResponses<CEMLRequest> retur = new MultiResourceResponses<>() ;
        CEMLRequest request;
        try {
            switch (requestType){


                case "":
                default:
                    request = mapper.readValue(body,CEMLManager.class);
                    request.setName(name);
                    retur= CemlJavaAPI.feedLearningRequest(request);
                    retur.addResources(request.getName(),request);
            }

        }catch (Exception e){
            loggerService.error(e.getMessage(), e);
            retur.addResponse(new GeneralRequestResponse("Internal Server Error",DynamicConst.getId(),name, "Learning Request","Error 500 Intern Error: Error while executing method " + e.getMessage(),500 ));

        }

        return retur;
    }
    public static GeneralRequestResponse get(String name, String typeRequest) {
        String retur;
        try {
            if (name == null)
                retur = (new Gson()).toJson(requests.values());
            else if (requests.containsKey(name))
                switch (typeRequest) {
                    case "complete":
                        retur = mapper.writeValueAsString(requests.get(name));
                        break;
                    case "data":
                        retur = mapper.writeValueAsString(requests.get(name).getDescriptors());
                        break;
                    case "evaluation":
                        // retur =mapper.writeValueAsString(requests.get(name).getEvaluation());
                        retur = "";
                        break;
                    case "learning":
                        retur = mapper.writeValueAsString((requests.get(name).getLearningStreamStatements()));
                        break;
                    case "model":
                        retur = mapper.writeValueAsString(requests.get(name).getModel());
                        break;
                    case "deployment":
                        retur = mapper.writeValueAsString((requests.get(name).getDeploymentStreamStatements()));
                        break;
                    default:
                        retur = mapper.writeValueAsString(requests.get(name));
                }
            else
                return new GeneralRequestResponse("Not Found",DynamicConst.getId(),name, "Learning Request","Error 404 Not Found: Request with name " + name,404 );

        } catch (Exception e) {
            loggerService.error(e.getMessage(), e);
            return new GeneralRequestResponse("Internal Server Error",DynamicConst.getId(),name, "Learning Request","Error 500 Intern Error: Error while executing method " + e.getMessage(),500 );


        }
        return new GeneralRequestResponse("OK",DynamicConst.getId(),name, "Learning Request", "OK",200 );

    }
/*
    public static StatementResponse update(String name, String body, String typeRequest) {
        Object retur = null;
        try {

            if (requests.containsKey(name)) {
                switch (typeRequest) {
                    case "":
                        requests.get(name).reBuild(mapper.readValue(body, LearningRequest.class));
                        retur = requests.get(name);
                        break;
                    case "evaluation":
                        // requests.get(name).getEvaluation().reBuild(mapper.readValue(body,EvaluatorBase.class));
                        //retur = requests.get(name).getEvaluation();
                        break;
                    case "learning":
                        ArrayList<String> learning = (new Gson()).fromJson(body, new TypeToken<ArrayList<String>>() {
                        }.getType());
                        requests.get(name).rebuildLearningStatements(learning);
                        retur = requests.get(name).getLeaningStatements();
                        break;
                    case "model":
                        //  requests.get(name).getModel().reBuild(mapper.readValue(body, Model.class));
                        retur = requests.get(name).getModel();
                        break;

                    case "regression":
                     //TODO: missing features of regression
                        break;

                    case "classify":
                        //  Model mdl =  requests.get(name).getModel();
                        Map input = mapper.readValue(body, new TypeReference<Map<String, Object>>() {
                        });
                        //    retur = requests.get(name).getData().getInstances().attribute(LearningHandler.classify(input, requests.get(name)));
                        break;
                    case "deployment":
                        ArrayList<String> deployed = mapper.readValue(body, new TypeReference<ArrayList<String>>() {
                        });
                        requests.get(name).rebuildDeploymentStatements(deployed);
                        retur = requests.get(name).getDeployStatements();
                    default:
                        requests.get(name).reBuild(mapper.readValue(body, LearningRequest.class));
                        retur = requests.get(name);
                }


            } else {
                loggerService.warn("There is no learning request with name " + name);
                return new StatementResponse("Not Found",DynamicConst.getId(),name, "Learning Request","Error 404 Not found: There is no request with given name" + name,404 );

            }

        } catch (Exception e) {
            loggerService.error(e.getMessage(), e);
            return new StatementResponse("Internal Server Error",DynamicConst.getId(),name, "Learning Request","Error 500 Intern Error: Error while executing method " + e.getMessage(),500 );

        }
        if (retur != null)


                return new StatementResponse("OK",DynamicConst.getId(),name, "Learning Request", "OK",200 );


        else
            return new StatementResponse("Internal Server Error",DynamicConst.getId(),name, "Learning Request","There was an unknown error!" ,500 );


    }
*/

    public static MultiResourceResponses<CEMLRequest> create( CEMLRequest request) {
        MultiResourceResponses<CEMLRequest> responses= new MultiResourceResponses<>();
        try {
            MultiResourceResponses<CEMLRequest> aux=feedLearningRequest(request);



            responses.addAllResponses(aux.getResponses());
            responses.getResources().putAll(aux.getResources());

        } catch (Exception e) {
            loggerService.error(e.getMessage(), e);
            responses.addResponse(new GeneralRequestResponse("Internal Server Error",DynamicConst.getId(),request.getName(), "Learning Request","Error 500 Intern Error: Error while executing method " + e.getMessage(),500 ));

        }

        return responses;
    }






}
