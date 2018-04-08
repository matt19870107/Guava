 import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
 
public class People{
    private String name;
    private int age;
 
    public People(String name,int age) {
        this.name = name;
        this.age = age;
    }  
 
    public void setAge(int age){
        this.age = age;
    }
 
    @Override
    public int hashCode() {
        return Objects.hashCode(name, age);
    }
 
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return this.name.equals(((People)obj).name) && this.age== ((People)obj).age;
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public static void main(String[] args) {
    	 
        People p1 = new People("Jack", 12);
        System.out.println(p1.hashCode());
 
        HashMap<People, Integer> hashMap = new HashMap<People, Integer>();
        hashMap.put(p1, 1);
 
        System.out.println(hashMap.get(new People("Jack", 12)));
        System.out.println(p1);
    }
    
    @Override
    public String toString() {
    	return MoreObjects.toStringHelper(this).add("name", name).add("age", age).toString();
    }
}
 
