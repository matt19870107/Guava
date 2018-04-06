import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
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
	/*public void testAdvanceOrdering() {
		List<People> list = Lists.newArrayList();
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
	}*/
}
