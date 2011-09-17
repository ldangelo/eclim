/**
 * Copyright (C) 2005 - 2011  Eric Van Dewoestine
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.eclim.plugin.jdt.command.webxml;

import java.util.HashMap;
import java.util.List;

import org.eclim.Eclim;

import org.eclim.plugin.jdt.Jdt;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test case for ValidateCommand.
 *
 * @author Eric Van Dewoestine
 */
public class ValidateCommandTest
{
  private static final String TEST_FILE = "webxml/web.xml";

  @Test
  @SuppressWarnings("unchecked")
  public void validateXmlErrors()
  {
    List<HashMap<String,Object>> results = (List<HashMap<String,Object>>)
      Eclim.execute(new String[]{
        "webxml_validate", "-p", Jdt.TEST_PROJECT, "-f", TEST_FILE
      });

    assertEquals("Wrong number of results", 3, results.size());

    String file = Eclim.resolveFile(Jdt.TEST_PROJECT, TEST_FILE);

    HashMap<String,Object> error = results.get(0);
    assertEquals(error.get("filename"), file);
    assertEquals(error.get("message"),
        "Class 'foo.bar.Listener' not found in project 'eclim_unit_test_java'.");
    assertEquals(error.get("line"), 12);
    assertEquals(error.get("column"), 1);
    assertEquals(error.get("warning"), 0);

    error = results.get(1);
    assertEquals(error.get("filename"), file);
    assertEquals(error.get("message"),
        "Class 'org.apache.solr.servlet.SolrServlet' " +
        "not found in project 'eclim_unit_test_java'.");
    assertEquals(error.get("line"), 22);
    assertEquals(error.get("column"), 1);
    assertEquals(error.get("warning"), 0);

    error = results.get(2);
    assertEquals(error.get("filename"), file);
    assertEquals(error.get("message"),
        "No servlet definition with name 'pong' defined.");
    assertEquals(error.get("line"), 32);
    assertEquals(error.get("column"), 1);
    assertEquals(error.get("warning"), 0);
  }
}
