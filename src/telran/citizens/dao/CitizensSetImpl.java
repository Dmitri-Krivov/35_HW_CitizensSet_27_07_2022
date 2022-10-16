package telran.citizens.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import telran.citizens.interfaces.Citizens;
import telran.citizens.model.Person;

public class CitizensSetImpl implements Citizens {
	private Set<Person> idList;
	private Set<Person> lastNameList;
	private Set<Person> ageList;
	private static Comparator<Person> lastNameComparator;
	private static Comparator<Person> ageComparator;

	static {
		lastNameComparator = (p1, p2) -> {
			int res = p1.getLastName().compareTo(p2.getLastName());
			return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
		};
		ageComparator = (p1, p2) -> {
			int res = Integer.compare(p1.getAge(), p2.getAge());
			return res != 0 ? res : Integer.compare(p1.getId(), p2.getId());
		};
	}

	public CitizensSetImpl() {
		idList = new TreeSet<>();
		lastNameList = new TreeSet<>(lastNameComparator);
		ageList = new TreeSet<>(ageComparator);
	}

	public CitizensSetImpl(List<Person> citizens) {
		this();
		citizens.forEach(p -> add(p));
	}

	public CitizensSetImpl(Person... citizens) {
		this();
		for (int i = 0; i < citizens.length; i++) {
			add(citizens[i]);
		}
	}

	// O(1)
	@Override
	public boolean add(Person person) {
		if (person == null) {
			System.out.println(person);
			return false;
		}
		return idList.add(person) && lastNameList.add(person) && ageList.add(person);

	}

	// O(n)
	@Override
	public boolean remove(int id) {
		Person victim = find(id);
		return victim!=null && idList.remove(victim) && lastNameList.remove(victim) && ageList.remove(victim);
	}

	// O(n)
	@Override
	public Person find(int id) {
//		for (Person person : idList) {
//			if (person.getId() == id) {
//				return person;
//			}
//		}
//		return null;
		
		return idList.stream()
			.filter(n->id==n.getId())
			.findFirst().orElse(null);
			
	}

//	 O(n)
	@Override
	public Iterable<Person> find(int minAge, int maxAge) {
//		ArrayList<Person> newList = new ArrayList<>();
//		for (Person person : ageList) {
//			if (person.getAge() >= minAge && person.getAge() <= maxAge) {
//				newList.add(person);
//			}
//		}
//
//		return newList;
		
		return ageList.stream()
					.filter(n->n.getAge()>=minAge   && n.getAge() <maxAge )
					.collect(Collectors.toList());
	}

//	 O(n)
	@Override
	public Iterable<Person> find(String lastName) {
//		ArrayList<Person> newList = new ArrayList<>();

//		for (Person person : ageList) {
//			if (person.getLastName() == lastName) {
//				newList.add(person);
//			}
//		}
//		return newList;
		
		return lastNameList.stream()
				.filter(n->lastName
				.equals(n.getLastName()))
				.collect(Collectors.toList());
		
	}

	// O(n)
	@Override
	public Iterable<Person> getAllPersonSortedById() {
		ArrayList<Person> newIdList = new ArrayList<>();
		newIdList.addAll(ageList);
		Collections.sort(newIdList);
		return newIdList;
	}

	// O(n)
	@Override
	public Iterable<Person> getAllPersonSortedByLastName() {
		ArrayList<Person> newLastNameList = new ArrayList<>();
		newLastNameList.addAll(ageList);
		Collections.sort(newLastNameList, lastNameComparator);
		return newLastNameList;
	}

	// O(n)
	@Override
	public Iterable<Person> getAllPersonSortedByAge() {
		ArrayList<Person> newAgeList = new ArrayList<>();
		newAgeList.addAll(ageList);
		Collections.sort(newAgeList, ageComparator);
		return newAgeList;
	}

	// O(1)
	@Override
	public int size() {
		return idList.size();
	}

}
