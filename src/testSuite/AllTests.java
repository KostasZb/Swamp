package testSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import gameTest.TheGameTest;
import gridTest.GridTest;
import gridTest.RowTest;

@RunWith(Suite.class)
@SuiteClasses({ TheGameTest.class,GridTest.class,RowTest.class })


public class AllTests {

}
