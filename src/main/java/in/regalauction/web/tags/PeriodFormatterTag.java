package in.regalauction.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeriodFormatterTag extends SimpleTagSupport {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PeriodFormatterTag.class);

	private DateTime date;
	
	public void setDate(DateTime date) {
		this.date = date;
	}

	@Override
	public void doTag() throws JspException, IOException {
		
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		
		String string = "";
		
		if (date.isAfterNow()) {
			Interval interval = new Interval(DateTime.now(), date);
			Period period = interval.toPeriod();
			
			PeriodFormatter periodFormatYears = new PeriodFormatterBuilder()
				.appendYears()
				.appendSuffix("&nbsp;year", "&nbsp;years")
				.toFormatter();
			
			PeriodFormatter periodFormatMonths = new PeriodFormatterBuilder()	
				.appendMonths()
				.appendSuffix("&nbsp;month", "&nbsp;months")
				.toFormatter();
				
			PeriodFormatter periodFormatWeeks = new PeriodFormatterBuilder()	
				.appendWeeks()
				.appendSuffix("&nbsp;week", "&nbsp;weeks")
				.toFormatter();
			
			PeriodFormatter periodFormatDays = new PeriodFormatterBuilder()	
				.appendDays()
				.appendSuffix("&nbsp;day", "&nbsp;days")
				.toFormatter();
			
			PeriodFormatter periodFormatHours = new PeriodFormatterBuilder()	
				.appendHours()
				.appendSuffix("&nbsp;hour", "&nbsp;hours")
				.toFormatter();
			
			PeriodFormatter periodFormatMinutes = new PeriodFormatterBuilder()	
				.appendMinutes()
				.appendSuffix("&nbsp;minute", "&nbsp;minutes")
				.toFormatter();
			
			PeriodFormatter periodFormatSeconds = new PeriodFormatterBuilder()	
				.appendSeconds()
				.appendSuffix("&nbsp;second", "&nbsp;seconds")
				.toFormatter();
			
			PeriodFormatter periodFormatter = periodFormatSeconds;
			if (period.getYears() > 0) {
				periodFormatter = periodFormatYears;
			} else if (period.getMonths() > 0) {
				periodFormatter = periodFormatMonths;
			} else if (period.getWeeks() > 0) {
				periodFormatter = periodFormatWeeks;
			} else if (period.getDays() > 0) {
				periodFormatter = periodFormatDays;
			} else if (period.getHours() > 0) {
				periodFormatter = periodFormatHours;
			} else if (period.getMinutes() > 0) {
				periodFormatter = periodFormatMinutes;
			} 
			
		 
			string = periodFormatter.print(period);
		}
		
		LOGGER.debug("Period: {}", string);
		
		out.print(string);
		
	}
}
