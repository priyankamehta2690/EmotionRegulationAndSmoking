//Copyright (c) Microsoft Corporation All rights reserved.
//
//MIT License:
//
//Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
//documentation files (the  "Software"), to deal in the Software without restriction, including without limitation
//the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
//to permit persons to whom the Software is furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all copies or substantial portions of
//the Software.
//
//THE SOFTWARE IS PROVIDED ""AS IS"", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
//TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
//THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
//CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//IN THE SOFTWARE.

package com.research.priyankamehta.smokingcessation;

import android.os.AsyncTask;

import com.firebase.client.Firebase;
import com.microsoft.band.BandClient;
import com.microsoft.band.BandException;
import com.microsoft.band.BandIOException;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.SampleRate;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by PriyankaMehta on 2/24/16.
 */
public class GetAccelerometerData extends AsyncTask<BandClient,Void,BandClient> {

    Firebase firebase=new Firebase("https://amber-heat-875.firebaseio.com");
    Firebase accelerometer=firebase.child("Accelerometer");
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

    private BandAccelerometerEventListener mAccelerometerEventListener = new BandAccelerometerEventListener() {
        @Override
        public void onBandAccelerometerChanged(final BandAccelerometerEvent event) {
            if (event != null) {
                accelerometer.child(timeStamp).push().setValue(" X: " + event.getAccelerationX() + " Y: "
                    + event.getAccelerationY() + " Z: " + event.getAccelerationZ());
            }
        }
    };

    @Override
    protected BandClient doInBackground(BandClient... params) {
        try{
            params[0].getSensorManager().registerAccelerometerEventListener(mAccelerometerEventListener, SampleRate.MS16);
        }
        catch (BandIOException e) {
            e.printStackTrace();
        } catch (BandException e) {
            e.printStackTrace();
        }
        return params[0];
    }
}
