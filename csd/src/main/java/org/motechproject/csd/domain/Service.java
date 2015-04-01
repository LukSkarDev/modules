package org.motechproject.csd.domain;

import org.motechproject.mds.annotations.Cascade;
import org.motechproject.mds.annotations.Entity;
import org.motechproject.mds.annotations.Field;
import org.motechproject.mds.annotations.UIDisplayable;

import javax.jdo.annotations.Order;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Java class for service complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="service">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:ihe:iti:csd:2013}uniqueID">
 *       &lt;sequence>
 *         &lt;element name="codedType" type="{urn:ihe:iti:csd:2013}codedtype"/>
 *         &lt;element name="extension" type="{urn:ihe:iti:csd:2013}extension" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="record" type="{urn:ihe:iti:csd:2013}record"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@Entity
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "codedType", "extensions", "record" })
public class Service extends AbstractUniqueID {

    @UIDisplayable(position = 0)
    @Field
    @Cascade(delete = true)
    private CodedType codedType;

    @UIDisplayable(position = 2)
    @Order(column = "service_extensions_idx")
    @Field(name = "service_extensions")
    @Cascade(delete = true)
    private List<Extension> extensions = new ArrayList<>();

    @UIDisplayable(position = 1)
    @Field
    @Cascade(delete = true)
    private Record record;

    public Service() {
    }

    public Service(String entityID, CodedType codedType, Record record) {
        setEntityID(entityID);
        this.codedType = codedType;
        this.record = record;
    }

    public Service(String entityID, CodedType codedType, List<Extension> extensions, Record record) {
        setEntityID(entityID);
        this.codedType = codedType;
        this.extensions = extensions;
        this.record = record;
    }

    public Record getRecord() {
        return record;
    }

    @XmlElement(required = true)
    public void setRecord(Record record) {
        this.record = record;
    }

    public CodedType getCodedType() {
        return codedType;
    }

    @XmlElement(required = true)
    public void setCodedType(CodedType codedType) {
        this.codedType = codedType;
    }

    public List<Extension> getExtensions() {
        return extensions;
    }

    @XmlElement(name = "extension")
    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        Service service = (Service) o;

        if (!codedType.equals(service.codedType)) {
            return false;
        }
        if (extensions != null ? !extensions.equals(service.extensions) : service.extensions != null) {
            return false;
        }
        if (!record.equals(service.record)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + codedType.hashCode();
        result = 31 * result + (extensions != null ? extensions.hashCode() : 0);
        result = 31 * result + record.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return codedType.toString();
    }
}
