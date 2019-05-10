package localapp.zingohotels.com.localapp.Activty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import localapp.zingohotels.com.localapp.Model.ActivityModel;
import localapp.zingohotels.com.localapp.Model.BookingPayment;
import localapp.zingohotels.com.localapp.Model.Bookings;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.BookingApi;
import localapp.zingohotels.com.localapp.WebApiClients.BookingPaymentApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailsEnter extends AppCompatActivity implements PaymentResultListener{

    RadioButton mPerson,mBusiness,mOnlinePay,mCashPay,mBookingMr,mBookingMs,mBookingMrs;
    LinearLayout mPersonLay,mBusinessLay;
    TextView mBookingDate,mBookingTime,mBookingDuration,mPersonCount,mChildCount,mDisplayPrice,mDiscountPrice,mTotalPrice,mTotal,
            mDisplayChildPrice;
    ImageView mActivityImage;
    EditText userName,mUserPhoneNumber,mUserEmail;
    Button mPayBtn;

    Bookings bookings,successbooking,failedbooking;
    ActivityModel activityDetail;
    String orderid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details_enter);

        mPerson = (RadioButton)findViewById(R.id.booking_personal);
        mBusiness = (RadioButton)findViewById(R.id.booking_company);
        mPersonLay = (LinearLayout)findViewById(R.id.personal_layout);
        mBusinessLay = (LinearLayout)findViewById(R.id.business_layout);
        mBookingDate = (TextView) findViewById(R.id.booking_detils_date);
        mBookingTime = (TextView) findViewById(R.id.booking_detils_time);
        mBookingDuration = (TextView) findViewById(R.id.booking_details_duration);
        mPersonCount = (TextView) findViewById(R.id.person_count);
        mChildCount = (TextView) findViewById(R.id.child_count);
        mDisplayPrice = (TextView) findViewById(R.id.display_price);
        mDisplayChildPrice = (TextView) findViewById(R.id.display_child_price);
        mDiscountPrice = (TextView) findViewById(R.id.discount_price);
        mTotalPrice = (TextView) findViewById(R.id.booking_total);
        mTotal = (TextView) findViewById(R.id.total_amount);
        userName = (EditText) findViewById(R.id.booking_user_name);
        mUserPhoneNumber = (EditText) findViewById(R.id.booking_user_phone_number);
        mUserEmail = (EditText) findViewById(R.id.booking_user_email);
        mActivityImage = (ImageView) findViewById(R.id.activity_name_image);
        mPayBtn = (Button) findViewById(R.id.payment_btn);
        mOnlinePay = (RadioButton) findViewById(R.id.booking_online_pay);
        mCashPay = (RadioButton) findViewById(R.id.booking_cash_pay);
        mBookingMr = (RadioButton) findViewById(R.id.booking_mr);
        mBookingMs = (RadioButton) findViewById(R.id.booking_ms);
        mBookingMrs = (RadioButton) findViewById(R.id.booking_mrs);

        mPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersonLay.setVisibility(View.VISIBLE);
                mBusinessLay.setVisibility(View.GONE);
            }
        });

        Checkout.preload(BookingDetailsEnter.this);

        mBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBusinessLay.setVisibility(View.VISIBLE);
                mPersonLay.setVisibility(View.GONE);
            }
        });

        mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderid = "Zingo"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                if(mCashPay.isChecked())
                {
                    System.out.println("mCashpay");
                    bookings.setBookingNumber(orderid);
                    bookeTicket(bookings,"Cash");
                    //bookeTicket();
                }
                else if(mOnlinePay.isChecked())
                {
                    System.out.println("mOnlinePay");
                    //startPayment();
                    bookings.setBookingNumber(orderid);
                    bookeTicket(bookings,"Online");
                }
                else
                {
                    Toast.makeText(BookingDetailsEnter.this,"Please select payment mode",Toast.LENGTH_SHORT).show();
                }
            }
        });


        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            bookings = (Bookings) bundle.getSerializable(Constants.ACTIVITYBOOKING);
            activityDetail = (ActivityModel) bundle.getSerializable(Constants.ACTIVITY);
            /*System.out.println(bookings.getActivities().getActivityName());
            System.out.println(bookings.getBookingDate());
            System.out.println(bookings.getTotalAmount());*/
            System.out.println(bookings.getBookingTimeSlot());
            fillFields(bookings,activityDetail);
        }


    }

    private void fillFields(Bookings bookings,ActivityModel activityModel) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM");
            if(bookings.getActivityDate()!=null&&bookings.getActivityDate().contains("-")){
                mBookingDate.setText(simpleDateFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(bookings.getActivityDate())));

            }else if(bookings.getActivityDate()!=null){
                mBookingDate.setText(simpleDateFormat.format(new SimpleDateFormat("MM/dd/yyyy").parse(bookings.getActivityDate())));

            }

            mBookingTime.setText(bookings.getBookingTimeSlot());
            mPersonCount.setText(bookings.getNoOfAdults()+" Adults");
            mChildCount.setText(bookings.getNoOfChilds()+" Childs");
            if(activityModel.getActivityImages().size() != 0) {
                //mActivityImage.setImageBitmap(Util.convertToBitMap(activityModel.getActivityImages().get(0).getImages()));
                Picasso.with(getApplicationContext()).load((activityModel.getActivityImages().get(0).getImages())).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(mActivityImage);

            }
            mDisplayPrice.setText("₹ "+bookings.getSellRate());
            mDisplayChildPrice.setText("₹ "+bookings.getSellRateForChild());
            mDiscountPrice.setText("₹ "+bookings.getDiscountAmount());
            mTotalPrice.setText("₹ "+bookings.getTotalAmount());
            mTotal.setText("₹ "+bookings.getTotalAmount());
            userName.setText(PreferenceHandler.getInstance(BookingDetailsEnter.this).getUserFullName());
            mUserEmail.setText(PreferenceHandler.getInstance(BookingDetailsEnter.this).getUserEmail());
            mUserPhoneNumber.setText(PreferenceHandler.getInstance(BookingDetailsEnter.this).getUserPhone());
            if(PreferenceHandler.getInstance(BookingDetailsEnter.this).getUserPrefix().equalsIgnoreCase("Mr"))
            {
                mBookingMr.setChecked(true);
            }
            else if(PreferenceHandler.getInstance(BookingDetailsEnter.this).getUserPrefix().equalsIgnoreCase("Ms"))
            {
                mBookingMs.setChecked(true);
            }
            else if(PreferenceHandler.getInstance(BookingDetailsEnter.this).getUserPrefix().equalsIgnoreCase("Mrs"))
            {
                mBookingMrs.setChecked(true);
            }

            //PreferenceHandler.getInstance(BookingDetailsEnter.this).ge

            //SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");

        }
        catch (ParseException ex)
        {
            ex.printStackTrace();
            System.out.println(ex.toString());
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(BookingDetailsEnter.this,"Bookng done successfully",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(BookingDetailsEnter.this,BookingDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.DETAIL_BOOKING,successbooking.getBookingId());
        bundle.putString("BookingStatus","Success");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Intent intent = new Intent(BookingDetailsEnter.this,BookingDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.DETAIL_BOOKING,successbooking.getBookingId());
        bundle.putString("BookingStatus","Failed");
        //bundle.putSerializable(Constants.ACTIVITY,activityDetail);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startPayment()
    {

        Checkout checkout = new Checkout();

        try
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name","ZINGO");
            jsonObject.put("description","Order #"+orderid);
            jsonObject.put("currency","INR");
            //jsonObject.put("amount",bookings.getTotalAmount()*100);
            jsonObject.put("amount",1*100);

            JSONObject preFill = new JSONObject();
            preFill.put("email",PreferenceHandler.getInstance(BookingDetailsEnter.this).getUserEmail());
            preFill.put("contact",PreferenceHandler.getInstance(BookingDetailsEnter.this).getUserPhone());

            jsonObject.put("prefill", preFill);

            checkout.open(BookingDetailsEnter.this,jsonObject);

        }
        catch (Exception ex)
        {
            Toast.makeText(BookingDetailsEnter.this, "Error in payment: " + ex.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            ex.printStackTrace();
        }
    }

    public void bookeTicket(final Bookings bookings,String mode)
    {
        final ProgressDialog dialog = new ProgressDialog(BookingDetailsEnter.this);
        dialog.setTitle("Please wait...");
        dialog.setCancelable(false);
        dialog.show();

        BookingApi bookingApi = Util.getClient().create(BookingApi.class);
        Call<Bookings> response = bookingApi.postBookings(bookings);

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

                        BookingPayment bookingPayment = new BookingPayment();
                        bookingPayment.setPaymentName(orderid);
                        bookingPayment.setAmount(bookings.getTotalAmount());
                        bookingPayment.setBookingId(response.body().getBookingId());
                        if(mCashPay.isChecked())
                        {
                            bookingPayment.setPaymentType("Cash");
                        }
                        else if(mOnlinePay.isChecked())
                        {
                            bookingPayment.setPaymentType("Online");
                        }
                        //addPayment(bookingPayment);
                        successbooking = response.body();
                        if(mOnlinePay.isChecked())
                        {
                            startPayment();
                        }
                        else if(mCashPay.isChecked())
                            {
                                Toast.makeText(BookingDetailsEnter.this,"Bookng done successfully",Toast.LENGTH_SHORT).show();
                                Intent bookedintent = new Intent(BookingDetailsEnter.this,BookingDetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt(Constants.DETAIL_BOOKING,successbooking.getBookingId());
                                bundle.putString("BookingStatus","Success");
                                bookedintent.putExtras(bundle);
                                startActivity(bookedintent);
                            }



                    }

                }
                else
                {
                    Toast.makeText(BookingDetailsEnter.this,response.message(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Bookings> call, Throwable t) {
                if(dialog != null)
                {
                    dialog.dismiss();
                }
                Toast.makeText(BookingDetailsEnter.this,getResources().getString(R.string.response_failure_message),Toast.LENGTH_SHORT).show();
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

    public void addPayment(final BookingPayment bookings)
    {
        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                BookingPaymentApi bookingPaymentApi = Util.getClient().create(BookingPaymentApi.class);
                Call<BookingPayment> bookingPaymentApiresponse = bookingPaymentApi.postBookingPayment(bookings);
                bookingPaymentApiresponse.enqueue(new Callback<BookingPayment>() {
                    @Override
                    public void onResponse(Call<BookingPayment> call, Response<BookingPayment> response) {
                        if(response.code() == 200 || response.code() == 201)
                        {
                            if(response.body() != null)
                            {
                                Toast.makeText(BookingDetailsEnter.this,"Payment done successfully",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(BookingDetailsEnter.this,"Payment "+response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookingPayment> call, Throwable t) {
                        Toast.makeText(BookingDetailsEnter.this,"Payment "+t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
