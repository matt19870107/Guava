import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Throwables;
import com.google.common.collect.BiMap;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Ordering;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.Table;
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
		System.out.println("naturalOrdering:" + naturalOrdering.reverse().nullsLast().sortedCopy(list));
		System.out.println("compoundOrdering:" + compoundOrdering.sortedCopy(peopleList));
		System.out.println("onResultOf:" + ordering.sortedCopy(peopleList));
		System.out.println("greatestOf:" + ordering.reverse().greatestOf(peopleList, 3));
		System.out.println("min:" + ordering.min(peopleList));
		System.out.println("isOrdered:" + naturalIntReduceOrdering.isOrdered(listtest));
		System.out.println("isStrictlyOrdered:" + naturalIntReduceOrdering.isStrictlyOrdered(listtest));
	}
	
	//@Test
	public void testSpliter(){
		String  ss= ";a;;b;";
		Iterable<String> s = Splitter.on(';')
        .trimResults()
        .limit(2)
        .omitEmptyStrings()
        .split(ss);
		System.out.println(s.toString());
		String aa[] = ",a,,b,".split(",");
		for(String str : aa) {
			System.out.println("value:"+str);
		}
		
	}
	
	//@Test
	public void testCharMatcher() {
        String removeFromResult = CharMatcher.isNot('a').removeFrom("abacd");  
        System.out.println("removeForm:" + removeFromResult);  
        String retainFormResult = CharMatcher.is('a').retainFrom("abacd");  
        System.out.println("retainForm:" + retainFormResult);  
        String replaceFormResult1 = CharMatcher.whitespace().replaceFrom("a bcd", 'f');  
        System.out.println("replaceFrom_1:" + replaceFormResult1);  
        String replaceFormResult2 = CharMatcher.digit().replaceFrom("a3bcd", "Three");  
        System.out.println("replaceFrom_2:" + replaceFormResult2);  
        String trimFromResult = CharMatcher.anyOf("ab").trimFrom("abacatabb");  
        System.out.println("trimFrom:" + trimFromResult);  
        String trimLeadingFromResult = CharMatcher.anyOf("ab").trimLeadingFrom("abacatabb");  
        System.out.println("trimLeadingFrom:" + trimLeadingFromResult);  
        String trimTrailingFromResult = CharMatcher.anyOf("ab").trimTrailingFrom("abacatabb");  
        System.out.println("trimTrailingFrom:" + trimTrailingFromResult);  
        String collapseFromResult = CharMatcher.anyOf("bre").collapseFrom("bookkeeper", '-');
        System.out.println("collapseFrom:" + collapseFromResult);  
        String trimAndCollapseFromResult = CharMatcher.anyOf("bre").trimAndCollapseFrom("bookkeeper", '-');  
        System.out.println("trimAndCollapseFrom:" + trimAndCollapseFromResult);  
        boolean matchesAllOfResult = CharMatcher.javaUpperCase().matchesAnyOf("hCd");  
        System.out.println("matchesAnyOf:" + matchesAllOfResult);  
        String orResult = CharMatcher.javaDigit().or(CharMatcher.javaUpperCase()).retainFrom("dd59cF");  
        System.out.println("or:" + orResult);  
        String negateResult = CharMatcher.javaDigit().negate().retainFrom("dd59cF");  
        System.out.println("negate:" + negateResult);  
        boolean matchesAllOf = CharMatcher.anyOf("adddddbccc").matchesAllOf("adddddbccc");
        System.out.println("matchesAllOf:" + matchesAllOf);
        boolean rangeResult = CharMatcher.inRange('a', 'c').matchesAllOf("abc");
        System.out.println("rangeResult:" + rangeResult);  
		
	}
	
	//@Test
	public void testUnmodifiableCollection(){
		List<String> list = Lists.newArrayList();
		list.add("peida");
		list.add("jerry");
		list.add("harry");
		list.add("eva");
		list.add("jhon");
		list.add("neron");
		List<String> undifiableList = Collections.unmodifiableList(list);
		ImmutableSet<String> immutableSet = ImmutableSet.<String>builder()
	            .addAll(list)
	            .add("matt")
	            .build();
		ImmutableSet<String> copyofset = ImmutableSet.copyOf(list);
		ImmutableSet<List<String>> ofset = ImmutableSet.of(list);
		System.out.println("undifiableList:" + undifiableList);
		System.out.println("immutableSet:" + immutableSet);
		System.out.println("copyofset:" + copyofset);
		System.out.println("ofset:" + ofset);
		list.add("1211");
		System.out.println("-----------after list modified------------");
		System.out.println("undifiableList:" + undifiableList);
		System.out.println("immutableSet:" + immutableSet);
		System.out.println("copyofset:" + copyofset);
		System.out.println("ofset:" + ofset);
	}
	
	@Test
	public void testMutiSet() {
		 String strWorld="wer|dfd|dd|dfd|dda|de|dr";
	        Iterable<String> words= Splitter.on("|").split(strWorld);
	        List<String> wordList=new ArrayList<String>();
	        for (String word : words) {
	            wordList.add(word);
	        }
	        Multiset<String> wordsMultiset = HashMultiset.create();
	        wordsMultiset.addAll(wordList);
	        
	        for(String key:wordsMultiset.elementSet()){
	            System.out.println(key+" count:"+wordsMultiset.count(key));
	        }
	        if(!wordsMultiset.contains("peida")){
	            wordsMultiset.add("peida", 22);
	        }
	        wordsMultiset.remove("peida", 2);
	        System.out.println("============================================");
	        for(String key:wordsMultiset.elementSet()){
	            System.out.println(key+" count:"+wordsMultiset.count(key));
	        }
	        if(wordsMultiset.contains("peida")){
	            wordsMultiset.setCount("peida", 23);
	        }
	        System.out.println("============================================");
	        for(String key:wordsMultiset.elementSet()){
	            System.out.println(key+" count:"+wordsMultiset.count(key));
	        }
	        if(wordsMultiset.contains("peida")){
	            wordsMultiset.setCount("peida", 23,45);
	        }
	        System.out.println("============================================");
	        for(String key:wordsMultiset.elementSet()){
	            System.out.println(key+" count:"+wordsMultiset.count(key));
	        }
	}
	
	//@Test
	public void testIterator() {
		 String strWorld="wer|dfd|dd|dfd|dda|de|dr";
	        Iterable<String> words= Splitter.on("|").split(strWorld);
	        List<String> result = Lists.newArrayList();  
	        PeekingIterator<String> iter = Iterators.peekingIterator(words.iterator());  
	        while (iter.hasNext()) {  
	            String current = iter.next();  
	            while (iter.hasNext() && iter.peek().equals(current)) {  
	                //跳过重复的元素  
	                iter.next();  
	            }  
	            result.add(current);  
	        }  
	        System.out.println(result);
	}
	
	//@Test
	public void testToString() {
		System.out.println(new People("",23));
	}
	
	//@Test
	public void testJoiner(){
		List<String> list = Lists.newArrayList();
		list.add("peida");
		list.add("jerry");
		list.add("harry");
		list.add("eva");
		list.add("jhon");
		list.add("neron");
		StringBuilder sb = new StringBuilder("result:");
		System.out.println(Joiner.on(",").appendTo(sb, list));
	}
	
	//@Test
	public void testBimap(){
		BiMap<String, Integer> userId = HashBiMap.create();
		userId.put("matt", 28);
		userId.put("matt1", 29);
		System.out.println(userId.inverse().get(28));
	}
	
	//@Test
	public void testTable(){
		Table<String, String, Integer> weightedGraph = HashBasedTable.create();
		weightedGraph.put("v1", "v2",  4);
		weightedGraph.put("v1", "v3", 20);
		weightedGraph.put("v2", "v3", 5);
		System.out.println(weightedGraph.row("v1"));

	}
	
	
}
