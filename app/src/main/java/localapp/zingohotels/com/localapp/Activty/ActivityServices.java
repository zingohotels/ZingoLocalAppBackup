package localapp.zingohotels.com.localapp.Activty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import localapp.zingohotels.com.localapp.Adapters.EventListAdapter;
import localapp.zingohotels.com.localapp.Adapters.ServiceListAdapter;
import localapp.zingohotels.com.localapp.ListOfEventsActivity;
import localapp.zingohotels.com.localapp.R;

public class ActivityServices extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Services");
        recyclerView = (RecyclerView) findViewById(R.id.service_list);

        ServiceListAdapter adapter = new ServiceListAdapter(ActivityServices.this);
        recyclerView.setAdapter(adapter);

    }
}
