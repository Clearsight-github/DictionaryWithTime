package timewordcouple1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeWordCouple implements Comparable<TimeWordCouple> {

    private Date date;
    private String foreignWord;
    private String motherTongue;
	
	public TimeWordCouple(Date date, String foreignWord, String motherTongue) {
		super();
		this.date = date;
		this.foreignWord = foreignWord;
		this.motherTongue = motherTongue;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getForeignWord() {
		return foreignWord;
	}

	public void setForeignWord(String foreignWord) {
		this.foreignWord = foreignWord;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	/*This separator ensure that the output is not in one line */
	String separator = System.getProperty("line.separator"); 
	
	public String toString() {
		return " " + getDateTime(date) + " " + foreignWord + " " + motherTongue + separator;
	}	
	
	//Format date to String as given in SimpleDateFormat
	public static String getDateTime(Date date) {
	    //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	    return dateFormat.format(date);
	} //Get date time example:  2017/04/22 18:38:20
	
	// Sort by date, I need a compareTo for each attributes of this class
	  public int compareTo(TimeWordCouple o) {	// to change Object o to TimeWordCouple need implements Comparable<TimeWordCouple>
	    if (this.date == null || o.date == null)
	      return 0;
	    return this.date.compareTo(o.date);
	  } // calling : Collections.sort(myList);

}
