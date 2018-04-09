import java.io.IOException;
import java.util.ArrayList;
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
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

public class TestException {
	
	 @Test
	 public void testException(){
		 try {
             g();
         } catch (ExceptionC e) {
        	 System.out.println(Throwables.getCausalChain(e));
        	 System.out.println(Throwables.getRootCause(e));
        	 System.out.println(Throwables.getStackTraceAsString(e));
         }
	   }

	 static void f() throws ExceptionB{
	        throw new ExceptionB("exception b");
	 }
	 
	 static void g() throws ExceptionC {
        try {
            f();
        } catch (ExceptionB e) {
            ExceptionC c = new ExceptionC("exception a");
            //“Ï≥£¡¨
            c.initCause(e);
            throw c;
        }
    }
}

 class ExceptionA extends Exception {
		 public ExceptionA(String str) {
		 super();
    }
}
 
 class ExceptionB extends ExceptionA {
 
    public ExceptionB(String str) {
        super(str);
    }
}
 
 class ExceptionC extends ExceptionA {
    public ExceptionC(String str) {
        super(str);
    }
}
