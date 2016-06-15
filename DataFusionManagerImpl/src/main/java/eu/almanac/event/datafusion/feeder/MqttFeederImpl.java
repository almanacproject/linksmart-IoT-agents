package eu.almanac.event.datafusion.feeder;

import eu.almanac.event.datafusion.intern.Const;
import eu.almanac.event.datafusion.intern.Utils;
import eu.almanac.event.datafusion.utils.generic.Component;
import eu.linksmart.api.event.datafusion.*;
import eu.linksmart.api.event.datafusion.core.EventFeederLogic;
import eu.linksmart.gc.utils.configuration.Configurator;
import eu.linksmart.gc.utils.logging.LoggerService;
import eu.linksmart.gc.utils.mqtt.broker.StaticBroker;
import eu.linksmart.gc.utils.mqtt.types.MqttMessage;
import eu.linksmart.gc.utils.mqtt.types.Topic;
import org.eclipse.paho.client.mqttv3.*;
import sun.awt.Mutex;

import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.locks.Lock;

/**
 * Created by Caravajal on 22.05.2015.
 */
public abstract class MqttFeederImpl extends Component implements Runnable, Feeder, EventFeederLogic, Observer {

    protected Map<String,CEPEngine> dataFusionWrappers = new HashMap<>();
    protected LoggerService loggerService = Utils.initDefaultLoggerService(this.getClass());
    protected Configurator conf =  Configurator.getDefaultConfig();
    protected StaticBroker brokerService= null;
    protected Map<Topic,Class> topicToClass= new Hashtable<Topic,Class>();
    protected Map<String,String> classToAlias= new Hashtable<String, String>();

    protected  Boolean toShutdown = false;
    protected static final Object lockToShutdown = new Object();
    protected long debugCount=0;

    protected Thread thisTread;

    protected  Boolean down =false;
    protected static final Object lockDown = new Object();
    private final int LOG_DEBUG_NUM_IN_EVENTS_REPORTED;


    public MqttFeederImpl(String brokerName, String brokerPort, String topic,String implName, String desc, String... implOf) throws MalformedURLException, MqttException {
        super(implName,desc,implOf);

        brokerService = new StaticBroker(brokerName,brokerPort);
        brokerService.addListener(topic,this);
        thisTread = new Thread(this);
        thisTread.start();
        try {
            LoadTypesIntoEngines();
        } catch (InstantiationException e) {
            loggerService.error(e.getMessage(),e);
        }
        LOG_DEBUG_NUM_IN_EVENTS_REPORTED=conf.getInt(FeederConst.LOG_DEBUG_NUM_IN_EVENTS_REPORTED_CONF_PATH);
    }



    @Override
    public boolean dataFusionWrapperSignIn(CEPEngine dfw) {
        dataFusionWrappers.put(dfw.getName(), dfw);

        //TODO: add code for the OSGi future
        return true;
    }

    @Override
    public boolean dataFusionWrapperSignOut(CEPEngine dfw) {
        dataFusionWrappers.remove(dfw.getName());

        //TODO: add code for the OSGi future
        return true;
    }

    
    @SuppressWarnings("SynchronizeOnNonFinalField")
    public boolean isDown(){

        boolean tmp ;

        synchronized (lockDown) {

            tmp = down;
        }

        return tmp;
    }

    @SuppressWarnings("SynchronizeOnNonFinalField")
    @Override
    public void run(){
        while (!down) {
            synchronized (toShutdown) {
                if (toShutdown  ) {



                    for (CEPEngine i : dataFusionWrappers.values())
                        i.destroy();



                    brokerService.removeListener(this);
                    try {
                        brokerService.destroy();
                    } catch (Exception e) {
                        loggerService.error(e.getMessage(),e);
                    }/**
	 * The feeder do not have an awareness of which engines are available. <p>
	 * For the feeder is enable to interact with a Data Fusion Engine,
	 * the wrapper of the engine has to explicitly subscribe to the feeder as a Data Fusion engine.<p>
	 * Doing so through this function
	 * @param dfw is the {@link eu.linksmart.api.event.datafusion.CEPEngine} which what to be subscribed.
	 *
	 * @return <code>true</code> in a successful subscription, <code>false</code> otherwise.
	 * */

                    loggerService.info(this.getClass().getSimpleName() + " logged off");

                    synchronized (lockDown) {
                        down = true;
                    }

                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
               loggerService.error(e.getMessage(),e);
            }
        }

    }
    @Override
    public boolean subscribeToTopic(String topic) {

          brokerService.addListener(topic,this);


        return true;
    }




    @Override
    public void update(Observable topic, Object mqttMessage)  {

        debugCount=(debugCount+1)%Long.MAX_VALUE;
        try {
            if(debugCount%LOG_DEBUG_NUM_IN_EVENTS_REPORTED == 0)
                loggerService.debug(Utils.getDateNowString() + " message arrived with topic: " + ((MqttMessage) mqttMessage).getTopic());

        }catch (Exception e){
            loggerService.warn("Error while loading configuration, doing the action from hardcoded values");
            if(debugCount%20== 0)
                loggerService.debug(Utils.getDateNowString() + " message arrived with topic: " + ((MqttMessage) mqttMessage).getTopic());

        }


        mangeEvent(((MqttMessage)mqttMessage).getTopic(), ((MqttMessage)mqttMessage).getPayload() );

    }



    protected abstract void mangeEvent(String topic, byte[] rawEvent);
    protected void LoadTypesIntoEngines() throws  InstantiationException {
        List topics =conf.getList(Const.FeederPayloadTopic);
        List classes =conf.getList(Const.FeederPayloadClass);
        List aliases =conf.getList(Const.FeederPayloadAlias);

        if(classes.size()!=aliases.size()&&aliases.size()!=topics.size())
            throw new InstantiationException(
                    "The configuration parameters of "
                            +Const.FeederPayloadAlias+" "
                            +Const.FeederPayloadClass+" "
                            +Const.FeederPayloadTopic+" do not match"
            );
        for (CEPEngine dfw: CEPEngine.instancedEngines.values()) {
            for(int i=0; i<classes.size();i++) {
                try {
                    Object aClassObject = Class.forName(classes.get(i).toString()).newInstance();
                    topicToClass.put(new Topic(topics.get(i).toString()),aClassObject.getClass());
                    classToAlias.put(aClassObject.getClass().getCanonicalName(),aliases.get(i).toString());
                    dfw.addEventType(aliases.get(i).toString(), aClassObject);
                } catch (ClassNotFoundException|IllegalAccessException e) {
                    loggerService.error(e.getMessage(), e);
                }
            }

        }
    }
}
