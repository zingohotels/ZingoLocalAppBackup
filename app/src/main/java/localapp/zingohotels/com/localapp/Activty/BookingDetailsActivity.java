package localapp.zingohotels.com.localapp.Activty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import localapp.zingohotels.com.localapp.MainActivity;
import localapp.zingohotels.com.localapp.Model.ActivityModel;
import localapp.zingohotels.com.localapp.Model.BookingPayment;
import localapp.zingohotels.com.localapp.Model.Bookings;
import localapp.zingohotels.com.localapp.Model.PackageDetails;
import localapp.zingohotels.com.localapp.Model.Profile;
import localapp.zingohotels.com.localapp.Model.UserProfile;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.BookingApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsActivity extends AppCompatActivity {

    TextView mBookedUserName,mCongratsMessage,mBookingNumber,mVoucherText,mInvoiceText,mConfirmText,
    mBookedActivityName,mBookedActivityPlace,mBookingDate,mBookedAdultNo,mBookedChildNo,mActivityInclusions,
    mBookedTravellerName,mBookedSellRate,mBookedDiscountAmount,mBookedDisplayRate,mBookedToatalAmount,mBookingTime;
    LinearLayout mBookingDateLinearLayout,mBookingTimeLinearLayout,mBookingInclusionLinearLayout;
    ImageView confirmImage;
    Button mGotoHome;



    Bookings bookedbookings;
    int bookingid;
    ActivityModel activityModel;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Booking Details");
        mBookedUserName = (TextView) findViewById(R.id.booking_details_user_name);
        mCongratsMessage = (TextView) findViewById(R.id.booking_details_congrats_message);
        mBookingNumber = (TextView) findViewById(R.id.booking_details_boking_number);
        mVoucherText = (TextView) findViewById(R.id.booking_details_voucher_text);
        mInvoiceText = (TextView) findViewById(R.id.booking_details_invoice_text);
        mConfirmText = (TextView) findViewById(R.id.booking_details_confirm_text);
        mBookedActivityName = (TextView) findViewById(R.id.booking_details_activity_name);
        mBookedActivityPlace = (TextView) findViewById(R.id.booking_details_activity_place);
        mBookingDate = (TextView) findViewById(R.id.booking_detail_date);
        mBookedAdultNo = (TextView) findViewById(R.id.booked_adult_count);
        mBookedChildNo = (TextView) findViewById(R.id.booked_child_count);
        mActivityInclusions = (TextView) findViewById(R.id.booked_acivity_package_description);
        mBookedTravellerName = (TextView) findViewById(R.id.booked_details_traveller_name);
        mBookedSellRate = (TextView) findViewById(R.id.booked_activity_sell_rate);
        mBookedDiscountAmount = (TextView) findViewById(R.id.booked_actvity_discount);
        mBookedDisplayRate = (TextView) findViewById(R.id.booked_activity_display_rate);
        mBookedToatalAmount = (TextView) findViewById(R.id.booked_activity_total_amount);
        mBookingTime = (TextView) findViewById(R.id.booking_detail_time);

        confirmImage = (ImageView) findViewById(R.id.booking_confirmation_icon);
        mGotoHome = (Button) findViewById(R.id.go_to_home_button);

        mBookingDateLinearLayout = (LinearLayout) findViewById(R.id.booked_date_linear_layout);
        mBookingTimeLinearLayout = (LinearLayout) findViewById(R.id.booked_time_linear_layout);
        mBookingInclusionLinearLayout = (LinearLayout) findViewById(R.id.booked_inclusion_linear_layout);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            bookingid =  bundle.getInt(Constants.DETAIL_BOOKING);
            activityModel = (ActivityModel) bundle.getSerializable(Constants.ACTIVITY);
            status = bundle.getString("BookingStatus");
            getBooking(bookingid);
            //setFields(bookedbookings,activityModel,status);
        }
        mGotoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingDetailsActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                BookingDetailsActivity.this.finish();
            }
        });
    }

    private void setFields(Bookings bookedbookings, ActivityModel activityModel, String status) {
        if(bookedbookings.getProfiles() != null)
        {
            UserProfile profile = bookedbookings.getProfiles();
            mBookedUserName.setText(profile.getPrefix()+" "+profile.getFullName());
            mBookedTravellerName.setText(profile.getPrefix()+" "+profile.getFullName());
        }
        else
        {
            mBookedUserName.setText(PreferenceHandler.getInstance(BookingDetailsActivity.this).getUserFullName());
            mBookedTravellerName.setText(PreferenceHandler.getInstance(BookingDetailsActivity.this).getUserFullName());
        }
        if(status.equalsIgnoreCase("Success"))
        {
            confirmImage.setImageResource(R.drawable.booking_success_icon);
            mCongratsMessage.setText("Congratulations! Your Booking is Confirmed");
            mVoucherText.setText("Please find your voucher attached with your registred email");
            mInvoiceText.setText("Invoice is sent to your registred email id");
            mConfirmText.setText("Kindly note your booking is now confirmed. Your are not required to contact ZingoHotels to confirm further");
        }
        else if(status.equalsIgnoreCase("Failed"))
        {
            confirmImage.setImageResource(R.drawable.booking_filed_icon);
            mCongratsMessage.setText("Sorry! Your Booking is failed");
            mVoucherText.setVisibility(View.GONE);
            mInvoiceText.setVisibility(View.GONE);
            mConfirmText.setVisibility(View.GONE);
        }

        mBookingNumber.setText("Your Booking Number "+bookedbookings.getBookingNumber());
        if(activityModel == null)
        {
            mBookedActivityName.setText(bookedbookings.getActivities().getActivityName());
            mBookedActivityPlace.setText(bookedbookings.getActivities().getAddress());
            mActivityInclusions.setText(bookedbookings.getActivities().getWhatIWillProvide());
        }
        else
        {
            mBookedActivityName.setText(activityModel.getActivityName());
            mBookedActivityPlace.setText(activityModel.getAddress());
            mActivityInclusions.setText(activityModel.getWhatIWillProvide());
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("MMM dd yyyy");
        try
        {
            Date bdate = simpleDateFormat.parse(bookedbookings.getActivityDate());
            mBookingDate.setText(simpleDateFormat1.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mBookedAdultNo.setText(bookedbookings.getNoOfAdults()+" Adults");
        mBookedChildNo.setText(bookedbookings.getNoOfChilds()+" Childs");
        mBookingTime.setText(bookedbookings.getBookingTimeSlot());
        mBookedSellRate.setText("₹ "+bookedbookings.getSellRate()+"");
        mBookedDiscountAmount.setText("₹ "+bookedbookings.getDiscountAmount()+"");
        mBookedDisplayRate.setText("₹ "+bookedbookings.getDeclaredRate()+"");
        mBookedToatalAmount.setText("₹ "+bookedbookings.getTotalAmount()+"");

    }

    public void getBooking(int id)
    {
        final ProgressDialog dialog = new ProgressDialog(BookingDetailsActivity.this);
        dialog.setTitle("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        BookingApi bookingApi = Util.getClient().create(BookingApi.class);
        Call<Bookings> response = bookingApi.getBooking(id);

        response.enqueue(new Callback<Bookings>() {
            @Override
            public void onResponse(Call<Bookings> call, Response<Bookings> response) {
                if(dialog != null)
                {
                    dialog.dismiss();
                }

                if(response.code() == 200 || response.code() == 201)

                {
                    if(response.body() != null)
                    {

                        bookedbookings = response.body();
                        setFields(response.body(),activityModel,status);

                    }

                }
                else
                {
                    Toast.makeText(BookingDetailsActivity.this,response.message(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Bookings> call, Throwable t) {
                if(dialog != null)
                {
                    dialog.dismiss();
                }
                Toast.makeText(BookingDetailsActivity.this,getResources().getString(R.string.response_failure_message),Toast.LENGTH_SHORT).show();
                //Toast.makeText(BookingDetailsEnter.this,response.message(),Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(BookingDetailsEnter.this,BookingDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.DETAIL_BOOKING,bookings);
                bundle.putString("BookingStatus","Failed");
                intent.putExtras(bundle);
                startActivity(intent);*/
            }
        });
    }
    private void setviews(PackageDetails packageDetails) {

        String descr = packageDetails.getDescription();

        String[] splitedString = descr.split(",");

        if(splitedString.length != 0)
        {
            for (int i=0;i<splitedString.length;i++)
            {
                if(splitedString[i] != null && !splitedString[i].isEmpty())
                {
                    createView(splitedString[i]);
                }
                //createView("");
            }
        }
    }

    private void createView(String s) {

        TableRow tr_head = new TableRow(this);
        // tr_head.setId(10);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(params);

        TextView label_date = new TextView(this);
        //label_date.setId(20);
        label_date.setText("\u2022");

        label_date.setGravity(Gravity.CENTER);

        label_date.setTextColor(Color.WHITE);
        label_date.setPadding(5, 5, 5, 5);
        //label_date.

        tr_head.addView(label_date);// add the column to the table row here
        setMargins(label_date,10,5,10,5);

        TextView label_weight_kg = new TextView(this);
        //label_weight_kg.setId(21);// define id that must be unique
        label_weight_kg.setText(s);
        label_weight_kg.setGravity(Gravity.CENTER);// set the text for the header
        label_weight_kg.setTextColor(Color.WHITE); // set the color
        setMargins(label_weight_kg,10,5,10,5);
        label_weight_kg.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_weight_kg); // add the column to the table row here

        /*t1.addView(tr_head, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.FILL_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));*/
    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                BookingDetailsActivity.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
