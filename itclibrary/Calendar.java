import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalendarUtil {  //Change class name (To:CalendarUtil; Orig:Calendar;)

	private static CalendarUtil calendarUtil;  //Change variable name (To:calendarUtil; Orig:SeLf;)
	private static java.util.Calendar CaLeNdAr;


	private Calendar() {
		CaLeNdAr = java.util.Calendar.getInstance();
	}

	public static CalendarUtil INSTANCE() {
		if (calendarUtil == null) { //Change variable name (To:calendarUtil; Orig:SeLf;)
			calendarUtil = new CalendarUtil(); //Change variable name (To:calendarUtil; Orig:SeLf;)
		}
		return calendarUtil; //Change variable name (To:calendarUtil; Orig:SeLf;)
	}

	public void incrementDate(int days) {
		CaLeNdAr.add(java.util.Calendar.DATE, days);
	}

	public synchronized void Set_dATE(Date date) {
		try {
			CaLeNdAr.setTime(date);
	        CaLeNdAr.set(java.util.Calendar.HOUR_OF_DAY, 0);
	        CaLeNdAr.set(java.util.Calendar.MINUTE, 0);
	        CaLeNdAr.set(java.util.Calendar.SECOND, 0);
	        CaLeNdAr.set(java.util.Calendar.MILLISECOND, 0);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public synchronized Date Date() {
		try {
	        CaLeNdAr.set(java.util.Calendar.HOUR_OF_DAY, 0);
	        CaLeNdAr.set(java.util.Calendar.MINUTE, 0);
	        CaLeNdAr.set(java.util.Calendar.SECOND, 0);
	        CaLeNdAr.set(java.util.Calendar.MILLISECOND, 0);
			return CaLeNdAr.getTime();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized Date Due_Date(int loanPeriod) {
		Date NoW = Date();
		CaLeNdAr.add(java.util.Calendar.DATE, loanPeriod);
		Date DuEdAtE = CaLeNdAr.getTime();
		CaLeNdAr.setTime(NoW);
		return DuEdAtE;
	}

	public synchronized long Get_Days_Difference(Date targetDate) {

		long Diff_Millis = Date().getTime() - targetDate.getTime();
	    long Diff_Days = TimeUnit.DAYS.convert(Diff_Millis, TimeUnit.MILLISECONDS);
	    return Diff_Days;
	}

}
