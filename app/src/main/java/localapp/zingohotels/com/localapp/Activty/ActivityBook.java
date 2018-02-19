package localapp.zingohotels.com.localapp.Activty;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import localapp.zingohotels.com.localapp.ListOfEventsActivity;
import localapp.zingohotels.com.localapp.Model.ActivityModel;
import localapp.zingohotels.com.localapp.Model.Availablity;
import localapp.zingohotels.com.localapp.Model.Bookings;
import localapp.zingohotels.com.localapp.Model.PackageDetails;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.BookingApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityBook extends AppCompatActivity {

    ImageView mAdultAdd,mAdultRem,mChildAdd,mChildRem;
    TextView mChildCount,mAdultCount,mBookDate,mBookTime,mTotalBookingAmount,mBookingAdultPrice,
            mBookingChildPrice,mActivityName,mTotalAdultPrice,mTotalChildPrice,mRemainingTickets;
    Button mNext;


    int childCount = 0,adultCount = 1,remainingtickets;
    int bookingAdultAmount = 0,bookingChildAmount = 0;
    ActivityModel activity;
    SimpleDateFormat simpleDateFormat;
    PackageDetails packageDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Booking ");

        final Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            activity = (ActivityModel)bundle.getSerializable(Constants.ACTIVITY);
            activity = (ActivityModel)bundle.getSerializable(Constants.ACTIVIT_BOOKING);
            packageDetails = (PackageDetails)bundle.getSerializable(Constants.ACTIVIT_PACKAGE);
        }

        mAdultAdd = (ImageView)findViewById(R.id.adult_add);
        mAdultRem = (ImageView)findViewById(R.id.adult_remove);

        //disbled in testng phase
        mChildAdd = (ImageView)findViewById(R.id.child_add);
        mChildRem = (ImageView)findViewById(R.id.child_remove);
        mChildCount = (TextView)findViewById(R.id.book_child_count);
        mBookingChildPrice = (TextView)findViewById(R.id.book_child_price);
        mTotalChildPrice = (TextView)findViewById(R.id.book_child_amount);

        mAdultCount = (TextView)findViewById(R.id.book_adult_count);
        mBookDate = (TextView)findViewById(R.id.book_date);
        mBookTime = (TextView)findViewById(R.id.book_time);
        mBookingAdultPrice = (TextView)findViewById(R.id.booking_adult_price);
        mRemainingTickets = (TextView)findViewById(R.id.book_ticket_remains);

        mTotalAdultPrice = (TextView)findViewById(R.id.book_adult_amount);

        mTotalBookingAmount = (TextView)findViewById(R.id.book_total_amount);
        mActivityName = (TextView)findViewById(R.id.book_activity_name);
        mNext = (Button) findViewById(R.id.next_btn);

        //bookingAmount = Integer.parseInt(mTotalBookingAmount.getText().toString().replace("₹","").trim());
        mAdultAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // int adult = Integer.parseInt(mAdultCount.getText().toString());
                if(adultCount<10 ){
                    if((remainingtickets >= adultCount))
                    {
                        mAdultCount.setText((adultCount+1)+"");
                        ++adultCount;
                        mTotalAdultPrice.setText("₹ "+(bookingAdultAmount*adultCount)+"");
                    }
                    else
                    {
                        Toast.makeText(ActivityBook.this,"You can not select more than available slots",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(ActivityBook.this,"You can only add 10 adults per booking",Toast.LENGTH_LONG).show();
                }


            }
        });
        mAdultRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // int adult = Integer.parseInt(mAdultCount.getText().toString());
                if(adultCount>1){
                    mAdultCount.setText((adultCount-1)+"");
                    --adultCount;
                    mTotalAdultPrice.setText("₹ "+(bookingAdultAmount*adultCount)+"");
                }else{
                    Toast.makeText(ActivityBook.this,"One adult is minimum for booking",Toast.LENGTH_LONG).show();
                }


            }
        });

        //Disabled in testing phase
        mChildAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // int adult = Integer.parseInt(mAdultCount.getText().toString());
                if(childCount<10 ){
                    if((remainingtickets >= childCount))
                    {
                        mChildCount.setText((childCount+1)+"");
                        ++childCount;
                        mTotalChildPrice.setText("₹ "+(bookingChildAmount*childCount)+"");
                    }
                    else
                    {
                        Toast.makeText(ActivityBook.this,"You can not select more than available slots",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(ActivityBook.this,"You can only add 10 childs per booking",Toast.LENGTH_LONG).show();
                }


            }
        });
        mChildRem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // int adult = Integer.parseInt(mAdultCount.getText().toString());
                if(childCount > 0){
                    mChildCount.setText((childCount-1)+"");
                    --childCount;

                    mTotalChildPrice.setText("₹ "+(bookingChildAmount*childCount)+"");
                }else{
                    //Toast.makeText(ActivityBook.this,"One adult is minimum for booking",Toast.LENGTH_LONG).show();
                }


            }
        });

        mTotalAdultPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //System.out.println("adult Changing");

            }

            @Override
            public void afterTextChanged(Editable s) {
                String adultprice = mTotalAdultPrice.getText().toString().replace("₹ ","").trim();
                if(!adultprice.isEmpty())
                {
                    int aprice = Integer.parseInt(adultprice);
                    //mTotalBookingAmount.setText("₹ "+(aprice));
                    //Disable in testing phase
                    String childPrice = mTotalChildPrice.getText().toString().replace("₹ ","").trim();
                    if(!childPrice.isEmpty())
                    {
                        int cPrice = Integer.parseInt(childPrice);
                        int tprice = aprice+cPrice;
                        mTotalBookingAmount.setText("₹ "+(tprice));
                    }
                }
            }
        });
        //disabled in testing phase
        mTotalChildPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("child Changing");//mTotalAdultPrice adultprice aprice

            }

            @Override
            public void afterTextChanged(Editable s) {
                String childPrice = mTotalChildPrice.getText().toString().replace("₹ ","").trim();
                if(!childPrice.isEmpty())
                {
                    int cPrice = Integer.parseInt(childPrice);
                    String adultprice = mTotalAdultPrice.getText().toString().replace("₹ ","").trim();
                    if(!adultprice.isEmpty())
                    {
                        int aprice = Integer.parseInt(adultprice);
                        int tprice = aprice+cPrice;
                        mTotalBookingAmount.setText("₹ "+tprice);
                    }
                }
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent summary = new Intent(ActivityBook.this,BookingDetailsEnter.class);
                startActivity(summary);*/
                validateFields();
            }
        });

        mBookDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(mBookDate);
            }
        });

        mBookTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker(mBookTime);
            }
        });

        if(activity != null)
        {
            mActivityName.setText(activity.getActivityName());
            if(packageDetails != null)
            {
                mTotalBookingAmount.setText("₹ "+packageDetails.getSellRate());
                mBookingAdultPrice.setText("₹ "+packageDetails.getSellRate());
                mTotalAdultPrice.setText("₹ "+packageDetails.getSellRate());
                mBookingChildPrice.setText("₹ "+packageDetails.getSellRateForChild());
                mTotalChildPrice.setText("₹ "+0);
                bookingAdultAmount = packageDetails.getSellRate();
                bookingChildAmount = packageDetails.getSellRateForChild();
            }
            else if(activity.getPackageDetails().get(0) != null)
            {
                mTotalBookingAmount.setText("₹ "+activity.getPackageDetails().get(0).getSellRate());
                mBookingAdultPrice.setText("₹ "+activity.getPackageDetails().get(0).getSellRate());
                mTotalAdultPrice.setText("₹ "+activity.getPackageDetails().get(0).getSellRate());
                mBookingChildPrice.setText("₹ "+activity.getPackageDetails().get(0).getSellRateForChild());
                mTotalChildPrice.setText("₹ "+0);
                bookingAdultAmount = activity.getPackageDetails().get(0).getSellRate();
                bookingChildAmount = activity.getPackageDetails().get(0).getSellRateForChild();
            }
        }
    }

    private void validateFields() {

        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String date = mBookDate.getText().toString();
        String time = mBookTime.getText().toString();

        if(date == null || date.isEmpty())
        {
            mBookDate.setError("Please Select Date");
            mBookDate.requestFocus();
        }
        else if(time == null || time.isEmpty())
        {
            mBookTime.setError("Please Select Time");
            mBookTime.requestFocus();
        }
        else
        {
            Bookings bookings = new Bookings();
            bookings.setBookingDate(simpleDateFormat.format(new Date()));
            bookings.setBookingTimeSlot(time);
            bookings.setActivityDate(date);
            bookings.setProfileId(PreferenceHandler.getInstance(ActivityBook.this).getUserId());
            bookings.setTravellerId(PreferenceHandler.getInstance(ActivityBook.this).getUserId());
            bookings.setTotalAmount(Integer.parseInt(mTotalBookingAmount.getText().toString().replace("₹ ","").trim()));
            bookings.setNoOfAdults(Integer.parseInt(mAdultCount.getText().toString()));
            bookings.setNoOfChilds(Integer.parseInt(mChildCount.getText().toString()));
            bookings.setActivitiesId(activity.getActivitiesId());
            //bookings.setActivities(activity);
            if(packageDetails != null)
            {
                bookings.setSellRate(packageDetails.getSellRate());
                bookings.setDeclaredRate(packageDetails.getDeclaredRate());
                bookings.setDiscount((int)packageDetails.getDiscount());
                bookings.setDiscountAmount(Integer.parseInt(mAdultCount.getText().toString()) * packageDetails.getDiscountAmount());
                bookings.setDeclaredRateForChild(packageDetails.getDeclaredRateForChild());
                bookings.setSellRateForChild(packageDetails.getSellRateForChild());
            }
            else if(activity.getPackageDetails().get(0) != null)
            {
                bookings.setSellRate(activity.getPackageDetails().get(0).getSellRate());
                bookings.setDeclaredRate(activity.getPackageDetails().get(0).getDeclaredRate());
                bookings.setDiscount((int)activity.getPackageDetails().get(0).getDiscount());
                bookings.setDeclaredRateForChild(activity.getPackageDetails().get(0).getDeclaredRateForChild());
                bookings.setSellRateForChild(activity.getPackageDetails().get(0).getSellRateForChild());
                bookings.setDiscountAmount(Integer.parseInt(mAdultCount.getText().toString()) * activity.getPackageDetails().get(0).getDiscountAmount());
            }


            Intent summary = new Intent(ActivityBook.this,BookingDetailsEnter.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.ACTIVITYBOOKING,bookings);
            bundle.putSerializable(Constants.ACTIVITY,activity);
            summary.putExtras(bundle);
            startActivity(summary);
        }
    }

    public void openDatePicker(final TextView tv) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
       int mYear  = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay   = c.get(Calendar.DAY_OF_MONTH);
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String from,to;
                        Log.d("Date", "DATE SELECTED "+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year,monthOfYear,dayOfMonth);


                        String date1 = (monthOfYear + 1)  + "/" + dayOfMonth + "/" + year;
                        //String date2 = (monthOfYear + 1)  + "/" + (dayOfMonth+1) + "/" + year;

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");


                        if (tv.equals(mBookDate)){

                            try {
                                Date fdate = simpleDateFormat.parse(date1);
                               // Date tdate = simpleDateFormat.parse(date2);

                                from = simpleDateFormat.format(fdate);
                                Date activityfdate = simpleDateFormat1.parse(activity.getValidFrom());
                                Date activitytdate = simpleDateFormat1.parse(activity.getValidTo());
                               // to = simpleDateFormat.format(tdate);
                                //long sdate
                                if(fdate.getTime() >= activityfdate.getTime() && fdate.getTime() <= activitytdate.getTime())
                                {
                                    getAvailablity(from);
                                }
                                else
                                {
                                    Toast.makeText(ActivityBook.this,"This activity is not available on this date. Please select another date",
                                            Toast.LENGTH_SHORT).show();
                                }
                                System.out.println("To = "+from);
                                tv.setText(from);
                                //mTo.setText(to);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //


                        }/*else {
                            //to = date1;
                            try {
                                Date tdate = simpleDateFormat.parse(date1);
                                to = simpleDateFormat.format(tdate);
                                System.out.println("To = "+to);
                                tv.setText(to);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }*/



                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.show();

    }

    private void getAvailablity(String from) {

        final ProgressDialog dialog = new ProgressDialog(ActivityBook.this);
        dialog.setTitle("Please wait...");
        dialog.show();

        BookingApi bookingApi = Util.getClient().create(BookingApi.class);
        Availablity availablity = new Availablity();
        availablity.setActivitiesId(activity.getActivitiesId());
        availablity.setActivityDate(from);
        Call<Integer> getresponse = bookingApi.getAvailablity(availablity);
        getresponse.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(dialog != null)
                {
                    dialog.dismiss();
                }
                if(response.code() == 200)
                {
                    remainingtickets = response.body();
                    mRemainingTickets.setText(remainingtickets+" Slots are left");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                if(dialog != null)
                {
                    dialog.dismiss();
                }
            }
        });


    }

    public void openTimePicker(final TextView tv){

        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ActivityBook.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                //tv.setText( selectedHour + ":" + selectedMinute);



                System.out.println("time==="+mcurrentTime);
                if (tv.equals(mBookTime)){
                   String ft = String.format("%02d:%02d:00",hourOfDay,minute);
                    boolean isPM =(hourOfDay >= 12);
                    mBookTime.setText( String.format("%02d:%02d %s ", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
                }/*else if (tv.equals(cout_time_tv)) {
                    tt = String.format("%02d:%02d:00",hourOfDay,minute);
                    boolean isPM =(hourOfDay >= 12);
                    cout_time_tv.setText( String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));

                }*/
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                ActivityBook.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
