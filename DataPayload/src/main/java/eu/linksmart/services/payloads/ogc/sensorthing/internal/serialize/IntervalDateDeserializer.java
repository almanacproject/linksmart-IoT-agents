package eu.linksmart.services.payloads.ogc.sensorthing.internal.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import eu.linksmart.services.payloads.ogc.sensorthing.internal.Interval;


import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

public class IntervalDateDeserializer extends JsonDeserializer<Interval> {


        public IntervalDateDeserializer() {
            super();
        }

        @Override
        public Interval deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

            return Interval.parse(jsonParser.getText());
        }
    }

