package twccomparartotforeign;

import timewordcouple1.*;
import java.util.Comparator;

public class TWCComparatorForeign implements Comparator<TimeWordCouple>{

	@Override
	public int compare(TimeWordCouple arg0, TimeWordCouple arg1) {
		
		return (arg0.getForeignWord().compareToIgnoreCase( arg1.getForeignWord() ) );
	}// Calling: Collections.sort(myList,new TimeWordCoupleCompare());
     //Collections.binarySearch(myList, string, new TimeWordCoupleComparatorForeign);

}
