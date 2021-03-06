package eu.linksmart.services.payloads.ogc.sensorthing.internal.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import eu.linksmart.services.payloads.ogc.sensorthing.internal.Interval;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Date;

public class DateDeserializer extends JsonDeserializer<Date> {


        public DateDeserializer() {
            super();
        }

        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

            return DatatypeConverter.parseDateTime(jsonParser.getText()).getTime();

        }
    }

