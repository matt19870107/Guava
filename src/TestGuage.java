import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

public class TestGuage {
	
	//@Test
	public void testOption() {
		Optional<Integer> possible=Optional.of(6);
        Optional<Integer> absentOpt=Optional.absent();
        Optional<Integer> NullableOpt=Optional.fromNullable(null);
        Optional<Integer> NoNullableOpt=Optional.fromNullable(10);
        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Optional<List<Integer>> NoNullableList=Optional.fromNullable(list);
        if(possible.isPresent()){
            System.out.println("possible isPresent:"+possible.isPresent());
            System.out.println("possible value:"+possible.get());
        }
        if(absentOpt.isPresent()){
            System.out.println("absentOpt isPresent:"+absentOpt.isPresent());
        }else {
        	System.out.println("absentOpt isPresent OR:"+absentOpt.or(13));
        }
        if(NullableOpt.isPresent()){
            System.out.println("fromNullableOpt isPresent:"+NullableOpt.isPresent());
        }else {
        	System.out.println("fromNullableOpt isPresent OR:"+absentOpt.orNull());
        }
        if(NoNullableOpt.isPresent()){
            System.out.println("NoNullableOpt isPresent:"+NoNullableOpt.isPresent());
        }
        if(NoNullableList.isPresent()){
            System.out.println("NoNullableList asSet:"+NoNullableList.asSet());
        }
	}
	
	//@Test
	public void testTraditionalPreconditions() {
		 int[] intArray = {1, 2, 3, 4, 5, 6};
		 testPreconditions(true, intArray, 6);
		 testTraditionPreconditions(true, intArray, 6);
	}
	

	 private void testTraditionPreconditions(boolean preCondition, int[] array, int position) {
	        if (!preCondition) {
	            throw new IllegalArgumentException("preCondition not allow!!");
	        }
	        if (array == null) {
	            throw new NullPointerException("array is null!!");
	        }
	        if (array.length == 0) {
	            throw new IllegalArgumentException("array length is 0!!");
	        }
	        if (position > array.length || position < 0) {
	            throw new ArrayIndexOutOfBoundsException("position error!!");
	        }
	 }
	
	private void testPreconditions(boolean preCondition, int[] array, int position) {
	        Preconditions.checkArgument(preCondition);
	        Preconditions.checkNotNull(array);
	        Preconditions.checkPositionIndex(position, array.length, "position error!");
	 }

	
	//@Test
	public void testOrdering() {
		List<String> list = Lists.newArrayList();
		list.add("peida");
		list.add("jerry");
		list.add("harry");
		list.add("eva");
		list.add("jhon");
		list.add("neron");

		System.out.println("list:" + list);

		Ordering<String> naturalOrdering = Ordering.natural();
		Ordering<Object> usingToStringOrdering = Ordering.usingToString();
		Ordering<String> byLengthOrdering = new Ordering<String>() {
			@Override
			public int compare(String left, String right) {
				return Ints.compare(left.length(), right.length());
			}
		};
		System.out.println("naturalOrdering:" + naturalOrdering.sortedCopy(list));
		System.out.println("usingToStringOrdering:" + usingToStringOrdering.sortedCopy(list));
		System.out.println("byLengthOrdering:" + byLengthOrdering.sortedCopy(list));
	}
	
	//@Test
	public void testAdvanceOrdering() {
		List<String> list = Lists.newArrayList();
		list.add("peida");
		list.add(null);
		list.add("jerry");
		list.add("harry");
		list.add("eva");
		list.add("jhon");
		list.add("neron");
		
		List<People> peopleList = Lists.newArrayList();
		peopleList.add(new People("peida",24));
		peopleList.add(new People("harry",28));
		peopleList.add(new People("jhon",18));
		peopleList.add(new People("harry",32));
		peopleList.add(new People("eva",18));
		peopleList.add(new People("harry",26));
		
		List<Integer> listtest= Lists.newArrayList();
        listtest.add(1);
        listtest.add(2);
        listtest.add(3);
        listtest.add(3);
	    Ordering<Integer> naturalIntReduceOrdering = Ordering.natural();

		System.out.println("list:" + list);

		Ordering<String> naturalOrdering = Ordering.natural();
		Ordering<People> compoundOrdering = new Ordering<People>() {
			  public int compare(People left, People right) {
				    return left.getName().compareTo(right.getName());
			  }
		}.compound(new Comparator<People>() {
		    public int compare(People people1, People people2) {
		        return people2.getAge() - people1.getAge();
		    }
		});
		
		Ordering<People> ordering = Ordering.natural().nullsFirst().reverse().onResultOf(new Function<People, String>() {
			public String apply(People input) {
				return String.valueOf(input.getAge());
			}
			});
		System.out.println("naturalOrdering:" + naturalOrdering.nullsLast().reverse().sortedCopy(list));
		System.out.println("compoundOrdering:" + compoundOrdering.sortedCopy(peopleList));
		System.out.println("onResultOf:" + ordering.sortedCopy(peopleList));
		System.out.println("greatestOf:" + ordering.reverse().greatestOf(peopleList, 3));
		System.out.println("min:" + ordering.min(peopleList));
		System.out.println("isOrdered:" + naturalIntReduceOrdering.isOrdered(listtest));
		System.out.println("isStrictlyOrdered:" + naturalIntReduceOrdering.isStrictlyOrdered(listtest));
	}
	
	@Test
	public void testSpliter(){
		String  ss= ";a;;b;";
		Iterable<String> s = Splitter.on(';')
        .trimResults()
        .omitEmptyStrings()
        .limit(2)
        .split(ss);
		System.out.println(s.toString());

	}
	
}
