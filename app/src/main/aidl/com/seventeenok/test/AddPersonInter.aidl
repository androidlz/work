// AddPersonInter.aidl
package com.seventeenok.test;
import com.seventeenok.test.Person;
// Declare any non-default types here with import statements

interface AddPersonInter {
    List<Person> addPerson(in Person person);
}
