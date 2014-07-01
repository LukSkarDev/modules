package org.motechproject.openmrs18.resource;

import org.motechproject.openmrs18.rest.HttpException;
import org.motechproject.openmrs18.resource.model.Attribute;
import org.motechproject.openmrs18.resource.model.AttributeTypeListResult;
import org.motechproject.openmrs18.resource.model.Person;
import org.motechproject.openmrs18.resource.model.Person.PreferredAddress;
import org.motechproject.openmrs18.resource.model.Person.PreferredName;

public interface PersonResource {

    Person getPersonById(String uuid) throws HttpException;

    Person createPerson(Person converted) throws HttpException;

    void createPersonAttribute(String uuid, Attribute attribute) throws HttpException;

    AttributeTypeListResult queryPersonAttributeTypeByName(String name) throws HttpException;

    void deleteAttribute(String personParentUuid, Attribute attribute) throws HttpException;

    void updatePerson(Person person) throws HttpException;

    void updatePersonName(String personUuid, PreferredName name) throws HttpException;

    void updatePersonAddress(String personUuid, PreferredAddress addr) throws HttpException;

    void removePerson(String personUuid) throws HttpException;

}
