package eu.linksmart.ceml.handlers;

import eu.linksmart.api.event.ceml.LearningStatement;
import eu.linksmart.ceml.handlers.base.LearningMapHandler;

import java.util.List;
import java.util.Map;

/**
 * Created by José Ángel Carvajal on 25.07.2016 a researcher of Fraunhofer FIT.
 */
public class IntegerMapLearningHandler extends LearningMapHandler<Integer, Integer> {
    public IntegerMapLearningHandler(LearningStatement<Map<String, Integer>, List<Integer>, Object> statement) throws Exception {
        super(statement);
    }
}