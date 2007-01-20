package lost.tok;

import junit.framework.Test;
import junit.framework.TestSuite;
import lost.tok.opTable.sourceDocument.SourceDocument_test;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for lost.tok");
		// $JUnit-BEGIN$
		suite.addTestSuite(ToK_test.class);
		suite.addTestSuite(SourceDocument_test.class);
		suite.addTestSuite(Discussion_test.class);
		// suite.addTestSuite(OperationtTable_test.class);
		// $JUnit-END$
		return suite;
	}

}
