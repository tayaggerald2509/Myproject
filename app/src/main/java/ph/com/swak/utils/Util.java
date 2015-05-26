package ph.com.swak.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ph.com.swak.R;
import ph.com.swak.callback.DialogCallback;
import ph.com.swak.receiver.NetworkReceiver;

/**
 * Created by SWAK-THREE on 3/25/2015.
 */
public class Util {

    private static DialogBuilder dialogBuilder;
    private static Date convertedDate = null;
    private static SimpleDateFormat formatter = null;
    private static Typeface tf;
    public static String FONT = "BernhGotLgt.ttf";
    private static DisplayImageOptions displayImageOptions;
    private static NetworkReceiver receiver;

    public static Typeface setTypeface(Context ctx, String font) {
        tf = Typeface.createFromAsset(ctx.getAssets(), font);
        return tf;
    }


    public static String convertDate(String date) {
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(Date.parse(date));
    }

    public static String convertTime(String time) {

        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");

        Date date = null;
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(date);
    }

    public static Date convertStringtoDate(String date) {
        try {
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static DateTime convertStringtoDatetime(String datetime) {
        try {
            formatter = new SimpleDateFormat("dd/MM/yyyy h:mm a");
            return new DateTime(formatter.parse(datetime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getCurrentDate(String format) {
        Calendar c = Calendar.getInstance();
        formatter = new SimpleDateFormat(format);

        return formatter.format(c.getTime());
    }

    public static void startActivity(Activity act, final Class<?> intent, Boolean paramBoolean) {
        Intent myIntent = new Intent(act, intent);
        act.startActivity(myIntent);
        if (paramBoolean) {
            act.finish();
        }
        act.overridePendingTransition(R.anim.appear_bottom_right_in, R.anim.appear_bottom_right_out);
    }

    public static ImageLoader getInstance() {
        return ImageLoader.getInstance();
    }


    public static DisplayImageOptions options() {
        displayImageOptions = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        return displayImageOptions;
    }

    public static ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {

        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    };

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static void showMessage(Context context, String title, String content, final DialogCallback callback) {

        dialogBuilder = DialogBuilder.getInstance((Activity) context);
        dialogBuilder.withTitle(title)
                .withTitleColor("#FFFFFF").withDividerColor("#11000000")
                .withEffect(Effectstype.BounceUp).withMessage(content)
                .withMessageColor("#000000").withDuration(700)
                .withButton1Text("OK").withButton2Text("CANCEL")
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        callback.positiveSelectionCallback(v);
                    }
                }).setButton2Click(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.exit(0);
                dialogBuilder.dismiss();
            }
        }).show();
    }

    public static int hoursBetween(DateTime startTime, DateTime endTime) {
        Period p = new Period(startTime, endTime);
        return p.getHours();
    }

    public static int minuteBetween(DateTime startTime, DateTime endTime) {
        Period p = new Period(startTime, endTime);
        return p.getMinutes();
    }

    public static long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    public static Calendar getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }

    public static Drawable addIcon(Context context, int drawable_id) {
        Drawable icon = context.getResources().getDrawable(drawable_id);
        icon.setBounds(new Rect(0, 0, icon.getIntrinsicWidth() / 2, icon.getIntrinsicHeight() / 2));
        return icon;
    }

    public static void showDialogErrorMessage(Context context, DialogCallback callback) {
        showMessage(context, "Connection error", "Unable to connect with the server. Check your \n internet connection and try again", callback);
    }


}
