package cmput301f17t12.quirks.Activities;

import android.os.Bundle;

import cmput301f17t12.quirks.R;

/**
 * Created by root on 11/29/17.
 */

public class FriendActivity extends SocialActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }





    @Override
    int getContentViewId() {
        return R.layout.activity_friends;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.action_friends;
    }
}
