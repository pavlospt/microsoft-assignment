package com.github.pavlospt.microsoftassignment;

import com.github.pavlospt.microsoftassignment.misc.utils.DBUtils;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class DBUtilsTest {

  @Test
  public void test_generate_like_statement() {
    String column = "COLUMN1";
    int termsSize = 3;

    Assert.assertEquals("(COLUMN1 LIKE ? OR COLUMN1 LIKE ? OR COLUMN1 LIKE ?)",
        DBUtils.INSTANCE.generateLikePerColumn(column, termsSize));
  }

  @Test
  public void test_generate_where_statement() {
    String column1 = "COLUMN1";
    String column2 = "COLUMN2";
    int termsSize = 3;

    List<String> columns = Arrays.asList(column1, column2);

    String expected =
        "(COLUMN1 LIKE ? OR COLUMN1 LIKE ? OR COLUMN1 LIKE ?) OR (COLUMN2 LIKE ? OR COLUMN2 LIKE ? OR COLUMN2 LIKE ?)";

    Assert.assertEquals(expected, DBUtils.INSTANCE.generateWhereStatement(columns, termsSize));
  }

  @Test
  public void test_generated_terms_list() {
    String term1 = "term1";
    String term2 = "term2";
    String term3 = "term3";

    List<String> terms = Arrays.asList(term1, term2, term3);
    List<String> expectedList = Arrays.asList(
        DBUtils.INSTANCE.wrapTerm(term1),
        DBUtils.INSTANCE.wrapTerm(term2),
        DBUtils.INSTANCE.wrapTerm(term3),
        DBUtils.INSTANCE.wrapTerm(term1),
        DBUtils.INSTANCE.wrapTerm(term2),
        DBUtils.INSTANCE.wrapTerm(term3)
    );

    Assert.assertEquals(6, DBUtils.INSTANCE.generateWrappedTermsList(terms, 2).size());
    Assert.assertThat(DBUtils.INSTANCE.generateWrappedTermsList(terms, 2), is(expectedList));
  }
}
