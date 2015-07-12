package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.fileio.FileTests;
// by FYICenter.com
import tests.fileio.LogHandlerTests;
import tests.logic.RouteTests;

// specify a runner class: Suite.class
@RunWith(Suite.class)

// specify an array of test classes
@Suite.SuiteClasses({
  FileTests.class,
  LogHandlerTests.class,
  RouteTests.class,}
)

// the actual class is empty
public class RunTests {
}
