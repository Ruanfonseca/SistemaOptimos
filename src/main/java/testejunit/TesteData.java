package testejunit;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.primefaces.model.StreamedContent;

import br.com.project.report.util.DateUtils;
import br.com.project.report.util.ReportUtil;

public class TesteData {
	@Test
	public void testGetDateAtualReportName() {
		try {

			assertEquals("08022023", DateUtils.getDateAtualReportName());
			assertEquals("'2023-02-08'", DateUtils.formatDateSqlSimple(Calendar.getInstance().getTime()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}