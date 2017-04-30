package twccomparatordate;

import timewordcouple1.*;
import java.util.Comparator;

public class TWCComparatorDate implements Comparator<TimeWordCouple>{

	@Override
	public int compare(TimeWordCouple arg0, TimeWordCouple arg1) {
		
		return (arg0.getDate().compareTo( arg1.getDate() ) );
	}// Calling: Collections.sort(myList,new TimeWordCoupleCompare());
     //Collections.binarySearch(myList, string, new TimeWordCoupleComparatorForeign);

}