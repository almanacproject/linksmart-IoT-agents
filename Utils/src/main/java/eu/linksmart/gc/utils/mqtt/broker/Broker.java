package eu.linksmart.gc.utils.mqtt.broker;

import eu.linksmart.gc.utils.configuration.Configurator;
import eu.linksmart.gc.utils.constants.Const;
import eu.linksmart.gc.utils.logging.LoggerService;
import eu.linksmart.gc.utils.mqtt.subscription.ForwardingListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by José Ángel Carvajal on 23.10.2015 a researcher of Fraunhofer FIT.
 */
public interface Broker extends Observer{

    static final Pattern ipPattern = Pattern.compile("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+"), urlPattern = Pattern.compile("\\b(tcp|ws)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|][:[0-9]+]");



    public boolean isConnected() ;
    public void connect() throws Exception ;
    public void disconnect() throws Exception ;
    public void destroy() throws Exception;
    public String getBrokerURL();

    public void createClient() throws MqttException;


    public boolean isWatchdog() ;

    public void startWatchdog() ;
    public void stopWatchdog();



    public void publish(String topic, byte[] payload, int qos, boolean retained) throws Exception;
    public void publish(String topic, byte[] payload) throws Exception ;
    public void publish(String topic, String payload) throws Exception;
    public String getBrokerName();

    public void setBrokerName(String brokerName) throws Exception ;

    public String getBrokerPort();

    public void setBrokerPort(String brokerPort) throws Exception ;
    public void setBroker(String brokerName, String brokerPort) throws Exception;

    public  boolean addListener(String topic, Observer stakeholder)  ;
    public  boolean removeListener(String topic, Observer stakeholder);
    public  void removeListener( Observer stakeholder);

    @Override
    public void update(Observable o, Object arg) ;
}
