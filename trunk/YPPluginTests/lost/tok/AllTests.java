package lost.tok;

import junit.framework.Test;
import junit.framework.TestSuite;
import lost.tok.opTable.sourceDocument.SourceDocumentTest;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for lost.tok");
		// $JUnit-BEGIN$
		suite.addTestSuite(ToKTest.class);
		suite.addTestSuite(SourceDocumentTest.class);
		suite.addTestSuite(DiscussionTest.class);
		// suite.addTestSuite(OperationtTableTest.class);
		// $JUnit-END$
		return suite;
	}

}
