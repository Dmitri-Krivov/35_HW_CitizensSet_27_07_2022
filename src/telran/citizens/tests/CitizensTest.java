package telran.citizens.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.citizens.dao.CitizensSetImpl;
import telran.citizens.interfaces.Citizens;
import telran.citizens.model.Person;

class CitizensTest {
	Citizens citizens;
	static final LocalDate now = LocalDate.now();

	@BeforeEach
	void setUp() throws Exception {
		citizens = new CitizensSetImpl(Arrays.asList(new Person(1, "Peter", "Jackson", now.minusYears(23)),
				new Person(2, "John", "Smith", now.minusYears(20)),
				new Person(3, "Mary", "Jackson", now.minusYears(20)),
				new Person(4, "Tigran", "Petrosian", now.minusYears(25))));
//		citizens = new CitizensSetImpl(
//				new Person(1, "Peter", "Jackson", now.minusYears(23)), 
//				new Person(2, "John", "Smith", now.minusYears(20)),
//				new Person(3, "Mary", "Jackson", now.minusYears(20)),
//				new Person(4, "Tigran", "Petrosian", now.minusYears(25))
//			);

	}

	@Test
	void testCitizensImplListOfPerson() {
		citizens = new CitizensSetImpl(List.of(new Person(1, "Peter", "Jackson", now.minusYears(23)),
				new Person(1, "Peter", "Jackson", now.minusYears(23))));
		assertEquals(1, citizens.size());
	}

	@Test
	void testAdd() {
		assertFalse(citizens.add(null));
		assertFalse(citizens.add(new Person(2, "John", "Smith", now.minusYears(20))));
		assertEquals(4, citizens.size());
		assertTrue(citizens.add(new Person(5, "John", "Smith", now.minusYears(20))));
		assertEquals(5, citizens.size());
	}

	@Test
	void testRemove() {
		assertFalse(citizens.remove(5));
		assertEquals(4, citizens.size());
		assertTrue(citizens.remove(2));
		assertEquals(3, citizens.size());
	}

	@Test
	void testFindInt() {
		Person person = citizens.find(1);
		assertEquals(1, person.getId());
		assertEquals("Peter", person.getFirstName());
		assertEquals("Jackson", person.getLastName());
		assertEquals(23, person.getAge());
		assertNull(citizens.find(5));
	}

	@Test
	void testFindIntInt() {
		Iterable<Person> res = citizens.find(20, 24);
		Iterable<Person> expected = Arrays.asList(new Person(1, "Peter", "Jackson", now.minusYears(23)),
				new Person(2, "John", "Smith", now.minusYears(20)),
				new Person(3, "Mary", "Jackson", now.minusYears(20)));
		ArrayList<Person> actual = new ArrayList<>();
		res.forEach(p -> actual.add(p));
		Collections.sort(actual, (e1, e2) -> Integer.compare(e1.getId(), e2.getId()));
		assertIterableEquals(expected, actual);
	}

	@Test
	void testFindString() {
		Iterable<Person> res = citizens.find("Jackson");
		Iterable<Person> expected = Arrays.asList(new Person(1, "Peter", "Jackson", now.minusYears(23)),
				new Person(3, "Mary", "Jackson", now.minusYears(20)));
		ArrayList<Person> actual = new ArrayList<>();
		res.forEach(p -> actual.add(p));
		Collections.sort(actual, (e1, e2) -> Integer.compare(e1.getId(), e2.getId()));
		assertIterableEquals(expected, actual);
	}

	@Test
	void testGetAllPersonSortedById() {
		Iterable<Person> res = citizens.getAllPersonSortedById();
		int id = 0;
		for (Person person : res) {
			assertTrue(person.getId() > id);
			id = person.getId();
		}
	}

	@Test
	void testGetAllPersonSortedByLastName() {
		Iterable<Person> res = citizens.getAllPersonSortedByLastName();
		String lastName = "";
		for (Person person : res) {
			assertTrue(person.getLastName().compareTo(lastName) >= 0);
			lastName = person.getLastName();
		}
	}

	@Test
	void testGetAllPersonSortedByAge() {
		Iterable<Person> res = citizens.getAllPersonSortedByAge();
		Integer age = null;
		for (Person person : res) {
			if (age != null) {
				assertTrue(person.getAge() >= age);
			}
			age = person.getAge();
		}
	}

	@Test
	void testSize() {
		assertEquals(4, citizens.size());
	}

}
