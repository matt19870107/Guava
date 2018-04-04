 import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
 
public class Person implements Comparable<Person>{
	private String lastName;
	private String firstName;
	private int zipCode;

    public int compareTo(Person other) {
    	 return ComparisonChain.start()
    			             .compare(this.lastName, other.lastName)
    			             .compare(this.firstName, other.firstName)
    			             .compare(this.zipCode, other.zipCode)
    			             .result();
    }
 
}
 
