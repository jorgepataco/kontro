package com.kontro.utils.comparators;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.kontro.beans.paging.Direction;
import com.kontro.entities.prompts.Prompt;

public final class PromptComparator {

	static class Key {
        String name;
        Direction dir;
		
        public Key(String name, Direction dir) {
			super();
			this.name = name;
			this.dir = dir;
		}
        
        
    }
	
	
	
	static Map<Key, Comparator<Prompt>> map = new HashMap<>();
	
	static {
        map.put(new Key("conteudo", Direction.asc), Comparator.comparing(Prompt::getConteudo));
        map.put(new Key("conteudo", Direction.desc), Comparator.comparing(Prompt::getConteudo)
                                                           .reversed());

//        map.put(new Key("start_date", Direction.asc), Comparator.comparing(Employee::getStartDate));
//        map.put(new Key("start_date", Direction.desc), Comparator.comparing(Employee::getStartDate)
//                                                                 .reversed());
//
//        map.put(new Key("position", Direction.asc), Comparator.comparing(Employee::getPosition));
//        map.put(new Key("position", Direction.desc), Comparator.comparing(Employee::getPosition)
//                                                               .reversed());
//
//        map.put(new Key("salary", Direction.asc), Comparator.comparing(Employee::getSalary));
//        map.put(new Key("salary", Direction.desc), Comparator.comparing(Employee::getSalary)
//                                                             .reversed());
//
//        map.put(new Key("office", Direction.asc), Comparator.comparing(Employee::getOffice));
//        map.put(new Key("office", Direction.desc), Comparator.comparing(Employee::getOffice)
//                                                             .reversed());
//
//        map.put(new Key("extn", Direction.asc), Comparator.comparing(Employee::getExtn));
//        map.put(new Key("extn", Direction.desc), Comparator.comparing(Employee::getExtn)
//                                                           .reversed());
    }
	
	public static Comparator<Prompt> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }
	
	private PromptComparator() {
    }
}
