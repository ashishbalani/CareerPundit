package inducesmile.com.CareerPundit.LoginRegister;

/**
 * Created by test on 11-03-2016.
 */
interface GetUserCallback {

    /**
     * Invoked when background task is completed
     */

    public abstract void done(User returnedUser);
}
