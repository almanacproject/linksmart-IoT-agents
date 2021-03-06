package eu.linksmart.services.payloads.ogc.sensorthing.linked;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import eu.linksmart.services.payloads.ogc.sensorthing.base.CommonControlInfoDescription;

import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Created by José Ángel Carvajal on 04.04.2016 a researcher of Fraunhofer FIT.
 */
public class ObservedProperty extends eu.linksmart.services.payloads.ogc.sensorthing.ObservedProperty {

    /**
     * A thing can have zero-to-many datastreams. A datastream entity can only
     * link to a thing as a collection of observations or properties.
     */

    @JsonProperty(value = "datastreams")
    @OneToMany(mappedBy = "observedProperty")
    private Set<Datastream> datastreams;

    /**navigationLink is the relative URL that retrieves content of related entities. */
    @JsonPropertyDescription("navigationLink is the relative Datastreams that retrieves content of related entities.")
    @JsonProperty(value = "Datastreams@iot.navigationLink")
    public String getDatastreamsNavigationLink() {
        return "ObservedProperty("+id+")/Datastreams";
    }

    @JsonPropertyDescription("TBD.")
    @JsonProperty(value = "Datastreams@iot.navigationLink")
    public void setDatastreamsNavigationLink(String value) {   }
    /**
     * Provides the list of datastreams generated by this Thing. The returned
     * set is Live reference to the internal data structure which is not
     * Thread-safe. Synchronization and concurrent modification issues might
     * arise in multi-threaded environments.
     *
     * @return the {@link java.util.Set}<{@link eu.almanac.ogc.sensorthing.api.datamodel.Datastream}> of datastreams generated by
     *         this {@link eu.linksmart.services.payloads.ogc.sensorthing.linked.Thing} instance.
     */
    @JsonBackReference(value = "datastreams")
    public Set<Datastream> getDatastreams()
    {
        return datastreams;
    }

    /**
     * Sets the list of datastreams generated by this thing. Removes any list
     * previously existing.
     *
     * @param datastreams
     *            the datastreams to set.
     */
    @JsonBackReference(value = "datastreams")
    public void setDatastreams(Set<Datastream> datastreams)
    {
        this.datastreams = datastreams;
    }
}
