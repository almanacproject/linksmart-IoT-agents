//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.07.17 at 05:13:45 PM CEST 
//


package it.ismb.pertlab.pwal.etsi_m2m_manager.model.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReferencePoint.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ReferencePoint">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="MIA_REFERENCE_POINT"/>
 *     &lt;enumeration value="DIA_REFERENCE_POINT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ReferencePoint")
@XmlEnum
public enum ReferencePoint {

    MIA_REFERENCE_POINT,
    DIA_REFERENCE_POINT;

    public String value() {
        return name();
    }

    public static ReferencePoint fromValue(String v) {
        return valueOf(v);
    }

}
