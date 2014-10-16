package com.apisense.bee.backend.experiment;

import android.util.Log;
import com.apisense.bee.BeeApplication;
import com.apisense.bee.backend.AsyncTaskWithCallback;
import com.apisense.bee.backend.AsyncTasksCallbacks;
import fr.inria.bsense.APISENSE;
import fr.inria.bsense.appmodel.Experiment;

/**
 * Task to start gathering data for the given experiment
 *
 */
public class StartExperimentTask extends AsyncTaskWithCallback<Experiment, Void, Void> {
    private final String TAG = this.getClass().getSimpleName();

    public StartExperimentTask(AsyncTasksCallbacks listener) {
        super(listener);
    }

    @Override
    protected Void doInBackground(Experiment... params) {
        Experiment exp = params[0];
        try {
            APISENSE.apisMobileService().startExperiment(exp);
            // TODO: Make {start,stop}Experiment change the given variable to avoid changing it by ourserlf.
            exp.state = true;
            Log.i(TAG, "Experiment (" + exp.name + ") started");
            this.errcode = BeeApplication.ASYNC_SUCCESS;
        } catch (Exception e) {
            Log.w(TAG, "Experiment (" + exp.name + ") start failed: " + e.getMessage());
            this.errcode = BeeApplication.ASYNC_ERROR;
        }
        return null;
    }
}
