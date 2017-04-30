package twccomparatormothertongue;

import java.util.Comparator;
import timewordcouple1.*;


public class TWCComparatorMotherTongue implements Comparator<TimeWordCouple>{

	@Override
	public int compare(TimeWordCouple arg0, TimeWordCouple arg1) {
		
		return (arg0.getMotherTongue().compareToIgnoreCase( arg1.getMotherTongue() ) );
	}// Calling: Collections.sort(myList,new TimeWordCoupleCompare());
    //Collections.binarySearch(myList, string, new TimeWordCoupleComparatorMotherTongue);
}
