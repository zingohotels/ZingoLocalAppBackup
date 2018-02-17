package localapp.zingohotels.com.localapp.CustomImplementations;

import java.util.Comparator;

import localapp.zingohotels.com.localapp.Model.PackageDetails;

/**
 * Created by ZingoHotels.com on 2/15/2018.
 */

public class SortPackageDetails implements Comparator<PackageDetails>
{

    @Override
    public int compare(PackageDetails o1, PackageDetails o2) {
        if(o1.getSellRate() > o2.getSellRate())
        {
            return 1;
        }
        else if(o1.getSellRate() == o2.getSellRate())
        {
            return 0;
        }
        else
        {
            return -1;
        }
    }
}