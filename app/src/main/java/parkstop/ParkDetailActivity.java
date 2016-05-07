package parkstop;
import android.app.Fragment;

import java.util.UUID;

public class ParkDetailActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment() {
        //return new ParkDetailFragment();

        UUID crimeId = (UUID)getIntent()
                .getSerializableExtra(ParkDetailFragment.EXTRA_PARK_ID);
        return ParkDetailFragment.newInstance(crimeId);
    }
}
