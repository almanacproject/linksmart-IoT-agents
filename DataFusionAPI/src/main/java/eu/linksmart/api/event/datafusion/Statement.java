package eu.linksmart.api.event.datafusion;


import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * This is the part of the API offered by Data Fusion. The Statement is the Interface that any statement object must fulfill. This interface is a generalization of any statement of a CEP engine.<p>
 *
 * This is the API offered to the CEP engine wrapper/s.
 *
 * @author Jose Angel Carvajal Soto
 * @version     0.03
 * @since       0.03
 * @see  CEPEngine
 *
 * */
public interface Statement extends JsonSerializable {
    /***
     * Name of the statement
     *
     * @return  returns the name of the statement as string
     * */
    public String getName();
    /***
     * The statement on the CEP engine (Typically EPL)
     *
     * @return  returns the statement in the native CEP language as string
     * */
    public String getStatement();
    /***
     * The source broker where the events are coming
     *
     * @return  The broker URL or alias as string
     * */
    public String getSource();
    /***
     * The input topics of the statement (DEPRECATED)
     *
     * @return  return the list of topics that are needed in the statement
     * */
    @Deprecated
     public String[] getInput();
    /***
     * The output brokers where the events will be published
     *
     * @return  The broker URLs or aliases as string
     * */
    public String[] getScope();
    /***
     * The input topic number i of the statement (DEPRECATED)
     *
     * @param index of the topic selected to return
     *
     * @return  return the selected topic
     * */
    @Deprecated
    public String getInput(int index);
    /***
     * The output broker number i of the statement
     *
     * @param index of the broker selected to return
     *
     * @return  The selected broker URL or alias as string
     * */
    public String getScope(int index);
    /***
     * The output topics where the events will be published
     *
     * @return  The output topics as string
     * */
    public String[] getOutput();
    /***
     * The Input value is a optional value. This return if the value has been defined or not (DEPRECATED)
     *
     * @return  <code>true</code> if there is Input has being defined, <code>false</code> otherwise
     * */

    @Deprecated
    public boolean haveInput();
    /***
     * The output value is a optional value. This return if the value has been defined or not
     *
     * @return  <code>true</code> if there is output has being defined, <code>false</code> otherwise
     * */
    public boolean haveOutput();
    /***
     * The Scope value is a optional value. This return if the value has been defined or not
     *
     * @return  <code>true</code> if there is Scope has being defined, <code>false</code> otherwise
     * */
    public boolean haveScope();
    /***
     * Returns the hash ID of the statement. By default this is the SHA256 of the name.
     *
     * @return  The ID as string.
     * */
    public String getID();
    /***
     * Return the handler selected to process the result of the complex event, @Default ComplexEventHandlerImpl.
     * Note: The value "" or null is a valid response, this value represent silent events, events that just happen inside the CEP engine.
     *
     * @return  the handler cannonic name of ComplexEventHandler of the statement, @Default ComplexEventHandlerImpl..
     * */
    public String getCEHandler();
    /***
     * Return the IDs selected to agents which are targeted to process this statement. If the array is empty means all receivers @Default Empty String[].
     *
     * @return  List of targeted Agents address to process the statement, otherwise empty (all available agents):
     * */
    public ArrayList<String> getTargetAgents();
    /***
     * Return the state of the Statement, which determines how the statement will be at runtime.
     *
     * @return  Lifecycle Statement State @see StatementLifecycle .
     * */
    public StatementLifecycle getStateLifecycle();
    public Object getSynchronousResponse();
    public boolean equals(Statement org);


    public void setScope(String[] scope);

    public void setOutput(String[] output);

    public void setInput(String[] input);

    public void setSource(String source);

    public void setStatement(String statement);

    public void setName(String name);
    public void setCEHandler(String CEHandler);

    public void setStateLifecycle(StatementLifecycle stateLifecycle);

    public void setSynchronousResponse(Map response) ;
    public void setId(String id);

    public void setTargetAgents(ArrayList<String> targetAgents);

    /***
     * Represent the possible States of a Statement can be in runtime.
     * The states for a new Statements represent the state how they will be deployed in the engine.
     * For an exiting Statement, the statements represent a change of state
     *
     * */
     public enum StatementLifecycle {
        /**
         * RUN Execute the statement adding a Handler, which adds a actuate or reacts to the triggered statement.
         */
        RUN,
        /**
         * ONCE Execute once, generating a default the response at the moment
         */
        ONCE,
        /**
         * SYNCHRONOUS similar to ONCE, but returns the generated request.
         */
        SYNCHRONOUS,
        /**
         * PAUSE deploy the query in but do not start it
         */
        PAUSE,
        /**
         * REMOVE makes sens in an existing Statement.
         * This will remove the Statement form the CEP engine realising all other resources related to it
         */
        REMOVE
    }

}
