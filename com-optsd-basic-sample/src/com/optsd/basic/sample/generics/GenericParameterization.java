package com.optsd.basic.sample.generics;

import java.util.List;

import com.google.common.reflect.TypeToken;


/**
 * 
 * @author optsd
 * 
 * 	Generic parameterization on a class can be inspected by creating an anonymous inner class. 
 *  This class will capture the type information. In general this mechanism is referred to as super type tokens
 * 
 * 	Three common implementations in Java are:
 * 		+ Guava's TypeToken
 * 		+ Spring's ParameterizedTypeReference
 * 		+ Jackson's TypeReference
 * 
 *		come back later
 *
 */
public class GenericParameterization {

	public class DataService<MODEL_TYPE> {
		private final DataDao dataDao = new DataDao();
		private final Class<MODEL_TYPE> type = (Class<MODEL_TYPE>) new TypeToken<MODEL_TYPE>(getClass()) {
		}.getRawType();

		public List<MODEL_TYPE> getAll() {
			return dataDao.getAllOfType(type);
		}
	}

	// the subclass definitively binds the parameterization to User
	// for all instances of this class, so that information can be
	// recovered at runtime
	public class UserService extends DataService<User> {
	}

	public class Main {
		public void test() {
			UserService service = new UserService();
			List<User> users = service.getAll();
		}
	}
	
	class User {};

	class DataDao {
		public <T> List getAllOfType(T type) {
			return null;};
	};
}
