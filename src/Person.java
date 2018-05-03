 import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableSet;
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
 
class Foo {
    final ImmutableSet<Person> immutablePersons;
    Foo(Set<Person> persons) {
      this.immutablePersons = ImmutableSet.copyOf(persons);
      // 保护性拷贝，使集合immutablePersons不可被删除增加元素，但是可以调用元素自身的方法进行元素的修改
    }
}